package top.dannystone.ddiwa.logAppendDB.transaction.service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 11:15 PM
 */
public interface TransactionService {
    void begin();

    void commit();
}
