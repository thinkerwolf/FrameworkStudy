package com.thinkerwolf.frameworkstudy.jvm;

import com.thinkerwolf.frameworkstudy.common.Util;
import org.junit.Test;
import sun.misc.Unsafe;

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

    @Test
    public void unsafe() {
        Unsafe unsafe = Util.getUnsafe();
        long address = unsafe.allocateMemory(10);
        unsafe.putByte(address, (byte) 1);
        unsafe.putByte(address + 1, (byte) 2);
        System.out.println(unsafe.getByte(address));
        System.out.println(unsafe.getByte(address + 1));
        unsafe.freeMemory(address);
    }

    @Test
    public void testChar() {
        System.out.println((int) '0');
    }


}
