package top.dannystone.validation.writeABinaryFile;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/23 12:21 AM
 */
public class BinaryReadWriteTest {
    String absoluteFilePath = null;

    @Before
    public void init() {
        absoluteFilePath = "/Users/daniel/Desktop/binary";
    }

    @Test
    public void testBinaryAppendWrite() throws IOException {
        String helloWorld = "hello world!";
        byte[] bytes = helloWorld.getBytes("UTF-8");
        BinaryReadWrite.binaryAppendWrite(absoluteFilePath, bytes);
        String s = BinaryReadWrite.readFileFromOffSet2Split(absoluteFilePath, 0, 127);
        Assert.assertTrue(s.contains(helloWorld));
    }

    @Test
    public void readFileFromOffSet2Split_OffSet() throws IOException {
        String s = BinaryReadWrite.readFileFromOffSet2Split(absoluteFilePath, 5, 127);
        Assert.assertEquals(" world!", s);
    }
    @Test
    public void readFileFromOffSet2Split_Split() throws IOException {
        //构造 "hello world!删除符号good night!"
        clearInfoForFile(absoluteFilePath);
        String helloWorld= "hello 中国!";
        byte[] helloWorldBytes= helloWorld.getBytes("UTF-8");
        BinaryReadWrite.binaryAppendWrite(absoluteFilePath,helloWorldBytes );

        String goodNight = "good night!";
        byte[] bytes = goodNight.getBytes("UTF-8");
        //加一个删除符号
        byte[] bytes1 = new byte[bytes.length+1];
        System.arraycopy(bytes, 0,bytes1 , 1, bytes.length);
        bytes1[0]=127;
        System.out.println("append Content : "+new String(bytes1));
        BinaryReadWrite.binaryAppendWrite(absoluteFilePath, bytes1);

        String s = BinaryReadWrite.readFileFromOffSet2Split(absoluteFilePath, 0, -1);
        System.out.println("full content : "+s);

        String s2 = BinaryReadWrite.readFileFromOffSet2Split(absoluteFilePath, 0, 127);
        Assert.assertEquals(s2,"hello 中国!" );

    }

    public static void clearInfoForFile(String fileName) {
        File file =new File(fileName);
        try {
            if(!file.exists()) {
                file.createNewFile();
            }
            FileWriter fileWriter =new FileWriter(file);
            fileWriter.write("");
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

