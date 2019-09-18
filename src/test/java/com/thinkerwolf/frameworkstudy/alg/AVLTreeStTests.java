package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.alogrithm.table.AVLTreeST;
import com.thinkerwolf.frameworkstudy.alogrithm.util.Util;
import org.junit.Test;

import java.util.*;

/**
 * AVL树测试
 *
 * @author wukai
 */
public class AVLTreeStTests {

    @Test
    public void testPut() {
        AVLTreeST<Integer, String> st = new AVLTreeST<>();
//        for (int i = 20; i >=1; i--) {
//            st.put(i, "val" + i);
//        }

        // fix error
//        int[] keys = new int[]{1, 9, 8};
//        for (int i = 0; i < keys.length; i++) {
//            st.put(keys[i], "val" + keys[i]);
//        }

        int max = 10000;
        Set<Integer> keySet = new LinkedHashSet<>();
        for (int i = 0; i < max; i++) {
            int key = Util.nextInt(max);
            keySet.add(key);
            st.put(key, "val" + key);
        }
        System.out.println(keySet);

        st.print();
        System.out.println("Height " + st.getHeight());
        System.out.println("size " + st.size());

        for (Integer k : keySet) {
            Util.isTrue(st.get(k) != null, "Bad AVL tree");
        }

    }

    @Test
    public void testDelete() {
        AVLTreeST<Integer, String> st = new AVLTreeST<>();
//        for (int i = 1; i <= 10; i++) {
//            st.put(i, "val" + i);
//        }
//        st.print();
//        for (int i = 1; i <= 10; i++) {
//            System.out.println("Get - " + st.get(i));
//        }
//
//        for (int i = 1; i <= 11; i++) {
//            System.out.println("Delete - " + st.delete(i));
//        }

        int max = 100000;
        Set<Integer> keySet = new LinkedHashSet<>();
        for (int i = 0; i < max; i++) {
            int key = Util.nextInt(max);
            keySet.add(key);
            st.put(key, "val" + key);
        }

        List<Integer> deleteKeys = new ArrayList<>(keySet);
        Collections.shuffle(deleteKeys);
        deleteKeys.add(max + 100);
        for (Integer key : deleteKeys) {
            String v = st.delete(key);
            // System.out.println("Delete -> " + v);
//            Util.isTrue(v != null, "Null key : " + key);
        }


    }

}
