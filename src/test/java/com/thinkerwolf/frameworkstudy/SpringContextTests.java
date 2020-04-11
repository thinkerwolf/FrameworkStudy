package com.thinkerwolf.frameworkstudy;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringContextTests {

    @Test
    public void xmlTest() {
        AnnotationConfigApplicationContext annotation = new AnnotationConfigApplicationContext("com.thinkerwolf");
        ClassPathXmlApplicationContext xml = new ClassPathXmlApplicationContext(new String[]{"applicationContext.xml"}, annotation);



        System.out.println(annotation.getBean("person").equals(xml.getBean("person")));
    }

}
