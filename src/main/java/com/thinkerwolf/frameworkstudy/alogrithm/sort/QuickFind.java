package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;

/**
 * 利用快速排序查找
 * @author wukai
 */
public class QuickFind {
    /**
     * 从数组中寻找第k小的元素
     * @param a
     * @param k
     * @return
     */
    public static final Comparable select(Comparable[] a, int k) {
        int hi = a.length - 1;
        int lo = 0;
        while (lo < hi) {
            int j = partition(a, lo, hi);
            if (j == k) {
                return a[k];
            } else if (j < k) {
                lo = j + 1;
            } else {
                hi = j - 1;
            }
        }
        return a[k];
    }

    private static int partition(Comparable[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        Comparable c = a[lo];
        for (;;) {
            while (Util.less(a[++i], c)) {
                if (i >= hi) {
                    break;
                }
            }
            while (Util.less(c, a[--j])) {
                if (j <= lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            Util.exch(a, i, j);
        }
        Util.exch(a, lo, j);
        return j;
    }


    public static void main(String[] args) {
        String[] a = new String[]{"S", "O", "R", "T", "C", "B", "A"};
        Util.show(Quick.sort(a.clone()));
        Util.show(a);
        Util.println(select(a, 1));
    }

}
