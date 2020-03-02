package top.dannystone.ddiwa.logAppendDB.transaction.lock.impl;

import org.junit.Before;
import org.junit.Test;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.LockService;
import top.dannystone.ddiwa.logAppendDB.utils.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 9:32 PM
 */
public class LockServiceImplTest {
    LockService lockService = null;

    @Before
    public void init() {
        lockService = new LockServiceImpl();
    }

    /**
     * 尝试获取共享锁。
     * case 1 : 如果有其他的共享锁，可成功获得
     * case 2 : 如果有其他的互斥锁，获取失败
     */
    @Test
    public void getSLockCase1() {

        Thread thread = new Thread(() -> {
            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String sLock = lockService.getSLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get s lock ,success : " + sLock);
            ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(transactionId)) {
                lockService.releaseSLock("hello", transactionId);
            }
            System.out.println("releaseSLock finished");

        });
        thread.start();



        Thread thread2 = new Thread(() -> {
            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String sLock = lockService.getSLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get s lock ,success : " + sLock);
            ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(transactionId)) {
                lockService.releaseSLock("hello", transactionId);
            }
            System.out.println("releaseSLock finished");

        });
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSLockCase2() {
        Thread thread = new Thread(() -> {

            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String hello = lockService.getXLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get x lock ,success : " + hello);
            ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!StringUtils.isEmpty(hello)){
                lockService.releaseXLock("hello",transactionId);
                System.out.println("releaseXLock finished");
            }

        });
        thread.start();


        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String sLock = lockService.getSLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get s lock ,success : " + sLock);

            if (!StringUtils.isEmpty(sLock)) {
                lockService.releaseSLock("hello", sLock);
                System.out.println("releaseSLock finished");
            }

        });
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * case1 : 如果没有任何锁，获得成功。
     * case2 ：如果有共享锁，获得失败。
     * case3 : 如果有互斥锁，获得失败。
     */
    @Test
    public void getXLockCase1() {
        //done before

    }

    @Test
    public void getXLockCase2() {
        Thread thread = new Thread(() -> {
            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String sLock = lockService.getSLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get s lock ,success : " + sLock);
            ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!StringUtils.isEmpty(sLock)){
                lockService.releaseSLock("hello",transactionId);
                System.out.println("releasexLock finished");
            }

        });
        thread.start();

        Thread thread2 = new Thread(() -> {
            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String hello = lockService.getXLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get x lock ,success : " + hello);
            ;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(hello)) {
                lockService.releaseXLock("hello",hello);
                System.out.println("releaseSLock finished");
            }

        });
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void getXLockCase3() {
        Thread thread = new Thread(() -> {
            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String xLock = lockService.getXLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get x lock ,success : " + xLock);
            ;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(!StringUtils.isEmpty(xLock)){
                lockService.releaseXLock("hello",xLock);
                System.out.println("releaseXLock finished");
            }

        });
        thread.start();

        Thread thread2 = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String transactionId = Thread.currentThread().getId()+""+System.currentTimeMillis();
            String xLock = lockService.getXLock("hello", transactionId);
            System.out.println("thread : " + Thread.currentThread().getId() + " get x lock ,success : " + xLock);
            ;

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (!StringUtils.isEmpty(xLock)) {
                lockService.releaseXLock("hello",xLock);
                System.out.println("releaseSLock finished");
            }

        });
        thread2.start();

        try {
            thread.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void releaseSLock() {
    }

    @Test
    public void releaseXLock() {
    }
}
