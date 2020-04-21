package com.thinkerwolf.frameworkstudy;

import org.junit.Test;

public class NumberTests {

    @Test
    public void IEEEFloat() {
        System.out.println("======== k=8 n=23 ===========");
        printIEEEFloat(8, 23);
        System.out.println("JdkMin " + Float.MIN_VALUE + "\nJdkMax " + Float.MAX_VALUE);
        System.out.println("======== k=11 n=52 ===========");
        printIEEEFloat(11, 52);
        System.out.println("JdkMin " + Double.MIN_VALUE + "\nJdkMax " + Double.MAX_VALUE);
    }

    /**
     * 打印IEEE(电气电子工程师学会)浮点数表示
     *
     * @param k 阶码位
     * @param n 尾数位
     */
    private void printIEEEFloat(int k, int n) {
        // 1.非正规化 2.正规化  3.特殊值NaN
        // floatNum = (-1)^s * M * 2^E   二进制表示就为 sme
        int bias = (int) (Math.pow(2, k - 1) - 1);
        int maxE = (int) (Math.pow(2, k) - 2 - bias);
        int minE = 1 - bias;
        double epsilon = Math.pow(2, -n);
        double maxM = -epsilon + 2;
        double minM = epsilon;
        System.out.println("Min " + minM * Math.pow(2, minE));
        System.out.println("Normal " + (1 - epsilon) * Math.pow(2, minE));
        System.out.println("Max " + maxM * Math.pow(2, maxE));
    }

    @Test
    public void threadPoolCtl() {
        // 高三位代表状态  低29代表数量
        int COUNT_BITS = Integer.SIZE - 3;
        int CAPACITY = (1 << COUNT_BITS) - 1;
        int RUNNING = -1 << COUNT_BITS;
        int ctl = RUNNING | 100;
        System.out.println("RUNNING " + RUNNING);
        System.out.println("State " + (ctl & ~CAPACITY));
        System.out.println("Num " + (ctl & CAPACITY));
    }

    @Test
    public void xor() {
        // 异或相同去0，不同取1..就是加或减
        System.out.println(3 ^ 1 ^ 1 ^ 2 ^ 2); // 6 - 4
        System.out.println(10 ^ 4); // 10 + 4
        System.out.println(3 * 10 ^ 4); // 30 - 4
    }

}
