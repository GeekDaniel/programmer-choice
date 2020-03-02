package top.dannystone.ddiwa.logAppendDB.sqlEngine.action;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/2 12:44 AM
 */
public interface WriteAction<T> extends SqlAction<T> {

    boolean commit();

    boolean rollBack();
}
