package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 红黑树（高度平衡的二叉树）实现的符号表
 * <p>
 *     性质：
 *     1.没有结点可以同时和两条红链接相连。
 *     2.红链接均为左链接。
 *     3.任意空链接到根结点路径上黑链接数量相同。
 * </p>
 * <p>
 *     性质简单版：
 *     1.结点非黑即白。
 *     2.根结点是黑色。
 *     3.叶子结点（空结点）是黑色。
 *     4.红色结点的子结点肯定是黑色。
 *     5.任意结点到叶子结点包含相同的黑色结点。（如果一个结点存在黑子结点，则结点肯定包含相同结点）
 * </p>
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class RedBlackTreeST<K, V> extends AbstractST<K, V> {
    /**
     * comparator
     */
    private Comparator<K> comparator;

    private Entry<K, V> root;

    public RedBlackTreeST(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    public RedBlackTreeST() {
    }

    @Override
    public V put(K key, V value) {
        root = put(root, key, value);
        root.color = BLACK;
        return null;
    }

    private Entry<K, V> put(Entry<K, V> h, K key, V val) {
        if (h == null) {
            return new Entry<>(key, val, RED);
        }
        int cmp = compare(key, h.key);
        if (cmp < 0) {
            h.left = put(h.left, key, val);
        } else if (cmp > 0) {
            h.right = put(h.right, key, val);
        } else {
            h.setValue(val);
        }
        if (isRed(h.right) && !isRed(h.left)) {
            h = rotateLeft(h);
        }
        if (isRed(h.left) && isRed(h.left.left)) {
            h = rotateRight(h);
        }
        if (isRed(h.left) && isRed(h.right)) {
            flipColors(h);
        }
        return h;
    }




    private boolean isRed(Entry<K, V> en) {
        if (en == null) {
            // 空结点默认为黑
            return false;
        }
        return en.isRed();
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
        return 0;
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

    private int compare(K k1, K k2) {
        if (comparator != null) {
            return comparator.compare(k1, k2);
        }
        if (!(k1 instanceof Comparable)) {
            throw new IllegalArgumentException(k1.getClass().getSimpleName() + " is not inherit from Comparable");
        }
        return ((Comparable<K>) k1).compareTo(k2);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // 中序遍历
        builder.append('{');
        LinkedList<Entry<K, V>> stack = new LinkedList<>();
        Entry<K, V> p = root;
        do {
            while (p != null) {
                stack.add(p);
                p = p.left;
            }
            if (stack.size() > 0) {
                p = stack.removeLast();
                builder.append(p.getKey());
                builder.append('=');
                builder.append(p.getValue());
                p = p.right;
            }

            if (p == null && stack.size() <= 0) {
                builder.append('}');
            } else {
                builder.append(',').append(' ');
            }

        } while (p != null || stack.size() > 0);
        return builder.toString();
    }

    // Red-Black operation

    /**
     * 左旋
     *
     * @param h
     * @return
     */
    private Entry<K, V> rotateLeft(Entry<K, V> h) {
        Entry<K, V> x = h.right;
        h.right = x.left;
        x.left = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /**
     * 右旋
     *
     * @param h
     * @return
     */
    private Entry<K, V> rotateRight(Entry<K, V> h) {
        Entry<K, V> x = h.left;
        h.left = x.right;
        x.right = h;
        x.color = h.color;
        h.color = RED;
        return x;
    }

    /**
     * 颜色转化
     *
     * @param h
     */
    private void flipColors(Entry<K, V> h) {
        h.color = RED;
        h.left.color = BLACK;
        h.right.color = BLACK;

    }


    private static final boolean RED = false;
    private static final boolean BLACK = true;

    static final class Entry<K, V> implements ST.Entry<K, V> {
        K key;
        V val;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;
        boolean color = RED;

        public Entry(K key, V val, boolean color) {
            this.key = key;
            this.val = val;
            this.color = color;
        }

        public boolean isRed() {
            return color == RED;
        }

        public boolean isBlack() {
            return color == BLACK;
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
        RedBlackTreeST<Integer, String> st = new RedBlackTreeST<>();
        int[] keys = new int[]{20, 10, 30};

        for (int i = 0; i < keys.length; i++) {
            if (i == 2) {
                i = 2;
            }
            st.put(keys[i], "val" + keys[i]);
        }
        System.out.println(st);
    }

}
