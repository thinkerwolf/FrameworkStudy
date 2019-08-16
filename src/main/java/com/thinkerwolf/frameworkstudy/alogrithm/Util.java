package com.thinkerwolf.frameworkstudy.alogrithm;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;

public class Util {

    public static final boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static final boolean equal(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    public static final void exch(Comparable[] arr, int i, int j) {
        Comparable t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static final void show(Comparable[] a) {
        System.out.println(Arrays.toString(a));
    }

    public static final boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i - 1])) {
                return false;
            }
        }
        return true;
    }

    public static final Comparable[] randomArray(int N) {
        Comparable[] a = new Comparable[N];
        for (int i = 0; i < N; i++) {
            a[i] = StdRandom.uniform();
        }
        return a;
    }

}
