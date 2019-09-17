package com.thinkerwolf.frameworkstudy.alogrithm.ex2;

import edu.princeton.cs.algs4.StdRandom;

import java.util.Comparator;

import static com.thinkerwolf.frameworkstudy.alogrithm.util.Util.exch;
import static com.thinkerwolf.frameworkstudy.alogrithm.util.Util.show;

/**
 * 将字符串按照给定的首字母排序
 * @author wukai
 */
public class Califormia {

    static final Character[] NAME_LETTER_SORT = new Character[] {
      'R', 'W', 'Q', 'O', 'J', 'M', 'V',
            'A', 'H', 'B', 'S', 'G', 'Z',
            'X', 'N', 'T', 'C', 'I', 'E',
            'K', 'U', 'P', 'D', 'Y', 'F', 'L',
    };

    static int[] indexPoses;

    static {
        indexPoses = new int[NAME_LETTER_SORT.length];
        for (int i = 0; i < NAME_LETTER_SORT.length; i++) {
            int pos = NAME_LETTER_SORT[i] - 'A';
            indexPoses[pos] = i;
        }
    }

    static Comparator<String> comparator = new Comparator <String>() {
        @Override
        public int compare(String o1, String o2) {
            if (o1 == null || o2 == null) {
                return 0;
            }
            if (o1.length() == 0 || o2.length() == 0) {
                return o1.length() - o2.length();
            }
            char c1 = Character.toUpperCase(o1.charAt(0));
            char c2 = Character.toUpperCase(o2.charAt(0));
            boolean b1 = c1 >= 'A' && c1 <= 'Z';
            boolean b2 = c2 >= 'A' && c2 <= 'Z';
            if (b1 && b2) {
                return indexPoses[c1 - 'A'] - indexPoses[c2 - 'A'];
            }
            return o1.compareTo(o2);
        }
    };

    public static Comparable[] sort(String[] a) {
        StdRandom.shuffle(a);
        sort(a, 0, a.length - 1);
        return a;
    }

    public static void sort(String[] a, int lo, int hi) {
        if (hi <= lo) {
            return;
        }
        int j = partition(a, lo, hi);
        sort(a, lo, j - 1);
        sort(a, j + 1, hi);
    }


    public static int partition(String[] a, int lo, int hi) {
        int i = lo;
        int j = hi + 1;
        // 切分对象,最大的元素会被交换多少次？？
        String p = a[lo];
        for (; ; ) {
            while (less(a[++i], p)) {
                if (i == hi) {
                    break;
                }
            }
            while (less(p, a[--j])) {
                if (j == lo) {
                    break;
                }
            }
            if (i >= j) {
                break;
            }
            exch(a, i, j);
        }
        exch(a, lo, j);
        return j;
    }

    private static boolean less(String s1, String s2) {
        return comparator.compare(s1, s2) < 0;
    }

    public static void main(String[] args) {
        String[] a = new String[]{"B", "A", "Z", "R", "C", "V", "T"};
        sort(a);
        show(a);
    }



}
