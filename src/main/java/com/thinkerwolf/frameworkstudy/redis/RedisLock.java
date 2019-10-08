package com.thinkerwolf.frameworkstudy.redis;

import redis.clients.jedis.Jedis;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.*;

public class RedisLock {
    /**
     * 连接ID暂定一个JVM进程一个
     */
    private static String connectId = UUID.randomUUID().toString();

    private static String luaLockScript;

    private static String luaUnlockScript;

    private static ScheduledExecutorService scheduled;

    static {
        luaLockScript =
                "if (redis.call('exists', KEYS[1]) == 0) then " +
                        "redis.call('hset', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil; " +
                        "end; " +
                        "if (redis.call('hexists', KEYS[1], ARGV[2]) == 1) then " +
                        "redis.call('hincrby', KEYS[1], ARGV[2], 1); " +
                        "redis.call('pexpire', KEYS[1], ARGV[1]); " +
                        "return nil; " +
                        "end; " +
                        "return redis.call('pttl', KEYS[1]);";
        // PTTL 获取键的剩余时间
        scheduled = Executors.newScheduledThreadPool(10);

        luaUnlockScript = "if (redis.call('hexists', KEYS[1], ARGV[2]) == 0) then \n" +
                "return nil;" +
                "end;" +
                "local counter = redis.call('hincrby', KEYS[1], ARGV[2], -1); " +
                "if (counter > 0) then \n" +
                "redis.call('pexpire', KEYS[1], ARGV[1]);" +
                "return 0;" +
                "else \n" +
                "redis.call('del', KEYS[1]);" +
                "return 1; " +
                "end; " +
                "return nil;";
    }

    private Jedis conn;

    private String name;


    public RedisLock(Jedis conn, String name) {
        this.conn = conn;
        this.name = name;
    }

    public void lock() {
        Long pttl = tryAcquire();
        if (pttl == null) {
            // 锁住本地进程
            return;
        }
        try {
            for (; ; ) { // loop
                pttl = tryAcquire();
                if (pttl == null) {
                    return;
                }
                // 轮询，至多能接受5ms的延迟
                try {
                    long waitNanos;
                    if (pttl <= 5) {
                        waitNanos = pttl * 1000 * 1000;
                    } else {
                        waitNanos = 5 * 1000 * 1000;
                    }
                    TimeUnit.NANOSECONDS.sleep(waitNanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } finally {

        }
    }


    public void unlock() {
        Thread current = Thread.currentThread();
        String expireTime = "5000";
        List<String> keys = Arrays.asList(getName());
        List<String> args = Arrays.asList(expireTime, getLockName(current));
        conn.eval(luaUnlockScript, keys, args);
    }

    private String getName() {
        return name;
    }

    private String getLockName(Thread thread) {
        return "id:" + connectId + ":" + thread.getId();
    }

    /**
     * 尝试获取锁
     *
     * @return
     */
    protected Long tryAcquire() {
        Thread current = Thread.currentThread();
        String expireTime = "5000";
        Object result = conn.eval(luaLockScript, Arrays.asList(getName()), Arrays.asList(expireTime, getLockName(current)));
        return (Long) result;
    }

    /**
     * 尝试释放锁
     *
     * @return
     */
    protected Long tryRelease() {
        Thread current = Thread.currentThread();

        return null;
    }


}
