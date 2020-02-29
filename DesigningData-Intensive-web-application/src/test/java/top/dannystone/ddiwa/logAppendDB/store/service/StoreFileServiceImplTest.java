package top.dannystone.ddiwa.logAppendDB.store.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 9:47 PM
 */
public class StoreFileServiceImplTest {
    StoreFileServiceImpl storeFileService = null;

    @Before
    public void init() {
        storeFileService = new StoreFileServiceImpl();
    }


    @Test
    public void getFileName() {
        String fileName = storeFileService.getLastFileName();
        Assert.assertEquals("1582984369999.raw",fileName );
    }
}
