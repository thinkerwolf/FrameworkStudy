package com.thinkerwolf.frameworkstudy.redis;

import java.util.concurrent.BlockingQueue;

public interface RBlockingQueue<E> extends RQueue<E>, BlockingQueue<E> {
}
