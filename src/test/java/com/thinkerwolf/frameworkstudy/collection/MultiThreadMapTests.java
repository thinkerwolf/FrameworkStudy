package com.thinkerwolf.frameworkstudy.collection;

import org.apache.commons.lang.time.StopWatch;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 多线程下jdk不同map的执行效率
 *
 * @author wukai
 */
public class MultiThreadMapTests {

    private static Object[] MAP_KEYS;
    private static Object[] MAP_VALUES;

    static {
        MAP_KEYS = new Object[Constants.MAP_TOTAL_DATA_NUM];
        MAP_VALUES = new Object[Constants.MAP_TOTAL_DATA_NUM];
        for (int i = 0; i < MAP_KEYS.length; i++) {
            MAP_KEYS[i] = UUID.randomUUID();
        }
        for (int i = 0; i < MAP_VALUES.length; i++) {
            MAP_VALUES[i] = UUID.randomUUID();
        }
    }

    private static void testMap(Map map) {
        Thread[] putThreads = new Thread[Constants.MAP_THREAD_NUM];
        Thread[] getThreads = new Thread[Constants.MAP_THREAD_NUM];
        for (int i = 0, max = putThreads.length; i < max; i++) {
            int si = i * Constants.MAP_SINGLE_DATA_NUM;
            int ei = si + Constants.MAP_SINGLE_DATA_NUM;
            if (ei > Constants.MAP_TOTAL_DATA_NUM) ei = Constants.MAP_TOTAL_DATA_NUM;
            putThreads[i] = new Thread(new PutTask(si, ei, map));
        }
        for (int i = 0, max = getThreads.length; i < max; i++) {
            int si = i * Constants.MAP_SINGLE_DATA_NUM;
            int ei = si + Constants.MAP_SINGLE_DATA_NUM;
            if (ei > Constants.MAP_TOTAL_DATA_NUM) ei = Constants.MAP_TOTAL_DATA_NUM;
            getThreads[i] = new Thread(new GetTask(si, ei, map));
        }
        StopWatch sw = new StopWatch();
        sw.start();
        for (Thread t : putThreads) {
            t.start();
        }
        for (Thread t : putThreads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        }
        final long putTime = sw.getTime();

        sw.reset();
        sw.start();
        for (Thread t : getThreads) {
            t.start();
        }
        for (Thread t : getThreads) {
            try {
                t.join();
            } catch (InterruptedException ignored) {
            }
        }

        System.out.printf(map.getClass().getSimpleName() + "-> Num:%d, Put:%d, Get:%d\n", map.size(), putTime, sw.getTime());
    }

    public static void main(String[] args) {
        testMap(new Hashtable());
        testMap(Collections.synchronizedMap(new HashMap()));
        testMap(new ConcurrentHashMap());
        testMap(new ConcurrentSkipListMap());
    }

    static class PutTask implements Runnable {
        private int startIndex;
        private int endIndex;
        private Map map;

        PutTask(int startIndex, int endIndex, Map map) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.map = map;
        }

        @Override
        public void run() {
            for (int i = startIndex; i < endIndex; i++) {
                map.put(MAP_KEYS[i], MAP_VALUES[i]);
            }
        }
    }

    static class GetTask implements Runnable {
        private int startIndex;
        private int endIndex;
        private Map map;

        GetTask(int startIndex, int endIndex, Map map) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.map = map;
        }

        @Override
        public void run() {
            for (int i = startIndex; i < endIndex; i++) {
                map.get(MAP_KEYS[i]);
            }
        }
    }

}
