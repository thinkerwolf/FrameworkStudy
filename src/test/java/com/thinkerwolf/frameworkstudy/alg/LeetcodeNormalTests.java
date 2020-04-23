package com.thinkerwolf.frameworkstudy.alg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Leetcode练习题混杂集合
 *
 * @author wukai
 * @date 2020/4/23 10:37
 */
public class LeetcodeNormalTests {
    public static void main(String[] args) {
        LeetcodeNormalTests solution = new LeetcodeNormalTests();

        int[] res = solution.twoSum(new int[]{2, 7, 11, 15}, 9);
        if (res != null) {
            System.out.println(res[0] + "," + res[1]);
        }

        double r = solution.findMedianSortedArraysFast(new int[]{1, 4, 6}, new int[]{2, 3, 5});
        System.out.println("findMedianSortedArrays : " + r);

        System.out.println(solution.longestPalindrome1("babad"));
        System.out.println(solution.longestPalindrome3("cbbd"));
        System.out.println(solution.longestPalindrome3("aa"));

        // System.out.println(solution.reverseInteger(-98889923));
        System.out.println(solution.reverseInteger3(1534236469));

        System.out.println(solution.convertStringZ2("PAYPALISHIRING", 4));

        System.out.println(solution.myAtoi("+"));
        System.out.println(solution.longestCommonPrefix(new String[]{"ccc", "cacc", "ccc"}));
        System.out.println(solution.threeSumClosest(new int[]{1, 1, -1, -1, 3}, -1));
        char c = 'a' + 25;
        System.out.println(c); // a 97 2 50

        System.out.println(solution.letterCombinations("3577"));
        // [[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],[-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]]
        System.out.println(solution.fourSum(new int[]{-3, -2, -1, 0, 0, 1, 2, 3}, 0));

        System.out.println(solution.generateParenthesis(4));
    }

