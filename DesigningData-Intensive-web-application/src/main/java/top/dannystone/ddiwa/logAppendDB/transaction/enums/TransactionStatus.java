package top.dannystone.ddiwa.logAppendDB.transaction.enums;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 11:50 PM
 */
public enum TransactionStatus {
    BEGIN,
    COMMITTING,
    COMMITTED,
    ROLLBACKING,
    ROLLBACKED
}
