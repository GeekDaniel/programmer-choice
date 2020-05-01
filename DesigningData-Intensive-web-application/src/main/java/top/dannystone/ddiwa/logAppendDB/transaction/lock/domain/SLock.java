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

    public SLock(String key, String transactionId) {
        this.setTransactionId(transactionId);
        this.setKey(key);
    }

    @Override
    public int hashCode() {
        return (this.getKey() + "_" + this.getTransactionId()).hashCode();
    }
}
