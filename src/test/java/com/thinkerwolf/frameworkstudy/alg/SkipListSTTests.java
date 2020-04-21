package com.thinkerwolf.frameworkstudy.alg;

import com.thinkerwolf.frameworkstudy.alogrithm.table.SkipListST;

import static com.thinkerwolf.frameworkstudy.common.Util.*;
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
        int max = 200;
        Set<Integer> keySet = new LinkedHashSet<>();
        println("Test put ====================== ");
        for (int i = 0; i < max; i++) {
            int key = nextInt(max);
            keySet.add(key);
            st.put(key, "val" + key);
            println(st.printIdx());
        }
        println(st);

        println("\nTest get ====================== ");
        for (Integer key : keySet) {
            printf("%d=%s, ", key, st.get(key));
        }
        println();

        println("\nTest remove ====================== ");
        for (Integer key : keySet) {
            printf("%d=%s, ", key, st.delete(key));
        }
        println();

        println(st.printIdx());
    }

}
