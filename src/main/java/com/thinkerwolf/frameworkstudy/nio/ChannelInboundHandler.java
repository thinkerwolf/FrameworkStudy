package com.thinkerwolf.frameworkstudy.nio;

/**
 * Down stream
 *
 * @author wukai
 * @date 2020/5/8 15:10
 */
public interface ChannelInboundHandler extends ChannelHandler {

    void handleInbound(ChannelHandlerContext ctx, Object obj);

}
