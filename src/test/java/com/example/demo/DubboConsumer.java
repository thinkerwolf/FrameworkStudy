package com.example.demo;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.example.demo.service.HelloService;

public class DubboConsumer {

	public static void main(String[] args) {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "dubbo-consumer-context.xml" });
		context.start();
		HelloService demoService = (HelloService) context.getBean("dubboHelloService");
		String hello = demoService.sayHello("world");
		System.out.println(hello);
	}

}
