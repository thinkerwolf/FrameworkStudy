package com.thinkerwolf.frameworkstudy.alogrithm;

public interface PQ<T> extends Iterable <T> {

    boolean isEmpty();

    int size();

    void insert(T t);

    T poll();


}
