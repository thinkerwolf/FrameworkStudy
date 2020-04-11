package com.thinkerwolf.frameworkstudy;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

import java.util.HashMap;
import java.util.Map;

public class OpenJdkJolTests {

    @Test
    public void test() {
        Map map = new HashMap();
        map.put("1", "val1");
        map.put("2", "val2");

        System.out.println(ClassLayout.parseInstance(map).toPrintable());

        synchronized (map) {
            System.out.println(ClassLayout.parseInstance(map).toPrintable());
        }

        String s1 = ClassLayout.parseInstance("55").toPrintable();
        System.out.println(s1);
    }


}
