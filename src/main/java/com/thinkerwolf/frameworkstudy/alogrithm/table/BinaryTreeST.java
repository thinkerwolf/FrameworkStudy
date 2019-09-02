package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;
import java.util.Random;

/**
 * 二叉查找树实现的<strong>符号表</strong>
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class BinaryTreeST<K extends Comparable <K>, V> extends AbstractST <K, V> {
    private transient int size;
    private transient Entry <K, V> root;


    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        Entry <K, V> en = find(key);
        if (en == null) {
            root = new Entry <>(key, value, null, null);
            size++;
            return null;
        }
        int cmp = key.compareTo(en.getKey());
        if (cmp == 0) {
            V oldVal = en.getValue();
            en.setValue(value);
            return oldVal;
        } else if (cmp < 0) {
            en.left = new Entry <>(key, value, null, null);
        } else {
            en.right = new Entry <>(key, value, null, null);
        }
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        Entry <K, V> en = find(key);
        if (en == null) {
            return null;
        }
        int cmp = key.compareTo(en.getKey());
        if (cmp == 0) {
            return en.getValue();
        }
        return null;
    }

    @Override
    public V delete(K key) {
        if (isEmpty()) {
            return null;
        }

        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Collection <K> keys() {
        return null;
    }

    @Override
    public Collection <V> values() {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        inorderTraversalToString(root, builder);
        builder.append('}');
        return builder.toString();
    }

    /**
     * 中序遍历树
     *
     * @param en
     * @param builder
     */
    private void inorderTraversalToString(Entry <K, V> en, StringBuilder builder) {
        if (en == null) {
            return;
        }
        inorderTraversalToString(en.left, builder);
        builder.append(en.getKey());
        builder.append('=');
        builder.append(en.getValue());
        builder.append(',').append(' ');
        inorderTraversalToString(en.right, builder);
    }

    private Entry <K, V> find(K key) {
        if (isEmpty()) {
            return null;
        }
        return innerFind(key, root);
    }

    private Entry <K, V> innerFind(K key, Entry <K, V> en) {
        if (en == null) {
            return null;
        }
        int cmp = key.compareTo(en.getKey());
        if (cmp == 0) {
            return en;
        } else if (cmp < 0) {
            return en.left == null ? en : innerFind(key, en.left);
        } else {
            return en.right == null ? en : innerFind(key, en.right);
        }
    }


    static final class Entry<K extends Comparable <K>, V> implements ST.Entry <K, V> {
        K key;
        V val;
        Entry <K, V> left;
        Entry <K, V> right;

        public Entry(K key, V val, Entry <K, V> left, Entry <K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
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

    public static void main(String[] args) {
        ST <Integer, String> st = new BinaryTreeST <>();
        Random r = new Random();
        for (int i = 1; i <= 20; i++) {
            int key = r.nextInt(20);
            st.put(key, "val" + key);
            st.put(i, "another" + i);
        }

        System.out.println(st);
    }

}
