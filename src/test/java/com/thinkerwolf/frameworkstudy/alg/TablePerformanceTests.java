package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.alogrithm.ST;
import com.thinkerwolf.frameworkstudy.alogrithm.table.AVLTreeST;
import com.thinkerwolf.frameworkstudy.alogrithm.table.BTreeST;
import com.thinkerwolf.frameworkstudy.alogrithm.table.BinaryTreeST;
import org.junit.Test;

import java.util.*;

/**
 * 树形符号表性能测试
 *
 * @author wukai
 */
public class TablePerformanceTests {

    private static int MAX_NUM = 65536;

    private static List<Integer> randomKeys;

    static {
        randomKeys = new LinkedList<>();
        for (int i = 0; i < MAX_NUM; i++) {
            randomKeys.add(i + 1);
        }
        Collections.shuffle(randomKeys);
        System.out.println("树形符号表性能测试");
        System.out.println("顺序插入键数量：" + MAX_NUM);
        System.out.println("随机插入键数量：" + randomKeys.size());
    }


    @Test
    public void testBinaryTreeSTOrderedInsert() {
        ST<Integer, String> st = new BinaryTreeST<>();
        for (int i = 1; i <= MAX_NUM; i++) {
            st.put(i, "val" + i);
        }
    }

    @Test
    public void testBinaryTreeSTRandomInsert() {
        ST<Integer, String> st = new BinaryTreeST<>();
        for (Integer i : randomKeys) {
            st.put(i, "val" + i);
        }
    }

    @Test
    public void testAVLTreeSTOrderedInsert() {
        ST<Integer, String> st = new AVLTreeST<>();
        for (int i = 1; i <= MAX_NUM; i++) {
            st.put(i, "val" + i);
        }
    }

    @Test
    public void testAVLTreeSTRandomInsert() {
        ST<Integer, String> st = new AVLTreeST<>();
        for (Integer i : randomKeys) {
            st.put(i, "val" + i);
        }
    }

    @Test
    public void testBTreeSTOrderedInsert() {
        ST<Integer, String> st = new BTreeST<>(5);
        for (int i = 1; i <= MAX_NUM; i++) {
            st.put(i, "val" + i);
        }
    }

    @Test
    public void testBTreeSTRandomInsert() {
        ST<Integer, String> st = new BTreeST<>(5);
        for (Integer i : randomKeys) {
            st.put(i, "val" + i);
        }
    }

    @Test
    public void testTreeMapOrderedInsert() {
        Map<Integer, String> map = new TreeMap<>();
        for (int i = 1; i <= MAX_NUM; i++) {
            map.put(i, "val" + i);
        }
    }

    @Test
    public void testTreeMapRandomInsert() {
        Map<Integer, String> map = new TreeMap<>();
        for (Integer i : randomKeys) {
            map.put(i, "val" + i);
        }
    }


}
