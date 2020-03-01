package top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain;

import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 3:55 PM
 */
@Data
public class Index {
    private String key;
    private int offSet;
    private String rawDataFileName;
}
