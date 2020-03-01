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

    boolean getSLock(String key);

    boolean getXLock(String key);

    void releaseSLock(String key);

    void releaseXLock(String key);
}

