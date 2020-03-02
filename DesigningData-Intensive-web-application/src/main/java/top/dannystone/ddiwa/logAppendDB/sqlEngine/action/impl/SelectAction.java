package top.dannystone.ddiwa.logAppendDB.sqlEngine.action.impl;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.SqlAction;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.QueryService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/2 12:41 AM
 */
@Component
@Slf4j
public class SelectAction implements SqlAction<String> {
    @Getter
    @Setter
    private String key;

    @Autowired
    private QueryService queryService;

    @Override
    public String execute() {
        return queryService.get(key);
    }
}
