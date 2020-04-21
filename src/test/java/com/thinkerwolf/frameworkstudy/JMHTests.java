package com.thinkerwolf.frameworkstudy;

import com.thinkerwolf.frameworkstudy.alogrithm.table.SkipListST;
import org.junit.Test;
import org.openjdk.jmh.annotations.*;

public class JMHTests {

    @Warmup(iterations = 1, time = 3)
    @Benchmark
    @Fork(5)
    @BenchmarkMode(Mode.AverageTime)
    @Measurement(iterations = 2, time = 3)
    public void skipListTest() {
        SkipListST st = new SkipListST();
        for (int i = 0; i < 10000; i++) {
            st.put(i, "v" + i);
        }
    }
}
