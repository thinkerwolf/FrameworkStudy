package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.common.Util;

/**
 * 堆排序
 * 1.先构造堆
 * 2.堆构造后，进行下沉排序。将最大元素删除放入缩小后空出的位置。
 */
public class Heap {

    public static final void sort(Comparable[] a) {
        int N = a.length;
        // 堆构造
        for (int k = N / 2; k >= 1; k--) {
            sink(a, k, N);
        }
        // 下沉排序
        while (N > 1) {
            exch(a, 1, N--);
            sink(a, 1, N);
        }

    }

    public static void sink(Comparable[] a, int k, int N) {
        int n = N;
        while (2 * k <= n) {
            int j = 2 * k;
            if (j < n && less(a, j, j + 1)) j++;
            if (less(a, k, j)) {
                exch(a, k, j);
                k = j;
            } else {
                break;
            }
        }
    }

    public static boolean less(Comparable[] a, int i, int j) {
        return Util.less(a[i - 1], a[j - 1]);
    }

    public static void exch(Comparable[] a, int i, int j) {
        Util.exch(a, i - 1, j - 1);
    }

    public static void main(String[] args) {
        Comparable[] a = Util.randomInt(7000000);
        sort(a);
        assert Util.isSorted(a);
        Util.show(a);
    }

}
