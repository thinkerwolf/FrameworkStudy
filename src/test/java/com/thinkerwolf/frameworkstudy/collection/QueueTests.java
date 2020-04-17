package com.thinkerwolf.frameworkstudy.collection;

import java.util.concurrent.*;

public class QueueTests {

    static class MyTask implements Delayed {
        private final String name;
        private final long runningTime;

        public MyTask(String name, long runningTime) {
            this.name = name;
            this.runningTime = runningTime;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(runningTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            long t = getDelay(TimeUnit.MILLISECONDS) - o.getDelay(TimeUnit.MILLISECONDS);
            if (t > 0) {
                return 1;
            }
            if (t < 0) {
                return -1;
            }
            return 0;
        }

        @Override
        public String toString() {
            return new StringBuilder("[Task ")
                    .append(name)
                    .append("-")
                    .append(runningTime)
                    .append(']')
                    .toString();
        }
    }

    public static void delayQueueTest() {
        DelayQueue dq = new DelayQueue();
        long now = System.currentTimeMillis();
        dq.add(new MyTask("t1", now + 500));
        dq.add(new MyTask("t2", now + 200));
        dq.add(new MyTask("t3", now + 300));
        dq.add(new MyTask("t4", now + 2000));
        dq.add(new MyTask("t5", now + 1000));

        for (int i = 0, m = dq.size(); i < m; i++) {
            MyTask task = null;
            try {
                task = (MyTask) dq.take();
            } catch (InterruptedException e) {
                continue;
            }
            System.out.print(task);
            System.out.println(" " + System.currentTimeMillis());
        }
    }

    public static void synchronizedQueueTest() {
        SynchronousQueue sq = new SynchronousQueue();
        final int num = 10;
        Thread t = new Thread(() -> {
            for (int i = 0; i < num; i++) {
                try {
                    System.out.println(sq.take());
                } catch (InterruptedException e) {
                }
            }
        });
        t.start();
        for (int i = 0; i < num; i++) {
            try {
                sq.put(i);
            } catch (InterruptedException e) {
            }
        }

        try {
            t.join();
        } catch (InterruptedException e) {
        }
    }

    public static void transferQueueTest() {
        TransferQueue tq = new LinkedTransferQueue();
        final int num = 6;
        Thread t = new Thread(() -> {
            for (int i = 0; i < num; i++) {
                try {
                    System.out.println(tq.take());
                } catch (InterruptedException e) {
                }
            }
        });
        t.start();

        for (int i = 0; i < 6; i++) {
            try {
                Thread.sleep(800);
                tq.transfer("aaa-" + i);
            } catch (InterruptedException e) {
            }
        }

    }


    public static void main(String[] args) {
        delayQueueTest();
        synchronizedQueueTest();
        transferQueueTest();
    }
}
