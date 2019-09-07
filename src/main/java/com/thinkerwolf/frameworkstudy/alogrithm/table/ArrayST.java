package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Arrays;
import java.util.Collection;

/**
 * 符号表的无序数组实现
 *
 * @param <K>
 * @param <V>
 */
public class ArrayST<K, V> extends AbstractST<K, V> {
    private static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * 查找表大小
     */
    private transient int size;

    private transient Entry<K, V>[] entries;

    public ArrayST() {
    }

    public ArrayST(int size) {
        if (size < 0) {
            throw new IllegalArgumentException("Illegal size : " + size);
        }
        this.entries = new Entry[size];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        if (entries == null) {
            this.entries = new Entry[DEFAULT_INITIAL_CAPACITY];
        }
        int index = find(key);
        if (index >= 0) {
            Entry<K, V> en = entries[index];
            V oldVal = en.getValue();
            en.setValue(value);
            return oldVal;
        }
        ensureCapacity(size + 1);
        entries[size] = new Entry<>(key, value);
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        int index = find(key);
        if (index <= 0) {
            return index < 0 ? null : entries[index].getValue();
        } else {
            // 访问过的元素前移，用于快速查找。前移编码
            Entry<K, V> en = entries[index];
            for (int i = index; i > 0; i--) {
                entries[i] = entries[i - 1];
            }
            entries[0] = en;
            return en.getValue();
        }
    }

    @Override
    public V delete(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        int index = find(key);
        if (index < 0) {
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
    public Collection<K> keys() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<V> values() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsKey(K key) {
        return find(key) >= 0;
    }

    public int find(K key) {
        for (int i = 0; i < size; i++) {
            if (entries[i].getKey().equals(key)) {
                return i;
            }
        }
        return -1;
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

    private static class Entry<K, V> implements ST.Entry<K, V> {

        private K key;
        private V val;

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
        ArrayST<Integer, String> st = new ArrayST<>(5);
        for (int i = 0; i < 20; i++) {
            st.put(i, "val" + i);
        }
        System.out.println(st);
        System.out.println(st.get(4));
        System.out.println(st.get(5));
        System.out.println(st.get(14));
        System.out.println(st);
    }

}
