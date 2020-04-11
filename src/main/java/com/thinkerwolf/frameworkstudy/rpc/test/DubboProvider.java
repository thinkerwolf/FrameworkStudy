package com.thinkerwolf.frameworkstudy.rpc.test;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboProvider {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("classpath:dubbo-provider-context.xml");
		//context.getBean("");
		context.start();
		System.in.read();
	}

}
