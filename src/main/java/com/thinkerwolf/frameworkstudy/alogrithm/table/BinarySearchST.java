package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.*;

/**
 * 有序查找表（二分查找）实现的符号表
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class BinarySearchST<K extends Comparable <K>, V> implements ST <K, V> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * key数组
     */
    private K[] keys;
    /**
     * val数组
     */
    private V[] vals;
    /**
     * '
     * 查找表大小
     */
    private int size;

    @SuppressWarnings("unchecked")
    public BinarySearchST(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(capacity + "");
        }
        this.keys = (K[]) new Comparable[capacity];
        this.vals = (V[]) new Object[capacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        int index = rank(key);
        // 找到的位置在队列中
        if (index < size && key.equals(keys[index])) {
            V oldVal = vals[index];
            vals[index] = value;
            return oldVal;
        }
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            keys[i] = keys[i - 1];
            vals[i] = vals[i - 1];
        }
        keys[index] = key;
        vals[index] = value;

        size++;
        return null;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        int index = rank(key);
        if (index >= size) {
            return null;
        }
        return vals[index];
    }

    @Override
    public V delete(K key) {
        int index = rank(key);
        if (index >= size) {
            return null;
        }
        V oldVal = vals[index];
        int numMoved = size - index - 1;
        System.arraycopy(keys, index + 1, keys, index, numMoved);
        System.arraycopy(vals, index + 1, vals, index, numMoved);
        keys[--size] = null;
        vals[size] = null;
        return oldVal;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    @Override
    public Collection <K> keys() {
        return new Keys();
    }

    @Override
    public Collection <V> values() {
        return new Values();
    }

    @Override
    public boolean containsKey(K key) {
        return rank(key) < size;
    }

    class Keys extends AbstractCollection <K> {

        @Override
        public Iterator <K> iterator() {
            return new KeysIterator(keys);
        }

        @Override
        public int size() {
            return BinarySearchST.this.size();
        }
    }

    class KeysIterator implements Iterator <K> {

        int cursor;
        K[] keys;

        KeysIterator(K[] keys) {
            this.keys = keys;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public K next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            return keys[cursor++];
        }

        @Override
        public void remove() {
            if (hasNext()) {
                BinarySearchST.this.delete(next());
                cursor--;
            }
        }
    }

    class Values extends AbstractCollection <V> {

        @Override
        public Iterator <V> iterator() {
            return new ValueIterator(keys, vals);
        }

        @Override
        public int size() {
            return BinarySearchST.this.size();
        }
    }

    class ValueIterator implements Iterator <V> {
        K[] keys;
        V[] vals;
        private int cursor;

        public ValueIterator(K[] keys, V[] vals) {
            this.keys = keys;
            this.vals = vals;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public V next() {
            if (cursor >= size) {
                throw new NoSuchElementException();
            }
            return vals[cursor++];
        }

        @Override
        public void remove() {
            if (hasNext()) {
                BinarySearchST.this.delete(keys[cursor]);
            }
        }
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = keys.length;
        if (minCapacity - oldCapacity > 0) {
            int newCapacity = oldCapacity + oldCapacity >> 1;
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity;
            }
            if (newCapacity - MAX_ARRAY_SIZE > 0) {
                newCapacity = minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
            }
            keys = Arrays.copyOf(keys, newCapacity);
            vals = Arrays.copyOf(vals, newCapacity);
        }
    }

    /**
     * 二分法搜索可以插入的index
     *
     * @param key
     * @return
     */
    public int rank(K key) {
        if (isEmpty()) {
            return 0;
        }
        int lo = 0;
        int hi = size - 1;
        if (key.compareTo(keys[hi]) > 0) {
            return size;
        }
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(keys[mid]);
            if (cmp > 0) {
                lo = mid + 1;
            } else if (cmp < 0) {
                hi = mid - 1;
            } else {
                return mid;
            }
        }
        return lo;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append('{');
        for (int i = 0; i < size; i++) {
            builder.append(keys[i]);
            builder.append('=');
            builder.append(vals[i]);
            if (i >= size - 1) {
                builder.append('}');
                break;
            }
            builder.append(',').append(' ');
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        BinarySearchST <Integer, String> st = new BinarySearchST <>(16);

        Random r = new Random();
        for (int i = 1; i <= 20; i++) {
            int key = r.nextInt(20);
            st.put(key, "val" + key);
            st.put(i, "another" + i);
        }
        for (Integer k : st.keys()) {
            System.out.println("iterator key -> " + k);
        }
        for (String v : st.values()) {
            System.out.println("iterator value -> " + v);
        }
        for (int i = 1; i <= 20; i++) {
            System.out.println(st.delete(i));
        }
        System.out.println(st);
    }

}
