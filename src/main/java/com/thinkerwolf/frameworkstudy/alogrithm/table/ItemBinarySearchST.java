package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.SortedST;

import java.util.*;

/**
 * 有序查找表（二分查找）实现的符号表
 * <strong>插入效率低下，因为涉及到数组扩容复制的过程；查找效率比较高（二分法查找）</strong>
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class ItemBinarySearchST<K extends Comparable <K>, V> extends AbstractST <K, V> implements SortedST <K, V> {

    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    /**
     * 查找表大小
     */
    private int size;

    private Entry <K, V>[] entries;

    public ItemBinarySearchST(int capacity) {
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

    @Override
    public SortedST <K, V> subST(K fromKey, K toKey) {
        return new AscendingSubST <>(this, fromKey, toKey, true, true);
    }

    @Override
    public K firstKey() {
        if (isEmpty()) {
            return null;
        }
        return entries[0].getKey();
    }

    @Override
    public K lastKey() {
        if (isEmpty()) {
            return null;
        }
        return entries[size - 1].getKey();
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

    static final class AscendingSubST<K extends Comparable <K>, V> extends AbstractST <K, V> implements SortedST <K, V> {
        final ItemBinarySearchST <K, V> m;

        final K lo, hi;
        final boolean loInclusive, hiInclusive;

        AscendingSubST(ItemBinarySearchST <K, V> m, K lo, K hi, boolean loInclusive, boolean hiInclusive) {
            this.m = m;
            this.lo = lo;
            this.hi = hi;
            this.loInclusive = loInclusive;
            this.hiInclusive = hiInclusive;
        }

        @Override
        public V put(K key, V value) {
            return m.put(key, value);
        }

        @Override
        public V get(K key) {
            if (!inRange(key)) {
                return null;
            }
            return m.get(key);
        }

        @Override
        public V delete(K key) {
            if (!inRange(key)) {
                return null;
            }
            return m.delete(key);
        }

        @Override
        public int size() {
            K fk = firstKey();
            if (fk == null) {
                return 0;
            }
            K lk = lastKey();
            if (lk == null) {
                return 0;
            }
            return m.rank(lk) - m.rank(fk) + 1;
        }

        @Override
        public Collection <K> keys() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Collection <V> values() {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsKey(K key) {
            if (!inRange(key)) {
                return false;
            }
            return m.containsKey(key);
        }

        @Override
        public SortedST <K, V> subST(K fromKey, K toKey) {
            return new AscendingSubST <K, V>(m, fromKey, toKey, true, true);
        }

        @Override
        public K firstKey() {
            int index = m.rank(lo);
            if (index >= m.size()) {
                return null;
            }
            int cmp = lo.compareTo(m.entries[index].getKey());
            if (cmp < 0) {
                return m.entries[index].getKey();
            } else if (cmp > 0) {
                return null;
            } else {
                if (index + 1 >= m.size && !loInclusive) {
                    return null;
                }
                return m.entries[loInclusive ? index : index + 1].getKey();
            }
        }

        @Override
        public K lastKey() {
            int index = m.rank(hi);
            if (index >= m.size()) {
                return m.lastKey();
            }
            int cmp = hi.compareTo(m.entries[index].getKey());
            if (cmp > 0) {
                return m.entries[index].getKey();
            } else if (cmp < 0) {
                return null;
            } else {
                // find
                if (index - 1 < 0 && !hiInclusive) {
                    return null;
                }
                return m.entries[hiInclusive ? index : index - 1].getKey();
            }
        }

        private boolean inRange(K key) {
            int cmpl = key.compareTo(lo);
            if (cmpl < 0 || (cmpl == 0 && !loInclusive)) {
                return false;
            }
            int cmpr = key.compareTo(hi);
            if (cmpr > 0 || (cmpr == 0 && !hiInclusive)) {
                return false;
            }
            return true;
        }

        @Override
        public boolean isEmpty() {
            return super.isEmpty();
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder('{');
            K fk = firstKey();
            K lk = lastKey();
            if (fk != null && lk != null) {
                int start = m.rank(fk);
                int end = m.rank(lk);
                if (!loInclusive) {
                    start++;
                }
                if (!hiInclusive) {
                    end--;
                }
                for (int i = start; i <= end; i++) {
                    builder.append(m.entries[i].getKey());
                    builder.append('=');
                    builder.append(m.entries[i].getValue());
                    if (i < end) {
                        builder.append(',').append(' ');
                    }
                }
            }
            builder.append('}');
            return builder.toString();
        }
    }


    class Keys extends AbstractCollection <K> {

        @Override
        public Iterator <K> iterator() {
            return new KeysIterator();
        }

        @Override
        public int size() {
            return ItemBinarySearchST.this.size();
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
                ItemBinarySearchST.this.delete(next());
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
            return ItemBinarySearchST.this.size();
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
                ItemBinarySearchST.this.delete(entries[cursor].getKey());
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
        ItemBinarySearchST <Integer, String> st = new ItemBinarySearchST <>(16);

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
        st.delete(5);

        System.out.println(st.subST(5, 18));
    }

}
