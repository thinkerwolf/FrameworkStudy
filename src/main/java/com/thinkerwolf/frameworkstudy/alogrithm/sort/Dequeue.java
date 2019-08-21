package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import com.thinkerwolf.frameworkstudy.alogrithm.Util;

import static com.thinkerwolf.frameworkstudy.alogrithm.Util.*;

/**
 * 出列排序
 *
 * @param
 * @author wukai
 * @date 2019/8/13 13:35
 * @return
 */
public class Dequeue {

    public static void sort(Comparable[] a) {
        int N = a.length;
        for (int i = 0; i < N - 1; i++) {
            for (int j = i; j < N - 1; j++) {
                if (less(a[i], a[i + 1])) {
                    exch(a, i + 1, i);
                }
                moveQueue(a, i);
            }
        }
    }

    private static void moveQueue(Comparable[] a, int start) {
        int N = a.length;
        Comparable temp = a[start];
        for (int i = start + 1; i < N; i++) {
            a[i - 1] = a[i];
        }
        a[N - 1] = temp;
    }

    public static void main(String[] args) {
        Comparable[] ss = Util.randomArray(10);
        sort(ss);
        show(ss);
    }
}
