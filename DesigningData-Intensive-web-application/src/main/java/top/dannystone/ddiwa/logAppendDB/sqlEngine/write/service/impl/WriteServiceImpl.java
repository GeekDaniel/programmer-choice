package top.dannystone.ddiwa.logAppendDB.sqlEngine.write.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service.IndexService;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.write.service.WriteService;
import top.dannystone.ddiwa.logAppendDB.store.service.RawDataIOService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 4:05 PM
 */
@Service
@Slf4j
public class WriteServiceImpl implements WriteService {
    @Autowired
    private RawDataIOService rawDataIOService;

    @Autowired
    private IndexService indexService;

    @Override
    public void write(String key, String value) {
        rawDataIOService.append(key, value);
        if (indexService.useIndex()) {
            Index index = rawDataIOService.appendThenReturnIndex(key, value);
            indexService.putIndex(key, index);
        }
    }
}
