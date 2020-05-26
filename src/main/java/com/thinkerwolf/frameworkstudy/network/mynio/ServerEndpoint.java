package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Random;

public class ServerEndpoint extends AbstractEndpoint<ServerEndpoint> {

    private static Random random = new Random();
    private NioLoop acceptLoop;
    private NioLoop[] nioLoops;
    private ServerSocketChannel serverCh;
    private ByteBufAllocator allocator;

    public ServerEndpoint(int workerNum) {
        try {
            this.nioLoops = new NioLoop[workerNum];
            for (int i = 0; i < workerNum; i++) {
                nioLoops[i] = new NioLoop("Worker-" + i, this);
            }
            this.acceptLoop = new NioLoop("Boss", this);
            this.serverCh = ServerSocketChannel.open();
            this.serverCh.configureBlocking(false);
            this.allocator = new PooledByteBufAllocator(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void bind(int port) {
        try {
            this.serverCh.bind(new InetSocketAddress(port));
            this.serverCh.register(acceptLoop.getSelector(), SelectionKey.OP_ACCEPT);
            for (NioLoop nioLoop : nioLoops) {
                nioLoop.start();
            }
            acceptLoop.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private NioLoop next() {
        return nioLoops[random.nextInt(nioLoops.length)];
    }

    @Override
    protected void accept(SelectionKey sk) {
        final NioLoop loop = next();
        try {
            ServerSocketChannel serverSocketChannel = this.serverCh;
            final SocketChannel ch = serverSocketChannel.accept();
            if (ch != null) {
                Runnable run = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            ch.configureBlocking(false);
                            DefaultChannelHandlerPipeline pipeline = new DefaultChannelHandlerPipeline(ch, allocator);
                            if (ServerEndpoint.this.initializer != null) {
                                ServerEndpoint.this.initializer.initChannel(pipeline);
                            }
                            ch.register(loop.getSelector(), SelectionKey.OP_READ, pipeline);
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

    @Override
    public ByteBufAllocator alloc() {
        return allocator;
    }
}
