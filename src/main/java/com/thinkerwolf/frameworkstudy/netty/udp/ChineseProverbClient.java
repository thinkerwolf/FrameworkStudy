package com.thinkerwolf.frameworkstudy.netty.udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramChannel;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;

public class ChineseProverbClient {

    public Channel run(int port) {
        EventLoopGroup bg = new NioEventLoopGroup(2);
        try {
            Bootstrap b = new Bootstrap();
            b.group(bg).channel(NioDatagramChannel.class);
            b.option(ChannelOption.SO_BROADCAST, true);
            b.handler(new ClientHandler());
            Channel ch = b.bind(0).sync().channel();
            return ch;

        } catch (Exception e) {

        } finally {
            bg.shutdownGracefully();
        }
        return null;
    }


    class ClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket msg) throws Exception {
            String r = msg.content().toString(CharsetUtil.UTF_8);
            System.err.println(r);
        }
    }

    public static void main(String[] args) {
        ChineseProverbClient client = new ChineseProverbClient();
        int port = 8080;
        Channel ch = client.run(8080);
        ch.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer("谚语查询".getBytes()), new InetSocketAddress("255.255.255.255", port)));
        try {
            ch.closeFuture().await(15000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
