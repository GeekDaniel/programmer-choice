package top.dannystone.ddiwa.logAppendDB.store.service;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 7:40 PM
 */
public interface StoreFileService {

    //todo move to properties file
    char ENTRYSPLIT = '\n';

    String getLastFileName();
}
