package com.thinkerwolf.frameworkstudy.alogrithm.table;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;

import java.util.List;

/**
 * 内存使用对比
 *
 * @author wukai
 */
public class MemoryUsageCompare {
    public static void main(String[] args) {
        List <String> keys = Performance.getRandomKeys(10000);

        ST <String, Integer> st1 = new SequentialSearchST <>();
        ST <String, Integer> st2 = new BinaryTreeST <>();

        for (String key : keys) {
            st1.put(key, key.length());
            st2.put(key, key.length());
        }

        System.out.println("Memory usage : " + st1.getClass().getSimpleName() + " - " + st1.memoryUsage());
        System.out.println("Memory usage : " + st2.getClass().getSimpleName() + " - " + st2.memoryUsage());
    }

}
