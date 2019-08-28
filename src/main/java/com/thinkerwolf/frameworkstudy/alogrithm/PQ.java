package com.thinkerwolf.frameworkstudy.alogrithm;

/**
 * 优先队列接口
 *
 * @param <T>
 * @see java.util.PriorityQueue
 */
public interface PQ<T> extends Iterable <T> {
    /**
     * 队列是否为空
     *
     * @return
     */
    boolean isEmpty();

    /**
     * 队列的大小
     *
     * @return
     */
    int size();

    /**
     * 插入数据
     *
     * @param t
     */
    void insert(T t);

    /**
     * 取出数据
     *
     * @return
     */
    T poll();


}
