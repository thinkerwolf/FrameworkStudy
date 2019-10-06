package com.thinkerwolf.frameworkstudy.alogrithm.ex2;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.Comparator;

/**
 * 检查堆
 * @author wukai
 */
public class CheckHeap {

    /**
     * 检查数组是否是面向最小元素的堆
     *
     * @param a
     * @return
     */
    public static boolean checkMin(Object[] a) {
        return checkMin(a, null);
    }

    public static boolean checkMin(Object[] a, Comparator comparator) {
        if (a.length <= 1) {
            return true;
        }
        int N = a.length;
        for (int k = N / 2; k >= 1; k--) {
            // 检查父元素与子元素大小关系
//            Util.println("check element -> l:" + 2 * k + ", r:" + (2 * k + 1));
            if (less(a, 2 * k, k, comparator)) {
                return false;
            }
            if (2 * k + 1 <= N && less(a, 2 * k + 1, k, comparator)) {
                return false;
            }
        }
        return true;
    }

    private static boolean less(Object[] a, int i, int j, Comparator comparator) {
        if (comparator != null) {
            return comparator.compare(a[i - 1], a[j - 1]) < 0;
        } else {
            return Util.less((Comparable) a[i - 1], (Comparable) a[j - 1]);
        }
    }

    public static void main(String[] args) {
        Integer[] a = new Integer[]{3, 8, 3, 14, 11, 21, 4, 25, 24, 11};
        Util.println(checkMin(a));

        Integer[] a1 = new Integer[]{1, 2, 3, 4};
        Util.println(checkMin(a1));
    }

}
