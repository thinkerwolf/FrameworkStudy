package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Arrays;
import java.util.Random;

/**
 * 二分法搜索的有序查找表
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
    /**'
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
        ensureCapacity(size + 1);
        int index = rank(key);
        // 找到的位置在队列中
        if (index < size && key.equals(keys[index])) {
            V oldVal = vals[index];
            vals[index] = value;
            return oldVal;
        }
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
        System.out.println(st);
        for (int i = 1; i <= 20; i++) {
            System.out.println(st.delete(i));
        }
        System.out.println(st);
    }

}
