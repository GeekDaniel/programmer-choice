package top.dannystone.ddiwa.logAppendDB.store;

import com.google.common.collect.Lists;

import java.io.*;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/22 10:36 PM
 */
public class BinaryReadWrite {

    public static void binaryAppendWrite(String absoluteFilePath, byte[] bytes) throws IOException {
        File file = new File(absoluteFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(bytes);
        }
    }

    /**
     * @param absoluteFilePath
     * @param offSet
     * @param split            一个byte 作为分隔符 e.g. 127 删除符号就很好 应该输入端输入不了,不会冲突
     * @return
     * @throws IOException
     */
//    public static String readFileFromOffSet2Split(String absoluteFilePath, int offSet, int split) throws IOException {
//        File file = new File(absoluteFilePath);
//        if (!file.exists()) {
//            return "";
//        }
//
//        try (FileInputStream fis = new FileInputStream(file)) {
//
//            //偏移
//            byte[] offSetBytes = new byte[offSet];
//            fis.get(offSetBytes);
//
//            //128 越界 byte ，所以安全
//            int get = 128;
//
//            List<Byte> bytes = Lists.newArrayList();
//            while (get != -1 && get != split) {
//                get = fis.get();
//
//                if (get != -1 && get != split) {
//                    bytes.add(new Byte((byte) get));
//                }
//            }
//
//            byte[] bytes2 = new byte[0];
//            if (bytes.size() > 0) {
//                bytes2 = new byte[bytes.size()];
//                for (int i = 0; i < bytes.size(); i++) {
//                    bytes2[i] = bytes.get(i);
//                }
//            }
//
//            //todo charset to config file
//            return new String(bytes2, "UTF-8");
//        }
//
//    }
    public static int countLines(String absoluteFilePath) throws IOException {
        int count = 0;
        File file = new File(absoluteFilePath);
        if (!file.exists()) {
            throw new FileNotFoundException(absoluteFilePath + "not found!!!");
        }

        try (BufferedReader bf = new BufferedReader(new FileReader(file));) {
            String line = null;
            do {

                line = bf.readLine();
                if (line != null) {
                    count++;
                }
            } while (line != null);

            return count;
        }
    }
}
