package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.alogrithm.table.AVLTreeST;
import com.thinkerwolf.frameworkstudy.common.Util;
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
        }
    }

}
