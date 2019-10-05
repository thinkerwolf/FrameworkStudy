package com.thinkerwolf.frameworkstudy.redis;

import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 简单Redis锁实现
 *
 * @author wukai
 */
public class SimpleRedisLock {


    /**
     * 尝试获取锁，参考Future写法
     *
     * @param conn    连接
     * @param name    lockName
     * @param timeout 超时时间
     * @return
     */
    public static String tryAcquire(Jedis conn, String name, long timeout) {
        String lock = "lock:" + name;
        String identifier = Util.nextString(10);
        final long startNanos = System.nanoTime();
        long waitNanos = TimeUnit.MILLISECONDS.toNanos(timeout);
        boolean ok;
        boolean interrupted = false;
        try {
            for (; ; ) {
                ok = conn.setnx(lock, identifier) > 0;
                if (ok) {
                    break;
                }
                try {
                    Thread.sleep(waitNanos / 1000000, (int) (waitNanos % 1000000));
                } catch (InterruptedException e) {
                    // 默认不可以被打断
                    interrupted = true;
                }
                ok = conn.setnx(lock, identifier) > 0;
                if (ok) {
                    break;
                }
                waitNanos = waitNanos - (System.nanoTime() - startNanos);
                if (waitNanos <= 0) {
                    break;
                }
            }
        } finally {
            if (interrupted) {
                Thread.currentThread().interrupt();
            }
        }
        return ok ? identifier : null;
    }

    /**
     * 尝试释放锁
     *
     * @param conn
     * @param name
     * @param identifier
     * @return
     */
    public static boolean tryRelease(Jedis conn, String name, String identifier) {
        String lock = "lock:" + name;
        for (; ; ) {
            conn.watch(lock);
            Transaction trans = conn.multi();
            trans.get(lock);
            trans.del(lock);
            List<Object> objs = trans.exec();
            Object obj1 = objs.get(0);
            Object obj2 = objs.get(1);
            if (obj1 instanceof Exception || obj2 instanceof Exception) {
                conn.unwatch();
                continue;
            }
            if (identifier.equals(obj1)) {
                return true;
            }
            break;
        }
        return false;
    }


}
