package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;

import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * 红黑树（高度平衡的二叉树）实现的符号表
 * <p>
 * 红黑树的高度平衡是相对的
 * </p>
 * <p>
 * 性质：
 * 1.没有结点可以同时和两条红链接相连。
 * 2.红链接均为左链接。
 * 3.任意空链接到根结点路径上黑链接数量相同。
 * </p>
 * <p>
 * 性质简单版：
 * 1.结点非黑即白。
 * 2.根结点是黑色。
 * 3.叶子结点（空结点）是黑色。
 * 4.红色结点的子结点肯定是黑色，父亲肯定是黑色。
 * 5.任意结点到叶子结点包含相同的黑色结点。（如果一个结点存在黑子结点，则结点肯定包含相同结点）
 * </p>
 *
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

    private transient int size = 0;

    public RedBlackTreeST() {
    }

    @Override
    public V put(K key, V value) {
//        root = put(root, key, value);
//        root.color = BLACK;
        Entry<K, V> t = root;
        if (t == null) {
            root = new Entry<>(null, key, value);
            setColor(root, BLACK);
            size++;
            return null;
        }
        Entry<K, V> p;
        int cmp;
        do {
            cmp = compare(key, t.getKey());
            if (cmp < 0) {
                p = t;
                t = t.left;
            } else if (cmp > 0) {
                p = t;
                t = t.right;
            } else {
                V old = t.getValue();
                t.setValue(value);
                return old;
            }
        } while (t != null);
        Entry<K, V> e = new Entry<>(p, key, value);
        if (cmp > 0) {
            p.right = e;
        } else {
            p.left = e;
        }
        size++;
        fixAfterInsertion(e);
        return null;
    }

    private Entry<K, V> put(Entry<K, V> h, K key, V val) {
        if (h == null) {
            return new Entry<>(null, key, val);
        }
        int cmp = compare(key, h.key);
        if (cmp < 0) {
            Entry<K, V> e = put(h.left, key, val);
            e.parent = h;
            h.left = e;
        } else if (cmp > 0) {
            Entry<K, V> e = put(h.left, key, val);
            e.parent = h;
            h.right = e;
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


    @Override
    public V get(K key) {
        Entry<K, V> t = root;
        while (t != null) {
            int cmp = compare(key, t.getKey());
            if (cmp == 0) {
                return t.getValue();
            } else if (cmp > 0) {
                t = t.right;
            } else {
                t = t.left;
            }
        }
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

    /**
     * @param k1 比较的键1
     * @param k2 比较的键2
     * @return
     */
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

    private static <K, V> boolean isRed(Entry<K, V> p) {
        if (p == null) {
            return BLACK;
        }
        return p.color == RED;
    }

    private static <K, V> Entry<K, V> leftOf(Entry<K, V> p) {
        return p == null ? null : p.left;
    }

    private static <K, V> Entry<K, V> rightOf(Entry<K, V> p) {
        return p == null ? null : p.right;
    }

    private static <K, V> Entry<K, V> parentOf(Entry<K, V> p) {
        return p == null ? null : p.parent;
    }

    private static <K, V> void setColor(Entry<K, V> p, boolean color) {
        if (p != null) {
            p.setColor(color);
        }
    }

    private void fixAfterInsertion(Entry<K, V> x) {
        x.color = RED;

        while (x != null && !isRed(parentOf(x)) && x != root) {
            // x当前结点，p为x的父节点，g为x的爷爷结点，y为x的叔叔结点
            Entry<K, V> p = parentOf(x);
            Entry<K, V> g = parentOf(parentOf(x));
            Entry<K, V> y = rightOf(g);

            // left-case1: (p为g的左孩子)，y为红色，x可左可右。 fix：p、y染黑，g染红，x回溯到g。
            // left-case2: (p为g的左孩子)，y为黑色，x为右孩子。 fix：左旋p，x指向p，转到case3。
            // left-case3: (p为g的左孩子)，y为黑色，x为左孩子。 fix：p染黑，g染红，右旋g。

            // right-case1: (p为g的右孩子)，y为红色，x可左可右。 fix：p、y染黑，g染红，x回溯到g。
            // right-case2: (p为g的右孩子)，y为黑色，x为左孩子。 fix：右旋p，x指向p，转到case3。
            // right-case3: (p为g的右孩子)，y为黑色，x为右孩子。 fix：p染黑，g染红，左旋g。

            // Notice：case1引起的rootOver会导致数的黑高增加1，是唯一会增加黑高的情况
            if (p == leftOf(g)) {
                if (isRed(y)) {
                    // left-case1
                    setColor(p, BLACK);
                    setColor(y, BLACK);
                    setColor(g, RED);
                    x = g;
                } else {
                    if (x == rightOf(p)) {
                        // left-case2
                        x = p;
                        rotateLeft(p);
                        // to case3
                    }
                    // left-case3
                    setColor(p, BLACK);
                    setColor(g, RED);
                    rotateRight(g);
                }
            } else {
                if (isRed(y)) {
                    // right-case1
                    setColor(p, BLACK);
                    setColor(y, BLACK);
                    setColor(g, RED);
                    x = g;
                } else {
                    if (x == leftOf(p)) {
                        // right-case2
                        x = p;
                        rotateRight(p);
                        // to case3
                    }
                    // right-case3
                    setColor(p, BLACK);
                    setColor(g, RED);
                    rotateLeft(g);
                }
            }
        }

        root.color = BLACK;
    }


    /**
     * 左旋
     *
     * @param h
     * @return
     */
    private Entry<K, V> rotateLeft(Entry<K, V> h) {
        if (h == null) {
            return null;
        }

        Entry<K, V> x = h.right;

        // 调整parent
        if (x.left != null) {
            x.left.parent = h;
        }
        Entry<K, V> p = parentOf(h);
        if (p != null) {
            if (p.left == h) {
                p.left = x;
            } else {
                p.right = x;
            }
        } else {
            root = x;
        }

        x.parent = p;
        h.parent = x;

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
        if (h == null) {
            return null;
        }
        Entry<K, V> x = h.left;
        Entry<K, V> p = parentOf(h);

        // 调整parent
        if (x.right != null) {
            x.right.parent = h;
        }
        if (p != null) {
            if (p.left == h) {
                p.left = x;
            } else {
                p.right = x;
            }
        } else {
            this.root = x;
        }
        x.parent = p;
        h.parent = x;

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

        public Entry(Entry<K, V> parent, K key, V val) {
            this.parent = parent;
            this.key = key;
            this.val = val;
        }

        public void setColor(boolean color) {
            this.color = color;
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

        keys = new int[100];
        for (int i = 0; i < 100; i++) {
            keys[i] = Util.nextInt(100);
        }

        for (int i = 0; i < keys.length; i++) {
            if (i == 2) {
                i = 2;
            }
            st.put(keys[i], "val" + keys[i]);
        }
        System.out.println(st);
    }

}
