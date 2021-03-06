package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.alogrithm.PQ;
import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.Comparator;
import java.util.Iterator;

public class MaxPQ<T> implements PQ <T> {
    private int n;
    private Comparator <T> comparator;
    private T[] pq;

    public MaxPQ() {
        this(1);
    }

    public MaxPQ(Comparator <T> comparator) {
        this(comparator, 1);
    }

    public MaxPQ(int cap) {
        this(null, cap);
    }

    public MaxPQ(Comparator <T> comparator, int cap) {
        this.comparator = comparator;
        this.pq = (T[]) new Object[cap + 1];
        this.n = 0;
    }

    public MaxPQ(T[] p) {
        // 使用给定的数组构造MaxPQ
        this.n = p.length;
        this.pq = (T[]) new Object[n + 1];
        System.arraycopy(p, 0, pq, 1, n);
        for (int k = pq.length / 2; k >= 1; k--) {
            sink(k);
        }
        this.n = p.length;
    }

    @Override
    public boolean isEmpty() {
        return n <= 0;
    }

    @Override
    public int size() {
        return n;
    }

    @Override
    public void insert(T t) {
        resize(n + 2);
        this.n++;
        pq[n] = t;
        swim(n);
    }

    private void resize(int size) {
        int len = pq.length;
        if (size > len) {
            // 进行扩容
            int resize = 0;
            if (len * 2 > Integer.MAX_VALUE || size > len * 2) {
                resize = size;
            } else {
                resize = len * 2;
            }
            T[] arr = (T[]) new Object[resize];
            System.arraycopy(pq, 0, arr, 0, n + 1);
            this.pq = arr;
        }
    }

    @Override
    public T poll() {
        if (isEmpty()) {
            return null;
        }
        T t = pq[1];
        Util.exch(pq, 1, n);
        pq[n] = null; // help GC
        n = n - 1;
        sink(1);
        return t;
    }

    /**
     * 上浮
     */
    private void swim(int k) {
        while (k / 2 >= 1) {
            int j = k / 2; // 父节点
            if (less(j, k)) {
                Util.exch(pq, j, k);
                k = j;
            } else {
                break;
            }
        }
    }

    /**
     * 下沉
     */
    private void sink(int k) {
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(j, j + 1)) j++;
            if (less(k, j)) {
                Util.exch(pq, k, j);
                k = j;
            } else {
                break;
            }
        }
    }

    private boolean less(int i, int j) {
        T t1 = pq[i];
        T t2 = pq[j];
        if (comparator != null) {
            return comparator.compare(t1, t2) < 0;
        } else {
            if (!(t1 instanceof Comparable) || !(t2 instanceof Comparable)) {
                throw new IllegalArgumentException("The element must be comparable");
            }
            return Util.less((Comparable) t1, (Comparable) t2);
        }
    }

    @Override
    public Iterator <T> iterator() {
        return new HeapIterator();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (int i = 1; i <= n; i++) {
            if (i != 1) {
                sb.append(", ");
            }
            sb.append(pq[i]);
        }
        sb.append(']');
        return sb.toString();
    }

    private class HeapIterator implements Iterator <T> {
        private MaxPQ <T> copy;

        public HeapIterator() {
            if (comparator == null) {
                copy = new MaxPQ <>(n);
            } else {
                copy = new MaxPQ <>(comparator, n);
            }
            System.arraycopy(pq, 0, copy.pq, 0, n + 1);
        }

        @Override
        public boolean hasNext() {
            return copy.isEmpty();
        }

        @Override
        public T next() {
            return copy.poll();
        }
    }

    public static void main(String[] args) {
        MaxPQ <Integer> pq = new MaxPQ <>();
        for (int i = 1; i <= 10; i++) {
            pq.insert(Util.nextInt(1, 30));
        }
        Util.println(pq);
        pq.insert(100);
//        pq.insert(99);
        pq.poll();
//        pq.poll();
        Util.println(pq);

    }
}
