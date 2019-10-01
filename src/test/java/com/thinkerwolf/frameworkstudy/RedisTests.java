package com.thinkerwolf.frameworkstudy;

import org.apache.catalina.core.StandardThreadExecutor;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.Transaction;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RedisTests {

    private static Jedis conn;

    public RedisTests() {

    }

    @Test
    public void testString() {
        conn.set("num", "0");
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

    public void testSubscribe() {
        List<String> channel = conn.pubsubChannels("channel");

    }

    @Test
    public void testTransaction() {
        Transaction transaction = conn.multi();
        transaction.hset("trans", "name", "Json");
        transaction.exec();
    }

    @BeforeClass
    public static void beforeStart() {
        conn = new Jedis("localhost");
        conn.select(15);
    }

    @AfterClass
    public static void afterOver() {
        conn.save();
        conn.close();
    }

}
