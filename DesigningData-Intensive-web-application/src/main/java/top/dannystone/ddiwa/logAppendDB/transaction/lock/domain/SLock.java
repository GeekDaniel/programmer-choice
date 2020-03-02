package top.dannystone.ddiwa.logAppendDB.transaction.lock.domain;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/2 11:59 PM
 */
public class SLock extends Lock {

    public SLock(String transactionId, String key) {
        this.setTransactionId(transactionId);
        this.setKey(key);
    }
}
