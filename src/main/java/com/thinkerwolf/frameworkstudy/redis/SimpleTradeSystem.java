package com.thinkerwolf.frameworkstudy.redis;

import com.thinkerwolf.frameworkstudy.common.OpResult;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Map;

/**
 * 简易交易系统
 *
 * @author wukai
 */
public class SimpleTradeSystem {

    /**
     * 创建user
     * redis数据结构：hash
     * <code>
     * "user:18" : {
     * "name": "Frank",
     * "funds": 43
     * }
     * </code>
     *
     * @param conn
     * @param userId
     * @param username
     * @param funds
     * @return
     */
    public static OpResult createUser(Jedis conn, long userId, String username, long funds) {
        String userKey = getUserKey(userId);
        Map<String, String> map = new HashMap<>(8, 1.0F);
        map.put("name", username);
        map.put("funds", String.valueOf(funds));
        RedisLock lock = new RedisLock(conn, "redislock:" + userKey);
        lock.lock();
        // 乐观锁
//        String identifier = RedisLockUtil.tryAcquireEfficient(conn, userKey);
//        if (identifier == null) {
//            return OpResult.fail("获取用户锁失败");
//        }

        try {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            if (conn.exists(userKey)) {
                return OpResult.fail("用户已存在");
            }
            conn.hmset(userKey, map);
            return OpResult.ok();
        } finally {
            lock.unlock();
//            RedisLockUtil.tryReleaseEfficient(conn, userKey, identifier);
        }

    }

    /**
     * 用户将物品上传至仓库
     *
     * @param conn
     * @param userId
     * @param itemId
     * @return
     */
    public static OpResult uploadToInventory(Jedis conn, long userId, String itemId) {
        String userKey = getUserKey(userId);
        // 判断当前用户是否存在
        if (!conn.exists(userKey)) {
            return OpResult.fail("用户不存在");
        }

        String inventoryKey = getInventoryKey(userId);
        String idInventory = RedisLockUtil.tryAcquireEfficient(conn, inventoryKey);
        if (idInventory == null) {
            // 有人正在玩家操作仓库表
            return OpResult.fail("获取仓库锁失败");
        }
        try {
            if (!conn.sismember(inventoryKey, itemId)) {
                conn.sadd(inventoryKey, itemId);
                return OpResult.ok();
            }
            return OpResult.fail("物品已在仓库中");
        } finally {
            RedisLockUtil.tryReleaseEfficient(conn, inventoryKey, idInventory);
        }
    }


    private static String getUserKey(long userId) {
        return "user:" + userId;
    }

    private static String getInventoryKey(long userId) {
        return "inventory:" + userId;
    }

}
