package top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service.IndexService;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.QueryOptimizeService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 3:56 PM
 */
@Service
@Slf4j
public class QueryOptimizeServiceImpl implements QueryOptimizeService {

    @Autowired
    private IndexService indexService;

    @Override

    public boolean goIndex() {
        return indexService.useIndex();
    }

    @Override
    public Index getIndex(String key) {
        return indexService.getIndex(key);
    }

    @Override
    public void putIndex(String key, Index idx) {
        indexService.putIndex(key, idx);

    }


}
