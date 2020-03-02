package top.dannystone.ddiwa.logAppendDB.transaction.lock;

import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.SLock;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.XLock;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 7:25 PM
 */
public interface LockService {

    SLock getSLock(String key, String transactionId);

    XLock getXLock(String key, String transactionId);

    void releaseSLock(String key, String transactionId);

    void releaseXLock(String key, String transactionId);
}

