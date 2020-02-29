package top.dannystone.ddiwa.logAppendDB.store.service;

import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.store.BinaryReadWrite;
import top.dannystone.ddiwa.logAppendDB.utils.StringUtils;

import java.io.File;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 7:41 PM
 */
@Service
public class StoreFileServiceImpl implements StoreFileService {


    private static int KEY_THRESHOLD_PERFILE = 1024;

    /**
     * rawData 文件格式为timeStamp.raw
     */
    private static final String SUFFIX = ".raw";

    private long currentFileTimeStamp = 0L;

    private int currentFileKeyCount = 0;

    private final String fileDirectory = "/Users/daniel/data/";

    @Override
    public String getLastFileName() {
        //如果重新启动服务器 ，需要重新初始化
        if (needInit()) {
            init();
        }

        // key size超限了，新建文件
        if (meetMaxKeySize()) {
            resetFileTimeStampAndKeyCount();
        }

        return fileDirectory+currentFileTimeStamp + SUFFIX;
    }

    private void resetFileTimeStampAndKeyCount() {
        currentFileKeyCount = 0;
        currentFileTimeStamp = System.currentTimeMillis();
    }


    private boolean meetMaxKeySize() {
        return currentFileKeyCount >= KEY_THRESHOLD_PERFILE;
    }

    boolean needInit() {
        if (currentFileTimeStamp == 0L) {
            return true;
        }
        return false;
    }

    private void init() {
        String lastFileName = getLastFileNameFromDisk();
        if (StringUtils.isEmpty(lastFileName)) {
            currentFileTimeStamp = System.currentTimeMillis();
            currentFileKeyCount = 0;
        } else {
            currentFileTimeStamp = Long.parseLong(lastFileName.substring(0, lastFileName.indexOf(SUFFIX)));
            try {
                currentFileKeyCount = BinaryReadWrite.countLines(fileDirectory + lastFileName);
            } catch (IOException e) {
                throw new RuntimeException("lastFileName not found!!!");
            }
        }

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

}
