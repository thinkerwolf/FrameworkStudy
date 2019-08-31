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
public class BinarySearch1ST<K extends Comparable <K>, V> implements ST <K, V> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * 查找表大小
     */
    private int size;

    private Entry <K, V>[] entries;

    public BinarySearch1ST(int capacity) {
        if (capacity < 0) {
            throw new IllegalArgumentException(capacity + "");
        }
        this.entries = new Entry[capacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        int index = rank(key);
        // 找到的位置在队列中
        if (index < size && key.equals(entries[index].getKey())) {
            V oldVal = entries[index].getValue();
            entries[index].setValue(value);
            return oldVal;
        }
        ensureCapacity(size + 1);
        for (int i = size; i > index; i--) {
            entries[i] = entries[i - 1];
        }
        Entry <K, V> en = new Entry <>(key, value);
        entries[index] = en;
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
        return entries[index].getValue();
    }

    @Override
    public V delete(K key) {
        int index = rank(key);
        if (index >= size) {
            return null;
        }
        V oldVal = entries[index].getValue();
        int numMoved = size - index - 1;
        System.arraycopy(entries, index + 1, entries, index, numMoved);
        entries[--size] = null;
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
    public boolean contains(K key) {
        return rank(key) < size;
    }

    class Keys extends AbstractCollection <K> {

        @Override
        public Iterator <K> iterator() {
            return new KeysIterator();
        }

        @Override
        public int size() {
            return BinarySearch1ST.this.size();
        }
    }

    class KeysIterator implements Iterator <K> {

        int cursor;

        KeysIterator() {
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
            return entries[cursor++].getKey();
        }

        @Override
        public void remove() {
            if (hasNext()) {
                BinarySearch1ST.this.delete(next());
                cursor--;
            }
        }
    }

    class Values extends AbstractCollection <V> {

        @Override
        public Iterator <V> iterator() {
            return new ValueIterator();
        }

        @Override
        public int size() {
            return BinarySearch1ST.this.size();
        }
    }

    class ValueIterator implements Iterator <V> {
        private int cursor;

        public ValueIterator() {
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
            return entries[cursor++].getValue();
        }

        @Override
        public void remove() {
            if (hasNext()) {
                BinarySearch1ST.this.delete(entries[cursor].getKey());
            }
        }
    }

    private void ensureCapacity(int minCapacity) {
        int oldCapacity = entries.length;
        if (minCapacity - oldCapacity > 0) {
            int newCapacity = oldCapacity + oldCapacity >> 1;
            if (newCapacity - minCapacity < 0) {
                newCapacity = minCapacity;
            }
            if (newCapacity - MAX_ARRAY_SIZE > 0) {
                newCapacity = minCapacity > MAX_ARRAY_SIZE ? Integer.MAX_VALUE : MAX_ARRAY_SIZE;
            }
            entries = Arrays.copyOf(entries, newCapacity);
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
        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;
            int cmp = key.compareTo(entries[mid].getKey());
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
            builder.append(entries[i].getKey());
            builder.append('=');
            builder.append(entries[i].getValue());
            if (i >= size - 1) {
                builder.append('}');
                break;
            }
            builder.append(',').append(' ');
        }
        return builder.toString();
    }


    private static class Entry<K extends Comparable <K>, V> implements ST.Entry <K, V> {
        K key;
        V val;

        Entry(K key, V val) {
            this.key = key;
            this.val = val;
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
        BinarySearch1ST <Integer, String> st = new BinarySearch1ST <>(16);

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
