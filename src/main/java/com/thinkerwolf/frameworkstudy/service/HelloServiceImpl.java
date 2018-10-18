package com.thinkerwolf.frameworkstudy.service;


public class HelloServiceImpl implements HelloService {
	private String prefix;

	private String suffix;

	public HelloServiceImpl(String prefix, String suffix) {
		this.prefix = prefix;
		this.suffix = suffix;
	}

	@Override
	public String sayHello(String name) {
		System.out.println(String.format("%s %s %s", prefix, name, suffix));
		return String.format("%s %s %s", prefix, name, suffix);
	}

}
