package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 跳表实现
 *
 * @param <K>
 * @param <V>
 */
public class SkipListST<K, V> implements ST<K, V> {

    private static final Object DEFAULT_HEADER_VAL = new Object();

    private HeadIndex<K, V> head;

    private Comparator<K> comparator;

    private int size;

    public SkipListST() {
        this.head = new HeadIndex(new Node(null, DEFAULT_HEADER_VAL, null), null, null, 1);
    }

    private static <K> int cmpx(Comparator<K> comparator, K k1, K k2) {
        return comparator != null ? comparator.compare(k1, k2) : ((Comparable) k1).compareTo(k2);
    }

    @Override
    public V put(K key, V value) {
        if (value == null) {
            throw new NullPointerException();
        }
        Node<K, V> z;
        for (Node<K, V> b = findPredecessor(key), n = b.next; ; ) {
            if (n != null) {
                int cmp = cmpx(comparator, key, n.getKey());
                if (cmp == 0) {
                    V oldV = n.getValue();
                    n.setValue(value);
                    return oldV;
                } else if (cmp > 0) {
                    b = n;
                    n = b.next;
                    continue;
                }
            }
            z = new Node<>(key, value, n);
            b.next = z;
            break;
        }

        boolean b = ThreadLocalRandom.current().nextInt(100) < 50; //test

        int rdm = ThreadLocalRandom.current().nextInt();
        //if ((rdm & 0x80000001) == 0) { // 10000000 00000000 00000000 00000001
        if (b) {
            HeadIndex<K, V> h = head;
            int level = 1, max = h.level;
            while (((rdm >>>= 1) & 1) != 0) { // max height = 31
                level++;
            }
            Index<K, V> idx = null;
            if (level <= max) { // 不增加高度，但是将新增节点延申出来索引
                for (int i = 1; i <= level; i++) {
                    idx = new Index<>(z, null, idx);
                }
            } else {
                // add new level
                level = max + 1;
                for (int i = 1; i <= level; i++) {
                    idx = new Index<>(z, null, idx);
                }
                HeadIndex<K, V> hidx = h;
                for (int l = max + 1; l <= level; l++) {
                    hidx = new HeadIndex<>(hidx.node, null, hidx, l);
                }
                casHead(h, hidx);
                h = hidx;
            }

            int insertLevel = level;
            level = h.level;
            for (; ; --level) {
                if (level > insertLevel) {
                    h = (HeadIndex<K, V>) h.down;
                    continue;
                }
                if (insertLevel <= 0) {
                    break;
                }
                for (Index<K, V> p = h, r = p.right; ; ) {
                    if (r == null || cmpx(comparator, idx.node.getKey(), r.node.getKey()) < 0) {
                        p.right = idx;
                        idx.right = r;
                        break;
                    }
                    p = r;
                    r = p.right;
                }
                idx = idx.down;
                h = (HeadIndex<K, V>) h.down;
                insertLevel--;
            }
        }
        this.size++;
        return null;
    }

    /**
     * 找到索引表中的前置节点
     *
     * @param key
     * @return
     */
    private Node<K, V> findPredecessor(K key) {
        if (key == null) {
            throw new NullPointerException();
        }
        final Comparator<K> cmp = this.comparator;
        for (Index<K, V> q = head, r = q.right, d;;) {
            if (r != null) {
                Node<K, V> n = r.node;
                K k = n.getKey();
                if (cmpx(cmp, key, k) > 0) {
                    q = r;
                    r = r.right;
                    continue;
                }
            }
            d = q.down;
            if (d == null) {
                return q.node;
            }
            q = d;
            r = q.right;
        }
    }

    private void casHead(HeadIndex<K, V> except, HeadIndex<K, V> update) {
        this.head = update;
    }

