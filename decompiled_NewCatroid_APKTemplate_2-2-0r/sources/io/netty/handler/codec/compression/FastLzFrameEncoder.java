package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes11.dex */
public class FastLzFrameEncoder extends MessageToByteEncoder<ByteBuf> {
    private final Checksum checksum;
    private final int level;

    public FastLzFrameEncoder() {
        this(0, null);
    }

    public FastLzFrameEncoder(int i) {
        this(i, null);
    }

    public FastLzFrameEncoder(boolean z) {
        this(0, z ? new Adler32() : null);
    }

    public FastLzFrameEncoder(int i, Checksum checksum) {
        super(false);
        if (i != 0 && i != 1 && i != 2) {
            throw new IllegalArgumentException(String.format("level: %d (expected: %d or %d or %d)", Integer.valueOf(i), 0, 1, 2));
        }
        this.level = i;
        this.checksum = checksum;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Code duplicated, block: B:34:0x00de  */
    /* JADX WARN: Code duplicated, block: B:35:0x00e1  */
    @Override // io.netty.handler.codec.MessageToByteEncoder
    public void encode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ByteBuf byteBuf2) throws Exception {
        byte[] bArr;
        int i;
        int i2;
        int i3;
        int i4;
        byte[] bArrArray;
        int iArrayOffset;
        Checksum checksum = this.checksum;
        while (byteBuf.isReadable()) {
            int i5 = byteBuf.readerIndex();
            int iMin = Math.min(byteBuf.readableBytes(), 65535);
            int iWriterIndex = byteBuf2.writerIndex();
            byteBuf2.setMedium(iWriterIndex, 4607066);
            int i6 = iWriterIndex + 4;
            int i7 = i6 + (checksum != null ? 4 : 0);
            if (iMin < 32) {
                byteBuf2.ensureWritable(i7 + 2 + iMin);
                byte[] bArrArray2 = byteBuf2.array();
                int iArrayOffset2 = byteBuf2.arrayOffset() + i7 + 2;
                if (checksum != null) {
                    if (byteBuf.hasArray()) {
                        bArrArray = byteBuf.array();
                        iArrayOffset = byteBuf.arrayOffset() + i5;
                    } else {
                        bArrArray = new byte[iMin];
                        byteBuf.getBytes(i5, bArrArray);
                        iArrayOffset = 0;
                    }
                    checksum.reset();
                    checksum.update(bArrArray, iArrayOffset, iMin);
                    byteBuf2.setInt(i6, (int) checksum.getValue());
                    System.arraycopy(bArrArray, iArrayOffset, bArrArray2, iArrayOffset2, iMin);
                } else {
                    byteBuf.getBytes(i5, bArrArray2, iArrayOffset2, iMin);
                }
                i2 = i7;
            } else {
                if (byteBuf.hasArray()) {
                    byte[] bArrArray3 = byteBuf.array();
                    int iArrayOffset3 = byteBuf.arrayOffset() + i5;
                    bArr = bArrArray3;
                    i = iArrayOffset3;
                } else {
                    byte[] bArr2 = new byte[iMin];
                    byteBuf.getBytes(i5, bArr2);
                    bArr = bArr2;
                    i = 0;
                }
                if (checksum != null) {
                    checksum.reset();
                    checksum.update(bArr, i, iMin);
                    byteBuf2.setInt(i6, (int) checksum.getValue());
                }
                byteBuf2.ensureWritable(i7 + 4 + FastLz.calculateOutputBufferLength(iMin));
                byte[] bArrArray4 = byteBuf2.array();
                int iArrayOffset4 = byteBuf2.arrayOffset() + i7;
                iMin = FastLz.compress(bArr, i, iMin, bArrArray4, iArrayOffset4 + 4, this.level);
                if (iMin < iMin) {
                    byteBuf2.setShort(i7, iMin);
                    i2 = i7 + 2;
                    i3 = 1;
                } else {
                    System.arraycopy(bArr, i, bArrArray4, iArrayOffset4 + 2, iMin);
                    i2 = i7;
                }
                byteBuf2.setShort(i2, iMin);
                int i8 = iWriterIndex + 3;
                if (checksum != null) {
                    i4 = 16;
                } else {
                    i4 = 0;
                }
                byteBuf2.setByte(i8, i3 | i4);
                byteBuf2.writerIndex(i2 + 2 + iMin);
                byteBuf.skipBytes(iMin);
            }
            i3 = 0;
            byteBuf2.setShort(i2, iMin);
            int i9 = iWriterIndex + 3;
            if (checksum != null) {
                i4 = 16;
            } else {
                i4 = 0;
            }
            byteBuf2.setByte(i9, i3 | i4);
            byteBuf2.writerIndex(i2 + 2 + iMin);
            byteBuf.skipBytes(iMin);
        }
    }
}
