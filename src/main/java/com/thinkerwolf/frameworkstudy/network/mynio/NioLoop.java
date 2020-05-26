package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.channels.*;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class NioLoop implements Runnable {
    private Selector selector;

    private Queue<Runnable> tasks;

    private Thread thread;

    private AbstractEndpoint endpoint;

    public NioLoop(String name, AbstractEndpoint endpoint) {
        try {
            this.selector = Selector.open();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.endpoint = endpoint;
        this.tasks = new LinkedBlockingQueue<>();
        this.thread = new Thread(this, name);
    }

    public void start() {
        thread.start();
    }

    public boolean inLoop() {
        return Thread.currentThread() == thread;
    }

    public void addTask(Runnable task) {
        this.tasks.add(task);
        selector.wakeup();
    }

    public Selector getSelector() {
        return selector;
    }

    @Override
    public void run() {
        for (; ; ) {
            for (Runnable task = tasks.poll(); task != null; task = tasks.poll()) {
                task.run();
            }
            try {
                selector.select();
            } catch (IOException ignored) {
            }
            Set<SelectionKey> sks = selector.selectedKeys();
            for (SelectionKey sk : sks) {
                processSelectedKey(sk);
            }
            sks.clear();
        }
    }

    private void processSelectedKey(SelectionKey sk) {
        Channel ch = sk.channel();
        try {
            if (sk.isAcceptable()) {
                handleAccept(sk);
            }
            if (sk.isConnectable()) {
                handleConnect(sk);
            }
            if (sk.isReadable()) {
                handleRead(sk);
            }
            if (sk.isWritable()) {
                handleWrite(sk);
            }
        } catch (Exception e) {
            NioUtils.closeChannel(ch);
            e.printStackTrace();
        }
    }

    private void handleRead(SelectionKey sk) {
        ByteBuf buf = endpoint.alloc().directBuffer(256);
        SocketChannel ch = (SocketChannel) sk.channel();
        ChannelHandlerPipeline pipeline = (ChannelHandlerPipeline) sk.attachment();
        int rd = 0;
        try {
            for (; ; ) {
                rd = buf.writeBytes(ch, buf.writableBytes());
                if (rd <= 0) {
                    break;
                }
                pipeline.fireInbound(buf);
            }
        } catch (IOException e) {
            NioUtils.closeChannel(ch);
        } finally {
            if (buf.refCnt() > 0)
                buf.release();
        }
    }

    public void handleWrite(SelectionKey key) {
        Object obj = key.attachment();

    }


    private void handleAccept(SelectionKey sk) {
        endpoint.accept(sk);
    }

    private void handleConnect(SelectionKey sk) {
        endpoint.accept(sk);
    }
}
