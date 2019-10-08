package com.thinkerwolf.frameworkstudy.redis;

import java.util.List;

/**
 * Redis工具类
 *
 * @author wukai
 */
public class JedisUtil {

    public static final String SUCCESS_STRING = "OK";

    public static final Long SUCCESS_LONG = 1L;

    public static boolean isStringOk(Object code) {
        return SUCCESS_STRING.equals(code);
    }

    public static boolean isLongOk(Object code) {
        return SUCCESS_LONG.equals(code);
    }

    public static Object getLast(List<Object> objs) {
        if (objs == null || objs.size() <= 0) {
            return null;
        }
        return objs.get(objs.size() - 1);
    }

    public static Long getLongResult(Object result) {
        return (Long) result;
    }

}
