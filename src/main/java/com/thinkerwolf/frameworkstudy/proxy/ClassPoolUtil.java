package com.thinkerwolf.frameworkstudy.proxy;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.ProtectionDomain;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class ClassPoolUtil {

	private static Method generateClassMethod1, generateClassMethod2;

	static {
		try {
			// 通过反射机制来使用底层无法调用的方法
			AccessController.doPrivileged(new PrivilegedExceptionAction() {
				public Void run() throws Exception {
					Class<?> cl = Class.forName("java.lang.ClassLoader");
					generateClassMethod1 = cl.getDeclaredMethod("defineClass",
							new Class[] { String.class, byte[].class, int.class, int.class });

					generateClassMethod2 = cl.getDeclaredMethod("defineClass",
							new Class[] { String.class, byte[].class, int.class, int.class, ProtectionDomain.class });
					
					//System.loadLibrary("awt");
					
					return null;
				}
			});
		} catch (PrivilegedActionException pae) {
			throw new RuntimeException("cannot initialize ClassPool", pae.getException());
		}
	}

	/**
	 * 定义class文件
	 * 
	 * @return
	 */
	public static Class<?> defineClass(String name) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		// generateClassMethod1.invoke(classLoader, args);
		byte[] b = toByteArray();
		Object[] args = new Object[] { name, b, new Integer(0), new Integer(b.length) };
		generateClassMethod1.setAccessible(true);
		Class<?> clazz = null;
		try {
			clazz = (Class<?>) generateClassMethod1.invoke(classLoader, args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			generateClassMethod1.setAccessible(false);
		}

		if (clazz != null) {
			System.out.println(clazz.getName());
		}

		return clazz;
	}

	@SuppressWarnings("resource")
	private static byte[] toByteArray() {
		ByteArrayOutputStream array = new ByteArrayOutputStream();

		String str = System.getProperty("user.dir");
		String fileName = str + File.separator + "class" + File.separator + "Test.class";

		try {
			FileInputStream fis = new FileInputStream(fileName);
			byte[] buffer = new byte[1024];
			int len = 0;
			while ((len = fis.read(buffer)) != -1) {
				array.write(buffer, 0, len);
			}
			// DataOutputStream data = new DataOutputStream(fos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return array.toByteArray();
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		try {
//			String str = System.getProperty("user.dir");
//			String fileName = str + File.separator + "class" + File.separator + "Test.class";
//			FilePermission fp = new FilePermission(fileName, "read");
//			AccessController.checkPermission(fp);
			
			AccessControlContext context = AccessController.getContext();
			int t = 1;
			t ++;
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			defineClass("Test");
		}
		
		
		
		
	}

}
