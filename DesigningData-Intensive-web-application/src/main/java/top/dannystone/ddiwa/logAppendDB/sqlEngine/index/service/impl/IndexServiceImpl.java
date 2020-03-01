package top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service.IndexService;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 4:08 PM
 */
@Service
@Slf4j
public class IndexServiceImpl implements IndexService {
    ConcurrentHashMap<String, Index> indexMap = new ConcurrentHashMap<>();

    //todo move to properties file
    public static final boolean useIndex = true;

    @Override
    public boolean useIndex() {
        return useIndex;
    }

    @Override
    public Index getIndex(String key) {
        return indexMap.get(key);

    }

    @Override
    public void putIndex(String key, Index idx) {

        indexMap.put(key, idx);

    }
}
