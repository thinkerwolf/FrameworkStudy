package com.thinkerwolf.frameworkstudy.rpc.test;

public class DemoServiceImpl implements DemoService {
	@Override
	public String sayHello(String str) {
		return "Hello " + str;
	}
}
