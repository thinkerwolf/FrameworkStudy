package com.thinkerwolf.frameworkstudy.network.mynio;

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

    /**
     * 从头开始处理Inbound信息
     *
     * @param obj
     */
    void fireInbound(Object obj);

    /**
     * 从头开始处理Outbound信息
     *
     * @param obj
     */
    void fireOutbound(Object obj);

    ByteBufAllocator allocator();

}
