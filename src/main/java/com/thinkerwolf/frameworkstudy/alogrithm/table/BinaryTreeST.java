package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.SortedST;
import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;

import java.util.*;

/**
 * 二叉查找树实现的<strong>符号表</strong>
 * 查找和插入的效率取决与树的高度
 * <strong>平均情况下查找和插入的效率还不错，但是在某些极端情况下效率并不是令人满意。比如数据是顺序插入但是树的高度为N</strong>
 *
 * @param <K>
 * @param <V>
 * @author wukai
 */
public class BinaryTreeST<K extends Comparable<K>, V> extends AbstractST<K, V> implements SortedST<K, V> {
    private transient int size;
    private transient Entry<K, V> root;

    public BinaryTreeST() {
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        Entry<K, V> en = root;
        while (en != null) {
            int cmp = key.compareTo(en.getKey());
            if (cmp == 0) {
                break;
            } else if (cmp < 0) {
                if (en.left == null) {
                    break;
                }
                en = en.left;
            } else {
                if (en.right == null) {
                    break;
                }
                en = en.right;
            }
        }
        if (en == null) {
            root = new Entry<>(key, value, null, null);
            size++;
            return null;
        }

        int cmp = key.compareTo(en.getKey());
        if (cmp == 0) {
            V oldVal = en.getValue();
            en.setValue(value);
            return oldVal;
        } else if (cmp < 0) {
            en.left = new Entry<>(key, value, null, null);
        } else {
            en.right = new Entry<>(key, value, null, null);
        }
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        if (isEmpty()) {
            return null;
        }
        Entry<K, V> en = root;
        while (en != null) {
            int cmp = key.compareTo(en.getKey());
            if (cmp > 0) {
                en = en.right;
            } else if (cmp < 0) {
                en = en.left;
            } else {
                return en.getValue();
            }
        }
        return null;
    }

    @Override
    public V delete(K key) {
        if (isEmpty()) {
            return null;
        }
        Entry<K, V> en = deleteNormal(key);
        if (en != null) {
            size--;
        }
        return en == null ? null : en.getValue();

//        this.root = delete(root, key);
//        return null;
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
        Entry<K, V> en = innerFind(root, key);
        if (en == null) {
            return false;
        }
        return key.compareTo(en.getKey()) == 0;
    }


    @Override
    public K firstKey() {
        Entry<K, V> en = root;
        while (en != null) {
            if (en.left == null) {
                return en.getKey();
            } else {
                en = en.left;
            }
        }
        return null;
    }

    @Override
    public K lastKey() {
        Entry<K, V> en = root;
        while (en != null) {
            if (en.right == null) {
                return en.getKey();
            } else {
                en = en.right;
            }
        }
        return null;
    }

