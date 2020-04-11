package com.thinkerwolf.frameworkstudy.proxy;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.ProtectionDomain;
import java.util.UUID;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;


public class ASMMain {

	static Method DEFINE_CLASS;
	static ProtectionDomain PROTECTION_DOMAIN;
	static {
		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				PROTECTION_DOMAIN = ASMMain.class.getProtectionDomain();
				return null;
			}
		});

		AccessController.doPrivileged(new PrivilegedAction<Void>() {
			@Override
			public Void run() {
				try {
					Class<?> loader = Class.forName("java.lang.ClassLoader");
					DEFINE_CLASS = loader.getDeclaredMethod("defineClass", new Class<?>[] { String.class, byte[].class,
							Integer.TYPE, Integer.TYPE, ProtectionDomain.class });
					DEFINE_CLASS.setAccessible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
				return null;
			}
		});

	}
	
	
	
	
	
	public static void main(String[] args) throws Exception {

		String clazzName = "AsmDeclare$$" + UUID.randomUUID().toString().replaceAll("-", "");
		String superClazzName = AsmUtil.getClazzName(AsmSuper.class);
		final Class<?> callBackClazz = MethodInterceptorTest.class;
		String callBackClazzName = AsmUtil.getClazzName(callBackClazz);
		Method interceptMethod = callBackClazz.getDeclaredMethod("invoke",
				new Class<?>[] { Object.class, Method.class, Object[].class });
		String callBackMethodParmStr = AsmUtil.getAsmMethodParmStr(interceptMethod, "");
		String callBackMethodName = interceptMethod.getName();
		
		
		
		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_MAXS + ClassWriter.COMPUTE_FRAMES);
		cw.visit(Opcodes.V1_7, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER, clazzName, null, superClazzName, null);
		
		FieldVisitor fv = cw.visitField(Opcodes.ACC_PRIVATE, "cgLibTest", "L" + callBackClazzName + ";", null, null);
		fv.visitEnd();
		
		MethodVisitor constructor = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
		constructor.visitCode();
		
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, clazzName, "<init>", "()V");
		
		constructor.visitVarInsn(Opcodes.ALOAD, 0);
		constructor.visitTypeInsn(Opcodes.NEW, callBackClazzName);
		constructor.visitInsn(Opcodes.DUP);
		constructor.visitMethodInsn(Opcodes.INVOKESPECIAL, callBackClazzName, "<init>", "()V");
		constructor.visitFieldInsn(Opcodes.PUTFIELD, clazzName, "cgLibTest", "L" + callBackClazzName + ";");
		constructor.visitInsn(Opcodes.RETURN);
		constructor.visitMaxs(0, 0);
		constructor.visitEnd();
		
		
		
		Method[] methods = AsmUtil.getCanInterceptorMethod(AsmSuper.class);
		for (Method method : methods) {
			String methodName = method.getName();
			MethodVisitor mv = cw.visitMethod(Opcodes.ACC_PUBLIC, methodName, AsmUtil.getAsmMethodParmStr(method, "V"),
					null, null);
			mv.visitCode();
			int num = method.getParameterTypes().length;
			
			// 准备参数
//			mv.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object");
//			for (int i = 1; i <= num; i++) {
//				mv.visitInsn(Opcodes.DUP);
//				//mv.visitInsn(Opcodes.ICONST_0);
//				mv.visitVarInsn(Opcodes.ALOAD, num);
//				mv.visitInsn(Opcodes.AASTORE);
//			}
//			
//			mv.visitVarInsn(Opcodes.ASTORE, 3);
			
			mv.visitVarInsn(Opcodes.ALOAD, 0);
			mv.visitFieldInsn(Opcodes.GETFIELD, clazzName, "cgLibTest", "L" + callBackClazzName + ";");
			
			//mv.visitVarInsn(Opcodes.ALOAD, 0);
			//mv.visitVarInsn(Opcodes.ALOAD, 3);
			//mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, callBackClazzName, callBackMethodName, callBackMethodParmStr + "Ljava/lang/Object;");
			
			
			
			
			mv.visitInsn(Opcodes.RETURN);
			mv.visitMaxs(0, 0);
			mv.visitEnd();

		}

		byte[] bs = cw.toByteArray();
		ClassLoader cl = Thread.currentThread().getContextClassLoader();

		Object[] ags = new Object[] { clazzName, bs, 0, bs.length, PROTECTION_DOMAIN };
		try {
			Class<?> clazz = (Class<?>) DEFINE_CLASS.invoke(cl, ags);
			Class.forName(clazzName, true, cl);

			AsmSuper c = (AsmSuper) clazz.newInstance();
			System.out.println(c.getClass().getName());

			c.sayHello("687293");
			c.sayGoodBye("liming");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
