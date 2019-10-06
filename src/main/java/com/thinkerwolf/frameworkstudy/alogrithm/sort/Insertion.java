package com.thinkerwolf.frameworkstudy.alogrithm.sort;

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdRandom;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static com.thinkerwolf.frameworkstudy.common.Util.*;

/**
 * 插入排序算法
 * <pre>
 *     一张一张的来，将每一张牌插入到其他已经有序的牌中的适当位置。
 * </pre>
 * <pre>
 *     最坏的情况是比较N^2/2次和交换N^2/2次，最好的情况是比较N-1次和交换0次，平均是比较N^2/4次和交换N^2/4次
 * </pre>
 *
 * @param
 * @author wukai
 * @date 2019/8/12 15:52
 * @return
 */
public class Insertion {
    public static final void sort(Comparable[] a) {
        int N = a.length;
        Set <Integer> set = new HashSet <>();
        for (int i = 1; i < N; i++) {
            set.clear();
            for (int j = i; j > 0 && less(a[j], a[j - 1]); j--) {
                exch(a, j - 1, j);
                set.add(j);
                set.add(j - 1);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

            drawArr(a, set);
            StdDraw.show();
        }
    }

    private static void drawArr(Comparable[] a, Set <Integer> set) {
        StdDraw.clear();
        StdDraw.setPenColor(Color.GRAY);
        for (int i = 0; i < a.length; i++) {
            double d = a[i] instanceof Double ? (Double) a[i] : 0.1;
            if (set.contains(i)) {
                StdDraw.setPenColor(Color.BLACK);
            } else {
                StdDraw.setPenColor(Color.GRAY);
            }
            StdDraw.filledRectangle((i + 1) * 0.05, 0, 0.005, d);
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
