package com.thinkerwolf.frameworkstudy.network.mynio;

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
            fireInbound(obj);
        }
    }

    @Override
    public void handleOutbound(Object obj) {
        if (handler instanceof ChannelOutboundHandler) {
            ((ChannelOutboundHandler) handler).handleOutbound(this, obj);
        } else {
            fireOutbound(obj);
        }
    }

    @Override
    public void fireInbound(Object obj) {
        if (next != null) {
            next.handleInbound(obj);
        }
    }

    @Override
    public void fireOutbound(Object obj) {
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
        pipeline.fireOutbound(obj);
    }


}
