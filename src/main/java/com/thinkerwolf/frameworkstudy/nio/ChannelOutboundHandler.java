package com.thinkerwolf.frameworkstudy.nio;

/**
 * Up stream
 *
 * @author wukai
 * @date 2020/5/8 15:10
 */
public interface ChannelOutboundHandler extends ChannelHandler {
    void handleOutbound(ChannelHandlerContext ctx, Object obj);
}
