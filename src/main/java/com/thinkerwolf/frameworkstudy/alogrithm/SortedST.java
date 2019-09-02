package com.thinkerwolf.frameworkstudy.alogrithm;

/**
 * 排序的符号表接口
 *
 * @param <K> 键
 * @param <V> 值
 */
public interface SortedST<K, V> extends ST <K, V> {
    /**
     * 返回子符号表
     *
     * @param fromKey
     * @param toKey
     * @return
     */
    SortedST <K, V> subST(K fromKey, K toKey);

    /**
     * 首key
     *
     * @return
     */
    K firstKey();

    /**
     * 尾key
     *
     * @return
     */
    K lastKey();

}
