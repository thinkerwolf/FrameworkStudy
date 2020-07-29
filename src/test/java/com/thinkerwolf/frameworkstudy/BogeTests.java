package com.thinkerwolf.frameworkstudy;

import org.junit.Test;

import java.util.Arrays;

public class BogeTests {


    public int zuiAiXiaoCao(int m, int x, int[] ai) {
        final int n = ai.length;
        int minJ = 0;
        int minAi = Integer.MAX_VALUE;
        for (int j = 0; j < n; j++) {
            if (ai[j] < minAi) {
                minAi = ai[j];
                minJ = j;
            }
        }
        ai[minJ] += x;

        for (int i = 1; i < m; i++) {
            int min = 0;
            minAi = Integer.MAX_VALUE;
            for (int j = 0; j < n; j++) {
                if (ai[j] < minAi) {
                    minAi = ai[j];
                    min = j;
                }
            }
            ai[min] += x;
        }

        return ai[minJ];
    }

    public int[] geBuXiangTong(int[] nums) {
        int n = nums.length;
        for (; ; ) {
            int minI = -1;
            int minV = Integer.MAX_VALUE;
            for (int i = 1; i < n; i++) {
                if (nums[i] == nums[i - 1] && nums[i] < minV) {
                    minI = i;
                    minV = nums[i];
                }
            }
            if (minI < 0) {
                break;
            }
            nums[minI] *= 2;
            System.arraycopy(nums, minI, nums, minI - 1, n - minI);
            if (n - 1 <= 0) {
                break;
            }
            n--;
        }
        return Arrays.copyOf(nums, n);

    }

    @Test
    public void testZuiAiXiaoCao() {
        int m = 1000;
        int x = 5;
        int[] ai = new int[]{10, 9, 8, 2, 12};
        System.out.println(zuiAiXiaoCao(m, x, ai));
    }

    @Test
    public void testBuXiangTong() {
        int[] nums = new int[]{2, 2, 1, 1, 1};
        System.out.println(Arrays.toString(geBuXiangTong(nums)));
    }

}
