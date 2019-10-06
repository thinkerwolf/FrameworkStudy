package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.common.Util;
import edu.princeton.cs.algs4.StdOut;

import static com.thinkerwolf.frameworkstudy.common.Util.*;

/**
 * 希尔排序
 *
 * @param
 * @author wukai
 * @date 2019/8/12 17:57
 * @return
 */
public class Shell {

    public static final void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) {
            h = h * 3 + 1;
        }
        while (h >= 1) {

            for (int i = h; i < N; i++) {
                for (int j = i; j - h >= 0 && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                }
            }
            h = h / 3;
        }
    }

    public static final void sort1(Comparable[] a) {
        int N = a.length;
        final int interval = 3;
        int[] ha = new int[1 + N / interval];
        ha[0] = 1;
        int pos = 0;
        while (ha[pos] < N / interval) {
            ha[pos + 1] = ha[pos] * interval + 1;
            pos++;
        }

        while (pos >= 0) {
            int h = ha[pos];
            int compareNum = 1;
            for (int i = h; i < N; i++) {
                for (int j = i; j - h >= 0 && less(a[j], a[j - h]); j -= h) {
                    exch(a, j, j - h);
                    compareNum++;
                }
            }
            // 打印出比较次数和数组大小
            StdOut.print("Array Size : " + a.length);
            StdOut.print(", Compare Num : " + compareNum);
            StdOut.print(", Ratio : " + ((double) compareNum / (double) a.length));
            StdOut.println();
            pos--;
        }
    }


    public static void main(String[] args) {
        Comparable[] ss = Util.randomArray(11);
        sort1(ss);
        show(ss);
    }

}
