package com.thinkerwolf.frameworkstudy;

import org.junit.Test;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpringUtilTests {
    @Test
    public void stringUtils() {
        System.out.println(SpringUtilTests.class.getPackage());
        String path = StringUtils.cleanPath("classpath:*/words.txt");
        System.out.println(path);

        System.out.println(0 + 'a');
        System.out.println(0 + 'z');
        System.out.println(0 + 'A');
        System.out.println(0 + 'Z');

        System.out.println(14 ^ 13);

    }

    @Test
    public void testReg() {
        String str = "t{}u{}bb{}";
        String pattern = "\\{\\s*\\}";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            m.appendReplacement(sb, "1");
        }

        System.out.println(sb.toString());
    }

}
