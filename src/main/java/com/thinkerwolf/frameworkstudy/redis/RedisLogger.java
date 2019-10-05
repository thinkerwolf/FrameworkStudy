package com.thinkerwolf.frameworkstudy.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.Transaction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 使用Redis记录log
 *
 * @author wukai
 */
public class RedisLogger {


    private Jedis conn;

    private String name;

    private static Pattern pattern = Pattern.compile("\\{\\s*\\}");

    private static long ONE_HOUR = 60 * 60 * 1000;

    private static long ONE_SECOND = 1000;

    public RedisLogger(String name, Jedis conn) {
        this.conn = conn;
        this.name = name;
    }

    public RedisLogger(String name, String host, int port) {
        this(name, new Jedis(host, port));
    }

    public RedisLogger(Class<?> clazz, Jedis conn) {
        this(clazz.getName(), conn);
    }

    public RedisLogger(Class<?> clazz, String host, int port) {
        this(clazz.getName(), host, port);
    }

    /**
     * 记录最近99条数据
     *
     * @param logLevel
     * @param msg
     * @param params
     */
    public void logResent(LogLevel logLevel, String msg, Object... params) {
        Jedis conn = this.conn;
        msg = formatMessage(msg, params);

        String destination = "resent:" + name + ':' + logLevel.toString();
        SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-dd HH:MM:ss:SSS");
        msg = format.format(new Date()) + " " + msg;

        Pipeline pipe = conn.pipelined();
        pipe.multi();
        pipe.lpush(destination, msg);
        pipe.ltrim(destination, 0, 99);
        pipe.exec();
        pipe.close();
    }

    /**
     * 打印常用日志，每一个小时对日志进行轮换
     *
     * @param logLevel
     * @param msg
     * @param params
     */
    public void logCommon(LogLevel logLevel, String msg, Object... params) {
        Jedis conn = this.conn;
        String destination = "common:" + name + ':' + logLevel.toString();
        String startKey = destination + ":start";
        msg = formatMessage(msg, params);
        boolean ok;
        do {
            conn.watch(startKey);
            String startTime = conn.get(startKey);
            Transaction trans = conn.multi();
            long curTime = System.currentTimeMillis();
            if (startTime == null) {
                trans.set(startKey, String.valueOf(curTime));
            }
            if (startTime != null && curTime - Long.parseLong(startTime) > ONE_SECOND) {
                trans.rename(destination, destination + ":last");
                trans.rename(startKey, destination + ":pstart");
                trans.set(startKey, String.valueOf(curTime));
            }

            trans.zincrby(destination, 1.0, msg);
            List<Object> res = trans.exec();
            ok = true;
            for (Object obj : res) {
                if (obj instanceof String) {
                    if (!"OK".equals(obj)) {
                        ok = false;
                        break;
                    }
                } else {
                    Double d = (Double) obj;
                    ok = d > 0;
                    break;
                }
            }

            if (ok) {
                logResent(logLevel, msg);
            }
        } while (!ok);
    }


    private static String formatMessage(String format, Object... params) {
        StringBuffer buffer = new StringBuffer();
        Matcher matcher = pattern.matcher(format);
        int pos = 0;
        while (matcher.find()) {
            String replacement;
            if (pos >= params.length) {
                replacement = "";
            } else {
                replacement = String.valueOf(params[pos]);
            }
            matcher.appendReplacement(buffer, replacement);
            pos++;
        }
        matcher.appendTail(buffer);
        return buffer.toString();
    }

}
