package top.dannystone.ddiwa.logAppendDB.store.service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/25 1:32 AM
 */
public interface RowDataWriteService {

    void append(String key, String data);

    void get(String key);
}
