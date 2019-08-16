package com.thinkerwolf.frameworkstudy;

import org.junit.Test;
import org.springframework.util.StringUtils;

public class SpringUtilTests {
    @Test
    public void stringUtils() {
        System.out.println(SpringUtilTests.class.getPackage());
        String path = StringUtils.cleanPath("classpath:*/words.txt");
        System.out.println(path);
    }
}
