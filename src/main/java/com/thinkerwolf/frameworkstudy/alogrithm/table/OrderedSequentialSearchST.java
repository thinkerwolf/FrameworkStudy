package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;
import java.util.Random;

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
        V oldVal = null;
        Entry <K, V> p = sink(key);
        if (p == null) {
            if (first == null) {
                first = new Entry <>(key, value, null, null);
                last = first;
            } else {
                // 插入尾部
                Entry <K, V> en = new Entry <>(key, value, last, null);
                last.next = en;
                last = en;
            }
        } else {
            if (p.pre != null) {
                oldVal = p.pre.getValue();
                p.pre.setValue(value);
            } else {
                // 插入首位置
                Entry <K, V> en = new Entry <>(key, value, null, first);
                first.pre = en;
                first = en;
            }
        }
        return oldVal;
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

    /**
     * 永远返回需要插入位置的后一个Entry
     *
     * @param key
     * @return
     */
    private Entry <K, V> sink(K key) {
        if (first == null) {
            return null;
        }
        Entry <K, V> find = null;
        Entry <K, V> fe = first;
        Entry <K, V> le = last;
        for (; !(fe == null && le == null) && !equals(fe, le); ) {
            if (fe != null) {
                int cmpl = key.compareTo(fe.getKey());
                if (cmpl < 0) {
                    return fe;
                } else if (cmpl > 0) {
                    fe = fe.next;
                } else {
                    find = fe;
                    fe = fe.next;
                }
            }
            if (le != null) {
                int cmpr = key.compareTo(le.getKey());
                if (cmpr >= 0) {
                    return le.next;
                } else {
                    le = le.pre;
                }
            }
        }
        return find;
    }


    private static boolean equals(Entry entry1, Entry entry2) {
        if (entry1 == null && entry2 == null) {
            return true;
        }
        if (entry1 != null && entry2 != null) {
            return entry1.getKey().compareTo(entry2.getKey()) == 0;
        }
        return false;
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
        for (int i = 1; i <= 20; i++) {
            int key = r.nextInt(20 - 1) + 1;
            st.put(key, "v" + key);
        }
        System.out.println(st);
    }

}
