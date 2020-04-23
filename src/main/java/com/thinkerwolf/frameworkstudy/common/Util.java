package com.thinkerwolf.frameworkstudy.common;

import sun.misc.Unsafe;

import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * @author wukai
 */
public class Util {

    private static final String CHARSET_NAME = "UTF-8";
    static Random R = new Random();
    static PrintWriter pw;
    private static Unsafe unsafe;

    static {
        try {
            pw = new PrintWriter(new OutputStreamWriter(System.out, CHARSET_NAME), true);
        } catch (UnsupportedEncodingException e) {
            System.out.println(e);
        }
    }

    static {
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            unsafe = (Unsafe) f.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
    }

    public static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    public static boolean less(Object v, Object w) {
        if (!(v instanceof Comparable) || !(w instanceof Comparable)) {
            throw new IllegalArgumentException();
        }
        return less((Comparable) v, (Comparable) w);
    }

    public static boolean less(Comparable[] a, int i, int j) {
        return less(a[i], a[j]);
    }

    public static final boolean equal(Comparable v, Comparable w) {
        return v.compareTo(w) == 0;
    }

    public static final boolean equal(Object v, Object w) {
        return Objects.equals(v, w);
    }

    public static final void exch(Comparable[] arr, int i, int j) {
        Comparable t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static final void exch(Object[] arr, int i, int j) {
        Object t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    public static final void exch(int[] arr, int i, int j) {
        int t = arr[i];
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
            a[i] = nextDouble();
        }
        return a;
    }

    public static final Comparable[] randomInt(int N) {
        Comparable[] a = new Comparable[N];
        for (int i = 0; i < N; i++) {
            a[i] = nextInt(30);
        }
        return a;
    }

    public static int nextInt() {
        return R.nextInt();
    }

    public static double nextDouble() {
        return R.nextDouble();
    }

    public static int nextInt(int n) {
        return R.nextInt(n);
    }

    public static int nextInt(int min, int max) {
        return min + R.nextInt(max - min);
    }

    public static String nextString(int len) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c;
            if (i == 0 && nextBoolean()) {
                c = (char) nextInt('A', 'Z' + 1);
            } else {
                c = (char) nextInt('a', 'z' + 1);
            }
            builder.append(c);
        }
        return builder.toString();
    }

    public static boolean nextBoolean() {
        return R.nextBoolean();
    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isFalse(boolean expression, String message) {
        if (expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void isNull(Object obj, String message) {
        if (obj != null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void println() {
        pw.println();
        pw.flush();
    }

    public static void println(Object obj) {
        pw.println(obj);
        pw.flush();
    }

    public static void print(Object obj) {
        pw.print(obj);
        pw.flush();
    }

    public static void printf(String format, Object... args) {
        pw.printf(format, args);
        pw.flush();
    }

    public static void print() {
        pw.flush();
    }

    public static Unsafe getUnsafe() {
        return unsafe;
    }

    public static void joinQuietly(Thread t) {
        if (t.isAlive()) {
            try {
                t.join();
            } catch (InterruptedException ignored) {
                // Ignore this exception
            }
        }
    }

}
