package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import edu.princeton.cs.algs4.StdRandom;

import static com.thinkerwolf.frameworkstudy.alogrithm.util.Util.*;

public class Quick {

    public static Comparable[] sort(Comparable[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        return a;
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }

    /**
     * 对数组进行切分的到一个切分位置，切分位置左边的数据都小于等于切分位置的数据，切分位置右边的数据都大于等于切分位置的数据。
     *
     * @param a
     * @param lo
     * @param hi
     * @return
     */
    public static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable p = a[lo]; // 切分对象,最大的元素会被交换多少次？？
        for (; ; ) {
            while (less(a[++i], p)) {
                if (i == hi) break;
            }
            while (less(p, a[--j])) {
                if (j == lo) break;
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    public static void main(String[] args) {
        Comparable[] a = randomArray(10);
        sort(a);
        show(a);
    }

}
