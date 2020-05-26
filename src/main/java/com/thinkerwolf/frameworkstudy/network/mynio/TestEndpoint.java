package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;
import java.util.List;

public class TestEndpoint {


    public static void main(String[] args) throws InterruptedException {
        Endpoint endpoint = new Endpoint();

        endpoint.channelInitializer(new ChannelInitializer() {
            @Override
            public void initChannel(ChannelHandlerPipeline pipeline) {
                pipeline.addLast("encoder", new Encoder());
                pipeline.addLast("decoder", new Decoder());
                pipeline.addLast("handleer", new ChannelInboundHandler() {
                    @Override
                    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
                        System.out.println(obj);
                    }
                });
            }
        });

        endpoint.connect("127.0.0.1", 7070);

        Thread.sleep(5000);

        endpoint.write(new Packet(1, "wukai"));
    }


    public static class Encoder extends MessageToByteEncoder {
        @Override
        protected void encode(ChannelHandlerContext ctx, ByteBuf out, Object obj) {
            Packet packet = (Packet) obj;
            byte[] bs = packet.getName().getBytes(Charset.defaultCharset());
            out.writeInt(4 + bs.length);
            out.writeInt(packet.getNum());
            out.writeBytes(bs);
        }
    }

    public static class Decoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) {
            if (in.readableBytes() < 4) {
                return;
            }
            int len = in.getInt(in.readerIndex());
            if (in.readableBytes() < len + 4) {
                return;
            }
            in.readInt();
            Packet packet = new Packet();
            packet.setNum(in.readInt());
            packet.setName(in.readCharSequence(len - 4, Charset.defaultCharset()).toString());
            out.add(packet);
        }
    }


    public static class Packet {
        int num;
        String name = "d";

        public Packet(int num, String name) {
            this.num = num;
            this.name = name;
        }

        public Packet() {
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

}
