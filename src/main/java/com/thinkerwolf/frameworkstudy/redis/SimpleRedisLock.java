package com.thinkerwolf.frameworkstudy.redis;

import com.thinkerwolf.frameworkstudy.common.Util;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.params.SetParams;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 简单Redis锁实现，基于单服实现的锁服务
 *
 * @author wukai
 */
public class SimpleRedisLock {


    /**
     * 尝试获取锁，setnx和expire一起操作不是原子性的。
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
                // 非原子性操作，可能误expire
                ok = conn.setnx(lock, identifier) > 0;
                if (ok) {
                    conn.expire(lock, 5);
                    break;
                }
                try {
                    Thread.sleep(waitNanos / 1000000, (int) (waitNanos % 1000000));
                } catch (InterruptedException e) {
                    // 默认不可以被打断
                    interrupted = true;
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

    public static String tryAcquire(Jedis conn, String name) {
        return tryAcquire(conn, name, 0);
    }

    /**
     * 尝试释放锁，使用watch和事务机制，效率比较低。
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

    /**
     * 高效的获取锁方式。
     *
     * @param conn
     * @param name
     * @return
     */
    public static String tryAcquireEfficient(Jedis conn, String name) {
        String lock = "lock:" + name;
        String identifier = Util.nextString(10);
        SetParams params = new SetParams();
        params.nx();
        params.ex(5);
        // Atomic
        String result = conn.set(lock, identifier, params);
        return RedisUtil.isStringOk(result) ? identifier : null;
    }

    private static final String RELEASE_SCRIPT =
            "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

    /**
     * 高效的释放锁方式，利用lua脚本执行释放锁操作。
     *
     * @param conn
     * @param name
     * @param identifier
     * @return
     */
    public static boolean tryReleaseEfficient(Jedis conn, String name, String identifier) {
        String lock = "lock:" + name;
        // Atomic
        Object result = conn.eval(RELEASE_SCRIPT, Collections.singletonList(lock), Collections.singletonList(identifier));
        if (RedisUtil.isLongOk(result)) {
            return true;
        }
        return false;
    }

    /**
     * 尝试获取信号量锁，每个客户端的时钟需要保持一致才能保持公平性。
     *
     * @param conn
     * @param limit
     * @return
     */
    public static String tryAcquireSemaphore(Jedis conn, String name, int limit, long timeout) {
        String identifier = UUID.randomUUID().toString();
        //
        long now = System.currentTimeMillis();
        String sename = "semaphore:" + name;
        Transaction trans = conn.multi();
        // 1.清理过期
        trans.zremrangeByScore(sename, 0, now - timeout);
        // 2.添加新数据
        trans.zadd(sename, now, identifier);
        // 3.新建rank
        trans.zrank(sename, identifier);
        List<Object> objs = trans.exec();
        System.out.println(objs);
        Object obj = objs.get(objs.size() - 1);
        if (obj instanceof Long && ((Long) obj).longValue() < limit) {
            return identifier;
        }
        return null;
    }

    public static boolean tryReleaseSemaphore(Jedis conn, String name, String identifier) {
        String sename = "semaphore:" + name;
        Long l = conn.zrem(sename, identifier);
        return RedisUtil.isLongOk(l);
    }


}
