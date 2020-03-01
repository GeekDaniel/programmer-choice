package top.dannystone.ddiwa.logAppendDB.store.service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 7:40 PM
 */
public interface RawDataFileService {

    //系统使用无法配置更改
    char ENTRYSPLIT = '\n';

    String getAbsoluteFileName();

    int countLines(String absoluteFilePath) ;
}
