package top.dannystone.ddiwa.logAppendDB.transaction.lock.impl;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.LockService;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * <p>
 * - 如果事务T1持有对row 的共享锁，则来自某些不同事务T2 对锁的请求将按以下方式处理：
 * <p>
 * - T2事务线程请求S锁可以立即被授予。其结果是，无论是T1与T2 持有一个共享锁。
 * <p>
 * - T2事务线程请求一个排他锁，则不能立即授予。
 * <p>
 * - 如果一个事务T1在某行上拥有一个互斥锁，则不能立即批准某个不同事务T2对任一类型的锁的请求。相反，事务T2必须等待事务T1释放对此行的互斥锁。
 * <p>
 * 获取一个共享锁
 * 当前key 有无互斥锁，如果有则获取失败。
 * 获取一个互斥锁
 * 当前key 有无锁，如果有则获取失败。
 *
 * @author: daniel
 * @creed: keep it simple and stupid !
 * @Time: 2020/3/1 7:27 PM
 */
@Service
@Slf4j
public class LockServiceImpl implements LockService {

    /**
     * key format
     * Key Set<threadId>
     */
    private static final ConcurrentHashMap<String, Set> sLockRegister = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Set> xLockRegister = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Object> keyCache = new ConcurrentHashMap<>();

    /**
     * 为什么直接用String 参数来做lock key 呢？
     * 因为 "hello" 和 new String("hello") 其实是两个Object
     * 锁两个对象这样其实就不串行了
     *
     * @param key
     * @return
     */
    //todo 实现依然有bug，因为String.hashCode 可能冲突
    private synchronized Object getSameObjectWhenStringEquals(String key) {
        Object o = keyCache.get(key);
        if (o == null) {
            o = new Object();
            keyCache.put(key, o);
        }
        return o;
    }

    private synchronized Object getSameObjectWhenStringEqualsS(String key) {
        String skey = key + "_S";
        Object o = keyCache.get(skey);
        if (o == null) {
            o = new Object();
            keyCache.put(skey, o);
        }
        return o;
    }

    private synchronized Object getSameObjectWhenStringEqualsX(String key) {
        String xkey = key + "_X";
        Object o = keyCache.get(xkey);
        if (o == null) {
            o = new Object();
            keyCache.put(xkey, o);
        }
        return o;
    }

    /**
     * 获取一个共享锁
     * 当前key 有无互斥锁，如果有则获取失败。
     */
    @Override
    public boolean getSLock(String key) {
        Object keyLock = getSameObjectWhenStringEqualsS(key);

        synchronized (keyLock) {
            Set set = xLockRegister.get(key);
            if (set==null||set.size() == 0) {
                registerSLock(key);
                return true;
            }
        }

        return false;
    }

    private void registerSLock(String key) {

        synchronized (key) {
            Set sLocksInKey = sLockRegister.get(key);
            if (sLocksInKey == null) {
                sLocksInKey = Sets.newHashSet();
            }
            sLocksInKey.add(Thread.currentThread().getId());
            sLockRegister.put(key, sLocksInKey);
        }
    }

    /**
     *   获取一个互斥锁
     *   当前key 有无锁，如果有则获取失败。
     * @param key
     * @return
     */
    @Override
    public boolean getXLock(String key) {
        Object keyLock = getSameObjectWhenStringEqualsX(key);
        synchronized (keyLock) {
            Set sSet = sLockRegister.get(key);
            if (sSet==null||sSet.size() != 0) {
                return false;
            }

            Set xSet = xLockRegister.get(key);
            if (xSet==null||xSet.size() != 0) {
                return false;
            }

            if(xSet==null){
                xSet = Sets.newHashSet();
            }

            xSet.add(Thread.currentThread().getId());
            xLockRegister.put(key,xSet );

        }
        return false;
    }

    @Override
    public void releaseSLock(String key) {
        sLockRegister.get(key).remove(Thread.currentThread().getId());
    }

    @Override
    public void releaseXLock(String key) {
        xLockRegister.get(key).remove(Thread.currentThread().getId());
    }


}