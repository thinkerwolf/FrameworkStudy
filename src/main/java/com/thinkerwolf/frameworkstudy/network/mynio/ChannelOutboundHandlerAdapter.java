package com.thinkerwolf.frameworkstudy.network.mynio;

public class ChannelOutboundHandlerAdapter implements ChannelOutboundHandler {
    @Override
    public void handleOutbound(ChannelHandlerContext ctx, Object obj) {
        ctx.fireOutbound(obj);
    }
}
