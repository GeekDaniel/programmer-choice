package top.dannystone.ddiwa.logAppendDB.sqlEngine.action.impl;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.WriteAction;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.write.service.WriteService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/2 1:03 AM
 */
@Component
@Slf4j
public class InsertOrUpdateAction implements WriteAction<Integer> {
    @Getter
    @Setter
    String key;

    @Getter
    @Setter
    String value;
    @Autowired
    private WriteService writeService;

    @Override
    public boolean commit() {
        return false;
    }

    @Override
    public boolean rollBack() {
        return false;
    }

    @Override
    public Integer execute() {
        writeService.write(key, value);
        return 1;
    }
}
