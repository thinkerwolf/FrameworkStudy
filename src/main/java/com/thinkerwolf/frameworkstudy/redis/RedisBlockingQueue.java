package com.thinkerwolf.frameworkstudy.redis;

import redis.clients.jedis.Jedis;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

/**
 * Redis队列，底层的数据存储在redis中，但是表现与JdkQueue相同
 *
 * @param <E>
 */
public class RedisBlockingQueue<E> extends AbstractQueue<E> implements RBlockingQueue<E> {


    private Jedis conn;

    private byte[] key;


    // 需要使用分布式锁 Redisson


    public RedisBlockingQueue(Jedis conn, String name) {
        this.conn = conn;
        this.key = name.getBytes();
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {

        return false;
    }

    @Override
    public void put(E e) throws InterruptedException {

    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public E take() throws InterruptedException {
        // 阻塞

        return null;
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;
    }

    @Override
    public int remainingCapacity() {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
