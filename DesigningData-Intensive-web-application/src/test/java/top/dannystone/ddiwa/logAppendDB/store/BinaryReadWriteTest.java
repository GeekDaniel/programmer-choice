package top.dannystone.ddiwa.logAppendDB.store;

import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 9:28 PM
 */
public class BinaryReadWriteTest {

    static final String absoluteFilePath = "/Users/daniel/Desktop/binary";
    @Test
    public void countSplits() throws IOException {
        int count = BinaryReadWrite.countLines(absoluteFilePath);
        Assert.assertEquals(1,count );
    }
}
