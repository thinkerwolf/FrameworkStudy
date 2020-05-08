package com.thinkerwolf.frameworkstudy.nio;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

public class NioServerDemo {
    public static void main(String[] args) {
        NioServer server = new NioServer(10);
        server.channelInitializer(new ChannelInitializer() {
            @Override
            public void initChannel(ChannelHandlerPipeline pipeline) {
                pipeline.addFirst(new ChannelInboundHandler() {
                    @Override
                    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
                        ByteBuf buf = (ByteBuf) obj;
                        int len = buf.readableBytes();
                        ctx.sendInbound(buf.getCharSequence(0, len, Charset.defaultCharset()));
                    }
                });

                pipeline.addLast(new ChannelInboundHandler() {
                    @Override
                    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
                        System.out.println(Thread.currentThread().getName() + ":" + obj);
                        ctx.write(obj.toString());
                    }
                });

                pipeline.addLast(new ChannelOutboundHandler() {
                    @Override
                    public void handleOutbound(ChannelHandlerContext ctx, Object obj) {
                        ByteBuf buf = ctx.pipeline().allocator().buffer();
                        buf.writeCharSequence((String) obj, Charset.defaultCharset());
                        ctx.sendOutbound(buf);
                    }
                });

            }
        });
        server.bind(8088);
    }
}
