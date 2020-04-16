package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Thinking in java Terminating tasks
 */
public class OrnamentalGarden {

    public static void main(String[] args) {
        ExecutorService exec = Executors.newCachedThreadPool();
        for (int i = 0; i < 4; i++) {
            exec.execute(new Entrance(i));
        }
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ignored) {

        }

        // shutdown
        Entrance.setCanceled(true);
        exec.shutdown();

        try {
            if (!exec.awaitTermination(250, TimeUnit.MILLISECONDS)) {

            }
        } catch (InterruptedException e) {
        }
        System.out.println("Total num: " + Entrance.getTotalCount());
        System.out.println("Sum num: " + Entrance.sumEntrances());
    }
}


class Count {
    private int count;
    private Random rand = new Random(47);

    public synchronized int increment() {
        int temp = count;
        if (rand.nextBoolean()) {
            Thread.yield();
        }
        return (count = ++temp);
    }

    public synchronized int getCount() {
        return count;
    }
}

class Entrance implements Runnable {
    private static List<Entrance> entrances = new ArrayList<>();
    private static volatile boolean canceled = false;
    private static Count count = new Count();
    private int id;
    private int number;

    public Entrance(int id) {
        this.id = id;
        entrances.add(this);
    }

    @Override
    public void run() {
        while (!canceled) {
            synchronized (this) {
                number++;
            }
            System.out.println(this.toString() + " Total: " + count.increment());
            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        System.out.println(this + " stop....");
    }

    public synchronized int getValue() {
        return number;
    }

    @Override
    public String toString() {
        return "Entrance-" + id + " value:" + number;
    }

    public static void setCanceled(boolean c) {
        canceled = c;
    }

    public static int getTotalCount() {
        return count.getCount();
    }

    public static int sumEntrances() {
        int num = 0;
        for (Entrance en : entrances) {
            num += en.getValue();
        }
        return num;
    }

}

