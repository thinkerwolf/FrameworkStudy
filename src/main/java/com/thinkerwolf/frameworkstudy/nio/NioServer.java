package com.thinkerwolf.frameworkstudy.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
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


    public NioServer(int workerNum) throws IOException {
        this.nioLoops = new NioLoop[workerNum];
        for (int i = 0; i < workerNum; i++) {
            nioLoops[i] = new NioLoop("Worker-" + i, Selector.open());
        }
        this.acceptLoop = new NioLoop("Boss", Selector.open());
        this.serverCh = ServerSocketChannel.open();
        this.serverCh.configureBlocking(false);
        this.allocator = new PooledByteBufAllocator(true);
    }

    public void bind(int port) throws IOException {
        this.serverCh.bind(new InetSocketAddress(port));
        this.serverCh.register(acceptLoop.selector, SelectionKey.OP_ACCEPT);
        for (NioLoop nioLoop : nioLoops) {
            nioLoop.start();
        }
        acceptLoop.start();
    }

    private void handleAccept(SelectionKey sk) {
        final NioLoop loop = next();
        try {
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) sk.channel();
            final SocketChannel ch = serverSocketChannel.accept();
            if (ch != null) {
                loop.addTask(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ch.configureBlocking(false);
                            ch.register(loop.selector, SelectionKey.OP_READ);
                            System.out.println("Client register success " + ch.toString());
                        } catch (IOException e) {
                        }
                    }
                });
            }
        } catch (IOException e) {
        }


    }

    private NioLoop next() {
        return nioLoops[random.nextInt(nioLoops.length)];
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
                    if (sk.isAcceptable()) {
                        handleAccept(sk);
                    } else if (sk.isReadable()) {
                        handleRead(sk);
                    } else if (sk.isWritable()) {

                    }
                }
                sks.clear();
            }
        }
    }


    private void handleRead(SelectionKey sk) {
        //buf.flip();
        ByteBuf buf = allocator.directBuffer(256);
        SocketChannel ch = (SocketChannel) sk.channel();
        int rd = 0;
        try {
            for (; ; ) {
                rd = buf.writeBytes(ch, buf.writableBytes());
                if (rd <= 0) {
                    break;
                }
                CharSequence s = buf.getCharSequence(0, rd, Charset.defaultCharset());
                System.out.println(s);
            }
        } catch (IOException e) {
            try {
                ch.close();
            } catch (IOException e1) {
            }
        } finally {
            buf.release();
        }
    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer(10);
        server.bind(8088);
    }
}
