package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBuf;

import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import java.nio.ByteBuffer;

/**
 * SSL处理器 pipeline.addFirst();
 *
 * @author wukai
 * @date 2020/5/12 14:40
 */
public class SSLHandler implements ChannelInboundHandler, ChannelOutboundHandler {

    public static final int SSL_HEADER_LENGTH = 5;

    private SSLEngine engine;
    private SSLSession session;
    private ByteBuffer peerAppBuffer;

    private int packetLength;

    public SSLHandler(SSLEngine engine) {
        this.engine = engine;
        this.session = engine.getSession();
        this.peerAppBuffer = ByteBuffer.allocate(session.getApplicationBufferSize());
    }

    @Override
    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
        if (!(obj instanceof ByteBuf)) {
            throw new IllegalArgumentException("Unsupported ssl data type");
        }
        ByteBuf buf = (ByteBuf) obj;
        decodeJdk(ctx, buf);

//        int size = engine.getSession().getPacketBufferSize();
//        if (buf.readableBytes() < size) {
//            return;
//        }
//        buf.readerIndex(buf.readerIndex() + size);
//        ByteBuffer nioBuffer = buf.nioBuffer(buf.readerIndex(), size);
//        peerAppBuffer.flip();
//        SSLEngineResult result = engine.unwrap(nioBuffer, peerAppBuffer);
//
//        switch (result.getHandshakeStatus()) {
//            case NEED_WRAP:
//
//            case NEED_UNWRAP:
//
//
//
//        }
//
//        switch (result.getStatus()) {
//            case BUFFER_UNDERFLOW:
//
//                break;
//            case BUFFER_OVERFLOW:
//
//                break;
//            case OK:
//
//                break;
//        }
//        ctx.fireInbound(obj);
    }

    private void decodeJdk(ChannelHandlerContext ctx, ByteBuf buf) {
        int packetLength = this.packetLength;
        if (packetLength > 0) {
            if (buf.readableBytes() < packetLength) {
                return;
            }
        } else {
            // packetLength == 0
            final int readableBytes = buf.readableBytes();
            if (readableBytes < SSL_HEADER_LENGTH) {
                return;
            }


        }


    }


    @Override
    public void handleOutbound(ChannelHandlerContext ctx, Object obj) {


    }
}
