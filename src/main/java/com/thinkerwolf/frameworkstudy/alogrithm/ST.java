package com.thinkerwolf.frameworkstudy.alogrithm;

import java.util.Collection;

/**
 * 一个符号表的接口
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public interface ST<K, V> {
    /**
     * 放入值
     *
     * @param key
     * @return
     */
    V put(K key, V value);

    /**
     * 获取值
     *
     * @param key
     * @return
     */
    V get(K key);

    /**
     * 删除值
     *
     * @param key
     * @return
     */
    V delete(K key);

    /**
     * 大小
     *
     * @return
     */
    int size();

    /**
     * 是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 返回Key集合
     *
     * @return
     */
    Collection <K> keys();

    /**
     * 返回值集合
     *
     * @return
     */
    Collection <V> values();

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    boolean containsKey(K key);

    /**
     * 内存使用
     *
     * @return
     */
    default long memoryUsage() {
        return 0;
    }

    /**
     * 符号表的数据存储单位
     *
     * @param <K>
     * @param <V>
     */
    interface Entry<K, V> {
        /**
         * 得到键
         *
         * @return
         */
        K getKey();

        /**
         * 得到值
         *
         * @return
         */
        V getValue();

        /**
         * 设置值
         *
         * @param v
         */
        void setValue(V v);

        /**
         * equals
         *
         * @param o
         * @return
         */
        @Override
        boolean equals(Object o);

        /**
         * hashCode
         *
         * @return
         */
        @Override
        int hashCode();
    }
}
