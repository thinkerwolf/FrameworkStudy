package com.thinkerwolf.frameworkstudy.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * 自动补全
 *
 * @author wukai
 */
public class RedisAutoCompletion {

    public static void addAndUpdateResent(Jedis conn, String user, String contract) {
        String acList = "resent:" + user;
        Transaction trans = conn.multi();
        trans.lrem(acList, 1, contract);
        trans.lpush(acList, contract);
        trans.ltrim(acList, 0, 100);
        trans.exec();
    }

    public static List<String> fetchResentCompletionList(Jedis conn, String user, String prefix) {
        String acList = "resent:" + user;
        List<String> contracts = conn.lrange(acList, 0, -1);
        List<String> result = new LinkedList<>();
        for (String contract : contracts) {
            if (contract.startsWith(prefix)) {
                result.add(contract);
            }
        }
        return result;
    }


    public static void addAndUpdateMembers(Jedis conn, String guild, String contract) {
        String members = "members:" + guild;
        conn.zrem(members, contract);
        conn.zadd(members, 0, contract);
    }

    public static String[] findPrefixRange(String prefix) {
        // abk    abj{  ab{
        char c = prefix.charAt(prefix.length() - 1);
        String start = prefix.substring(0, prefix.length() - 1) + (char) (c - 1);
        return new String[]{start, prefix + '{'};
    }


    public static Set<String> autoCompletionOnPrefix(Jedis conn, String guild, String prefix) {
        String members = "members:" + guild;
        String[] ranges = findPrefixRange(prefix);
        String identifier = UUID.randomUUID().toString();
        String start = ranges[0] + identifier;
        String end = ranges[1] + identifier;
        conn.zadd(members, 0, start);
        conn.zadd(members, 0, end);
        boolean ok = false;
        Set<String> result = null;
        do {
            conn.watch(members);
            Long sindex = conn.zrank(members, start);
            Long eindex = conn.zrank(members, end);
            long range = Math.min(sindex + 9, eindex - 2);
            Transaction trans = conn.multi();
            trans.zrem(members, start, end);
            trans.zrange(members, sindex, range);

            List<Object> resp = trans.exec();
            if (resp != null) {
                result = (Set<String>) resp.get(resp.size() - 1);
                ok = true;
            } else {
                ok = false;
            }
        } while (!ok);

        result.removeIf(s -> s.contains("{"));

        return result;
    }


}
