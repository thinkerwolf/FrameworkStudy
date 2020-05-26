package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.PooledByteBufAllocator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;

public class Endpoint extends AbstractEndpoint {

    private ByteBufAllocator allocator;

    private NioLoop loop;

    private SocketChannel channel;

    private ChannelHandlerPipeline pipeline;

    public Endpoint() {
        try {
            this.channel = SocketChannel.open();
            this.channel.configureBlocking(false);
            this.allocator = new PooledByteBufAllocator(true);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void connect(String host, int port) {
        try {
            this.pipeline = new DefaultChannelHandlerPipeline(channel, allocator);
            if (initializer != null) {
                initializer.initChannel(pipeline);
            }
            this.loop = new NioLoop("Boss", this);
            loop.start();
            this.channel.register(loop.getSelector(), SelectionKey.OP_CONNECT);
            this.channel.connect(new InetSocketAddress(host, port));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void write(Object msg) {
        Runnable run = () -> pipeline.fireOutbound(msg);
        if (loop.inLoop()) {
            run.run();
        } else {
            loop.addTask(run);
        }
    }

    @Override
    protected void accept(SelectionKey sk) {
        try {
            channel.finishConnect();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sk.attach(pipeline);
    }

    @Override
    public ByteBufAllocator alloc() {
        return allocator;
    }
}
