package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

public abstract class AbstractST<K, V> implements ST <K, V> {

    protected AbstractST() {

    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }




}
