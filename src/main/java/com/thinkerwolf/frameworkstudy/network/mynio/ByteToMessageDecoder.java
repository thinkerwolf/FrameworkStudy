package com.thinkerwolf.frameworkstudy.network.mynio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;

import java.util.ArrayList;
import java.util.List;

/**
 * 解决TCP粘包和拆包问题
 *
 * @author wukai
 */
public abstract class ByteToMessageDecoder implements ChannelInboundHandler {

    public static final Cumulator MERGE_CUMULATOR = (alloc, cumulation, in) -> {
        final ByteBuf buffer;
        if (cumulation.writerIndex() > cumulation.maxCapacity() - in.readableBytes()
                || cumulation.refCnt() > 1 || cumulation.isReadOnly()) {
            buffer = expandCumulation(alloc, cumulation, in.readableBytes());
        } else {
            buffer = cumulation;
        }
        buffer.writeBytes(in);
        in.release();
        return buffer;
    };

    private List<Object> out = new ArrayList<>();

    private Cumulator cumulator = MERGE_CUMULATOR;

    private ByteBuf cumulation;

    static ByteBuf expandCumulation(ByteBufAllocator alloc, ByteBuf cumulation, int readable) {
        ByteBuf oldCumulation = cumulation;
        cumulation = alloc.buffer(oldCumulation.readableBytes() + readable);
        cumulation.writeBytes(oldCumulation);
        oldCumulation.release();
        return cumulation;
    }

    @Override
    public void handleInbound(ChannelHandlerContext ctx, Object obj) {
        if (obj instanceof ByteBuf) {
            ByteBuf data = (ByteBuf) obj;
            boolean first = cumulation == null;
            if (first) {
                cumulation = data;
            } else {
                cumulation = cumulator.cumulate(ctx.pipeline().allocator(), cumulation, data);
            }
            callDecode(ctx);
        } else {
            ctx.fireInbound(obj);
        }
    }

    private void callDecode(ChannelHandlerContext ctx) {
        ByteBuf buf = cumulation;
        for (; ; ) {
            int outSize = out.size();
            if (outSize > 0) {
                for (int i = 0; i < outSize; i++) {
                    ctx.fireInbound(out.get(i));
                }
                out.clear();
                outSize = 0;
            }
            if (!buf.isReadable()) {
                break;
            }
            decode(ctx, buf, out);
            outSize = out.size();
            if (outSize == 0) {
                break;
            }
        }
//        if (!buf.isReadable()) {
//            buf.release();
//        }
    }


    protected abstract void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out);

    private void release(List<Object> out) {
        out.clear();
    }

    public interface Cumulator {

        ByteBuf cumulate(ByteBufAllocator alloc, ByteBuf cumulation, ByteBuf in);
    }

}
