package com.thinkerwolf.frameworkstudy.nio;

import io.netty.buffer.ByteBuf;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.WritableByteChannel;

public class DefaultChannelHandlerContext implements ChannelHandlerContext {
    DefaultChannelHandlerContext prev;
    DefaultChannelHandlerContext next;
    ChannelHandler handler;
    private Channel ch;
    private ChannelHandlerPipeline pipeline;


    public DefaultChannelHandlerContext(Channel ch, ChannelHandler handler, ChannelHandlerPipeline pipeline) {
        this.ch = ch;
        this.handler = handler;
        this.pipeline = pipeline;
    }

    @Override
    public Channel channel() {
        return ch;
    }

    @Override
    public void handleInbound(Object obj) {
        if (handler instanceof ChannelInboundHandler) {
            ((ChannelInboundHandler) handler).handleInbound(this, obj);
        } else {
            sendInbound(obj);
        }
    }

    @Override
    public void handleOutbound(Object obj) {
        if (handler instanceof ChannelOutboundHandler) {
            ((ChannelOutboundHandler) handler).handleOutbound(this, obj);
        } else {
            sendOutbound(obj);
        }
    }

    @Override
    public void sendInbound(Object obj) {
        if (next != null) {
            next.handleInbound(obj);
        }
    }

    @Override
    public void sendOutbound(Object obj) {
        if (prev != null) {
            prev.handleOutbound(obj);
        } else {
            if (!(channel() instanceof WritableByteChannel)) {
                return;
            }
            WritableByteChannel wch = (WritableByteChannel) channel();
            if (obj instanceof ByteBuf) {
                ByteBuf buf = (ByteBuf) obj;
                try {
                    wch.write(buf.nioBuffer());
                } catch (IOException e) {
                } finally {
                    buf.release();
                }
            } else if (obj instanceof ByteBuffer) {
                try {
                    wch.write((ByteBuffer) obj);
                } catch (IOException e) {
                }
            }
        }
    }

    @Override
    public ChannelHandlerPipeline pipeline() {
        return pipeline;
    }

    @Override
    public void write(Object obj) {
        pipeline.handleOutbound(obj);
    }


}
