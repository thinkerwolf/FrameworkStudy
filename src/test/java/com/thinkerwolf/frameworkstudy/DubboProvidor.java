package com.thinkerwolf.frameworkstudy;

import java.io.IOException;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboProvidor {

	public static void main(String[] args) throws IOException {
		System.setProperty("java.net.preferIPv4Stack", "true");
		ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { "dubbo-provider-context.xml" });
		context.start();
		System.out.println("Provider started.");
		System.in.read();
	}

}
