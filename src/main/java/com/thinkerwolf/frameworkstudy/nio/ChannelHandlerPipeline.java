package com.thinkerwolf.frameworkstudy.nio;

import io.netty.buffer.ByteBufAllocator;

/**
 * @author wukai
 * @date 2020/5/8 13:16
 */
public interface ChannelHandlerPipeline {

    void addLast(ChannelHandler handler);

    void addFirst(ChannelHandler handler);


    void addLast(String name, ChannelHandler handler);

    void addFirst(String name, ChannelHandler handler);

    void handleInbound(Object obj);

    void handleOutbound(Object obj);

    ByteBufAllocator allocator();

}
