package com.thinkerwolf.frameworkstudy;

import org.junit.Test;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.concurrent.TimeUnit;

public class JavaReferenceTests {

    static class T {
        @Override
        protected void finalize() throws Throwable {
            System.out.println("finalize....");
        }
    }

    @Test
    public void testStrongReference() throws InterruptedException {
        T t = new T();
        t = null;
        System.gc();
        TimeUnit.SECONDS.sleep(2);
    }

    @Test
    // -Xmx20M
    public void testSoftReference() {
        SoftReference<byte[]> m = new SoftReference<>(new byte[1024 * 1024 * 10]);
        System.gc();
        System.out.println(m.get()); // not null
        byte[] b = new byte[1024 * 1024 * 12];
        System.out.println(m.get()); // null
    }

    @Test
    public void testWeakReference() {
        WeakReference<byte[]> m = new WeakReference<>(new byte[1024 * 1024 * 10]);
        System.out.println(m.get());
        System.gc();
        System.out.println(m.get());
    }

    @Test
    public void testPhantomReference() throws InterruptedException {
        ReferenceQueue<byte[]> queue = new ReferenceQueue<>();
        PhantomReference<byte[]> m = new PhantomReference<>(new byte[10], queue);
        System.out.println(m.get());
        System.gc();
        Thread t = new Thread(() -> {
            for (; ; ) {
                Object t1 = queue.poll();
                if (t1 != null) {
                    System.out.println("Queue t " + t1);
                    break;
                }
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException ignored) {
                }
            }
        });
        t.start();
        t.join();
    }

}
