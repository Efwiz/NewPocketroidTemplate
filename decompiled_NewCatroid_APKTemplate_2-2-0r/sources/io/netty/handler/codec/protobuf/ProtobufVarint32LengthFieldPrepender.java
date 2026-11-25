package io.netty.handler.codec.protobuf;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.luaj.vm2.Lua;

/* JADX INFO: loaded from: classes11.dex */
@ChannelHandler.Sharable
public class ProtobufVarint32LengthFieldPrepender extends MessageToByteEncoder<ByteBuf> {
    static int computeRawVarint32Size(int i) {
        if ((i & (-128)) == 0) {
            return 1;
        }
        if ((i & Lua.MASK_Bx) == 0) {
            return 2;
        }
        if (((-2097152) & i) == 0) {
            return 3;
        }
        return (i & (-268435456)) == 0 ? 4 : 5;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.handler.codec.MessageToByteEncoder
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        int i = byteBuf.readableBytes();
        byteBuf2.ensureWritable(computeRawVarint32Size(i) + i);
        writeRawVarint32(byteBuf2, i);
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), i);
    }

    static void writeRawVarint32(ByteBuf byteBuf, int i) {
        while ((i & (-128)) != 0) {
            byteBuf.writeByte((i & 127) | 128);
            i >>>= 7;
        }
        byteBuf.writeByte(i);
    }
}
