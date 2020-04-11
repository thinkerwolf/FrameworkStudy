package com.thinkerwolf.frameworkstudy;

/**
 * 证明指令重排序
 */
public class InstructionResorting {
    private int x, y, a, b;
    public void test() throws InterruptedException {
        long loop = 0;
        for (;;) {
            x = 0; y = 0; a = 0; b = 0;
            Thread t1 = new Thread(() -> {
                x = 1; a = y;
            });
            Thread t2 = new Thread(() -> {
                y = 1; b = x;
            });
            t1.start(); t2.start(); t1.join(); t2.join();
            loop++;
            if (a == 0 && b == 0) {
                break;
            }
        }
        System.out.printf("Loop %d (%d, %d)\n", loop, a, b);
    }
    public static void main(String[] args) throws InterruptedException {
        new InstructionResorting().test();
    }
}
