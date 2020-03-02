package top.dannystone.ddiwa.logAppendDB.transaction.lock;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 7:25 PM
 */
public interface LockService {

    String getSLock(String key,String transactionId);

    String getXLock(String key,String transactionId);

    void releaseSLock(String key, String transactionId);

    void releaseXLock(String key, String transactionId);
}

