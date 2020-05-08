package com.thinkerwolf.frameworkstudy.alg;

import org.apache.commons.lang.math.RandomUtils;
import org.junit.Test;

import java.util.*;

/**
 * Sharding sphere 测试
 *
 * @author wukai
 * @date 2020/5/8 19:04
 */
public class ShardingSphereTests {

    private static List<Integer> randomGenerate(int size, int min, int max) {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            int num = RandomUtils.nextInt(max - min) + min;
            l.add(num);
        }
        Collections.sort(l);
        return l;
    }

    @Test
    public void testMergeSort() {
        QueryResult qr1 = new QueryResult(randomGenerate(3, 1, 10));
        QueryResult qr2 = new QueryResult(randomGenerate(2, 1, 10));
        QueryResult qr3 = new QueryResult(randomGenerate(4, 1, 10));
        QueryResultGroup qrg = new QueryResultGroup();
        qrg.add(qr1);
        qrg.add(qr2);
        qrg.add(qr3);
        while (qrg.hasNext()) {
            System.out.print(qrg.next());
            System.out.print(" - ");
        }
    }


    private static class QueryResultGroup {
        private PriorityQueue<QueryResult> results = new PriorityQueue<>();

        public void add(QueryResult result) {
            if (result.isOver()) {
                return;
            }
            results.add(result);
        }

        public boolean hasNext() {
            if (results.size() == 0) {
                return false;
            }
            return true;
        }

        public Object next() {
            QueryResult result = results.poll();
            Object obj = result.next();
            add(result);
            return obj;
        }
    }


    private static class QueryResult implements Comparable<QueryResult> {
        List datas;
        int cursor;
        private Comparator comparator;

        public QueryResult(List datas) {
            this(datas, null);
        }

        public QueryResult(List datas, Comparator comparator) {
            this.datas = datas;
            this.comparator = comparator;
        }

        public Object get(int index) {
            return datas.get(index);
        }

        public boolean isOver() {
            return cursor >= datas.size();
        }

        public Object next() {
            return get(cursor++);
        }

        @Override
        public int compareTo(QueryResult o) {
            Object obj1 = this.get(cursor);
            Object obj2 = o.get(o.cursor);
            if (comparator != null) {
                return comparator.compare(obj1, obj2);
            } else if (obj1 instanceof Comparable) {
                return ((Comparable) obj1).compareTo(obj2);
            }
            throw new RuntimeException("无法比较");
        }
    }


}
