package com.thinkerwolf.frameworkstudy.nio;

import java.nio.channels.Channel;

public interface ChannelHandlerContext {

    Channel channel();

    void handleInbound(Object obj);

    void handleOutbound(Object obj);

    void sendInbound(Object obj);

    void sendOutbound(Object obj);

    ChannelHandlerPipeline pipeline();

    void write(Object obj);

}
