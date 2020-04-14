package com.thinkerwolf.frameworkstudy;

import com.thinkerwolf.frameworkstudy.common.OpResult;
import com.thinkerwolf.frameworkstudy.common.Util;
import com.thinkerwolf.frameworkstudy.redis.*;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import redis.clients.jedis.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTests {

    private static Jedis conn;
    private static JedisPool jedisPool;

    @BeforeClass
    public static void beforeStart() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxIdle(10);
        poolConfig.setMaxTotal(10);
        poolConfig.setMinIdle(0);
        jedisPool = new JedisPool(poolConfig);
        conn = jedisPool.getResource();
        conn.select(15);
    }

    @AfterClass
    public static void afterOver() {
//        conn.save();
        conn.close();
        jedisPool.destroy();
    }

    @Test
    public void testString() {
        System.out.println(conn.set("num", "0"));
        conn.incrBy("num", 300L);
        conn.decr("num");
        conn.decrBy("num", 100);
        conn.incrByFloat("num", -100);
        System.out.println(conn.get("num"));

        System.out.println(conn.bitcount("num")); // 16进制？？
    }

    @Test
    public void testList() {
        conn.del("list-key");
        conn.del("list-key2");

        conn.lpush("list-key", "14", "29", "30");
        conn.rpush("list-key", "97", "98", "99");

        System.out.println(conn.lpop("list-key"));
        System.out.println(conn.rpop("list-key"));

        System.out.println(conn.lindex("list-key", 1));
        System.out.println(conn.lrange("list-key", 0, -1));

        conn.ltrim("list-key", 0, 2);

        System.out.println(conn.lrange("list-key", 0, -1));

        conn.brpoplpush("list-key", "list-key2", 1000);

        System.out.println(conn.lrange("list-key", 0, -1));
        System.out.println(conn.lrange("list-key2", 0, -1));
    }

    @Test
    public void testSet() {
        conn.del("set1");
        conn.del("set2");
        conn.sadd("set1", "31", "32", "40", "99", "101", "102", "103", "200");
        conn.sadd("set2", "21", "32", "40", "98", "100");

        conn.srem("set1", "102");
        conn.srem("set2", "109");

        Long aLong = conn.scard("set1");
        System.out.println(aLong);

        System.out.println(conn.smembers("set1"));

        System.out.println(conn.srandmember("set1", 2));

        System.out.println(conn.smove("set1", "set2", "103"));

        System.out.println(conn.spop("set1"));

        System.out.println("\n高级操作 ===============");

        System.out.println(conn.sdiff("set1", "set2"));

        System.out.println(conn.sinter("set1", "set2"));

        System.out.println(conn.sunion("set1", "set2"));
    }

    @Test
    public void testHash() {
        System.out.println("\n基本操作 ================");
        conn.hset("user1", "name", "Liming");
        conn.hset("user1", "age", "20");
        conn.hset("user1", "lv", "3");

        conn.hset("user2", "name", "JoJo");
        conn.hset("user2", "age", "30");
        conn.hset("user2", "lv", "4");
        Map<String, String> map = new HashMap<>();
        map.put("name", "Jose");
        map.put("age", "90");
        map.put("lv", "10");
        conn.hmset("user3", map);
        System.out.println(conn.hlen("user1"));
        System.out.println(conn.hmget("user2", "name", "age", "lv"));
        System.out.println(conn.hgetAll("user3"));
        System.out.println(conn.hdel("user3", "name"));

        System.out.println("\n高级操作 ================");
        System.out.println(conn.hexists("user3", "name"));
        System.out.println(conn.hkeys("user2"));
        System.out.println(conn.hvals("user2"));
        System.out.println(conn.hincrByFloat("user2", "age", 9.8));
    }

    @Test
    public void testOrderSet() {
        System.out.println("\n基本操作 ================");
        final String zsetKey = "zset";
        final String zsetKey1 = "zset1";
        final String zsetKey2 = "zset2";

//        conn.del(zsetKey);
//        conn.del(zsetKey1);
//        conn.del(zsetKey2);


        Map<String, Double> map = new HashMap<>();
        map.put("Lili", 1.0);
        map.put("Ben", 2.0);
        map.put("Fast", 3.0);
        map.put("Amy", 4.0);
        conn.zadd(zsetKey, map);
        System.out.println(conn.zcard(zsetKey));
        conn.zscore(zsetKey, "Lili");
        conn.zrank(zsetKey, "Lili");
        conn.zcard(zsetKey);

        map.put("Harder", 8.0);
        conn.zadd(zsetKey1, map);

        System.out.println("\n高级操作 ================");

        System.out.println(conn.zinterstore(zsetKey2, zsetKey, zsetKey1));

        System.out.println(conn.zrangeWithScores(zsetKey2, 0, 1000));

    }

    @Test
    public void testPublish() {
        Executor executor = new ThreadPoolExecutor(1, 1, 1000, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<>());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    try {
                        TimeUnit.MILLISECONDS.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                    conn.publish("channel", String.valueOf(i));
                }
            }
        });
    }

    @Test
    public void testSubscribe() {
        conn.subscribe(new JedisPubSub() {
            @Override
            public void onMessage(String channel, String message) {
                System.out.println(message);
                this.unsubscribe();
            }
        },"channel");


        System.out.println("3213213");
    }

    @Test
    public void testTransaction() {

        // 准备
        String inventory = "inventory:";
        String market = "market:";
        String itemId = "0001";
        conn.sadd(inventory, itemId);

        Pipeline pipe = conn.pipelined();
        pipe.multi();
        pipe.srem(inventory, itemId);
        pipe.zadd(market, 30.7, itemId);

        Response<List<Object>> resp = pipe.exec();
        pipe.close();
        System.out.println(resp.get());

        boolean ok;
        do { // CAS
            conn.watch("mykey");
            String val = conn.get("mykey");
            val = val == null ? String.valueOf(1) : String.valueOf(Integer.parseInt(val) + 1);

            Transaction transaction = conn.multi();
            transaction.set("mykey", val);
            List<Object> res = transaction.exec();
            System.out.println(res);
            ok = "OK".equals(String.valueOf(res.get(0)));
        } while (!ok);

    }

    @Test
    public void testLogResent() {
        RedisLogger logger = new RedisLogger("com.thinkerwolf.redis", conn);
        for (int i = 0; i < 100; i++) {
            logger.logResent(LogLevel.INFO, "Test redis log {}, {}", i, i);
        }
    }

    @Test
    public void testLogCommon() {
        RedisLogger logger = new RedisLogger("com.thinkerwolf.redis", conn);
        for (int i = 0; i < 10; i++) {
            logger.logCommon(LogLevel.INFO, "Test redis log {}, {}", i, i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void textCompletion() {
        String[] prefixes = new String[]{"吴", "周", "王", "M"};
        String user = "wukai";
        for (String prefix : prefixes) {
            int num = Util.nextInt(5, 10);
            for (int i = 0; i < num; i++) {
                String contract = prefix + Util.nextString(4);
                RedisAutoCompletion.addAndUpdateResent(conn, user, contract);
            }
        }

        for (String prefix : prefixes) {
            System.out.println(RedisAutoCompletion.fetchResentCompletionList(conn, user, prefix));
        }

        System.out.println("========================");

        prefixes = new String[100];
        for (int i = 0; i < prefixes.length; i++) {
            prefixes[i] = Util.nextString(3);
        }

        for (String prefix : prefixes) {
            for (int i = 0; i < 1000; i++) {
                RedisAutoCompletion.addAndUpdateMembers(conn, "kfc", prefix + Util.nextString(4));
            }
        }
        System.out.println("prefix " + prefixes[0]);
        System.out.println(RedisAutoCompletion.autoCompletionOnPrefix(conn, "kfc", prefixes[0]));
    }

    @Test
    public void testRangeCompletion() {
        System.out.println(Arrays.toString(RedisAutoCompletion.findPrefixRange("aba")));
    }


    @Test
    public void testLock() {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        final int taskNum = 6;
        final CountDownLatch countDown = new CountDownLatch(taskNum);
        final long startTime = System.currentTimeMillis();
        for (int i = 0; i < taskNum; i++) {
            final Jedis jedis = jedisPool.getResource();
            executor.execute(() -> {
                try {
                    // 多个线程，多个conn
                    OpResult op = SimpleTradeSystem.createUser(jedis, 2, "Frank", 50);
                    System.out.println("delay time:" + (System.currentTimeMillis() - startTime));
                    System.out.println("添加用户 -> " + op);
//                    try {
//                        Thread.sleep(Util.nextInt(50, 10 * 7));
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    op = SimpleTradeSystem.uploadToInventory(jedis, 2, "item-" + Util.nextString(6));
//                    System.out.println("添加商品 -> " + op);
                } finally {
                    jedis.close();
                    countDown.countDown();
                }
            });
        }
        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }

    @Test
    public void testSemaphore() {
        ExecutorService executor = Executors.newFixedThreadPool(22);
        // 20个任务 10把锁
        final int taskNum = 20;
        final CountDownLatch countDown = new CountDownLatch(taskNum);
        for (int i = 0; i < taskNum; i++) {
            executor.execute(() -> {
                Jedis jedis = jedisPool.getResource();
                String identifier = null;
                try {
                    identifier = RedisSemaphoreUtil.tryAcquireFair(jedis, "test", 5, 1000);
                    if (identifier == null) {
                        System.out.println("获得信号量失败");
                        return;
                    }
                    System.out.println("获得信号量成功");
                    System.out.println("operation ======");
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                } finally {
                    if (identifier != null) {
                        boolean rs = RedisSemaphoreUtil.tryReleaseFair(jedis, "test", identifier);
                        System.out.println(rs ? "释放信号量成功" : "超时释放");
                    }
                    jedis.close();
                    countDown.countDown();
                }
            });
        }


        try {
            countDown.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executor.shutdown();
    }


}
