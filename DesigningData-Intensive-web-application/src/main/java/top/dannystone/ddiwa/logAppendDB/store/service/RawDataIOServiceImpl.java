package top.dannystone.ddiwa.logAppendDB.store.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.input.ReversedLinesFileReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/25 1:35 AM
 */
@Service
@Slf4j
public class RawDataIOServiceImpl implements RawDataIOService {

    public static final String CHARSET = "utf-8";

    @Autowired
    private RawDataFileService rawDataFileService;

    private static final char kvSplit = 127;

    private Object writeLock = new Object();

    @Override
    public void append(String key, String data) {
        //找到文件
        String fileName = rawDataFileService.getAbsoluteFileName();

        //todo 这里jvm锁不支持 scale out
        //获取文件写入锁
        //kv 格式封装写入
        //释放锁
        synchronized (writeLock) {
            try {
                byte[] bytes = formatKV(key, data).getBytes(CHARSET);
                byte[] bytesWithSplit = new byte[bytes.length + 1];
                System.arraycopy(bytes, 0, bytesWithSplit, 0, bytes.length);
                bytesWithSplit[bytes.length] = RawDataFileService.ENTRYSPLIT;
                binaryAppendWrite(fileName, bytesWithSplit);
            } catch (IOException e) {
                log.error("binaryAppendWrite fail", e);
                throw new RuntimeException("binaryAppendWrite fail");
            }
        }
    }

    private String formatKV(String key, String data) {
        return key + kvSplit + data;
    }

    @Override
    public String get(String key) {
        //todo 多文件不一定是最新文件
        String fileName = rawDataFileService.getAbsoluteFileName();
        try (ReversedLinesFileReader fr = new ReversedLinesFileReader(new File(fileName));) {
            String lineKey;
            String lineValue;
            String line;
            do {
                line = fr.readLine();
                String[] split = line.split(kvSplit + "");
                lineKey = split[0];
                lineValue = split[1];
            } while (!lineKey.equals(key) && line != null);

            if (lineKey.equals(key)) {
                return lineValue;
            }
            return null;
        } catch (Exception e) {
            log.error("get file : " + fileName + " error", e);
            throw new RuntimeException(e.getMessage());
        }

    }

    private void binaryAppendWrite(String absoluteFilePath, byte[] bytes) throws IOException {
        File file = new File(absoluteFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        try (FileOutputStream fos = new FileOutputStream(file, true)) {
            fos.write(bytes);
        }
    }

}
