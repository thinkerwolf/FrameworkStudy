package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.common.Util;
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

    private static final int M = 10;

    @Test
    public void testQuickSort() {
        int[] nums = new int[]{7, 1, 5, 3, 6, 4, 14, 1, 2, 18, 10, 16, 9, 8};
        StdRandom.shuffle(nums);
        insertSort(nums, 0, nums.length - 1);
        System.out.printf("insertSort %s\n", Arrays.toString(nums));

        StdRandom.shuffle(nums);
        int[] aux = new int[nums.length];
        mergeSort(nums, aux, 0, nums.length - 1);
        System.out.printf("mergeSort %s\n", Arrays.toString(nums));

        StdRandom.shuffle(nums);
        quickSort(nums, 0, nums.length - 1);
        System.out.printf("quickSort %s\n", Arrays.toString(nums));

        StdRandom.shuffle(nums);
        quickSort3(nums, 0, nums.length - 1);
        System.out.printf("quickSort3 %s\n", Arrays.toString(nums));

    }

    @Test
    public void testRemoveDuplicates() {
        int[] nums = new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4};
        //nums = new int[]{1, 1};
        int len = removeDuplicates(nums);
        System.out.printf("Len %d nums %s\n", len, Arrays.toString(nums));
    }

    @Test
    public void testMaxProfit() {
        int[] prices = new int[]{7, 1, 5, 3, 6, 4};
        prices = new int[]{1, 2, 3, 4, 5};
        int p = maxProfit(prices);
        System.out.printf("Max profit %d\n", p);
    }

    @Test
    public void testRotate() {
        int[] nums = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int k = 3;
        rotate(nums, k);
        System.out.printf("nums %s\n", Arrays.toString(nums));
    }

    @Test
    public void testSingleNumber() {
        int[] nums = new int[]{2, 2, 1};
        System.out.println(singleNumber(nums));
    }

    @Test
    public void testIntersect() {
//        int[] nums1 = new int[]{1, 2, 4, 5, 7};
//        int[] nums2 = new int[]{9, 4, 5};
        int[] nums1 = new int[]{4, 9, 5};
        int[] nums2 = new int[]{9, 4, 9, 8, 4};
        System.out.printf("Nums %s\n", Arrays.toString(intersect(nums1, nums2)));
    }

    @Test
    public void testPlusOne() {
        int[] a = new int[]{1, 1, 2, 4};
        System.out.printf("Nums %s\n", Arrays.toString(plusOne(a)));
        a = new int[]{0};
        System.out.printf("Nums %s\n", Arrays.toString(plusOne(a)));
        a = new int[]{9, 9, 9};
        System.out.printf("Nums %s\n", Arrays.toString(plusOne(a)));
    }

    @Test
    public void testMoveZeros() {
        int[] a = new int[]{1, 2, 3, 0, 0, 4};
        moveZeros(a);
        System.out.printf("Nums %s\n", Arrays.toString(a));
        a = new int[]{1, 2, 3, 0, 0, 4};
        moveZeros2(a);
        System.out.printf("Nums %s\n", Arrays.toString(a));
    }

    @Test
    public void testIsValidSudoku() {
        char[][] board = new char[][]{
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
        System.out.println(isValidSudoku(board));
    }

    @Test
    public void testReverseMatrix() {
        int[][] matrix = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 9}
        };
