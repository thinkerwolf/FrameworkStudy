package com.thinkerwolf.frameworkstudy.proxy;

public class AsmSuper {
	
	public final static int num = 123;
	
	
	public String name;
	private int age;
	
	public void sayHello(String string) {
		System.out.println("Hello " + string);
	}
	
	public void sayGoodBye(String string) {
		System.out.println("Goodbye " + string);
	}
}
