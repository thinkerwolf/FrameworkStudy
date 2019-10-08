package com.thinkerwolf.frameworkstudy.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;
import redis.clients.jedis.ZParams;

import java.util.List;
import java.util.UUID;

/**
 * 简单的信号量实现
 *
 * @author wukai
 */
public class RedisSemaphoreUtil {
    /**
     * 尝试获取信号量锁(非公平)
     * 因为客户端系统时钟不同会导致锁获取不公平
     * <p>
     * zset数据结构
     * </p>
     *
     * @param conn
     * @param limit
     * @return
     */
    public static String tryAcquireUnfair(Jedis conn, String name, int limit, long timeout) {
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

        trans = conn.multi();
        trans.zrem(sename, identifier);
        trans.exec();
        return null;
    }

    public static boolean tryReleaseUnfair(Jedis conn, String name, String identifier) {
        String sename = "semaphore:" + name;
        Long l = conn.zrem(sename, identifier);
        return JedisUtil.isLongOk(l);
    }

    /**
     * @param conn
     * @param name
     * @param limit
     * @param timeout
     * @return
     */
    public static String tryAcquireFair(Jedis conn, String name, int limit, long timeout) {
        String identifier = UUID.randomUUID().toString();

        String timeouter = "semaphore:" + name; // 超时信号量有序集合
        String owner = "semaphore:owner:" + name; // 拥有者信号量有序集合
        String counter = "semaphore:counter:" + name; // 计数器
//        conn.del(timeouter);
//        conn.del(owner);
//        conn.del(counter);

        long now = System.currentTimeMillis();
        Transaction trans = conn.multi();
        // 移除超时的信号量
        trans.zremrangeByScore(timeouter, 0, now - timeout);
        ZParams params = new ZParams();
        params.aggregate(ZParams.Aggregate.MIN);
        trans.zinterstore(owner, params, owner, timeouter);
        trans.incr(counter);
        Long count = (Long) JedisUtil.getLast(trans.exec());

        trans = conn.multi();
        trans.zadd(timeouter, now, identifier);
        trans.zadd(owner, count, identifier);
        trans.zrank(owner, identifier);
        Long rank = (Long) JedisUtil.getLast(trans.exec());
        if (rank < limit) {
            return identifier;
        }

        trans = conn.multi();
        trans.zrem(timeouter, identifier);
        trans.zrem(owner, identifier);
        trans.exec();
        return null;
    }

    public static boolean tryReleaseFair(Jedis conn, String name, String identifier) {
        String timeouter = "semaphore:" + name; // 超时信号量有序集合
        String owner = "semaphore:owner:" + name; // 拥有者信号量有序集合
        Transaction trans = conn.multi();
        trans.zrem(timeouter, identifier);
        trans.zrem(owner, identifier);
        return JedisUtil.isLongOk(JedisUtil.getLast(trans.exec()));
    }


}
