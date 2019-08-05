package top.dannystone.test;

import com.alibaba.fastjson.JSONObject;

import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/8/5 9:45 AM
 */
public class GetSession {


    @Data
    @AllArgsConstructor
    public static class Tick {
        int State;
        String TimeStamp;
    }

    @Data
    @AllArgsConstructor
    public static class Session {
        String start;
        String end;
    }

    public static List<Tick> events = Lists.newArrayList(
            new Tick(1, "2019-01-01 06:00"),
            new Tick(1, "2019-01-01 07:00"),
            new Tick(0, "2019-01-01 10:00"),
            new Tick(1, "2019-01-01 11:00"),
            new Tick(0, "2019-01-01 12:00"),
            new Tick(1, "2019-01-01 13:00")
    );


    public static void main(String[] args) {
        events = events.stream().sorted(Comparator.comparing(Tick::getTimeStamp)).collect(Collectors.toList());

        //去重
        int lastStatus = -1;
        for (Tick event : events) {
            if (lastStatus == -1) {
                lastStatus = event.getState();
            }
            if (lastStatus == event.getState()) {
                event.setState(-2);
            }
        }

        List<Tick> valids = Lists.newArrayList();
        for (Tick event : events) {
            if (event.getState() != -2) {
                valids.add(event);
            }
        }

        //组队
        int startCount = 0;

        int startCountIndex = 0;
        String startTime = "";
        List<Session> sessions = Lists.newArrayList();
        for (Tick event : valids) {
            if (startCount == 0 && event.getState() == 0) {
                startCount = 1;
            }
            if (startCount == 1) {
                startCountIndex++;
                if (startCountIndex % 2 == 0) {
                    Session session = new Session(startTime, event.getTimeStamp());
                    sessions.add(session);
                } else {
                    startTime = event.getTimeStamp();
                }
            }

        }

        sessions.forEach(e -> {
            System.out.println(JSONObject.toJSONString(e));
        });
    }

}
