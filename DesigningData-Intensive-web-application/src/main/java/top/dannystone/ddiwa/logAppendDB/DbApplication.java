package top.dannystone.ddiwa.logAppendDB;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.Assert;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.domain.Index;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.index.service.IndexService;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.query.service.QueryService;
import top.dannystone.ddiwa.logAppendDB.sqlEngine.write.service.WriteService;

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
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ServiceConfig.class);

        WriteService writeService = context.getBean(WriteService.class);
        writeService.write("love story", "taylor swift");

        IndexService indexService= context.getBean(IndexService.class);
        Index love_story = indexService.getIndex("love story");
        System.out.println(JSONObject.toJSONString(love_story));

        QueryService queryService = context.getBean(QueryService.class);
        String love_story1 = queryService.get("love story");
        System.out.println(JSONObject.toJSONString(love_story1));

    }

}
