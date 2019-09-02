package com.thinkerwolf.frameworkstudy;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class SpringUtilTests {
    @Test
    public void stringUtils() {
        System.out.println(SpringUtilTests.class.getPackage());
        String path = StringUtils.cleanPath("classpath:*/words.txt");
        System.out.println(path);

        System.out.println(0+ 'a');
        System.out.println(0+ 'z');
        System.out.println(0 + 'A');
        System.out.println(0 + 'Z');
    }
}
