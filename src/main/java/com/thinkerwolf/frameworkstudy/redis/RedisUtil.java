package com.thinkerwolf.frameworkstudy.redis;

/**
 * Redis工具类
 *
 * @author wukai
 */
public class RedisUtil {

    public static final String SUCCESS_STRING = "OK";

    public static final Long SUCCESS_LONG = 1L;

    public static boolean isStringOk(Object code) {
        return SUCCESS_STRING.equals(code);
    }

    public static boolean isLongOk(Object code) {
        return SUCCESS_LONG.equals(code);
    }
}
