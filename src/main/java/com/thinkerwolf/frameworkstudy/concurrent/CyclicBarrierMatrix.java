package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.Arrays;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierMatrix {
    final float[][] matrix;
    final int N;
    final CyclicBarrier barrier;
    public CyclicBarrierMatrix(float[][] data) {
        this.matrix = data;
        this.N = data.length;
        this.barrier = new CyclicBarrier(N, () -> { System.out.println("满员" + Arrays.deepToString(matrix)); });
    }
    public void start() {
        for (int i = 0; i < N; i++)
            new Thread(new Worker(i), "Worker-" + i).start();
    }
    class Worker implements Runnable {
        private int row;
        Worker(int row) {
            this.row = row;
        }
        @Override
        public void run() {
            float[] rows = matrix[row];
            for (int i = 0; i < rows.length; i++)
                rows[i] = rows[i] * 100;
            try {
                barrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
            }
            System.out.printf("Worker %s finish \n", Thread.currentThread().getName());
        }
    }
    public static void main(String[] args) {
        float[][] data = new float[][]{{9.0f, 8.0f, 7.0f},{2.4f, 3.5f, 4.9f}};
        new CyclicBarrierMatrix(data).start();
    }
}
