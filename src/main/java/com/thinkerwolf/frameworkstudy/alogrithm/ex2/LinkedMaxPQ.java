package com.thinkerwolf.frameworkstudy.alogrithm.ex2;

import com.thinkerwolf.frameworkstudy.alogrithm.PQ;

import java.util.Iterator;

/**
 * 链表实现的优先队列，效率不太好
 *
 * @param <T>
 */
public class LinkedMaxPQ<T> implements PQ <T> {
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public void insert(T t) {

    }

    @Override
    public T poll() {
        return null;
    }

    @Override
    public Iterator <T> iterator() {
        return null;
    }
}
