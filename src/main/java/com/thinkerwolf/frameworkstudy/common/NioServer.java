package com.thinkerwolf.frameworkstudy.common;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Queue;
import java.util.Random;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;

public class NioServer {

    private static Random random = new Random();

    private NioLoop[] nioLoops;
    private ServerSocketChannel serverCh;
    private AcceptTask acceptTask;


    public NioServer(int workerNum) throws IOException {
        this.nioLoops = new NioLoop[workerNum];
        for (int i = 0; i < workerNum; i++) {
            nioLoops[i] = new NioLoop(Selector.open());
        }
        this.acceptTask = new AcceptTask(Selector.open());
        this.serverCh = ServerSocketChannel.open();
        this.serverCh.configureBlocking(false);
    }

    public void bind(int port) throws IOException {
        this.serverCh.bind(new InetSocketAddress(port));
        this.serverCh.register(acceptTask.selector, SelectionKey.OP_ACCEPT);
        Thread bossThread = new Thread(this.acceptTask, "boss");
        int pos = 0;
        for (NioLoop nioLoop : nioLoops) {
            Thread t = new Thread(nioLoop, "worker-" + pos);
            pos++;
            t.start();
        }
        bossThread.start();
    }

    private void register(SelectionKey sk) {
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

    private class AcceptTask implements Runnable {

        private Selector selector;

        public AcceptTask(Selector selector) {
            this.selector = selector;
        }

        @Override
        public void run() {
            for (; ; ) {
                try {
                    selector.select(200);
                } catch (IOException ignored) {
                }
                Set<SelectionKey> sks = selector.selectedKeys();
                for (SelectionKey sk : sks) {
                    if (sk.isAcceptable()) {
                        register(sk);
                    }
                }
                sks.clear();
            }
        }
    }

    private class NioLoop implements Runnable {
        private Selector selector;

        private ByteBuffer buffer;

        private Queue<Runnable> tasks;

        public NioLoop(Selector selector) {
            this.selector = selector;
            this.buffer = ByteBuffer.allocateDirect(2048);
            this.tasks = new LinkedBlockingQueue<>();
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
                    selector.select(200);
                } catch (IOException ignored) {
                }
                Set<SelectionKey> sks = selector.selectedKeys();
                for (SelectionKey sk : sks) {
                    if (sk.isReadable()) {
                        handleRead(sk, buffer);
                    } else if (sk.isWritable()) {

                    }
                }
                sks.clear();
            }
        }
    }


    private void handleRead(SelectionKey sk, ByteBuffer buf) {
        //buf.flip();
        buf = ByteBuffer.allocate(1024);
        SocketChannel ch = (SocketChannel) sk.channel();
        int rd = 0;
        try {
            rd = ch.read(buf);
            buf.flip();
            if (rd > 0) {
                String s = new String(buf.array(), 0, rd);
                System.out.println(s);
            }
        } catch (IOException e) {
            try {
                ch.close();
                sk.cancel();
            } catch (IOException e1) {
            }

        }

    }

    public static void main(String[] args) throws IOException {
        NioServer server = new NioServer(10);
        server.bind(8088);
    }
}