    public int[] twoSum(int[] nums, int target) {
        int[] res = null;
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                if (nums[i] + nums[j] == target) {
                    res = new int[2];
                    res[0] = i;
                    res[1] = j;
                    break;
                }
            }
        }
        return res;
    }


    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        ListNode next1 = l1;
        ListNode next2 = l2;
        ListNode res = new ListNode(0);
        ListNode resNext = res;

        int carry = 0;
        while (next1 != null || next2 != null) {
            int num1 = next1 == null ? 0 : next1.val;
            int num2 = next2 == null ? 0 : next2.val;
            int sum = num1 + num2 + carry;
            int val = sum % 10;
            resNext.val = val;
            resNext.next = new ListNode(0);
            carry = sum / 10;
            next1 = next1 == null ? null : next1.next;
            next2 = next2 == null ? null : next2.next;
            resNext = resNext.next;
        }
        return res;
    }

    public double findMedianSortedArrays1(int[] nums1, int[] nums2) {
        int len = nums1.length + nums2.length;
        int[] nums = new int[len];
        for (int i = 0; i < nums1.length; i++) {
            nums[i] = nums1[i];
        }
        for (int i = 0; i < nums2.length; i++) {
            nums[i + nums1.length] = nums2[i];
        }
        Arrays.sort(nums);
        if (len % 2 != 0) {
            return nums[len / 2];
        } else {
            return (nums[len / 2] + nums[len / 2 - 1]) / 2.0;
        }
    }

    /**
     * 二分法变种
     *
     * @param nums1
     * @param nums2
     * @return
     */
    public double findMedianSortedArraysFast(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        if (m > n) {
            int[] temp = nums1;
            nums1 = nums2;
            nums2 = temp;
            int tmp = m;
            m = n;
            n = tmp;
        }
        int iMin = 0, iMax = m, halfLen = (m + n + 1) / 2;
        while (iMin <= iMax) {
            int i = (iMin + iMax) / 2;
            int j = halfLen - i;
            if (i > iMin && nums1[i] < nums2[j - 1]) {
                iMin = i + 1;
            } else if (j < iMax && nums2[j] < nums1[i - 1]) {
                iMax = i - 1;
            } else {
                // 正好
                int maxLeft = 0;
                if (i == 0) {
                    maxLeft = nums2[j - 1];
                } else if (j == 0) {
                    maxLeft = nums1[i - 1];
                } else {
                    maxLeft = Math.max(nums1[i - 1], nums2[j - 1]);
                }
                if ((m + n) % 2 == 1) {
                    return maxLeft;
                }
                int minRight = 0;
                if (i == n) {
                    minRight = nums2[j];
                } else if (j == m) {
                    minRight = nums1[i];
                } else {
                    minRight = Math.min(nums1[i], nums2[j]);
                }
                return (maxLeft + minRight) / 2.0;
            }
        }
        return 0.0;
    }

    /**
     * 字符串回文（暴力法）
     *
     * @param s
     * @return
     */
    public String longestPalindrome1(String s) {
        int len = s.length();
        int maxLen = 0;
        String ans = s;
        for (int i = 0; i < len; i++) {
            for (int j = i; j < len; j++) {
                int low = i;
                int high = j;
                boolean b = true;
                while (low <= high) {
                    if (s.charAt(low) != s.charAt(high)) {
                        b = false;
                        break;
                    }
                    low++;
                    high--;
                }
                if (b) {
                    if (maxLen < j - i + 1) {
                        maxLen = j - i + 1;
                        ans = s.substring(i, j + 1);
                    }
                }
            }
        }
        return ans;
    }

    /**
     * 字符串回文（）
     *
     * @param s
     * @return
     */
    public String longestPalindrome2(String s) {
        int len = s.length();
        if (len == 0 || len == 1) {
            return s;
        }
        int start = 0;
        int end = 0;
        int maxLen = 0;
        for (int i = 0; i < s.length(); i++) {
            int l1 = expendAndCompare(s, i, i);
            int l2 = expendAndCompare(s, i, i + 1);
            if (l1 > l2) {
                if (l1 > maxLen) {
                    maxLen = l1;
                    start = i - l1 / 2;
                    end = i + l1 / 2;
                }
            } else {
                if (l2 > maxLen) {
                    maxLen = l2;
                    start = i - l2 / 2 + 1;
                    end = i + l2 / 2;
                }
            }
        }
        return s.substring(start, end + 1);
    }

    private String processStr(String s) {
        StringBuilder sb = new StringBuilder("$#");
        for (int i = 0; i < s.length(); i++) {
            sb.append(s.charAt(i));
            sb.append("#");
        }
        return sb.toString();
    }

    /**
     * @param s
     * @return
     */
    public String longestPalindrome3(String s) {
        if (s == null) {
            return "";
        }
        int len = s.length();
        if (len == 0 || len == 1) {
            return s;
        }
        // 1.预处理，在字符串中插入'#',开头加入
        s = processStr(s);
        int length = s.length();
        int[] P = new int[s.length()];
        // 2.定义两个参数 mx表示 p[id] + i
        int mx = 1, id = 1;
        int max = 0, maxId = 0;
        for (int i = 1; i < s.length(); i++) {
            P[i] = mx > i ? Math.min(P[2 * id - i], mx - i) : 1;
            while (i + P[i] < length && i - P[i] >= 0 && s.charAt(i + P[i]) == s.charAt(i - P[i])) {
                P[i]++;
            }
            if (i + P[i] > mx) {
                mx = i + P[i];
                id = i;
            }
            if (P[i] > max) {
                max = P[i];
                maxId = i;
            }
        }
        return s.substring(maxId - P[maxId] + 1, maxId + P[maxId]).replaceAll("#", "");
    }

    public int expendAndCompare(String s, final int left, final int right) {
        int l = left;
        int r = right;
        while (l >= 0 && r < s.length()) {
            if (s.charAt(l) != s.charAt(r)) {
                break;
            }
            l--;
            r++;
        }
        return r - l - 1;
    }

    public int reverseInteger(int x) {
        String s = String.valueOf(x);
        int len = s.length();
        int start = 0;
        boolean isFu = false;
        if (s.charAt(0) == '-') {
            start = 1;
            isFu = true;
        }
        long num = 0;
        for (int i = len - 1; i >= start; i--) {
            int n = Integer.parseInt(String.valueOf(s.charAt(i)));
            num += n * Math.pow(10, i - start);

        }
        num = isFu ? -num : num;
        if (num > Integer.MAX_VALUE || num < Integer.MIN_VALUE) {
            return 0;
        }
        return (int) num;
    }

    public int reverseInteger2(int x) {
        long res = 0;
        int pow = String.valueOf(x).replaceAll("-", "").length() - 1;
        while (x != 0) {
            int n = x % 10;
            x = x / 10;
            res += n * Math.pow(10, pow);
            pow--;
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
                return 0;
            }
        }
        return (int) res;
    }

    public int reverseInteger3(int x) {
        long res = 0;
        while (x != 0) {
            int pop = x % 10;
            x = x / 10;
            res = res * 10 + pop;
            if (res > Integer.MAX_VALUE || res < Integer.MIN_VALUE) {
                return 0;
            }
        }
        return (int) res;
    }

    /**
     * @param s
     * @param numRows
     * @return
     */
    public String convertStringZ(String s, int numRows) {
        if (numRows <= 1) {
            return s;
        }
        int len = s.length();
        int rows = numRows;
        int col3 = 2 * rows - 2;
        List<StringBuilder> list = new ArrayList<StringBuilder>();
        for (int i = 0; i < Math.min(s.length(), rows); i++) {
            list.add(new StringBuilder());
        }
        for (int i = 0; i < len; i++) {
            int n = i / col3;
            int x = i - n * col3;
            int row = x < rows ? x : col3 - x;
            list.get(row).append(s.charAt(i));
        }
        StringBuilder sb = new StringBuilder();
        for (StringBuilder builder : list) {
            sb.append(builder);
        }
        return sb.toString();
    }

    /**
     * @param s
     * @param numRows
     * @return
     */
    public String convertStringZ2(String s, int numRows) {
        if (numRows <= 1) {
            return s;
        }
        int len = s.length();
        int rows = numRows;
        int col3 = 2 * rows - 2;
        int l1 = len / col3;
        int l2 = len % col3;
        int cols = (l1 == 0 ? 1 : l1) * (rows - 1) + (l2 % rows + 1);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; i + j < len; j += col3) {
                sb.append(s.charAt(i + j));
                if (i > 0 && i < rows - 1 && i + j - col3 > 0) {
                    sb.append(s.charAt(i + j - col3));
                }
            }
        }
        return sb.toString();
    }

    public int myAtoi(String str) {
        if (str.length() <= 0) {
            return 0;
        }
        final char[] val = str.toCharArray();
        int st = 0;
        int symbol = 0;
        while (st < val.length) {
            if (val[st] == '-') {
                if (symbol != 0) {
                    return 0;
                }
                symbol = -1;
            } else if (val[st] == '+') {
                if (symbol != 0) {
                    return 0;
                }
                symbol = 1;
            } else if (val[st] <= ' ') {
                if (symbol != 0) {
                    return 0;
                }
            } else {
                break;
            }
            st++;
        }
        if (st >= val.length) {
            return 0;
        }
        symbol = symbol == 0 ? 1 : symbol;
        char first = val[st];
        if (first >= '0' && first <= '9') {
            long res = 0;
            for (; st < val.length; st++) {
                char c = val[st];
                if (c >= '0' && c <= '9') {
                    res = res * 10 + c - '0';
                    if (symbol == 1) {
                        if (res >= Integer.MAX_VALUE) {
                            return Integer.MAX_VALUE;
                        }
                    } else {
                        if (res - 1 >= Integer.MAX_VALUE) {
                            return Integer.MIN_VALUE;
                        }
                    }
                } else {
                    break;
                }
            }
            return ((int) res) * symbol;
        }

        return 0;

    }

    public String longestCommonPrefix(String[] strs) {
        if (strs.length == 0) {
            return "";
        }
        String res = strs[0];
        for (int i = 1; i < strs.length; i++) {
            while (!strs[i].startsWith(res)) {
                res = res.substring(0, res.length() - 1);
                if (res.length() <= 0) {
                    return "";
                }
            }
        }
        return res;
    }

    public List<List<Integer>> threeSum(int[] nums) {
        HashSet<List<Integer>> set = new HashSet<>();
        Arrays.sort(nums);
        int len = nums.length;
        for (int x = 0; x < len; x++) {
            int left = x + 1;
            int right = len - 1;
            while (left < right) {
                int sum = nums[x] + nums[left] + nums[right];
                ArrayList<Integer> path = new ArrayList<Integer>();
                if (sum == 0) {
                    path.add(nums[x]);
                    path.add(nums[left]);
                    path.add(nums[right]);
                    set.add(path);
                    left++;
                    right--;
                } else if (sum > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }
        return new ArrayList<>(set);
    }

    public int threeSumClosest(int[] nums, int target) {
        int len = nums.length;
        int res = 0;
        int min = Integer.MAX_VALUE;
        Arrays.sort(nums);
        for (int i = 0; i < len; i++) {
            int left = i + 1;
            int right = len - 1;
            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    return sum;
                }
                int abs = Math.abs(target - sum);
                if (abs < min) {
                    min = abs;
                    res = sum;
                }
            }

        }
        return res;
    }

    private String[] getDigits() {
        return new String[]{"abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};
    }

    public List<String> letterCombinations(String digits) {
        char[] chars = digits.toCharArray();
        List<String> list = new LinkedList<>();
        int len = chars.length;
        if (len > 0) {
            char[] tmp = new char[len];
            appendStr(list, tmp, getDigits(), chars, 0);
        }
        return list;
    }

    public void appendStr(List<String> list, char[] tmp, String[] table, char[] chars, int x) {
        if (x > chars.length - 1) {
            list.add(new String(tmp));
            return;
        }
        String s = table[chars[x] - '2'];
        for (int i = 0; i < s.length(); i++) {
            tmp[x] = s.charAt(i);
            appendStr(list, tmp, table, chars, x + 1);
        }
    }

    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        int len = nums.length;
        Set<List<Integer>> set = new HashSet<>();
        /**
         * int ll = 0; int rr = len - 1; while (ll < rr) { int l = ll + 1; int r
         * = rr - 1; int sl = l, sr = r, el = l, er = r; while (l < r) { int sum
         * = nums[ll] + nums[rr] + nums[l] + nums[r]; if (sum < target) { l++; }
         * else if (sum > target) { r--; } else { l++; r--; List<Integer> list =
         * new ArrayList<>(); list.add(nums[ll]); list.add(nums[l]);
         * list.add(nums[r]); list.add(nums[rr]); set.add(list); } if (l < r) {
         * el = l; er = r; } } int offSetL = el - sl; int offSetR = sr - er; if
         * (offSetR > offSetL) { // 右侧偏移多 rr--; } else if (offSetR < offSetL) {
         * // 左侧偏移多 ll++; } else { // 两边偏移一样 ll++; rr--; } }
         */
        return new ArrayList<>(set);
    }

    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode ln1 = l1;
        ListNode ln2 = l2;
        List<ListNode> lnList = new ArrayList<>();
        while (ln1.next != null) {
            lnList.add(ln1);
            ln1 = ln1.next;
        }
        ln1.next = l2;
        while (ln2.next != null) {
            lnList.add(ln2);
            ln2 = ln2.next;
        }
        int[] tmp = new int[lnList.size()];
        helper(tmp, lnList, 0, lnList.size() - 1);
        return l1;
    }

    public void helper(int[] tmp, List<ListNode> lnList, int low, int high) {
        if (low >= high) {
            return;
        }
        int mid = (low + high) / 2;
        helper(tmp, lnList, low, mid);
        helper(tmp, lnList, mid, high);
        // 合并排序
        int i = low;
        int j = mid + 1;
        int k = 0;
        for (; i <= mid && j <= high; k++) {
            ListNode l1 = lnList.get(i);
            ListNode l2 = lnList.get(j);
            if (l1.val > l2.val) {
                tmp[k] = l2.val;
            } else {
                tmp[k] = l1.val;
            }
        }

        // 将多余的部分加入tmp中
        while (i <= mid) {
            tmp[k++] = lnList.get(i++).val;
        }
        while (j <= high) {
            tmp[k++] = lnList.get(j++).val;
        }

        for (int l = 0; l < tmp.length; l++)
            lnList.get(low + l).val = tmp[l];

    }

    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<>();
        char[] chars = new char[n * 2];
        generateParenthesisHelper(list, chars, n, n, 0);
        return list;
    }

    private void generateParenthesisHelper(List<String> list, char[] chars, int l, int r, int count) {
        if (l > r) {
            return;
        }
        if (count == chars.length) {
            list.add(new String(chars));
            return;
        }
        if (l > 0) {
            chars[count] = '(';
            generateParenthesisHelper(list, chars, l - 1, r, count + 1);
        }
        if (r > 0) {
            chars[count] = ')';
            generateParenthesisHelper(list, chars, l, r - 1, count + 1);
        }
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return val + "";
        }
    }

}