    @Override
    public long memoryUsage() {
        if (isEmpty()) {
            return 0;
        }
        long m = 0;
        Deque<Entry<K, V>> stack = new LinkedList<>();
        stack.add(root);
        while (stack.size() > 0) {
            Entry<K, V> en = stack.pollLast();
            m += 2;
            if (en.left != null) {
                m += 1;
                stack.add(en.left);
            }
            if (en.right != null) {
                m += 1;
                stack.add(en.right);
            }
        }
        return m;
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

    /**
     * 在与树高度成正比的时间内返回一个随机的键
     *
     * @return
     */
    public K randomKey() {
        if (root == null) {
            return null;
        }
        Entry<K, V> en = root;
        while (en != null) {
            if (en.left == null && en.right == null) {
                return en.getKey();
            }
            if (Util.nextBoolean()) {
                return en.getKey();
            }
            en = Util.nextBoolean() && en.left != null ? en.left : en.right;
        }
        return null;
    }

    /**
     * 中序遍历树（递归版本）
     *
     * @param en
     * @param builder
     */
    private void inorderTraversalToString(Entry<K, V> en, StringBuilder builder) {
        if (en == null) {
            return;
        }
        inorderTraversalToString(en.left, builder);
        builder.append(en.getKey());
        builder.append('=');
        builder.append(en.getValue());
        builder.append(',').append(' ');
        inorderTraversalToString(en.right, builder);
    }

    /**
     * 根据key查找槽位（递归版）
     *
     * @param en
     * @param key
     * @return
     */
    private Entry<K, V> innerFind(Entry<K, V> en, K key) {
        if (en == null) {
            return null;
        }
        int cmp = key.compareTo(en.getKey());
        if (cmp == 0) {
            return en;
        } else if (cmp < 0) {
            return en.left == null ? en : innerFind(en.left, key);
        } else {
            return en.right == null ? en : innerFind(en.right, key);
        }
    }

    /**
     * 递归删除一个元素
     *
     * @param en
     * @param key
     * @return
     */
    private Entry<K, V> delete(Entry<K, V> en, K key) {
        if (en == null) {
            return null;
        }
        int cmp = key.compareTo(en.getKey());
        if (cmp > 0) {
            en.right = delete(en.right, key);
        } else if (cmp < 0) {
            en.left = delete(en.left, key);
        } else {
            if (en.right == null) {
                return en.left;
            }
            if (en.left == null) {
                return en.right;
            }
            Entry<K, V> t = en;
            en = min(t.right);
            en.right = deleteMin(t.right);
            en.left = t.left;
        }
        return en;
    }

    /**
     * 非递归删除一个元素
     *
     * @param key key
     * @return 被删除的元素
     */
    private Entry<K, V> deleteNormal(K key) {
        // 需要删除的元素
        Entry<K, V> en = root;
        // 需要删除元素的父节点
        Entry<K, V> parent = root;
        while (en != null) {
            int cmp = key.compareTo(en.getKey());
            if (cmp < 0) {
                parent = en;
                en = en.left;
            } else if (cmp > 0) {
                parent = en;
                en = en.right;
            } else {
                break;
            }
        }
        Entry<K, V> t = en;
        if (t == null) {
            return null;
        }

        if (t.left == null) {
            en = t.right;
        } else if (t.right == null) {
            en = t.left;
        } else {
            en = min(t.right);
            en.right = deleteMinNormal(t.right);
            en.left = t.left;
        }

        if (t != root) {
            if (parent.left == t) {
                parent.left = en;
            } else {
                parent.right = en;
            }
        } else {
            root = en;
        }
        t.left = null;
        t.right = null;
        return t;
    }

    private Entry<K, V> min(Entry<K, V> root) {
        Entry<K, V> t = root;
        while (t != null) {
            if (t.left == null) {
                return t;
            }
            t = t.left;
        }
        return null;
    }

    /**
     * 删除最小元素（递归版本）
     *
     * @param root 根节点
     * @return 删除后的root
     */
    private Entry<K, V> deleteMin(Entry<K, V> root) {
        if (root == null) {
            return null;
        }
        if (root.left == null) {
            return root.right;
        }
        root.left = deleteMin(root.left);
        return root;
    }

    /**
     * 删除最小元素（非递归版本）
     *
     * @param root 根节点
     * @return 删除后的根节点
     */
    private Entry<K, V> deleteMinNormal(Entry<K, V> root) {
        if (root == null) {
            return null;
        }
        Entry<K, V> en = root;
        Entry<K, V> parent = root;
        while (en != null) {
            if (en.left == null) {
                break;
            }
            parent = en;
            en = en.left;
        }

        if (parent.left == en) {
            parent.left = en.right;
        } else {
            parent.right = en.right;
        }
        if (en == root) {
            return root.right;
        } else {
            return root;
        }
    }

    /**
     * 返回树的深度（递归版本）
     *
     * @param root
     * @return
     */
    private int depth(Entry<K, V> root) {
        if (root == null) {
            return 0;
        }
        int l = depth(root.left);
        int r = depth(root.right);
        return Math.max(l, r) + 1;
    }

    /**
     * 是否是二叉树
     *
     * @param root
     * @return
     */
    private boolean isBinaryTree(Entry<K, V> root) {
        if (root == null) {
            return true;
        }
        List<K> list = new ArrayList<>(size);
        Deque<Entry<K, V>> stack = new LinkedList<>();
        stack.add(root);
        while (!stack.isEmpty()) {
            Entry<K, V> en = stack.pop();
            if (list.contains(en.getKey())) {
                return false;
            }
            list.add(en.getKey());
            if (en.left != null) {
                stack.push(en.left);
            }
            if (en.right != null) {
                stack.push(en.right);
            }
        }
        return true;
    }

    /**
     * 是否是排序的树
     *
     * @param root
     * @return
     */
    private boolean isSorted(Entry<K, V> root) {
        // 中序遍历
        Deque<Entry<K, V>> stack = new LinkedList<>();
        stack.add(root);
        Entry<K, V> p = root;
        K k = null;
        do {
            while (p != null) {
                stack.push(p);
                p = p.left;
            }
            if (!stack.isEmpty()) {
                p = stack.pop();
                if (k != null) {
                    int cmp = p.getKey().compareTo(k);
                    if (cmp < 0) {
                        return false;
                    }
                }
                k = p.getKey();
                p = p.right;
            }
        } while (p != null && !stack.isEmpty());
        return true;
    }


    static final class Entry<K extends Comparable<K>, V> implements ST.Entry<K, V> {
        K key;
        V val;
        Entry<K, V> left;
        Entry<K, V> right;

        Entry(K key, V val, Entry<K, V> left, Entry<K, V> right) {
            this.key = key;
            this.val = val;
            this.left = left;
            this.right = right;
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

        @Override
        public String toString() {
            return key + "=" + val;
        }
    }

    public static void main(String[] args) {
        BinaryTreeST<Integer, String> st = new BinaryTreeST<>();
        int[] keyArr = new int[]{
                19, 7, 14, 7, 19, 8, 18, 16, 8, 17, 12, 11, 19, 14, 18, 7, 20, 9, 11
        };
        for (int i = 0; i < keyArr.length; i++) {
            st.put(keyArr[i], "val" + keyArr[i]);
            st.put(i + 1, "another" + (i + 1));
        }
        System.out.println(st);
        System.out.println(st.delete(7));

        System.out.println(st.isBinaryTree(st.root));
        System.out.println(st.isSorted(st.root));
        try {
            System.out.println(st);
        } catch (Error e) {
            e.printStackTrace();
        }
        for (int i = 1; i <= 20; i++) {
            if (i == 19) {
                int j = 0;
                j++;
            }
            st.delete(i);
        }
        System.out.println(st);
    }


}
