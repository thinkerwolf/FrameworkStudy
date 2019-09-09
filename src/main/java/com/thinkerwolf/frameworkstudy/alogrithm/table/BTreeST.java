package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.Collection;

/**
 * BTree(高度平衡二叉树)实现的查找表
 * <p>
 * 内部牵扯很多数组的复制操作，效率可能没有普通的二叉树好。
 * </p>
 *
 * @param <K>
 * @param <V>
 */
public class BTreeST<K extends Comparable<K>, V> extends AbstractST<K, V> {

    private static final int DEFAULT_M = 3;
    /**
     * 树阶数(结点数量)
     */
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
        Node<K, V> node = root.delete(key);
        if (node != null) {
            if (!node.isLeaf() && node.kvsize == 0) {
                this.root = ((InternalNode<K, V>) node).pointers[0];
            } else {
                this.root = node;
            }
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

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (root != null) {
            root.print(builder, 1);
        }
        return builder.toString();
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
        protected void check() {
            super.check();
            int num = 0;
            boolean isNull = false;
            for (int i = 0; i < pointers.length; i++) {
                if (pointers[i] == null) {
                    isNull = true;
                } else {
                    if (isNull) {
                        throw new BTreeNodeException("Pointers with middle null ");
                    }
                    num++;
                }
            }
            if (kvsize + 1 != num) {
                throw new BTreeNodeException("Pointers'num != kvsize + 1");
            }
        }

        @Override
        Entry<K, V> find(K key) {
            if (kvsize == 0) {
                return null;
            }
            int pos = findPos(this, key);
            if (pos >= kvsize) {
                return pointers[pos].find(key);
            }
            Entry<K, V> e = kvs[pos];
            int cmp = key.compareTo(e.getKey());
            if (cmp == 0) {
                return e;
            } else {
                return pointers[pos].find(key);
            }
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
                entriesRightShift(pos);
                kvs[pos] = kv;

                pointersRightShift(pos + 1);
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

        /**
         * 指针数组元素向右移动一位
         *
         * @param startPos 移动开始位置
         */
        void pointersRightShift(int startPos) {
            for (int i = kvsize + 1; i > startPos; i--) {
                pointers[i] = pointers[i - 1];
            }
        }

        /**
         * 指针数组元素向左移动一位
         *
         * @param startPos 移动开始位置
         */
        void pointersLeftShift(int startPos) {
            if (startPos <= 0) {
                return;
            }
            int numMoved = kvsize + 1 - startPos;
            System.arraycopy(pointers, startPos, pointers, startPos - 1, numMoved);
            pointers[kvsize] = null;
        }

        @Override
        Node<K, V> delete(K key) {
            check();
            int pos = -1;
            int lo = 0;
            int hi = kvsize - 1;
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

            // TODO 节点在非叶子节点，转化成终端节点后再删除
            int minSize = (M + 1) / 2 - 1;
            if (pos >= 0) {
                // 向下找到其相邻节点叶子节点并替换，替换后进行删除
                Node<K, V> leftNode;
                Node<K, V> rightNode;
                if ((leftNode = findLeftLeafSide(pos)).kvsize > minSize) {
                    // 直接删除并替换
                    Entry<K, V> en = leftNode.kvs[leftNode.kvsize - 1];
                    kvs[pos] = en;
                    leftNode.delete(en.getKey());
                    return null;
                } else if ((rightNode = findRightLeafSide(pos)).kvsize > minSize) {
                    // 直接删除并替换
                    Entry<K, V> en = rightNode.kvs[0];
                    kvs[pos] = en;
                    rightNode.delete(en.getKey());
                    return null;
                }

                if (leftNode.kvsize >= rightNode.kvsize) {
                    Entry<K, V> en = leftNode.kvs[leftNode.kvsize - 1];
                    leftNode.kvs[leftNode.kvsize - 1] = kvs[pos];
                    kvs[pos] = en;
                } else {
                    Entry<K, V> en = rightNode.kvs[0];
                    rightNode.kvs[0] = kvs[pos];
                    kvs[pos] = en;
                    pos++;
                }
                leftNode.check();
                rightNode.check();
                check();
            } else {
                // 没有找到，继续往子节点删除
                pos = lo;
            }

            Node<K, V> n = pointers[pos].delete(key);
            if (n != null) {
                // 查看左右兄弟节点数量够借
                if (n.kvsize >= minSize) {
                    return null;
                }
                int borrowedBroPos = -1;
                if (pos > 0 && pointers[pos - 1].kvsize > minSize) {
                    borrowedBroPos = pos - 1;
                } else if (pos < kvsize && pointers[pos + 1].kvsize > minSize) {
                    borrowedBroPos = pos + 1;
                }

                if (borrowedBroPos >= 0) {
                    Node<K, V> borrowedBro = pointers[borrowedBroPos];
                    if (borrowedBroPos < pos) {
                        // 左边的兄弟借
                        Entry<K, V> borrowedKv = borrowedBro.kvs[borrowedBro.kvsize - 1];
                        borrowedBro.kvs[borrowedBro.kvsize - 1] = null;

                        Entry<K, V> rootKv = kvs[pos - 1];
                        kvs[pos - 1] = borrowedKv;

                        n.entriesRightShift(0);
                        n.kvs[0] = rootKv;

                        if (!n.isLeaf()) {
                            InternalNode<K, V> borrowedBroInternal = (InternalNode<K, V>) borrowedBro;
                            InternalNode<K, V> nInternal = (InternalNode<K, V>) n;
                            Node<K, V> borrowedPointer = borrowedBroInternal.pointers[borrowedBro.kvsize];
                            borrowedBroInternal.pointers[borrowedBro.kvsize] = null;
                            nInternal.pointersRightShift(0);
                            nInternal.pointers[0] = borrowedPointer;
                        }

                        borrowedBro.kvsize--;
                        n.kvsize++;
                    } else {
                        // 右边的兄弟借
                        Entry<K, V> borrowedKv = borrowedBro.kvs[0];
                        borrowedBro.kvs[0] = null;
                        borrowedBro.entriesLeftShift(1);


                        Entry<K, V> rootKv = kvs[pos];
                        kvs[pos] = borrowedKv;

                        n.kvs[n.kvsize] = rootKv;


                        if (!n.isLeaf()) {
                            InternalNode<K, V> borrowedBroInternal = (InternalNode<K, V>) borrowedBro;
                            InternalNode<K, V> nInternal = (InternalNode<K, V>) n;
                            Node<K, V> borrowedPointer = borrowedBroInternal.pointers[0];
                            borrowedBroInternal.pointersLeftShift(1);
                            nInternal.pointers[nInternal.kvsize + 1] = borrowedPointer;
                        }

                        borrowedBro.kvsize--;
                        n.kvsize++;
                    }

                    borrowedBro.check();
                    n.check();
                    return null;
                }

                // 不够借，合并；优先找右节点
                int broPos = pos < kvsize ? pos + 1 : pos - 1;
                int rootPos = pos < kvsize ? pos : pos - 1;
                Node<K, V> bro = pointers[broPos];
                Entry<K, V> rootKv = kvs[rootPos];
                for (int i = 0; i < n.kvsize; i++) {
                    bro.insert(n.kvs[i].getKey(), n.kvs[i].getValue());
                }
                bro.insert(rootKv.getKey(), rootKv.getValue());

                entriesLeftShift(rootPos + 1);
                pointersLeftShift(pos + 1);

                kvsize--;

                bro.check();
                n.check();
                check();

                Node<K, V> returnNode = this;
                if (!n.isLeaf() && kvsize < minSize) {
                    Node[] npointers = ((InternalNode) n).pointers;
                    for (int i = 0; i < n.kvsize + 1; i++) {
                        for (int j = 0; j < npointers[i].kvsize; j++) {
                            Entry<K, V> en = npointers[i].kvs[j];
                            Node<K, V> node = returnNode.insert(en.getKey(), en.getValue());
                            if (node != null) {
                                node.check();
                                returnNode = node;
                            }
                        }
                    }
                }
                return returnNode;
            }

            return null;
        }

        /**
         * 找到左相邻叶子节点元素
         *
         * @param idx
         * @return
         */
        private Node<K, V> findLeftLeafSide(int idx) {
            Node<K, V> pointer = pointers[idx];
            while (pointer != null) {
                if (pointer.isLeaf()) {
                    break;
                }
                pointer = ((InternalNode<K, V>) pointer).pointers[pointer.kvsize];
            }
            return pointer;
        }

        /**
         * 找到右相邻叶子节点元素
         *
         * @param idx
         * @return
         */
        private Node<K, V> findRightLeafSide(int idx) {
            Node<K, V> pointer = pointers[idx + 1];
            while (pointer != null) {
                if (pointer.isLeaf()) {
                    break;
                }
                pointer = ((InternalNode<K, V>) pointer).pointers[0];
            }
            return pointer;
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
        protected void check() {
            boolean isNull = false;
            int num = 0;
            for (int i = 0; i < kvs.length; i++) {
                if (kvs[i] == null) {
                    isNull = true;
                } else {
                    if (isNull) {
                        throw new BTreeNodeException("Kvs with middle null ");
                    }
                    num++;
                }
            }
            if (kvsize != num) {
                throw new BTreeNodeException("kvs'num != kvsize");
            }
        }

        @Override
        Entry<K, V> find(K key) {
            if (kvsize == 0) {
                return null;
            }
            int pos = findPos(this, key);
            if (pos >= kvsize) {
                return null;
            }
            Entry<K, V> e = kvs[pos];
            int cmp = key.compareTo(e.getKey());
            if (cmp == 0) {
                return e;
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
            entriesLeftShift(pos + 1);
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

        protected void check() {
            boolean isNull = false;
            int num = 0;
            for (int i = 0; i < kvs.length; i++) {
                if (kvs[i] == null) {
                    isNull = true;
                } else {
                    if (isNull) {
                        throw new BTreeNodeException("Kvs with middle null ");
                    }
                    num++;
                }
            }
            if (kvsize != num) {
                throw new BTreeNodeException("kvs'num != kvsize");
            }
        }

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

        /**
         * 找到删除的位置
         *
         * @param root 结点
         * @param key  删除的key
         * @return pos
         */
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

        int findPos(Node<K, V> root, K key) {
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
            return lo;
        }

        /**
         * 从pos开始的entry右移动一位，pos位置null
         *
         * @param startPos 开始位置
         */
        void entriesRightShift(int startPos) {
            if (startPos >= kvsize) {
                return;
            }
            for (int i = kvsize; i > startPos; i--) {
                kvs[i] = kvs[i - 1];
            }
            kvs[startPos] = null;
        }

        /**
         * 从pos开始的entry往左移动一位，最后一个位置设null
         *
         * @param startPos 开始位置
         */
        void entriesLeftShift(int startPos) {
            if (startPos <= 0) {
                return;
            }
            int numMoved = kvsize - startPos;
            System.arraycopy(kvs, startPos, kvs, startPos - 1, numMoved);
            kvs[kvsize - 1] = null;
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
        System.out.println(st.get(52));
        st.print();


        int[] deleteKeys = new int[]{
                20, 30, 50, 52, 60, 68, 70,
                17, 18, 19, 6, 7, 8};
        for (int key : deleteKeys) {
            st.delete(key);
        }

        st.print();

    }


}
