package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.common.Util;


/**
 * 选择排序
 * <pre>
 *     先找到数组中最小的那个元素，与第一个互换位置，然后再从剩余的找到最小的元素，与第二个互换位置。后面依次类推。
 * </pre>
 * <pre>
 *     一共进行了(N-1) + (N- 2) + ... + 2 + 1 = (N-1)*N /2 次比较和N次交换
 * </pre>
 */
public class Selection {

    public static void sort(Comparable[] a) {
        for (int i = 0; i < a.length; i++) {
            int min = i;
            for (int j = i + 1; j < a.length; j++) {
                if (Util.less(a[j], a[min])) {
                    min = j;
                }
            }
            if (min != i) {
                Util.exch(a, i, min);
            }
        }
    }

    public static void main(String[] args) {
        Comparable[] a = Util.randomArray(10);
        sort(a);
        Util.show(a);
    }

}
