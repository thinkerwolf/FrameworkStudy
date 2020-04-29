package com.thinkerwolf.frameworkstudy.alg;

import org.junit.Test;

import java.util.Arrays;

/**
 * Leetcode基本字符串
 *
 * @author wukai
 * @date 2020/4/28 14:13
 */
public class LeetcodeBasicStringTests {

    @Test
    public void testStrStr() {
        System.out.println(strStr("hello", "ll"));
        System.out.println(strStr("aaaaa", "bba"));
        System.out.println(strStr("a", "a"));
        System.out.println(strStr("", ""));
        System.out.println(strStr("mississippi", "a"));
        System.out.println(strStr("mississippi", "issip"));
    }

    @Test
    public void testCountAndSay() {
        System.out.println(countAndSay(6));
    }


    /**
     * 有效的字母异位此
     */
    public boolean isAnagram(String s, String t) {
        char[] cs = s.toCharArray();
        char[] ct = t.toCharArray();
        Arrays.sort(cs);
        Arrays.sort(ct);
        return Arrays.equals(cs, ct);
    }

    /**
     * 整数反转
     */
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
     * @param haystack
     * @param needle
     * @return
     * @see java.lang.String#indexOf(String)
     */
    public int strStr(String haystack, String needle) {
        StringBuilder sb = new StringBuilder();
        if (haystack.length() < needle.length()) {
            return -1;
        }
        if (needle.length() == 0) {
            return 0;
        }
        int max = haystack.length() - needle.length();
        for (int i = 0; i <= max; i++) {
            if (needle.charAt(0) != haystack.charAt(i)) {
                while (++i <= max && haystack.charAt(i) != needle.charAt(0)) ;
            }

            if (i <= max) {
                int j = i + 1;
                int end = j + needle.length() - 1;
                for (int k = 1; j < end && haystack.charAt(j) == needle.charAt(k); j++, k++) ;
                if (j == end) {
                    return i;
                }
            }
        }
        return -1;
    }

    public String countAndSay(int n) {
        String s = "1";
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < n; i++) {
            int len = 0;
            sb.delete(0, sb.length());
            for (int j = 0; j < s.length(); j++) {
                len++;
                if (j + 1 >= s.length() || s.charAt(j) != s.charAt(j + 1)) {
                    sb.append(len);
                    sb.append(s.charAt(j));
                    len = 0;
                }
            }
            s = sb.toString();
        }
        return s;
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
}
