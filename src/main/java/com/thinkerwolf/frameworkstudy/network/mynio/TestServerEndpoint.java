package com.thinkerwolf.frameworkstudy.network.mynio;


public class TestServerEndpoint {
    public static void main(String[] args) {
        ServerEndpoint server = new ServerEndpoint(10);
        server.channelInitializer(new ChannelInitializer() {
            @Override
            public void initChannel(ChannelHandlerPipeline pipeline) {
                pipeline.addLast("decoder", new TestEndpoint.Decoder());
                pipeline.addLast("encoder", new TestEndpoint.Encoder());
                pipeline.addLast(new ChannelInboundHandler() {
                    @Override
                    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
                        System.out.println(Thread.currentThread().getName() + ":" + obj);
                        TestEndpoint.Packet packet = new TestEndpoint.Packet();
                        packet.setNum(1);
                        packet.setName("Server to client");
                        ctx.write(packet);
                    }
                });
            }
        });
        server.bind(7070);
    }
}
