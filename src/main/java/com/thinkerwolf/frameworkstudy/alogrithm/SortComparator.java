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
    private final static int INSERTION = 0, SELECTION = 1, SHELL = 2, MERGE = 3, QUICK = 4, HEAP = 5;

    public static double time(int alg, Comparable[] a) {
        Stopwatch sw = new Stopwatch();
        switch (alg) {
            case INSERTION:
                Insertion.sort(a);
                break;
            case SELECTION:
                Selection.sort(a);
                break;
            case SHELL:
                Shell.sort(a);
                break;
            case MERGE:
                Merge.sort(a);
                break;
            case QUICK:
                Quick.sort(a);
                break;
            case HEAP:
                Heap.sort(a);
                break;
            default:
                break;
        }
        return sw.elapsedTime();
    }

    public static double timeRandomInput(int alg, int N, int T) {
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
        double insertionTime = timeRandomInput(INSERTION, 10000, 100);
        StdOut.printf("Insertion Time:%f", insertionTime);
    }
}
