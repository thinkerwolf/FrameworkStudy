package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.*;

/**
 * 有序链表实现的符号表
 *
 * @param <K>
 * @param <V>
 */
public class OrderedSequentialSearchST<K extends Comparable <K>, V> implements ST <K, V> {
    /**
     * Pointer to first entry
     */
    private transient Entry <K, V> first;
    /**
     * Pointer to last entry
     */
    private transient Entry <K, V> last;

    private transient int size;

    public OrderedSequentialSearchST() {

    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        // 队列为空
        if (first == null) {
            first = new Entry <>(key, value, null, null);
            last = first;
            size++;
            return null;
        }

        Entry <K, V> find = null;
        Entry <K, V> nextInsert = null;
        for (Entry <K, V> fe = first; fe != null; ) {
            int cmpf = key.compareTo(fe.getKey());
            if (cmpf == 0) {
                find = fe;
                break;
            } else if (cmpf < 0) {
                nextInsert = fe;
                break;
            } else if (cmpf > 0) {
                fe = fe.next;
            }
        }

        // 找到进行替换
        if (find != null) {
            V oldVal = find.getValue();
            find.setValue(value);
            return oldVal;
        }

        // 没有找到
        if (nextInsert == null) {
            // 插入尾部
            Entry <K, V> en = new Entry <>(key, value, last, null);
            last.next = en;
            last = en;
        } else {
            // 插入到头部或者中间位置
            Entry <K, V> nextInsertPre = nextInsert.pre;
            Entry <K, V> en = new Entry <>(key, value, nextInsertPre, nextInsert);
            nextInsert.pre = en;
            if (nextInsertPre == null) {
                first = en;
            } else {
                nextInsertPre.next = en;
            }
        }
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        Entry <K, V> find = node(key);
        return find == null ? null : find.getValue();
    }

    @Override
    public V delete(K key) {
        if (isEmpty()) {
            return null;
        }
        Entry <K, V> find = node(key);
        V val = null;
        if (find != null) {
            val = find.getValue();
            if (find.pre != null || find.next != null) {
                if (find.next != null) {
                    find.next.pre = find.pre;
                } else {
                    last = find.pre;
                }
                if (find.pre != null) {
                    find.pre.next = find.next;
                } else {
                    first = find.next;
                }
            } else {
                first = last = null;
            }
            size--;
        }
        return val;
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
        return null;
    }

    @Override
    public Collection <V> values() {
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    class Keys extends AbstractCollection <K> {

        @Override
        public Iterator <K> iterator() {
            return new KeysIterator(first);
        }

        @Override
        public int size() {
            return OrderedSequentialSearchST.this.size();
        }
    }

    class KeysIterator implements Iterator <K> {
        Entry <K, V> entry;
        int exceptCount;

        KeysIterator(Entry <K, V> entry) {
            this.entry = entry;
            this.exceptCount = size();
        }

        @Override
        public boolean hasNext() {
            return entry != null;
        }

        @Override
        public K next() {
            Entry <K, V> en = entry;
            checkForComodification();
            entry = en.next;
            return en.getKey();
        }

        @Override
        public void remove() {
            checkForComodification();
            if (hasNext()) {
                Entry<K, V> en = entry;
                entry = en.next;
                OrderedSequentialSearchST.this.delete(en.getKey());
                exceptCount = size();
            }
        }

        final void checkForComodification() {
            if (exceptCount != size) {
                throw new ConcurrentModificationException();
            }
        }

    }

    class Values extends AbstractCollection <V> {

        @Override
        public Iterator <V> iterator() {
            return new ValueIterator(first);
        }

        @Override
        public int size() {
            return OrderedSequentialSearchST.this.size();
        }

    }

    class ValueIterator implements Iterator <V> {

        Entry <K, V> entry;
        int exceptCount;

        ValueIterator(Entry <K, V> entry) {
            this.entry = entry;
            this.exceptCount = size();
        }

        @Override
        public boolean hasNext() {
            return entry != null;
        }

        @Override
        public V next() {
            Entry <K, V> en = entry;
            checkForComodification();
            entry = en.next;
            return en.getValue();
        }

        @Override
        public void remove() {
            checkForComodification();
            if (hasNext()) {
                Entry<K, V> en = entry;
                entry = en.next;
                OrderedSequentialSearchST.this.delete(en.getKey());
                exceptCount = size();
            }
        }

        final void checkForComodification() {
            if (exceptCount != size) {
                throw new ConcurrentModificationException();
            }
        }
    }


    private Entry <K, V> node(K key) {
        for (Entry <K, V> fe = first; fe != null; ) {
            int cmpf = key.compareTo(fe.getKey());
            if (cmpf == 0) {
                return fe;
            }
            fe = fe.next;
        }
        return null;
    }

    static final class Entry<K extends Comparable, V> implements ST.Entry <K, V> {
        Entry <K, V> pre;
        Entry <K, V> next;
        K key;
        V value;

        Entry(K key, V value, Entry <K, V> pre, Entry <K, V> next) {
            this.key = key;
            this.value = value;
            this.pre = pre;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public void setValue(V v) {
            this.value = v;
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        Entry <K, V> en = first;
        builder.append('{');
        while (en != null) {
            builder.append(en.getKey());
            builder.append('=');
            builder.append(en.getValue());
            en = en.next;
            if (en != null) {
                builder.append(',').append(' ');
            } else {
                builder.append('}');
            }
        }
        return builder.toString();
    }

    public static void main(String[] args) {
        ST <Integer, String> st = new OrderedSequentialSearchST <>();
        Random r = new Random();
        Integer[] keys = new Integer[]{5, 6, 17, 4, 17, 9, 13, 15, 4, 14, 19, 6, 8, 13, 2, 16, 5, 12, 19, 18};
        for (int i = 1; i <= 20; i++) {
            int key = keys[i - 1];
            System.out.println("key -> " + key);
            st.put(key, "v" + key);
            System.out.println(st);
        }

        System.out.println(st.get(5));
        System.out.println(st.get(19));
        System.out.println(st.get(48));

        st.delete(17);
        st.delete(2);

        st.put(188, "v" + 188);

        System.out.println(st.size());

    }

}
