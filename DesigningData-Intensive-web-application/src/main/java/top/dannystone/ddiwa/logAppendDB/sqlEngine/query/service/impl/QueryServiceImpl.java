package top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service.IndexService;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.QueryOptimizeService;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.QueryService;
import top.dannystone.ddiwa.logAppendDB.store.service.RawDataIOService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 4:35 PM
 */
@Slf4j
@Service
public class QueryServiceImpl implements QueryService {
    @Autowired
    private QueryOptimizeService queryOptimizeService;

    @Autowired
    private IndexService indexService;

    @Autowired
    private RawDataIOService rawDataIOService;

    @Override
    public String get(String key) {
        if (queryOptimizeService.goIndex()) {
            Index index = indexService.getIndex(key);
            String rawDataFileName = index.getRawDataFileName();
            int offSet = index.getOffSet();
            return rawDataIOService.getValueArOffsetInFile(rawDataFileName, offSet);
        }
        return rawDataIOService.get(key);
    }
}
