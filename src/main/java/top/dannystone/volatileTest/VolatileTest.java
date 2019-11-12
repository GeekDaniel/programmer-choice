package top.dannystone.volatileTest;

/**
 * Created with IntelliJ IDEA.
 * Description: test no volatile works fine ,maybe not as soon as possible with volatile modified
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2019/11/12 8:53 AM
 */
public class VolatileTest {
    static class ShutDown {
        boolean shutDowned=false;

        public void shutDownNow() {
            this.shutDowned = true;
        }

        public void doWork() {
            while (!shutDowned) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("dida");
            }

        }
    }

    public static void main(String[] args) {

        ShutDown shutDown = new ShutDown();
        Thread thread = new Thread(() -> {
            shutDown.doWork();
        });
        thread.start();

        System.out.println("main is not block after other thread start in main thread ");
        shutDown.shutDownNow();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
