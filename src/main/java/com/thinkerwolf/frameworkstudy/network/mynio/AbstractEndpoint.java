package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBufAllocator;

import java.nio.channels.SelectionKey;

public abstract class AbstractEndpoint<E extends AbstractEndpoint<E>> {

    protected ChannelInitializer initializer;

    protected abstract void accept(SelectionKey sk);

    public abstract ByteBufAllocator alloc();

    public E channelInitializer(ChannelInitializer initializer) {
        this.initializer = initializer;
        return (E) this;
    }

}
