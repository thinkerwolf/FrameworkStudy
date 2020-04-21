package com.thinkerwolf.frameworkstudy.concurrent;

import com.thinkerwolf.frameworkstudy.common.Util;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 * ForkJoinPool测试代码
 *
 * @author wukai
 * @date 2020/4/20 16:21
 */
public class ForkJoinArraySum {

    static int MAX_GAP = 5000;

    public static void main(String[] args) {
        int[] nums = new int[1000000];
        for (int i = 0, max = nums.length; i < max; i++) {
            nums[i] = Util.nextInt(10);
        }
        long start = System.currentTimeMillis();
        System.out.println(Arrays.stream(nums).sum());
        System.out.println("Spend stream sum " + (System.currentTimeMillis() - start)); // 191

        ForkJoinPool fjp = new ForkJoinPool();
        AddTaskRet task = new AddTaskRet(nums, 0, nums.length);
        start = System.currentTimeMillis();
        fjp.execute(task);
        Long r = task.join();
        System.out.println(r);
        System.out.println("Spend fork join " + (System.currentTimeMillis() - start)); // 27

        Arrays.parallelSort(nums);
    }

    static class AddTaskRet extends RecursiveTask<Long> {
        int[] nums;
        int start;
        int end;

        public AddTaskRet(int[] nums, int start, int end) {
            this.nums = nums;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            if (end - start <= MAX_GAP) {
                long sum = 0;
                for (int i = start; i < end; i++) {
                    sum += nums[i];
                }
                return sum;
            }
            int mid = (end + start) / 2;
            AddTaskRet a1 = new AddTaskRet(nums, start, mid);
            AddTaskRet a2 = new AddTaskRet(nums, mid, end);
            a1.fork();
            a2.fork();
            return a1.join() + a2.join();
        }
    }

}
