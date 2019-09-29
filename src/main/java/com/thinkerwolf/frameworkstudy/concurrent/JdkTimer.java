package com.thinkerwolf.frameworkstudy.concurrent;

import java.util.Timer;
import java.util.TimerTask;

public class JdkTimer {


    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Schedule task start.........");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Schedule task end...............");
            }
        }, 1000);


        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Schedule period task start.........");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Schedule period task end...............");
            }
        }, 6000, 1000);


        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("ScheduleAtFixedRate task start.........");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("ScheduleAtFixedRate task end...............");
            }
        }, 6000, 1000);

    }


}
