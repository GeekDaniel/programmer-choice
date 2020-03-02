package top.dannystone.ddiwa.logAppendDB.transaction.lock.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/3 12:07 AM
 */
@Data
@AllArgsConstructor
public class Lock {
    public Lock(){

    }
    private String transactionId;
    private String key;
}
