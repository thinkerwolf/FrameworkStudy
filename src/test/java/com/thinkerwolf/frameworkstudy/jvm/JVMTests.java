package com.thinkerwolf.frameworkstudy.jvm;

import org.junit.Test;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class JVMTests {

    static int t = 1;


    private volatile int i = 1;

    @Test
    public void methodHandle() {
        Object a = 1234.2134D;

        MethodType mt = MethodType.methodType(int.class);
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        try {
            // MethodHandle handle = lookup.findVirtual(a.getClass(), "intValue", mt);
            //MethodHandle handle = lookup.findGetter(a.getClass(), "value", double.class);
            MethodHandle handle = lookup.findStaticGetter(a.getClass(), "NaN", double.class);
//            handle.asFixedArity();
            Object value = handle.invoke();
            System.out.println(value);
        } catch (Throwable e) {
            e.printStackTrace();
        }

    }

    @Test
    public void newT() {
        i += 1;
    }


}
