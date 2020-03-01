package top.dannystone.ddiwa.logAppendDB.store.service;

import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/25 1:32 AM
 */
public interface RawDataIOService {

    void append(String key, String data);

    Index appendThenReturnIndex(String key, String data);

    String get(String key);

    String getValueArOffsetInFile(String absoluteFileName, int offSet);
}
