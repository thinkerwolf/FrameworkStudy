package com.thinkerwolf.frameworkstudy.redis;

/**
 * Redis工具类
 *
 * @author wukai
 */
public class RedisUtil {

    public static final String SUCCESS_CODE = "OK";


    public static boolean isOk(String code) {
        return SUCCESS_CODE.equals(code);
    }

}
