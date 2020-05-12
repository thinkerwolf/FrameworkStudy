package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBufAllocator;

import java.nio.channels.Channel;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class DefaultChannelHandlerPipeline implements ChannelHandlerPipeline {

    private static final String DEFAULT_NAME_PREFIX = "Default-process-";
    private Channel channel;
    private ConcurrentMap<String, ChannelHandler> name2Handlers = new ConcurrentHashMap<>();
    private DefaultChannelHandlerContext headCtx;
    private DefaultChannelHandlerContext tailCtx;
    private ByteBufAllocator allocator;


    public DefaultChannelHandlerPipeline(Channel channel, ByteBufAllocator allocator) {
        this.channel = channel;
        this.allocator = allocator;
    }

    private static String defaultName(ChannelHandler handler) {
        return String.format("%s%d", DEFAULT_NAME_PREFIX, handler.hashCode());
    }

    @Override
    public void addLast(ChannelHandler handler) {
        addLast(defaultName(handler), handler);
    }

    @Override
    public void addFirst(ChannelHandler handler) {
        addFirst(defaultName(handler), handler);
    }

    @Override
    public void addLast(String name, ChannelHandler handler) {
        checkDuplicate(name);
        name2Handlers.put(name, handler);
        DefaultChannelHandlerContext ctx = new DefaultChannelHandlerContext(channel, handler, this);
        if (headCtx == null || tailCtx == null) {
            headCtx = tailCtx = ctx;
        } else {
            tailCtx.next = ctx;
            ctx.prev = tailCtx;
            tailCtx = ctx;
        }
    }

    @Override
    public void addFirst(String name, ChannelHandler handler) {
        checkDuplicate(name);
        name2Handlers.put(name, handler);
        DefaultChannelHandlerContext ctx = new DefaultChannelHandlerContext(channel, handler, this);
        if (headCtx == null || tailCtx == null) {
            headCtx = tailCtx = ctx;
        } else {
            headCtx.prev = ctx;
            ctx.next = headCtx;
            headCtx = ctx;
        }
    }

    @Override
    public void fireInbound(Object obj) {
        DefaultChannelHandlerContext ctx = headCtx;
        if (ctx != null) {
            ctx.handleInbound(obj);
        }
    }

    @Override
    public void fireOutbound(Object obj) {
        DefaultChannelHandlerContext ctx = tailCtx;
        if (ctx != null) {
            ctx.handleOutbound(obj);
        }
    }

    @Override
    public ByteBufAllocator allocator() {
        return allocator;
    }

    private void checkDuplicate(String name) {
        if (name2Handlers.containsKey(name)) {
            throw new RuntimeException(name);
        }
    }

}
