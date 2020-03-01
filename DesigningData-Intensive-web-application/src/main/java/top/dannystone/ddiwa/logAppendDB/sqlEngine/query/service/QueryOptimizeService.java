package top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service;

import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 3:53 PM
 */
public interface QueryOptimizeService {

    /**
     * 是否走索引
     *
     * @return
     */
    boolean goIndex();

    Index getIndex(String key);

    void putIndex(String key, Index idx);

}
