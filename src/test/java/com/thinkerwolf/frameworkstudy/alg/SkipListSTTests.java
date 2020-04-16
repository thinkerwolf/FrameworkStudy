package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.alogrithm.table.SkipListST;
import com.thinkerwolf.frameworkstudy.common.Util;
import org.junit.Test;

import java.util.LinkedHashSet;
import java.util.Set;

public class SkipListSTTests {

    @Test
    public void testPut1() {
        SkipListST st = new SkipListST();
        int[] keys = new int[]{4, 5, 1, 6, 10, 2, 3, 5};
        int[] values = new int[]{400, 500, 100, 600, 1000, 200, 300, 500};
        for (int i = 0; i < keys.length; i++) {
            st.put(keys[i], values[i]);
        }
        System.out.println(st);
    }

    @Test
    public void testPut2() {
        SkipListST st = new SkipListST();
        int max = 100;
        Set<Integer> keySet = new LinkedHashSet<>();
        for (int i = 0; i < max; i++) {
            int key = Util.nextInt(max);
            keySet.add(key);
            st.put(key, "val" + key);
            System.out.println(st.printIdx());
        }
        System.out.println(st);

    }

}
