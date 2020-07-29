package com.thinkerwolf.frameworkstudy.alg;

import org.apache.commons.lang.time.StopWatch;
import org.junit.Test;

import java.util.Arrays;

/**
 * 动态规划
 *
 * @author wukai
 */
public class LeetcodeBasicDynamicProgramingTests {


    @Test
    public void testCutRod() {
        int[] p = new int[]{1, 5, 8, 9, 10, 17, 17, 20, 24, 30};

        StopWatch sw = new StopWatch();
        sw.start();
        int maxProfit = cutRodRecursive(p, 2);
        System.out.printf("Recursion-%d, maxProfit-%d, spend-%d\n", 2, maxProfit, sw.getTime());

        sw.reset();
        sw.start();
        maxProfit = cutRodRecursive(p, 3);
        System.out.printf("Recursion-%d, maxProfit-%d, spend-%d\n", 3, maxProfit, sw.getTime());

        sw.reset();
        sw.start();
        maxProfit = cutRodRecursive(p, 10);
        System.out.printf("Recursion-%d, maxProfit-%d, spend-%d\n", 10, maxProfit, sw.getTime());

        sw.reset();
        sw.start();
        maxProfit = curRodBottomUp(p, 3);
        System.out.printf("BottomUp-%d, maxProfit-%d, spend-%d\n", 3, maxProfit, sw.getTime());

        sw.reset();
        sw.start();
        maxProfit = curRodBottomUp(p, 10);
        System.out.printf("BottomUp-%d, maxProfit-%d, spend-%d\n", 10, maxProfit, sw.getTime());

        int[] sch = new int[10];
        for (int i = 1; i <= 10; i++) {
            int[] mem = new int[i];
            sw.reset();
            sw.start();
            maxProfit = cutRodMemoizedWithScheme(p, mem, sch, i);
            System.out.printf("Memorized recursion-%d, maxProfit-%d, spend-%d\n", i, maxProfit, sw.getTime());
        }
        System.out.println("Scheme " + Arrays.toString(sch));

    }

    /**
     * 切钢条-递归自顶而下
     *
     * @param p
     * @param n
     * @return
     */
    public int cutRodRecursive(int[] p, int n) {
        if (n <= 0) {
            return 0;
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, p[i] + cutRodRecursive(p, n - i - 1));
        }
        return max;
    }

    /**
     * 切钢条-递归自顶而下-带备忘
     *
     * @param p
     * @param mem 钢条切割子问题备忘数组
     * @param n
     * @return
     */
    public int cutRodMemoized(int[] p, int[] mem, int n) {
        if (n <= 0) {
            return 0;
        }
        if (mem[n - 1] > 0) {
            return mem[n - 1];
        }
        int max = 0;
        for (int i = 0; i < n; i++) {
            max = Math.max(max, p[i] + cutRodRecursive(p, n - i - 1));
        }
        mem[n - 1] = max;
        return max;
    }

    /**
     * 切钢条-自底而上
     */
    public int curRodBottomUp(int[] p, int n) {
        if (n <= 0) {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = p[0];
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                max = Math.max(max, p[j] + dp[i - j - 1]);
            }
            dp[i] = Math.max(max, p[i]);
        }
        return dp[n - 1];
    }

    /**
     * 切钢条-自底而上 切割一次有成本
     */
    public int curRodBottomUpWithCost(int[] p, int n, final int c) {
        if (n <= 0) {
            return 0;
        }
        int[] dp = new int[n];
        dp[0] = p[0];
        for (int i = 0; i < n; i++) {
            int max = 0;
            for (int j = 0; j < i; j++) {
                max = Math.max(max, p[j] + dp[i - j - 1] - c);
            }
            dp[i] = Math.max(max, p[i]);
        }
        return dp[n - 1];
    }

    /**
     * 切钢条-递归自顶而下-带备忘
     *
     * @param p
     * @param mem 钢条切割子问题备忘数组
     * @param n
     * @return
     */
    public int cutRodMemoizedWithScheme(final int[] p, int[] mem, int[] sch, int n) {
        if (n <= 0) {
            return 0;
        }
        if (mem[n - 1] > 0) {
            return mem[n - 1];
        }
        int max = 0;
        int len = 0;
        for (int i = 0; i < n; i++) {
            int sub = p[i] + cutRodMemoizedWithScheme(p, mem, sch, n - i - 1);
            if (sub > max) {
                max = sub;
                len = i + 1;
            }
        }
        sch[n - 1] = len;
        mem[n - 1] = max;
        return max;
    }

}
