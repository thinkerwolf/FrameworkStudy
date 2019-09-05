package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;

/**
 * BTree实现的查找表
 *
 * @param <K>
 * @param <V>
 */
public class BTreeST<K extends Comparable<K>, V> extends AbstractST<K, V> {

    private static final int DEFAULT_M = 3;

    private final int M;

    private Node<K, V> root;

    private volatile int size;

    public BTreeST(int M) {
        this.M = M;
        this.root = new LeafNode<>(M);
    }

    public BTreeST() {
        this(DEFAULT_M);
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new NullPointerException();
        }
        Node<K, V> node = root.insert(key, value);
        if (node != null) {
            this.root = node;
        }
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> e = root.find(key);
        return e == null ? null : e.getValue();
    }

    @Override
    public V delete(K key) {
        if (key == null) {
            throw new NullPointerException();
        }

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

    public void print() {
        StringBuilder builder = new StringBuilder();
        root.print(builder, 1);
        System.out.println(builder.toString());
    }

    /**
     * 非叶结点
     *
     * @param <K>
     * @param <V>
     */
    class InternalNode<K extends Comparable<K>, V> extends Node<K, V> {
        /**
         * 孩子指针数组
         */
        Node[] pointers;

        InternalNode(int m) {
            super(m);
            this.pointers = new Node[m + 1];
        }

        @Override
        boolean isLeaf() {
            return false;
        }

        @Override
        Entry<K, V> find(K key) {
            if (kvsize == 0) {
                return null;
            }
            for (int i = 0; i < kvsize; i++) {
                Entry<K, V> e = kvs[i];
                int cmp = key.compareTo(e.getKey());
                if (cmp == 0) {
                    return e;
                }
                if (cmp < 0) {
                    return pointers[i].find(key);
                }
            }
            return pointers[kvsize].find(key);
        }

        @Override
        Node<K, V> insert(K key, V val) {
            int pos = findInsertPos(this, key, val);
            if (pos < 0) {
                return null;
            }
            Node<K, V> node = pointers[pos].insert(key, val);
            if (node != null) {
                InternalNode<K, V> n = (InternalNode<K, V>) node;
                Entry<K, V> kv = n.kvs[0];
                for (pos = 0; pos <= kvsize; pos++) {
                    Entry<K, V> en = kvs[pos];
                    if (en == null) {
                        break;
                    }
                    int cmp = kv.getKey().compareTo(kvs[pos].getKey());
                    if (cmp == 0) {
                        throw new RuntimeException();
                    }
                    if (cmp < 0) {
                        break;
                    }
                }
                for (int i = kvsize; i > pos; i--) {
                    kvs[i] = kvs[i - 1];
                }
                kvs[pos] = kv;

                for (int i = kvsize + 1; i > pos + 1; i--) {
                    pointers[i] = pointers[i - 1];
                }
                pointers[pos] = n.pointers[0];
                pointers[pos + 1] = n.pointers[1];

                kvsize += n.kvsize;
            }


            if (kvsize > M - 1) {
                // 非叶结点分裂
                int p = M / 2; // 分裂位置，左边分裂左子树，右边分裂右子树

                InternalNode<K, V> left = new InternalNode<>(M);
                InternalNode<K, V> right = new InternalNode<>(M);
                System.arraycopy(kvs, 0, left.kvs, 0, p);
                System.arraycopy(kvs, p + 1, right.kvs, 0, kvsize - 1 - p);
                for (int i = 0; i < p + 1; i++) {
                    left.pointers[i] = pointers[i];
                }
                for (int i = 0; i < kvsize - p; i++) {
                    right.pointers[i] = pointers[p + 1 + i];
                }
                left.kvsize = p;
                right.kvsize = kvsize - 1 - p;

                kvs[0] = kvs[p];
                for (int i = 1; i < kvsize; i++) {
                    kvs[i] = null;
                }
                pointers[0] = left;
                pointers[1] = right;
                for (int i = 2; i < kvsize + 1; i++) {
                    pointers[i] = null;
                }
                kvsize = 1;
                return this;
            }
            return null;
        }

        @Override
        Node<K, V> delete(K key) {
            int pos = -1;
            int lo = 0;
            int hi = root.kvsize - 1;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                Entry<K, V> en = kvs[mid];
                int cmp = key.compareTo(en.getKey());
                if (cmp == 0) {
                    pos = mid;
                    break;
                } else if (cmp > 0) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }

            // 节点在非叶子节点，转化成终端节点后再删除
            if (pos >= 0) {


                return null;
            }

            // 没有找到，继续往子节点删除
            pos = lo;
            Node<K, V> n = pointers[pos].delete(key); // 返回的一定是叶子节点
            if (n != null) {
                // 查看左右兄弟节点数量是否够


            }

            return null;
        }

        @Override
        public void print(StringBuilder builder, int height) {
            super.print(builder, height);

            StringBuilder internalBuilder = new StringBuilder();
            for (int i = 0; i <= kvsize; i++) {
                pointers[i].print(internalBuilder, height + 1);
            }

            internalBuilder.append("\n");
            builder.append("\n").append(internalBuilder.toString());
        }
    }

    /**
     * 叶结点
     *
     * @param <K>
     * @param <V>
     */
    class LeafNode<K extends Comparable<K>, V> extends Node<K, V> {


        LeafNode(int m) {
            super(m);
        }

        @Override
        boolean isLeaf() {
            return true;
        }

        @Override
        Entry<K, V> find(K key) {
            if (kvsize == 0) {
                return null;
            }
            for (int i = 0; i < kvsize; i++) {
                Entry<K, V> e = kvs[i];
                int cmp = key.compareTo(e.getKey());
                if (cmp == 0) {
                    return e;
                }
            }
            return null;
        }


        @Override
        Node<K, V> insert(K key, V val) {
            int pos = findInsertPos(this, key, val);
            if (pos < 0) {
                return null;
            }

            for (int i = kvsize; i > pos; i--) {
                kvs[i] = kvs[i - 1];
            }

            BTreeST.this.size++;

            kvs[pos] = new Entry<>(key, val);
            kvsize++;
            if (kvsize > M - 1) {
                // 需要分裂
                int p = M / 2; // 分裂位置，左边分裂左子树，右边分裂右子树
                Node<K, V> left = new LeafNode<>(M);
                Node<K, V> right = new LeafNode<>(M);

                System.arraycopy(kvs, 0, left.kvs, 0, p);
                System.arraycopy(kvs, p + 1, right.kvs, 0, kvsize - 1 - p);
                left.kvsize = p;
                right.kvsize = kvsize - p - 1;

                // 此节点跑到父节点上
                InternalNode<K, V> internalNode = new InternalNode<>(M);
                internalNode.kvs[0] = kvs[p];
                internalNode.pointers[0] = left;
                internalNode.pointers[1] = right;
                internalNode.kvsize = 1;

                for (int i = 0; i < kvsize; i++) {
                    kvs[i] = null;
                }
                kvsize = 1;
                return internalNode;
            }
            return null;
        }

        @Override
        Node<K, V> delete(K key) {
            int pos = findDeletePos(this, key);
            if (pos < 0) {
                return null;
            }
            BTreeST.this.size--;
            int minSize = (M + 1) / 2 - 1;
            int numMoved = kvsize - pos - 1;
            System.arraycopy(kvs, pos + 1, kvs, pos, numMoved);
            kvsize--;
            if (kvsize >= minSize) {
                return null;
            }
            return this;
        }
    }

    abstract class Node<K extends Comparable<K>, V> {
        /**
         * 关键字数组
         */
        Entry<K, V>[] kvs;
        /**
         * 关键字数量
         */
        int kvsize;

        Node(int m) {
            this.kvs = new Entry[m];
        }

        /**
         * 是否是叶子节点
         *
         * @return
         */
        abstract boolean isLeaf();

        /**
         * 查找节点
         *
         * @param key 键
         * @return kv对
         */
        abstract Entry<K, V> find(K key);

        /**
         * 插入节点
         *
         * @param key 键
         * @param val 值
         * @return 插入后返回的节点
         */
        abstract Node<K, V> insert(K key, V val);

        /**
         * 删除节点
         *
         * @param key 键
         * @return 删除后返回的节点
         */
        abstract Node<K, V> delete(K key);

        /**
         * 插入时，如果键值存在进行替换；如果键值不存在，则找到pointer的位置。（二分法）
         *
         * @param root 节点
         * @param key  键
         * @param val  值
         * @return -1表示找到相同的键值并替换; >=0 表示插入的位置
         */
        int findInsertPos(Node<K, V> root, K key, V val) {
            int lo = 0;
            int hi = root.kvsize - 1;
            Entry<K, V>[] kvs = root.kvs;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                Entry<K, V> en = kvs[mid];
                int cmp = key.compareTo(en.getKey());
                if (cmp == 0) {
                    en.setValue(val);
                    return -1;
                } else if (cmp > 0) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return lo;
        }

        int findDeletePos(Node<K, V> root, K key) {
            int lo = 0;
            int hi = root.kvsize - 1;
            Entry<K, V>[] kvs = root.kvs;
            while (lo <= hi) {
                int mid = lo + (hi - lo) / 2;
                Entry<K, V> en = kvs[mid];
                int cmp = key.compareTo(en.getKey());
                if (cmp == 0) {
                    return mid;
                } else if (cmp > 0) {
                    lo = mid + 1;
                } else {
                    hi = mid - 1;
                }
            }
            return -1;
        }

        public void print(StringBuilder builder, int height) {
            builder.append(isLeaf() ? "L^" : "I^").append(height).append('(');
            for (int i = 0; i < kvsize; i++) {
                builder.append(kvs[i]);
                if (i >= kvsize - 1) {
                    builder.append(')').append(' ');
                } else {
                    builder.append(' ');
                }
            }
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append('[');
            for (int i = 0; i < kvsize; i++) {
                if (kvs[i] == null) {
                    continue;
                }
                sb.append(kvs[i].toString());
                if (i == kvsize - 1) {
                    sb.append(']');
                } else {
                    sb.append(',').append(' ');
                }
            }
            return sb.toString();
        }
    }

    class Entry<K extends Comparable<K>, V> implements ST.Entry<K, V> {

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

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(key);
            sb.append('=');
            sb.append(val);
            return sb.toString();
        }
    }

    public static void main(String[] args) {
        int[] keys = new int[]{
                20, 30, 50, 52, 60, 68, 70,
                20, 30, 50, 52, 60, 68, 70,
                17, 18, 19, 6, 7, 8};
        BTreeST<Integer, String> st = new BTreeST<>(5);

        for (int i = 0, len = keys.length; i < len; i++) {
            if (i == len - 1) {
                i = len - 1;
            }
            st.put(keys[i], "val" + keys[i]);
        }
        System.out.println(st.get(70));
        st.print();
    }


}
