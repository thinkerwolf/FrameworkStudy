package com.thinkerwolf.frameworkstudy.alg;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Leetcode数组初级算法测试
 *
 * @author wukai
 * @date 2020/4/21 11:02
 */
public class LeetcodeBasicArrayTests {

    @Test
    public void quickSortTest() {
        int[] nums = new int[]{7, 1, 5, 3, 6, 4};
        sort(nums, 0, nums.length - 1);
        System.out.printf("nums %s\n", Arrays.toString(nums));
        StdRandom.shuffle(nums);
        sort2(nums, 0, nums.length - 1);
        System.out.printf("nums %s\n", Arrays.toString(nums));
    }

    @Test
    public void removeDuplicatesTest() {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        //nums = new int[]{1, 1};
        int len = removeDuplicates(nums);
        System.out.printf("Len %d nums %s\n", len, Arrays.toString(nums));
    }

    @Test
    public void maxProfitTest() {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        prices = new int[]{1, 2, 3, 4, 5};
        int p = maxProfit(prices);
        System.out.printf("Max profit %d\n", p);
    }

    @Test
    public void rotateTest() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 3;
        rotate(nums, k);
        System.out.printf("nums %s\n", Arrays.toString(nums));
    }

    @Test
    public void singleNumberTest() {
        int[] nums = new int[]{1, 1, 3, 2, 8, 8, 2, 1};
        System.out.println(singleNumber(nums));
    }

    /**
     * 快排实现
     */
    public void sort(int[] nums, int l, int h) {
        if (l >= h)
            return;
        int base = nums[l];
        int i = l;
        int j = h + 1;
        int t;
        for (; ; ) {
            while (nums[++i] <= base) {
                if (i >= h)
                    break;
            }
            while (nums[--j] >= base) {
                if (j <= l)
                    break;
            }
            if (i >= j) {
                break;
            }
            t = nums[i];
            nums[j] = nums[i];
            nums[j] = t;
        }
        nums[l] = nums[j];
        nums[j] = base;
        sort(nums, l, j);
        sort(nums, j + 1, h);
    }

    public void sort2(int[] nums, int l, int h) {
        if (l >= h) {
            return;
        }
        int base = nums[l];
        int i = l + 1;
        int j = h;
        int t;
        while (i <= j) {
            if (nums[i] <= base) {
                i++;
            } else if (nums[j] >= base) {
                j--;
            } else {
                t = nums[i];
                nums[i] = nums[j];
                nums[j] = t;
            }
        }
        nums[l] = nums[j];
        nums[j] = base;
        sort2(nums, l, j);
        sort2(nums, j + 1, h);
    }

    /**
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/21/">移除排序数组中重复的元素</a>
     */
    public int removeDuplicates(int[] nums) {
        int rs = 0;
        final int max = nums.length;
        for (int i = 1; i < max; i++) {
            if (nums[i] == nums[i - 1]) {
                rs++;
            } else {
                if (rs > 0) {
                    nums[i - rs] = nums[i];
                }
            }
        }
        for (int i = max - rs; i < max; i++) {
            nums[i] = 0;
        }
        return max - rs;
    }

    /**
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/22/">股票交易最大收益</a>
     */
    public int maxProfit(int[] prices) {
        final int len = prices.length;
        int valley;
        int peek;
        int maxProfit = 0;
        for (int i = 0; i < len; ) {
            while (i < prices.length - 1 && prices[i] >= prices[i + 1]) {
                i++;
            }
            valley = prices[i];
            while (i < prices.length - 1 && prices[i] <= prices[i + 1]) {
                i++;
            }
            peek = prices[i];
            maxProfit += peek - valley;
            i++;
        }
        return maxProfit;
    }

    /**
     * 要求空间复杂度为O(1)的原地算法
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/23/">右移k数组</a>
     */
    public void rotate(int[] nums, int k) {
        if (k <= 0 || nums.length < 2) {
            return;
        }
        // 方案1：一步步右移时间复杂度为O(k * (N - 1))
//        int t;
//        for (int i = 0; i < k; i++) {
//            t = nums[nums.length - 1];
//            System.arraycopy(nums, 0, nums, 1, nums.length - 1);
//            nums[0] = t;
//        }
        // 方案2：先通过交换左滑k个数量，遇到交换数量不足时逐个右移动
        int i, j, t;
        for (i = nums.length - 1; i - k + 1 >= k; ) {
            for (j = 0; j < k; j++) {
                t = nums[i - j];
                nums[i - j] = nums[i - j - k];
                nums[i - j - k] = t;
            }
            i = i - k;
        }
        if (i > 0) {
            for (j = 0; j < k; j++) {
                t = nums[i];
                System.arraycopy(nums, 0, nums, 1, i);
                nums[0] = t;
            }
        }
    }

    /**
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/24/">是否包含重复数字</a>
     */
    public boolean containsDuplicate(int[] nums) {
        if (nums.length < 2) {
            return false;
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (map.containsKey(num)) {
                return true;
            }
            map.put(num, 1);
        }
        return false;
    }

    /**
     * 要求时间复杂度为线性的，空间复杂度为O(1)
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/25/">只出现一次的数字</a>
     */
    public int singleNumber(int[] nums) {
        if (nums.length < 2) {
            return nums[0];
        }
        // 方案1，快速排序后判断
        // 终极方案 异或?????
        int ans = nums[0];
        for (int i = 1; i < nums.length - 1; i++) {
            // 3 ^ 1 ^ 1 = 3
            ans = ans ^ nums[i];
        }
        return ans;
    }

}
