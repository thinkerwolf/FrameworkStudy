package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EvenChecker implements Runnable {

    private IntGenerator ig;
    private int id;

    public EvenChecker(IntGenerator ig, int id) {
        this.ig = ig;
        this.id = id;
    }

    @Override
    public void run() {
        for (; !ig.isCanceled(); ) {
            int val = ig.next();
            if (val % 2 != 0) {
                System.out.println(val + " not even!");
                ig.setCanceled(true);
            }

            if (val > 10000) {
                break;
            }
        }
    }

    public static void test(IntGenerator ig, int count) {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < count; i++) {
            es.execute(new EvenChecker(ig, i));
        }
        es.shutdown();
    }
}




