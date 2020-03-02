package top.dannystone.ddiwa.logAppendDB.sqlEngine.action;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 11:31 PM
 */
public interface SqlAction<T> {
    String sql = null;

    T execute();


}
