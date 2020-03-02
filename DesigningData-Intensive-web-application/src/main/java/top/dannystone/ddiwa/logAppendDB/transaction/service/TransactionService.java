package top.dannystone.ddiwa.logAppendDB.transaction.service;

import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.SqlAction;
import top.dannystone.ddiwa.logAppendDB.transaction.domain.Transaction;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 11:15 PM
 */
public interface TransactionService {
    Transaction begin();

    void addAction(Transaction transaction, SqlAction sqlAction);

    boolean requireAllLocks(Transaction transaction);

    void commit(Transaction transaction);
}
