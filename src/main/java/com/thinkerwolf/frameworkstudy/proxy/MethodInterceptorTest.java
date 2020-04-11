package com.thinkerwolf.frameworkstudy.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;



public class MethodInterceptorTest implements InvocationHandler {
	
	public MethodInterceptorTest() {
		
	}
	
	public Object intercept() {
		System.out.println("intercepetor start....");
		//Object res = proxy.invokeSuper(obj, args);
		//Object res = method.invoke(obj, args);
		System.out.println("intercepetor end....");
		return null;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println("invoke start....");
		Object res = method.invoke(proxy, args);
		System.out.println("intercepetor end....");
		return res;
	}
	
	
}
