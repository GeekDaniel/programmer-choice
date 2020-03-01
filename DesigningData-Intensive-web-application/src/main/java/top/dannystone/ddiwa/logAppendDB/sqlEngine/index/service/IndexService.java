package top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service;

import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 4:07 PM
 */
public interface IndexService {

    boolean useIndex();

    Index getIndex(String key);

    void putIndex(String key, Index idx);

}
