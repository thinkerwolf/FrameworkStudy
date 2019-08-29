package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.io.Serializable;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Iterator;

/**
 * 无序链表实现的符号表，效率比较低
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class SequentialSearchST<K, V> implements ST <K, V>, Serializable, Cloneable {

    private Entry <K, V> head;

    private int size;

    @Override
    public V put(K key, V value) {
        check(key, value);
        if (head == null) {
            head = new Entry <K, V>(key, value);
            size++;
            return null;
        }
        V oldValue = null;
        for (Entry <K, V> en = head; en != null; en = en.next) {
            if (en.getKey().equals(key)) {
                oldValue = en.getValue();
                en.setValue(value);
                break;
            } else if (en.next == null) {
                en.next = new Entry <K, V>(key, value);
                size++;
                break;
            }
        }
        return oldValue;
    }

    @Override
    public V get(K key) {
        if (head == null) {
            return null;
        }
        Entry <K, V> en = head;
        V value = null;
        for (; en != null; ) {
            if (en.getKey().equals(key)) {
                value = en.getValue();
                break;
            }
            en = en.next;
        }
        return value;
    }

    @Override
    public V delete(K key) {
        if (head == null) {
            return null;
        }
        if (head.getKey().equals(key)) {
            V value = head.getValue();
            Entry <K, V> newHead = head.next;
            head.next = null;
            head = newHead;
            size--;
            return value;
        }
        V value = null;
        for (Entry <K, V> en = head; en.next != null; en = en.next) {
            if (en.next.getKey().equals(key)) {
                value = en.next.getValue();
                en.next = en.next.next;
                size--;
                break;
            }
        }
        return value;
    }

    private static <K, V> void check(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
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

    class Keys extends AbstractCollection <K> {

        @Override
        public Iterator <K> iterator() {
            return new KeysIterator(head);
        }

        @Override
        public int size() {
            return SequentialSearchST.this.size();
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
                Entry <K, V> en = entry;
                entry = en.next;
                SequentialSearchST.this.delete(en.getKey());
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
            return new ValueIterator(head);
        }

        @Override
        public int size() {
            return SequentialSearchST.this.size();
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
                Entry <K, V> en = entry;
                entry = en.next;
                SequentialSearchST.this.delete(en.getKey());
                exceptCount = size();
            }
        }

        final void checkForComodification() {
            if (exceptCount != size) {
                throw new ConcurrentModificationException();
            }
        }
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("{");
        Entry <K, V> en = head;
        for (; en != null; ) {
            builder.append(en.getKey());
            builder.append(":");
            builder.append(en.getValue());
            en = en.next;
            if (en != null) {
                builder.append(", ");
            }
        }
        builder.append("}");
        return builder.toString();
    }

    private static class Entry<K, V> implements ST.Entry <K, V> {
        Entry <K, V> next;
        K key;
        V value;

        Entry(K key, V value) {
            this(key, value, null);
        }

        Entry(K key, V value, Entry <K, V> next) {
            this.key = key;
            this.value = value;
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

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Entry <?, ?> entry = (Entry <?, ?>) o;

            if (!key.equals(entry.key)) return false;
            return value.equals(entry.value);
        }

        @Override
        public int hashCode() {
            int result = key.hashCode();
            result = 31 * result + value.hashCode();
            return result;
        }
    }

    public static void main(String[] args) {
        ST <Integer, String> st = new SequentialSearchST <>();
        st.put(1, "A");
        st.put(2, "B");
        st.put(100, "C");
        System.out.println(st.get(1));
        System.out.println(st.get(100));
        System.out.println(st.get(2));
        System.out.println(st.get(90));

        for (Integer k : st.keys()) {
            System.out.println("iterator key -> " + k);
        }

        for (String v : st.values()) {
            System.out.println("iterator value -> " + v);
        }

        //System.out.println(st.delete(100));
        System.out.println(st.delete(1));
        System.out.println(st.delete(80));

        System.out.println(st);


        Iterator <Integer> iterator = st.keys().iterator();
        while (iterator.hasNext()) {
//            Integer key = iterator.next();
            st.delete(2);
            iterator.remove();
        }

        System.out.println(st);
    }

}
