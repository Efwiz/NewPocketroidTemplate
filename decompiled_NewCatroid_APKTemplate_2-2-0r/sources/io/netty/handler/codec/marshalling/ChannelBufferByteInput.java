package io.netty.handler.codec.marshalling;

import io.netty.buffer.ByteBuf;
import java.io.IOException;
import org.jboss.marshalling.ByteInput;

/* JADX INFO: loaded from: classes11.dex */
class ChannelBufferByteInput implements ByteInput {
    private final ByteBuf buffer;

    public void close() throws IOException {
    }

    ChannelBufferByteInput(ByteBuf byteBuf) {
        this.buffer = byteBuf;
    }

    public int available() throws IOException {
        return this.buffer.readableBytes();
    }

    public int read() throws IOException {
        if (this.buffer.isReadable()) {
            return this.buffer.readByte() & 255;
        }
        return -1;
    }

    public int read(byte[] bArr) throws IOException {
        return read(bArr, 0, bArr.length);
    }

    public int read(byte[] bArr, int i, int i2) throws IOException {
        int iAvailable = available();
        if (iAvailable == 0) {
            return -1;
        }
        int iMin = Math.min(iAvailable, i2);
        this.buffer.readBytes(bArr, i, iMin);
        return iMin;
    }

    public long skip(long j) throws IOException {
        long j2 = this.buffer.readableBytes();
        if (j2 < j) {
            j = j2;
        }
        ByteBuf byteBuf = this.buffer;
        byteBuf.readerIndex((int) (((long) byteBuf.readerIndex()) + j));
        return j;
    }
}
