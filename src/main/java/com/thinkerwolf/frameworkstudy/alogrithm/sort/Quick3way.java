package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.common.Util;
import edu.princeton.cs.algs4.StdRandom;

/**
 * 快速排序三路排序法（最佳快速排序？？）
 */
public class Quick3way {
    public static final void sort(Comparable<?>[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        assert Util.isSorted(a);
    }

    public static void sort(Comparable[] a, int lo, int hi) {
        if (lo >= hi) {
            return;
        }
        int lt = lo;
        int gt = hi;
        int i = lo + 1;
        Comparable v = a[lo];
        while (i <= gt) {
            int cmp = a[i].compareTo(v);
            if (cmp > 0) Util.exch(a, i, gt--);
            else if (cmp < 0) Util.exch(a, lt++, i++);
            else i++;
        }
        sort(a, lo, lt - 1);
        sort(a, gt + 1, hi);
    }

    public static void main(String[] args) {
        Comparable[] a = Util.randomArray(7);
        sort(a);
        Util.show(a);
    }
}