//        transpostMatrix(matrix); // 矩阵转置
//        System.out.printf("Transpose %s\n", Arrays.deepToString(matrix));
//        reverseRowMatrix(matrix); // 反转行
//        System.out.printf("Reverse Row %s \n", Arrays.deepToString(matrix));
        rotate(matrix);
        System.out.printf("Rotate %s \n", Arrays.deepToString(matrix));
    }

    /**
     * 插入排序
     *
     * @param a 原数组
     * @param l 起始点
     * @param h 终点
     */
    public void insertSort(int[] a, int l, int h) {
        if (l >= h) {
            return;
        }
        for (int i = l + 1; i <= h; i++) {
            for (int j = i; j > l && a[j] < a[j - 1]; j--) {
                int t = a[j];
                a[j] = a[j - 1];
                a[j - 1] = t;
            }
        }
    }

    /**
     * 归并排序
     *
     * @param a   原数组
     * @param aux 合并暂存数组
     * @param l   起始点
     * @param h   终点
     */
    public void mergeSort(int[] a, int[] aux, int l, int h) {
        if (l >= h) {
            return;
        }
        final int m = (h + l) / 2;
        mergeSort(a, aux, l, m);
        mergeSort(a, aux, m + 1, h);
        if (a[m] > a[m + 1]) {
            int i = l;
            int j = m + 1;
            if (h + 1 - l >= 0) System.arraycopy(a, l, aux, l, h + 1 - l);
            for (int k = l; k <= h; k++) {
                if (i > m) a[k] = aux[j++];
                else if (j > h) a[k] = aux[i++];
                else if (aux[i] > aux[j]) a[k] = aux[j++];
                else a[k] = aux[i++];
            }
        }
    }

    /**
     * 快速排序
     *
     * @param a
     * @param l
     * @param h
     */
    public void quickSort(int[] a, int l, int h) {
        if (l >= h)
            return;
        int base = a[l];
        int i = l;
        int j = h + 1;
        int t;
        for (; ; ) {
            while (a[++i] < base) {
                if (i == h)
                    break;
            }
            while (base < a[--j]) {
                if (j == l)
                    break;
            }
            if (i >= j) {
                break;
            }
            t = a[i];
            a[i] = a[j];
            a[j] = t;
        }
        t = a[j];
        a[j] = a[l];
        a[l] = t;
        quickSort(a, l, j - 1);
        quickSort(a, j + 1, h);
    }

    /**
     * 快速排序 三向切分
     *
     * @param a 数组
     * @param l 起始点
     * @param h 终点
     */
    public void quickSort3(int[] a, int l, int h) {
        if (l >= h) return;
        int lt = l;
        int gt = h;
        int m = l + 1;
        int base = a[l];
        while (m <= gt) {
            if (a[m] > base) Util.exch(a, m, gt--);
            else if (a[m] < base) Util.exch(a, m++, lt++);
            else m++;
        }
        quickSort3(a, l, lt - 1);
        quickSort3(a, gt + 1, h);
    }


    /**
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/21/">移除排序数组中重复的元素</a>
     */
    public int removeDuplicates(int[] nums) {
        /* 第一遍解法
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
        return max - rs;*/


        /* 第二遍解法 */
        int n = nums.length;
        int idx = 1;
        for (int i = 1; i < n; i++) {
            if (nums[i - 1] != nums[i]) {
                nums[idx++] = nums[i];
            }
        }
        return idx;
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
        // 方案1：先通过交换左滑k个数量，遇到交换数量不足时逐个右移动
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

    public void rotate1(int[] nums, int k) {
        if (k <= 0 || nums.length < 2) {
            return;
        }
        // 方案2：一步步右移时间复杂度为O(k * (N - 1))
        int t;
        for (int i = 0; i < k; i++) {
            t = nums[nums.length - 1];
            System.arraycopy(nums, 0, nums, 1, nums.length - 1);
            nums[0] = t;
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
        for (int i = 1; i < nums.length; i++) {
            // 3 ^ 1 ^ 1 = 3
            ans = ans ^ nums[i];
        }
        return ans;
    }

    /**
     * 相同的元素
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/26/">两个数组的交集</a>
     */
    public int[] intersect(int[] nums1, int[] nums2) {
        // 方案1 : 排序后查找
        // 时间复杂度 O(N1logN1 + N2logN2 + (N1+N2)/2)
        // 空间复杂度 O(min(N1, N2))
        if (nums1.length > nums2.length) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }
        int size = 0;
        int[] res;
        int i = 0, j = 0;
        Arrays.sort(nums1);
        Arrays.sort(nums2);
        res = new int[nums1.length];
        for (; i < nums1.length && j < nums2.length; ) {
            if (nums1[i] == nums2[j]) {
                res[size++] = nums1[i];
                i++;
                j++;
            } else if (nums1[i] > nums2[j]) {
                j++;
            } else {
                i++;
            }
        }
        return Arrays.copyOf(res, size);
    }

    public int[] intersect2(int[] nums1, int[] nums2) {
        // 方案2: 使用map
        if (nums1.length > nums2.length) {
            int[] t = nums1;
            nums1 = nums2;
            nums2 = t;
        }
        int size = 0;
        int[] res;
        int i = 0, j = 0;
        Map<Integer, Integer> map1 = new HashMap<>();
        for (i = 0; i < nums1.length; i++) {
            map1.compute(nums1[i], (key, old) -> {
                if (old == null) {
                    return 1;
                }
                return old + 1;
            });
        }
        Map<Integer, Integer> map2 = new HashMap<>();
        for (i = 0; i < nums2.length; i++) {
            map2.compute(nums2[i], (key, old) -> {
                if (old == null) {
                    return 1;
                }
                return old + 1;
            });
        }
        res = new int[nums1.length];
        for (Map.Entry<Integer, Integer> kv : map2.entrySet()) {
            Integer v = map1.get(kv.getKey());
            if (v != null) {
                v = Math.min(kv.getValue(), v);
                size += v;
                while (v-- > 0) {
                    res[j++] = kv.getKey();
                }
            }
        }
        return Arrays.copyOf(res, size);
    }


    /**
     * 数组组成的十进制数加1
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/26/">数组加1</a>
     */
    public int[] plusOne(int[] digits) {
        int f = 1;
        for (int i = digits.length - 1; i >= 0; i--) {
            int n = digits[i] + f;
            digits[i] = n % 10;
            f = n / 10;
            if (f < 1) {
                break;
            }
        }
        if (f > 0) {
            int[] res = new int[digits.length + 1];
            System.arraycopy(digits, 0, res, 1, digits.length);
            res[0] = f;
            return res;
        }
        return digits;
    }

    /**
     * 原地操作，不适用额外空间。
     * 尽量减少操作次数
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/27/">0移动到右边</a>
     */
    public void moveZeros(int[] nums) {
        // 方案1 暴力法，遇到一个0就往右移动一个
        if (nums.length < 2) return;
        int s = 0;
        int e = nums.length;
        for (; s < e; ) {
            if (nums[s] == 0) {
                System.arraycopy(nums, s + 1, nums, s, e - s - 1);
                nums[e - 1] = 0;
                e--;
            } else {
                s++;
            }
        }
    }

    public void moveZeros2(int[] nums) {
        // 方案2 双指针法
        if (nums == null || nums.length < 2) return;
        int i = 0;
        int j = 0;
        for (; i < nums.length && j < nums.length; ) {
            if (nums[i] != 0 && nums[j] == 0) {
                nums[j] = nums[i];
                nums[i] = 0;
            }
            i++;
            if (nums[j] != 0) {
                j++;
            }
        }
    }

    /**
     * 9 x 9
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/30/">有效的数独</a>
     */
    public boolean isValidSudoku(char[][] board) {
        // 0, 0  0, 3   0, 6
        // 3, 0  3, 3   3, 6
        final char empty = '.';
        Map<Integer, Integer>[] rows = new HashMap[9];
        Map<Integer, Integer>[] columns = new HashMap[9];
        for (int i = 0; i < 9; i++) {
            rows[i] = new HashMap<>();
            columns[i] = new HashMap<>();
        }
        Map<Integer, Integer> map = new HashMap<>();
        for (int n = 0; n < 9; n++) {
            int y = 3 * (n / 3); // 行
            int x = 3 * (n % 3); // 列
            for (int i = y; i < y + 3; i++) {
                for (int j = x; j < x + 3; j++) {
                    int c = (int) board[i][j];
                    if (empty == c) {
                        continue;
                    }
                    if (map.containsKey(c)) {
                        return false;
                    }
                    rows[i].put(c, rows[i].getOrDefault(c, 0) + 1); // 行
                    columns[j].put(c, columns[j].getOrDefault(c, 0) + 1); // 列
                    if (rows[i].get(c) > 1 || columns[j].get(c) > 1) {
                        return false;
                    }
                    map.put(c, 1);
                }
            }
            map.clear();
        }
        return true;
    }

    /**
     * n x n的矩阵旋转，原地交换，不可使用额外空间
     * <a href="https://leetcode-cn.com/explore/interview/card/top-interview-questions-easy/1/array/31/">旋转矩阵</a>
     */
    public void rotate(int[][] matrix) {
        // 先进行矩阵转置 A^T
        transpostMatrix(matrix);
        // 行反转
        reverseRowMatrix(matrix);
    }

    /**
     * 矩阵转置
     */
    private void transpostMatrix(int[][] matrix) {
        // transpose matrix
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                int tmp = matrix[j][i];
                matrix[j][i] = matrix[i][j];
                matrix[i][j] = tmp;
            }
        }
    }

    private void reverseRowMatrix(int[][] matrix) {
        int n = matrix.length;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n / 2; j++) {
                int tmp = matrix[i][j];
                matrix[i][j] = matrix[i][n - j - 1];
                matrix[i][n - j - 1] = tmp;
            }
        }
    }


}
