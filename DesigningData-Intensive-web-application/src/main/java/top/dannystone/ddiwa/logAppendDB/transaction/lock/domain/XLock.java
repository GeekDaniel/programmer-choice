package top.dannystone.ddiwa.logAppendDB.transaction.lock.domain;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/2 11:59 PM
 */
public class XLock extends Lock {
    public XLock(String transactionId, String key) {
        this.setKey(key);
        this.setTransactionId(transactionId);
    }
}
