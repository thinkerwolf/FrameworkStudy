package com.thinkerwolf.frameworkstudy.proxy;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class AsmUtil {

	public static String getAsmMethodParmStr(Method method, String S) {
		// ([Ljava/lang/String;Ljava/lang/String;)V
		StringBuilder sb = new StringBuilder("(");

		Class<?>[] parmTypes = method.getParameterTypes();
		for (Class<?> parmType : parmTypes) {
			String parmName = parmType.getCanonicalName();
			parmName = parmName.replaceAll("\\.", "/").replaceAll("\\[", "").replaceAll("\\]", "");
			if (parmType.isArray()) {
				sb.append("[L");
			} else {
				sb.append("L");
			}
			sb.append(parmName);
			sb.append(";");
		}
		sb.append(")");
		sb.append(S);
		return sb.toString();
	}

	public static Method[] getCanInterceptorMethod(Class<?> clazz) {
		Method[] methods = clazz.getMethods();
		List<Method> list = new ArrayList<Method>();
		for (Method method : methods) {
			if ((method.getModifiers() & Modifier.FINAL) == 0 && (method.getModifiers() & Modifier.PRIVATE) == 0) {
				list.add(method);
			}
		}
		Method[] res = new Method[list.size()];

		for (int i = 0; i < list.size(); i++) {
			res[i] = list.get(i);
		}
		return res;
	}
	
	public static String getClazzName(Class<?> clazz) {
		return clazz.getCanonicalName().replaceAll("\\.", "/");
	}
	
//	public static void main(String[] args) throws Exception {
//		List<String> list = new LinkedList<String>();
//
//	}

}
