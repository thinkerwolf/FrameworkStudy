package com.thinkerwolf.frameworkstudy.network.mynio;

import java.nio.channels.Channel;

/**
 * ChannelHandler 上下文
 *
 * @author wukai
 * @date 2020/5/12 14:36
 */
public interface ChannelHandlerContext {

    Channel channel();

    /**
     * Inbound
     *
     * @param obj
     */
    void handleInbound(Object obj);

    void handleOutbound(Object obj);

    /**
     * 将Inbound传输到下一个ChannelHandlerContext
     *
     * @param obj
     */
    void fireInbound(Object obj);

    /**
     * 将Outbound传输到下一个ChannelHandlerContext
     *
     * @param obj
     */
    void fireOutbound(Object obj);

    ChannelHandlerPipeline pipeline();

    void write(Object obj);

}
