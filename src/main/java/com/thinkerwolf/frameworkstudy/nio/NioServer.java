package com.thinkerwolf.frameworkstudy.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class NioServer {

    private static Random random = new Random();
    private NioLoop acceptLoop;
    private NioLoop[] nioLoops;
    private ServerSocketChannel serverCh;
    private ByteBufAllocator allocator;

    private ChannelInitializer initializer;

    public NioServer(int workerNum) {
        try {
            this.nioLoops = new NioLoop[workerNum];
            for (int i = 0; i < workerNum; i++) {
                nioLoops[i] = new NioLoop("Worker-" + i, Selector.open());
            }
            this.acceptLoop = new NioLoop("Boss", Selector.open());
            this.serverCh = ServerSocketChannel.open();
            this.serverCh.configureBlocking(false);
            this.allocator = new PooledByteBufAllocator(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public NioServer channelInitializer(ChannelInitializer initializer) {
        this.initializer = initializer;
        return this;
    }

    public void bind(int port) {
        try {
            this.serverCh.bind(new InetSocketAddress(port));
            this.serverCh.register(acceptLoop.selector, SelectionKey.OP_ACCEPT);
            for (NioLoop nioLoop : nioLoops) {
                nioLoop.start();
            }
            acceptLoop.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void processSelectedKey(SelectionKey sk) {
        Channel ch = sk.channel();
        try {
            if (sk.isAcceptable()) {
                handleAccept(sk);
            }
            if (sk.isReadable()) {
                handleRead(sk);
            }
        } catch (Exception e) {
            e.printStackTrace();
            NioUtils.closeChannel(ch);
        }
    }

    private void handleAccept(SelectionKey sk) {
        final NioLoop loop = next();
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) sk.channel();
            final SocketChannel ch = serverSocketChannel.accept();
            if (ch != null) {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ch.configureBlocking(false);
                            DefaultChannelHandlerPipeline pipeline = new DefaultChannelHandlerPipeline(ch, allocator);
                            if (initializer != null) {
                                initializer.initChannel(pipeline);
                            }
                            ch.register(loop.selector, SelectionKey.OP_READ, pipeline);
                            System.out.println("Client register success " + ch.toString());
                        } catch (IOException e) {
                        }
                    }
                };
                if (loop.inLoop()) {
                    run.run();
                } else {
                    loop.addTask(run);
                }
            }
        } catch (IOException e) {
        }
    }

    private NioLoop next() {
        return nioLoops[random.nextInt(nioLoops.length)];
    }

    private void handleRead(SelectionKey sk) {
        ByteBuf buf = allocator.directBuffer(256);
        SocketChannel ch = (SocketChannel) sk.channel();
        ChannelHandlerPipeline pipeline = (ChannelHandlerPipeline) sk.attachment();
        int rd = 0;
        try {
            for (; ; ) {
                rd = buf.writeBytes(ch, buf.writableBytes());
                if (rd <= 0) {
                    break;
                }
                pipeline.handleInbound(buf);
            }
        } catch (IOException e) {
            NioUtils.closeChannel(ch);
        } finally {
            buf.release();
        }
    }

    private class NioLoop implements Runnable {
        private Selector selector;

        private Queue<Runnable> tasks;

        private Thread thread;

        public NioLoop(String name, Selector selector) {
            this.selector = selector;
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
    }
}
