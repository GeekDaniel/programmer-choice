package top.dannystone.forTransfer;

import com.piaoniu.open.api.PNApi;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * Description:  comply time don't check down casting validation
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/11/3 7:21 PM
 */
public class Test {

    public static void main(String[] args){
        PNApi pnApi = new PNApi("","","");
        Map map=(Map)pnApi;
    }
}