    @Override
    public V get(K key) {
        for (Node<K, V> b = findPredecessor(key), n = b.next; ; ) {
            if (n != null) {
                int cmp = cmpx(comparator, key, n.getKey());
                if (cmp == 0) {
                    return n.getValue();
                } else if (cmp > 0) {
                    b = n;
                    n = b.next;
                    continue;
                }
                break;
            }
        }
        return null;
    }

    @Override
    public V delete(K key) {
        for (Node<K, V> b = findPredecessor(key), n = b.next; ; ) {
            if (n != null) {
                int cmp = cmpx(comparator, key, n.getKey());
                if (cmp == 0) {
                    V oldV = n.getValue();
                    // 找到节点并删除
                    b.next = n.next;
                    n.next = null;
                    // 索引删除
                    HeadIndex h = head;
                    for (; h != null; h = (HeadIndex) h.down) {
                        for (Index<K, V> p = h, r = p.right; r != null; ) {
                            if (cmpx(comparator, key, r.node.getKey()) == 0) {
                                p.right = r.right;
                                r.right = null;
                                break;
                            }
                            p = r;
                            r = p.right;
                        }
                    }
                    tryReduceLevel();
                    size--;
                    return oldV;
                } else if (cmp > 0) {
                    b = n;
                    n = b.next;
                    continue;
                }
                break;
            }
        }
        return null;
    }

    private void tryReduceLevel() {
        if (head.right == null) {
            if (head.level > 1) {
                HeadIndex d = (HeadIndex) head.down;
                head.down = null;
                casHead(head, d);
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
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

    @Override
    public String toString() {
        Node<K, V> node = head.node;
        StringBuilder sb = new StringBuilder();
        if (node.next == null) {
            sb.append("{}");
            return sb.toString();
        }
        sb.append("{");
        for (Node<K, V> r = node.next; r != null; r = r.next) {
            sb.append(r.getKey());
            sb.append("=");
            sb.append(r.getValue());
            if (r.next != null) {
                sb.append(", ");
            } else {
                sb.append("}");
                break;
            }
        }
        return sb.toString();
    }

    public String printIdx() {
        StringBuilder sb = new StringBuilder();
        HeadIndex<K, V> h = this.head;
        for (; h != null; h = (HeadIndex<K, V>) h.down) {
            sb.append("L").append(h.level).append(" ");
            for (Index<K, V> r = h.right; r != null; r = r.right) {
                sb.append(r.node.getKey());
                if (r.right == null) {
                    break;
                } else {
                    sb.append("-");
                }
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public Index checkIdx() {
        Index idx = this.head;
        Set<Index> set = new HashSet<>();
        for (Index n = idx; n != null; n = n.down) {
            if (set.contains(n)) {
                set.clear();
                return n;
            }
            set.add(n);
        }
        set.clear();
        return null;
    }

    static class Index<K, V> {
        Node<K, V> node;
        Index<K, V> right;
        Index<K, V> down;

        public Index(Node<K, V> node, Index<K, V> right, Index<K, V> down) {
            this.node = node;
            this.right = right;
            this.down = down;
        }

        @Override
        public String toString() {
            return new StringBuilder().append(node.getKey()).append("=").append(node.getValue()).toString();
        }
    }

    static class HeadIndex<K, V> extends Index<K, V> {
        int level;

        HeadIndex(Node<K, V> node, Index<K, V> right, Index<K, V> down) {
            super(node, right, down);
        }

        HeadIndex(Node<K, V> node, Index<K, V> right, Index<K, V> down, int level) {
            super(node, right, down);
            this.level = level;
        }
    }

    static class Node<K, V> implements Entry<K, V> {
        private K k;
        private V v;
        private Node<K, V> next;

        Node(K k, V v, Node<K, V> next) {
            this.k = k;
            this.v = v;
            this.next = next;
        }

        @Override
        public K getKey() {
            return k;
        }

        @Override
        public V getValue() {
            return v;
        }

        @Override
        public void setValue(V v) {
            this.v = v;
        }
    }

}
