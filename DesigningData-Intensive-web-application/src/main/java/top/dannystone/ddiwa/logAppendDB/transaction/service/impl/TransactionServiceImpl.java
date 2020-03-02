package top.dannystone.ddiwa.logAppendDB.transaction.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.SqlAction;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.impl.InsertOrUpdateAction;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.impl.SelectAction;
import top.dannystone.ddiwa.logAppendDB.transaction.domain.Transaction;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.LockService;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.Lock;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.SLock;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.XLock;
import top.dannystone.ddiwa.logAppendDB.transaction.service.TransactionService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 11:18 PM
 */
@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private LockService lockService;

    @Override
    public Transaction begin() {
        Transaction transaction = new Transaction();
        String transactionId = Thread.currentThread().getId() + "_" + System.currentTimeMillis();
        transaction.setTransactionId(transactionId);
        return transaction;
    }

    @Override
    public void addAction(Transaction transaction, SqlAction sqlAction) {
        List<SqlAction> actions = transaction.getActions();
        if (actions == null) {
            actions = Lists.newArrayList();
        }
        actions.add(sqlAction);
        transaction.setActions(actions);
    }

    @Override
    public boolean requireAllLocks(Transaction transaction) {
        List<Lock> locks = Lists.newArrayList();

        List<SqlAction> actions = transaction.getActions();
        for (SqlAction sqlAction : actions) {
            String transactionId = transaction.getTransactionId();

            if (sqlAction instanceof SelectAction) {
                SelectAction selectAction = (SelectAction) sqlAction;
                Lock lock = lockService.getSLock(selectAction.getKey(), transactionId);
                if (lock != null) {
                    locks.add(lock);
                } else {
                    break;
                }
            }

            if (sqlAction instanceof InsertOrUpdateAction) {
                InsertOrUpdateAction insertOrUpdateAction = (InsertOrUpdateAction) sqlAction;
                Lock lock = lockService.getXLock(insertOrUpdateAction.getKey(), transactionId);
                if (lock != null) {
                    locks.add(lock);
                } else {
                    break;
                }
            }

        }

        if (locks.size() != actions.size()) {
            releaseAcquiredLocks(locks);
            return false;
        } else {
            transaction.setLocks(locks);
            return true;
        }
    }

    private void releaseAcquiredLocks(List<Lock> locks) {
        locks.forEach(e -> {
            if (e instanceof SLock) {
                lockService.releaseSLock(e.getKey(), e.getTransactionId());
            }
            if (e instanceof XLock) {
                lockService.releaseXLock(e.getKey(), e.getTransactionId());
            }
        });
    }

    @Override
    public void commit(Transaction transaction) {
        for (SqlAction sqlAction : transaction.getActions()) {
            sqlAction.execute();
        }

    }
}
