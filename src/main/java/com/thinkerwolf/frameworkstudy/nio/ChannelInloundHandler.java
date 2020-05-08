package com.thinkerwolf.frameworkstudy.nio;

import io.netty.buffer.ByteBuf;

import java.nio.channels.ByteChannel;


public interface ChannelInloundHandler {

    void handleInbound(ByteChannel channel, ByteBuf buf);

}
