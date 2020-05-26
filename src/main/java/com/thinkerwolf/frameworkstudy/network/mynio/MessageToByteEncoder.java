package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBuf;

public abstract class MessageToByteEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void handleOutbound(ChannelHandlerContext ctx, Object obj) {
        if (!(obj instanceof ByteBuf)) {
            ByteBuf buf = ctx.pipeline().allocator().buffer();
            encode(ctx, buf, obj);
            ctx.fireOutbound(buf);
        } else {
            ctx.fireOutbound(obj);
        }
    }


    protected abstract void encode(ChannelHandlerContext ctx, ByteBuf out, Object obj);

}
