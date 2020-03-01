package top.dannystone.validation.stringObjectEquals;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 8:29 PM
 */
public class Test {

    public static void main(String[] args) {
        String hello = "hello";
        String hello2 = "hello";
        String hello3 = new String("hello");
        if (hello == hello2) {
            System.out.println("hello==hello2");
        }
        if (hello3 != hello2) {
            System.out.println("hello3!=hello2");
        }

        testHelloEquals(hello,hello2 ,hello3 );

    }

    private static void testHelloEquals(String hello, String hello2, String hello3) {
        if (hello == hello2) {
            System.out.println("hello==hello2");
        }
        if (hello3 != hello2) {
            System.out.println("hello3!=hello2");
        }
    }


}
