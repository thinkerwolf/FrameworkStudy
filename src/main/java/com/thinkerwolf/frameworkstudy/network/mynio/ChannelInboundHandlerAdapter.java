package com.thinkerwolf.frameworkstudy.network.mynio;

public class ChannelInboundHandlerAdapter implements ChannelInboundHandler {
    @Override
    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
        ctx.fireInbound(obj);
    }
}
