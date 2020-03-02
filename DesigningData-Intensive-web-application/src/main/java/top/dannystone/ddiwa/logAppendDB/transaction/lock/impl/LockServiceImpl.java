package top.dannystone.ddiwa.logAppendDB.transaction.lock.impl;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.LockService;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.enums.LockType;

import java.util.Optional;
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
     * Key Set<transactionId>
     */
    private static final ConcurrentHashMap<String, Set<String>> sLockRegister = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, Set<String>> xLockRegister = new ConcurrentHashMap<>();

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
        String skey = key + "_" + LockType.S;
        return getSameObjectWhenStringEquals(skey);
    }

    private synchronized Object getSameObjectWhenStringEqualsX(String key) {
        String xkey = key + "_" + LockType.X;
        return getSameObjectWhenStringEquals(xkey);

    }

    /**
     * 获取一个共享锁
     * 当前key 有无互斥锁，如果有则获取失败。
     */
    @Override
    public String getSLock(String key, String transactionId) {
        //如果本事务已经有Slock 则返回
        Set<String> sLocks = sLockRegister.get(key);
        if (sLocks!=null&&sLocks.contains(transactionId)) {
            return transactionId;
        }

        Object keyLock = getSameObjectWhenStringEqualsS(key);
        synchronized (keyLock) {
            Set set = xLockRegister.get(key);
            //如果没有互斥锁||或者互斥锁是本事务申请的
            if (set == null || set.size() == 0 || set.contains(transactionId)) {
                registerSLock(key, transactionId);
                return transactionId;
            }
        }

        return "";
    }

    private void registerSLock(String key, String transactionId) {

        synchronized (key) {
            Set sLocksInKey = sLockRegister.get(key);
            if (sLocksInKey == null) {
                sLocksInKey = Sets.newHashSet();
            }
            sLocksInKey.add(transactionId);
            sLockRegister.put(key, sLocksInKey);
        }
    }

    /**
     * 获取一个互斥锁
     * 当前key 有无锁，如果有则获取失败。
     *
     * @param key
     * @return
     */
    @Override
    public String getXLock(String key, String transactionId) {

        //如果本事务已经有xlock 则返回
        Set xSet = xLockRegister.get(key);
        if (xSet!=null&&xSet.contains(transactionId)) {
            return transactionId;
        }

        Object keyLock = getSameObjectWhenStringEqualsX(key);
        synchronized (keyLock) {
            Set<String> sSet = sLockRegister.get(key);

            //如果存在共享读锁,并且非本事务独占，那么申请不成功
            if (sSet != null && sSet.size() != 0) {
                Optional<String> any = sSet.stream()
                        .filter(e -> !e.equals(transactionId))
                        .findAny();
                if (any.isPresent()) {
                    return "";
                }
            }

            //如果存在互斥锁，并且非本事务独占，那么申请不成功
            if (xSet != null && xSet.size() != 0) {
                Optional any = xSet.stream()
                        .filter(e -> !e.equals(transactionId))
                        .findAny();
                if (any.isPresent()) {
                    return "";
                }
            }
            registerXLock(key, xSet, transactionId);
            return transactionId;
        }
    }

    private void registerXLock(String key, Set xSet, String transactionId) {
        if (xSet == null) {
            xSet = Sets.newHashSet();
        }

        xSet.add(transactionId);
        xLockRegister.put(key, xSet);
    }

    @Override
    public void releaseSLock(String key, String transactionId) {
        Set<String> lockHolders = sLockRegister.get(key);
        if (!lockHolders.contains(transactionId)) {
            return;
        }
        lockHolders.remove(transactionId);
    }

    @Override
    public void releaseXLock(String key, String transactionId) {
        Set<String> lockHolders = xLockRegister.get(key);
        if (!lockHolders.contains(transactionId)) {
            return;
        }
        lockHolders.remove(transactionId);
    }


}
