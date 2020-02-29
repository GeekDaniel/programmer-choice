package top.dannystone.ddiwa.logAppendDB;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import top.dannystone.ddiwa.logAppendDB.store.service.RawDataIOService;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/2/29 11:25 PM
 */
public class DbApplication {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext annotationConfigApplicationContext = new AnnotationConfigApplicationContext(ServiceConfig.class);
        RawDataIOService rawService = annotationConfigApplicationContext.getBean(RawDataIOService.class);
        rawService.append("hello","world" );
        rawService.append("hello","world2" );
        String hello = rawService.get("hello");
        Assert.isTrue("world2".equals(hello),"error" );

    }

}
