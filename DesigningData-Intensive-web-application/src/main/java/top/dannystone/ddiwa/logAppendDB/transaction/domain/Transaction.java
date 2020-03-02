package top.dannystone.ddiwa.logAppendDB.transaction.domain;

import lombok.Data;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.action.SqlAction;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 11:31 PM
 */
@Data
public class Transaction {
    private int threadId;
    private List<SqlAction> actions;
    private int status;

}
