package com.thinkerwolf.frameworkstudy.redis;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

public class RedissonUtil {

    static RedissonClient redissonClient;


    static {
        Config config = new Config();
        config.useClusterServers().addNodeAddress("redis://localhost:6379");
        redissonClient = Redisson.create(config);
    }


    public static RedissonClient getRedissn() {
        return redissonClient;
    }

    public static RLock getLock(String name) {
        return redissonClient.getLock(name);
    }

}
