package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.sun.org.apache.regexp.internal.RE;
import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;
import java.util.Comparator;

/**
 * 红黑树（高度平衡的二叉树）实现的符号表
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class RedBlackTreeST<K, V> extends AbstractST<K, V> {
    /**
     * comparator
     */
    private Comparator<K> comparator;

    public RedBlackTreeST(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public RedBlackTreeST() {
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V get(K key) {
        return null;
    }

    @Override
    public V delete(K key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Collection<K> keys() {
        return null;
    }

    @Override
    public Collection<V> values() {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        if (!(k1 instanceof Comparable)) {
            throw new IllegalArgumentException(k1.getClass().getSimpleName() + " is not inherit from Comparable");
        }
        return ((Comparable<K>) k1).compareTo(k2);
    }

    // Red-Black operation

    /**
     * 左旋
     *
     * @param h
     * @return
     */
    private Entry<K, V> rotateLeft(Entry<K, V> h) {
        Entry<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /**
     * 右旋
     *
     * @param h
     * @return
     */
    private Entry<K, V> rotateRight(Entry<K, V> h) {
        Entry<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }


    private static final boolean RED = false;
    private static final boolean BLACK = true;

    static final class Entry<K, V> implements ST.Entry<K, V> {
        K key;
        V val;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;
        boolean color = RED;


        public boolean isRead() {
            return color == RED;
        }

        public boolean isBlack() {
            return color == BLACK;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return val;
        }

        @Override
        public void setValue(V v) {
            this.val = v;
        }
    }

}
