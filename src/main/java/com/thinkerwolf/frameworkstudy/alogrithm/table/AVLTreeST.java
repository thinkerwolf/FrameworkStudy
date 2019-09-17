package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;

import java.util.*;

public class AVLTreeST<K, V> extends AbstractST<K, V> {

    private transient Entry<K, V> root;
    private transient int size = 0;
    private Comparator<K> comparator;

    public AVLTreeST() {
    }

    public AVLTreeST(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public V put(K key, V value) {
        Entry<K, V> t = root;
        if (t == null) {
            this.root = new Entry<>(null, key, value);
            size++;
            return null;
        }
        Entry<K, V> parent = null;
        int cmp = 0;
        while (t != null) {
            cmp = compare(key, t.getKey());
            if (cmp == 0) {
                V old = t.getValue();
                t.setValue(value);
                return old;
            } else if (cmp > 0) {
                parent = t;
                t = t.right;
            } else {
                parent = t;
                t = t.left;
            }
        }
        Entry<K, V> en = new Entry<>(parent, key, value);
        if (cmp > 0) {
            parent.right = en;
        } else {
            parent.left = en;
        }
        size++;
        fixAfterInsertion(en);
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> t = root;
        if (t == null) {
            return null;
        }
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
        Entry<K, V> t = root;
        if (t == null) {
            return null;
        }
        Entry<K, V> d = null;
        while (t != null) {
            int cmp = compare(key, t.getKey());
            if (cmp < 0) {
                t = t.left;
            }
            if (cmp > 0) {
                t = t.right;
            } else {
                // 找到删除
                d = t;
                break;
            }
        }
        if (d == null) {
            return null;
        }
        V old = d.getValue();
        deleteEntry(d);
        return old;
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

    private int compare(K k1, K k2) {
        Comparator<K> cp = this.comparator;
        if (cp != null) {
            return cp.compare(k1, k2);
        }
        return ((Comparable) k1).compareTo(k2);
    }

    // AVL Tree Operation

    private void deleteEntry(Entry<K, V> x) {
        if (x == null) {
            return;
        }
        Entry<K, V> p = x.parent;
        // 先进行普通二叉树删除
        Entry<K, V> replacement = leftOf(x) != null ? leftOf(x) : rightOf(x);
        if (replacement != null) {
            // 删除的结点有孩子
            


        } else if (parentOf(x) == null) {
            root = null;
        } else {
            // 删除的结点没有孩子

        }


    }

    public int getHeight() {
        Entry<K, V> t = root;
        if (t == null) {
            return 0;
        }
        return t.height;
    }

    private void assertAVLTree(Entry<K, V> p) {
        // 断言。。。是否符合AVL树性质，左右子树高度差
        if (p == null) {
            return;
        }
        int d = Math.abs(heightOf(p.left) - heightOf(p.right));
        assert d >= 2;
        assertAVLTree(p.left);
        assertAVLTree(p.right);
    }

    private Entry<K, V> leftOf(Entry<K, V> p) {
        return p == null ? null : p.left;
    }

    private Entry<K, V> rightOf(Entry<K, V> p) {
        return p == null ? null : p.right;
    }

    private Entry<K, V> parentOf(Entry<K, V> p) {
        return p == null ? null : p.parent;
    }

    private int heightOf(Entry<K, V> p) {
        return p == null ? 0 : p.height;
    }

    private void fixAfterInsertion(Entry<K, V> x) {
        while (x != null) {
            Entry<K, V> p = x.parent;
            Entry<K, V> b = leftOf(p) == x ? rightOf(p) : leftOf(p);
            // 查看左右子树的高度差
            int d = Math.abs(heightOf(x) - heightOf(b));
            if (d == 2) { // 高度差2
                // x左右子树谁的高
                boolean left = heightOf(x.left) >= heightOf(x.right);
                if (leftOf(p) == x) {
                    if (left) {
                        // 左左 右旋
                        rotateRight(p);
                    } else {
                        // 左右 先左旋再右旋
                        rotateLeftThanRight(p);
                    }
                } else if (rightOf(p) == x) {
                    if (!left) {
                        //右右 左旋
                        rotateLeft(p);
                    } else {
                        //右左 先右旋再左旋
                        rotateRightThanLeft(p);
                    }
                }
                if (p != null) {
                    p.height = Math.max(heightOf(p.left), heightOf(p.right)) + 1;
                }
                break;
            }

            if (p != null) {
                p.height = Math.max(heightOf(p.left), heightOf(p.right)) + 1;
            }
            x = p;
        }
    }

    private Entry<K, V> rotateLeftThanRight(Entry<K, V> h) {
        rotateLeft(leftOf(h));
        return rotateRight(h);
    }

    private Entry<K, V> rotateRightThanLeft(Entry<K, V> h) {
        rotateRight(rightOf(h));
        return rotateLeft(h);
    }

    /**
     *
     */
    private Entry<K, V> rotateRight(Entry<K, V> h) {
        if (h != null) {
            Entry<K, V> x = h.left;
            Entry<K, V> p = parentOf(h);

            // 修正父节点
            if (x.right != null) {
                x.right.parent = h;
            }
            if (p != null) {
                if (leftOf(p) == h) {
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

            //FIXME 修正高度
            h.height = Math.max(heightOf(h.left), heightOf(h.right)) + 1;
            x.height = Math.max(heightOf(x.left), h.height) + 1;

            return x;
        }
        return null;
    }

    /**
     *
     */
    private Entry<K, V> rotateLeft(Entry<K, V> h) {
        if (h != null) {
            Entry<K, V> x = h.right;
            Entry<K, V> p = parentOf(h);
            if (x.left != null) {
                x.left.parent = h;
            }
            // 修正h父节点所有子树
            if (p != null) {
                if (leftOf(p) == h) {
                    p.left = x;
                } else {
                    p.right = x;
                }
            } else {
                this.root = x;
            }
            x.parent = p;
            h.parent = x;

            h.right = x.left;
            x.left = h;

            //FIXME 修正高度
            h.height = Math.max(heightOf(h.left), heightOf(h.right)) + 1;
            x.height = Math.max(h.height, heightOf(x.right)) + 1;

            return x;
        }
        return null;
    }


    public void print() {
        StringBuilder builder = new StringBuilder();
        print(builder, root, 1);
        System.out.println(builder.toString());
    }

    private void print(StringBuilder builder, Entry<K, V> en, int level) {
        if (en == null) {
            return;
        }
        builder.append("L" + level).append(en.toString()).append(' ');
        builder.append("\n");
        StringBuilder childBuilder = new StringBuilder();
        print(childBuilder, en.left, level + 1);
        print(childBuilder, en.right, level + 1);
        builder.append(childBuilder);
    }

    static class Entry<K, V> implements ST.Entry<K, V> {

        K key;
        V val;
        Entry<K, V> left;
        Entry<K, V> right;
        Entry<K, V> parent;
        /**
         * 树的高度
         */
        int height;

        Entry(Entry<K, V> parent, K key, V val) {
            this.parent = parent;
            this.key = key;
            this.val = val;
            this.height = 1;
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
        public void setValue(V value) {
            this.val = value;
        }

        @Override
        public String toString() {
            return "Entry(" +
                    "key=" + key +
                    ", val=" + val +
                    ", height=" + height +
                    ')';
        }
    }

    public static void main(String[] args) {
        AVLTreeST<Integer, String> st = new AVLTreeST<>();
//        for (int i = 20; i >=1; i--) {
//            st.put(i, "val" + i);
//        }

        // fix
//        int[] keys = new int[]{1, 9, 8};
//        for (int i = 0; i < keys.length; i++) {
//            st.put(keys[i], "val" + keys[i]);
//        }


        Random r = new Random();
        int max = 10000;
        Set<Integer> keySet = new LinkedHashSet<>();
        for (int i = 0; i < max; i++) {
            int key = r.nextInt(max);
            keySet.add(key);
            st.put(key, "val" + key);
        }
        System.out.println(keySet);

        st.print();
        System.out.println("Height " + st.getHeight());
        System.out.println("size " + st.size);

        for (Integer k : keySet) {
            Util.isTrue(st.get(k) != null, "Bad AVL tree");
        }

        st.assertAVLTree(st.root);
    }

}
