package top.dannystone.ddiwa.logAppendDB.store.service;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.utils.StringUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 7:41 PM
 */
@Service
@Slf4j
public class RawDataFileServiceImpl implements RawDataFileService {


    private static int KEY_THRESHOLD_PERFILE = 1024;

    /**
     * rawData 文件格式为timeStamp.raw
     */
    private static final String SUFFIX = ".raw";

    private long currentFileTimeStamp = 0L;

    private int currentFileKeyCount = 0;

    private final String fileDirectory = "/Users/daniel/data/";

    @Override
    public String getAbsoluteFileName() {
        //如果重新启动服务器 ，需要重新初始化
        if (needInit()) {
            init();
        }

        // key size超限了，新建文件
        if (meetMaxKeySize()) {
            resetFileTimeStampAndKeyCount();
        }

        return fileDirectory + currentFileTimeStamp + SUFFIX;
    }

    private void resetFileTimeStampAndKeyCount() {
        currentFileKeyCount = 0;
        currentFileTimeStamp = System.currentTimeMillis();
    }


    private boolean meetMaxKeySize() {
        return currentFileKeyCount >= KEY_THRESHOLD_PERFILE;
    }

    private boolean needInit() {
        if (currentFileTimeStamp == 0L) {
            return true;
        }
        return false;
    }

    //重启数据库需要初始化文件状态
    private void init() {
        String lastFileName = getLastFileNameFromDisk();
        if (StringUtils.isEmpty(lastFileName)) {
            currentFileTimeStamp = System.currentTimeMillis();
            currentFileKeyCount = 0;
        } else {
            currentFileTimeStamp = Long.parseLong(lastFileName.substring(0, lastFileName.indexOf(SUFFIX)));
            currentFileKeyCount = countLines(getAbsoluteFileName(lastFileName));
        }

    }

    private String getAbsoluteFileName(String lastFileName) {
        return fileDirectory + lastFileName;
    }

    /**
     * 返回时间戳最大的文件名
     *
     * @return
     */
    private String getLastFileNameFromDisk() {
        File file = new File(fileDirectory);
        return Lists.newArrayList(file.list()).stream()
                .filter(e -> e.contains(SUFFIX))
                .max(String::compareTo)
                .orElse("");
    }

    @Override
    public int countLines(String absoluteFilePath) {
        int count = 0;
        File file = new File(absoluteFilePath);
        if (!file.exists()) {
            throw new RuntimeException(absoluteFilePath + "not found!!!");
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
        } catch (Exception e) {
            log.error("readLine failed", e);
            throw new RuntimeException("readLine failed");
        }
    }

}
