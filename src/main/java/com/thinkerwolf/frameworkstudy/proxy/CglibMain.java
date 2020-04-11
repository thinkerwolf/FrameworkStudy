package com.thinkerwolf.frameworkstudy.proxy;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.commons.io.FileUtils;
import org.objectweb.asm.ClassWriter;

import net.sf.cglib.core.ClassGenerator;
import net.sf.cglib.core.DefaultGeneratorStrategy;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

public class CglibMain {
	static boolean testFile;
	static String classFilePath;
	static {
		testFile = true;
		classFilePath = System.getProperty("user.dir") + File.separator + "cglibBin";
	}

	String s1;
	String s2;

	public CglibMain(String s1, String s2) {
		this.s1 = s1;
		this.s2 = s2;
	}

	public static void main(String[] args) throws IOException {
		System.out.println(MethodInterceptorImpl.class.getCanonicalName());
		CglibGeneratorStrategy cgs = new CglibGeneratorStrategy();

		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(CglibMain.class);
		enhancer.setCallback(new MethodInterceptorImpl());
		enhancer.setStrategy(cgs);
		CglibMain cgm = (CglibMain) enhancer.create(new Class<?>[] { String.class, String.class },
				new Object[] { "666", "555" });

		String className = cgm.getClass().getSimpleName();
		FileUtils.writeByteArrayToFile(new File(classFilePath + File.separator + className + ".class"),
				cgs.getByteCode());
		cgm.sayHello("cglib");
		System.out.println(cgm.get());
	}

	static class MethodInterceptorImpl implements MethodInterceptor {
		@Override
		public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
			System.out.println("intercepetor start....");
			Object res = proxy.invokeSuper(obj, args);
			System.out.println("intercepetor res...." + res);
			System.out.println("intercepetor end....");
			return res;
		}
	}

	static class CglibGeneratorStrategy extends DefaultGeneratorStrategy {

		byte[] byteCode;

		public byte[] generate(ClassGenerator cg) throws Exception {
			ClassWriter cw = getClassWriter();
			transform(cg).generateClass(cw);
			this.byteCode = cw.toByteArray();
			String className = "FastClass&&" + cw.hashCode();
			FileUtils.writeByteArrayToFile(new File(classFilePath + File.separator + className + ".class"),
					cw.toByteArray());
			return transform(cw.toByteArray());
		}

		public byte[] getByteCode() {
			return byteCode;
		}

	}

	public void sayHello(String s) {
		this.s1 = s;
		System.out.println("hello " + s);
	}

	public String get() {
		return s1;
	}

}
