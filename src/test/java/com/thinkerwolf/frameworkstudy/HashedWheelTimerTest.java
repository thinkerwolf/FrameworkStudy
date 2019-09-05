package com.thinkerwolf.frameworkstudy;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class HashedWheelTimerTest {

    @Test
    public void timer() {
        HashedWheelTimer timer = new HashedWheelTimer();
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {

            }
        }, 1000, TimeUnit.MILLISECONDS);
    }

}
