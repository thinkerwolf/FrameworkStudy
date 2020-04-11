package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.concurrent.TimeUnit;

class Sleeper extends Thread {

    private long time;

    Sleeper(int time) {
        super("Sleeper");
        this.time = time;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        try {
            TimeUnit.MILLISECONDS.sleep(time);
        } catch (InterruptedException e) {
            System.out.printf("%s interrupt %b\n", t.getName(), t.isInterrupted());
        }
        System.out.printf("%s sleep complete\n", t.getName());
    }
}

class Joiner extends Thread {

    private Thread sleeper;

    Joiner(String name, Thread sleeper) {
        super(name);
        this.sleeper = sleeper;
    }

    @Override
    public void run() {
        Thread t = Thread.currentThread();
        try {
            sleeper.join();
        } catch (InterruptedException e) {
            System.out.printf("%s interrupt %b\n", t.getName(), t.isInterrupted());
        }
        System.out.printf("%s join complete\n", t.getName());
    }
}


public class Joining {

    public static void main(String[] args) {
        Sleeper sleeper = new Sleeper(2000);
        Joiner joiner = new Joiner("Danny", sleeper);

        sleeper.start();
        joiner.start();

        try {
            TimeUnit.MILLISECONDS.sleep(800);
        } catch (InterruptedException e) {
        }
        joiner.interrupt();
    }


}
