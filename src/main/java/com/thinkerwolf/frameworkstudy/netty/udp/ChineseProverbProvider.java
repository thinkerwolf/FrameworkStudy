package com.thinkerwolf.frameworkstudy.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

public class ChineseProverbProvider {

    public void run(int port) {
        EventLoopGroup bg = new NioEventLoopGroup(2);
        try {
            Bootstrap b = new Bootstrap();
            b.group(bg).channel(NioDatagramChannel.class);
            b.option(ChannelOption.SO_BROADCAST, true);
            b.handler(new ChineseProverbProviderHandler());
            b.bind(port).sync().channel().closeFuture().await();
        } catch (Exception e) {

        } finally {
            bg.shutdownGracefully();
        }


    }

    class ChineseProverbProviderHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        final String[] DICTIONARY = new String[]{
                "只要功夫深，铁杵磨成臻", "一寸光阴一寸金"
        };
        volatile int index;

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String r = msg.content().toString(CharsetUtil.UTF_8);
            System.err.println(r);
            ctx.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(DICTIONARY[index++ % DICTIONARY.length].getBytes()), msg.sender()));
        }
    }


    public static void main(String[] args) {
        ChineseProverbProvider s = new ChineseProverbProvider();
        try {
            s.run(8080);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
