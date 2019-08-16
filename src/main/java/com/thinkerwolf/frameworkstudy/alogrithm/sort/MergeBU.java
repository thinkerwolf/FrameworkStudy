package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import static com.thinkerwolf.frameworkstudy.alogrithm.Util.less;
import static com.thinkerwolf.frameworkstudy.alogrithm.Util.show;

public class MergeBU {
    static Comparable[] aux;

    public static void sort(Comparable[] a) {
        int N = a.length;
        aux = new Comparable[a.length];
        for (int sz = 1; sz < N; sz = sz + sz) { // sz子数组大小，先暂时理解成半个数组大小吧
            for (int k = 0; k < N - sz; k += (sz * 2)) {
                int lo = k;
                int mid = lo + sz - 1;
                int hi = Math.min(lo + sz + sz - 1, N - 1);
                StdOut.printf("sz#%d, lo#%d, mid#%d, hi#%d", sz, lo, mid, hi);
                StdOut.println();
                merge(a, lo, hi, mid);
            }
            StdOut.println("==============");
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
