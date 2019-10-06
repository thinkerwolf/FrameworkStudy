package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.common.Util;
import edu.princeton.cs.algs4.StdRandom;

import static com.thinkerwolf.frameworkstudy.common.Util.less;
import static com.thinkerwolf.frameworkstudy.common.Util.show;

/**
 * 归并排序
 */
public class Merge {

    static Comparable[] aux;

    public static void sort(Comparable[] a) {
        aux = new Comparable[a.length];
        sort(a, 0, aux.length - 1);
        assert Util.isSorted(a);
    }

    private static void sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(a, lo, mid);
        sort(a, mid + 1, hi);

        if (!less(a[mid], a[mid + 1])) {
            merge(a, lo, hi, mid);
        }

    }

    private static void merge(Comparable[] a, int lo, int hi, int mid) {
        // 将两边进行归并
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        for (int k = lo; k <= hi; k++) {
            // 边界情况
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (less(aux[j], aux[i])) a[k] = aux[j++];
            else a[k] = aux[i++];
        }
    }

    public static void main(String[] args) {
        Double[] d = new Double[15];
        for (int i = 0; i < d.length; i++) {
            d[i] = StdRandom.uniform();
        }
        sort(d);
        show(d);
    }
}
