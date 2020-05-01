package top.dannystone.ddiwa.logAppendDB.transaction.lock.impl;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.LockService;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.SLock;
import top.dannystone.ddiwa.logAppendDB.transaction.lock.domain.XLock;
import top.dannystone.ddiwa.logAppendDB.utils.StringUtils;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
 * <p>
 * 实现细节：
 * <b>不支持一个事务跨多个线程，以下场景都没有加锁来约束多线程对同一事务操作的场景。</b>
 * 不考虑同一事务在多线程里对同一资源key共享锁的可见性问题，默认只会存在一个线程里。
 * 不考虑同一事务在多线程里对同一资源key互斥锁的可见性问题，默认只会存在一个线程里。
 * 考虑不同事务在多线程里对同一资源key共享锁的可见性问题
 * 考虑不同事务在多线程里对同一资源key互斥锁的可见性问题
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

    /**
     * key 是 资源key value 是占有共享锁的事务
     */
    private static final ConcurrentHashMap<String, Set<SLock>> sLockRegister = new ConcurrentHashMap<>();

    /**
     * key 是 资源key value 是占有共享锁的事务
     */
    private static final ConcurrentHashMap<String, XLock> xLockRegister = new ConcurrentHashMap<>();

    /**
     * @desc 可重入地获取一个共享锁
     * 1. 当前事务对此key 有共享锁 返回成功
     * 2. ...
     */
    @Override
    public SLock getSLock(String key, String transactionId) {
        //如果本事务已经有SLock 则返回
        if (sReenterCheck(key, transactionId)) {
            return new SLock(key, transactionId);
        }

        boolean success = false;
        //如果可以获取共享锁，那么尝试注册共享锁
        if (canHoldSLock(key, transactionId)) {
            success = tryRegisterSLock(key, transactionId);
        }

        if (success) {
            return new SLock(key, transactionId);
        } else {
            return null;
        }
    }


    //如果本事务已经有SLock 则返回
    private boolean sReenterCheck(String key, String transactionId) {
        Set<SLock> sLocks = sLockRegister.get(key);
        if (sLocks != null) {
            List<String> lockHolders = sLocks.stream().map(SLock::getTransactionId).collect(Collectors.toList());
            if (lockHolders.contains(transactionId)) {
                return true;
            }
        }
        return false;
    }

    //这里会做线程安全的校验和注册
    private boolean tryRegisterSLock(String key, String transactionId) {
        genLock(key);

        Set<SLock> sLocks = sLockRegister.get(key);
        XLock xLock = xLockRegister.get(key);
        synchronized (sLocks) {
            synchronized (xLock) {
                //防止其他线程在上次检查后申请了互斥锁，double check 一下
                if (canHoldSLock(key, transactionId)) {
                    doHoldSLock(key, transactionId);
                    return true;
                } else {
                    return false;
                }
            }
        }

    }

    private void genLock(String key) {
        Set<SLock> sLocks = sLockRegister.get(key);
        if (sLocks == null) {
            synchronized (sLockRegister) {
                Set<SLock> sLocks1 = sLockRegister.get(key);
                //do double check
                if (sLocks1 == null) {
                    sLockRegister.put(key, Sets.newHashSet());
                }
            }
        }

        XLock xLock = xLockRegister.get(key);
        if (xLock == null) {
            synchronized (xLockRegister) {
                XLock xLock1 = xLockRegister.get(key);
                //do double check
                if (xLock1 == null) {
                    xLockRegister.put(key, new XLock(key, null));
                }
            }
        }
    }

    /**
     * 内部方法，此处不再考虑多线程一致性检查。所以调用方必须做好并发下的先锁后check
     *
     * @param key
     * @param transactionId
     */
    private void doHoldSLock(String key, String transactionId) {
        Set<SLock> sLocks = sLockRegister.get(key);
        SLock sLock = new SLock(key, transactionId);
        sLocks.add(sLock);
    }

    //如果当前没有互斥锁或者是本事务独占的，那么就可以获取共享锁
    private boolean canHoldSLock(String key, String transactionId) {
        XLock xLock = xLockRegister.get(key);
        return xLock == null || StringUtils.isEmpty(xLock.getTransactionId()) || xLock.getTransactionId().equals(transactionId);
    }

    //因为不是原子的所以结果不准确，用来过滤 "不能获取共享锁" 的场景，正真获取时还需要 先加锁 再double check
    //如果当前没有互斥锁或者是本事务独占的，并且当前没有共享锁或者是本事务独占的,那么就可以获取互斥锁
    private boolean canHoldXLock(String key, String transactionId) {
        XLock xLock = xLockRegister.get(key);
        boolean eitherNoOrSelfOccupyXLock = xLock == null || StringUtils.isEmpty(xLock.getTransactionId()) || xLock.getTransactionId().equals(transactionId);

        Set<SLock> sLocks = sLockRegister.get(key);

        List<String> transactionIds = sLocks.stream().map(SLock::getTransactionId).collect(Collectors.toList());
        boolean eitherNoOrSelfOccupySLock = sLocks == null || CollectionUtils.isEmpty(sLocks)
                || transactionIds.size() == 1 && transactionIds.get(0).equals(transactionId);

        return eitherNoOrSelfOccupyXLock && eitherNoOrSelfOccupySLock;
    }


    /**
     * @desc 可重入地获取一个互斥锁
     */
    @Override
    public XLock getXLock(String key, String transactionId) {
        //如果本事务已经有xlock 则返回
        boolean success = xReenterCheck(key, transactionId);
        if (success) {
            return new XLock(transactionId, key);
        }

        //如果当前不可获取，返回空
        if (!canHoldXLock(key, transactionId)) {
            return null;
        }

        //获取锁，可能失败。
        success = tryRegisterXLock(key, transactionId);

        if (success) {
            return new XLock(transactionId, key);
        } else {
            return null;
        }

    }

    private boolean xReenterCheck(String key, String transactionId) {
        XLock xLock = xLockRegister.get(key);
        if (xLock != null && !StringUtils.isEmpty(xLock.getTransactionId()) && xLock.getTransactionId().equals(transactionId)) {
            return true;
        }
        return false;
    }

    private boolean tryRegisterXLock(String key, String transactionId) {
        genLock(key);

        Set<SLock> sLocks = sLockRegister.get(key);
        XLock xLock = xLockRegister.get(key);
        synchronized (sLocks) {
            synchronized (xLock) {
                //防止其他线程在上次检查后锁状态发生了变更，double check 一下
                if (canHoldXLock(key, transactionId)) {
                    doHoldXLock(key, transactionId);
                    return true;
                } else {
                    return false;
                }
            }
        }


    }

    private void doHoldXLock(String key, String transactionId) {
        XLock xLock = xLockRegister.get(key);
        xLock.setTransactionId(transactionId);
    }


    @Override
    public void releaseSLock(String key, String transactionId) {
        Set<SLock> sLocks = sLockRegister.get(key);
        synchronized (sLocks) {
            sLocks.remove(new SLock(key, transactionId));
        }
    }

    @Override
    public void releaseXLock(String key, String transactionId) {
        XLock xLock = xLockRegister.get(key);
        xLock.setTransactionId(null);
    }

}
