package com.thinkerwolf.frameworkstudy.alogrithm;

import edu.princeton.cs.algs4.*;

/**
 * 排序算法比较
 *
 * @param
 * @author wukai
 * @date 2019/8/12 16:21
 * @return
 */
public class SortComparator {

    public static double time(String alg, Comparable[] a) {
        Stopwatch sw = new Stopwatch();
        if ("Insertion".equals(alg)) {
            // 插入排序
            Insertion.sort(a);
        } else if ("Selection".equals(alg)) {
            // 选择排序
            Selection.sort(a);
        } else if ("Shell".equals(alg)) {
            // 希尔排序
            Shell.sort(a);
        } else if ("Merge".equals(alg)) {
            // 归并排序
            Merge.sort(a);
        } else if ("Quick".equals(alg)) {
            // 快速排序
            Quick.sort(a);
        } else if ("Heap".equals(alg)) {
            // 堆排序
            Heap.sort(a);
        }
        return sw.elapsedTime();
    }

    public static double timeRandomInput(String alg, int N, int T) {
        // 使用算法1将T个长度为N的数组排序
        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) {
            // 进行一次测试（生成一个数组并排序)
            for (int i = 0; i < N; i++)
                a[i] = StdRandom.uniform();
            total += time(alg, a);
        }
        return total;
    }

    public static void main(String[] args) {
        double insertionTime = timeRandomInput("Insertion", 10000, 100);
        StdOut.printf("Insertion Time:%f", insertionTime);
    }
}
