package io.netty.handler.codec.xml;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.TooLongFrameException;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class XmlFrameDecoder extends ByteToMessageDecoder {
    private final int maxFrameLength;

    private static boolean isValidStartCharForXmlElement(byte b) {
        return (b >= 97 && b <= 122) || (b >= 65 && b <= 90) || b == 58 || b == 95;
    }

    public XmlFrameDecoder(int i) {
        if (i < 1) {
            throw new IllegalArgumentException("maxFrameLength must be a positive int");
        }
        this.maxFrameLength = i;
    }

    /* JADX WARN: Code duplicated, block: B:43:0x0090  */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i;
        int i2;
        int iWriterIndex = byteBuf.writerIndex();
        if (iWriterIndex > this.maxFrameLength) {
            byteBuf.skipBytes(byteBuf.readableBytes());
            fail(iWriterIndex);
            return;
        }
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        long j = 0;
        int i4 = 0;
        boolean z3 = false;
        for (int i5 = byteBuf.readerIndex(); i5 < iWriterIndex; i5++) {
            byte b = byteBuf.getByte(i5);
            if (!z && Character.isWhitespace(b)) {
                i3++;
            } else {
                if (!z && b != 60) {
                    fail(channelHandlerContext);
                    byteBuf.skipBytes(byteBuf.readableBytes());
                    return;
                }
                if (!z2 && b == 60) {
                    int i6 = iWriterIndex - 1;
                    if (i5 < i6) {
                        byte b2 = byteBuf.getByte(i5 + 1);
                        if (b2 == 47) {
                            for (int i7 = i5 + 2; i7 <= i6; i7++) {
                                if (byteBuf.getByte(i7) == 62) {
                                    j--;
                                    break;
                                }
                            }
                        } else if (isValidStartCharForXmlElement(b2)) {
                            j++;
                            z3 = true;
                        } else if (b2 == 33) {
                            if (isCommentBlockStart(byteBuf, i5)) {
                                j++;
                            } else if (isCDATABlockStart(byteBuf, i5)) {
                                j++;
                                z2 = true;
                            }
                        } else if (b2 == 63) {
                            j++;
                        }
                    }
                    z = true;
                } else if (z2 || b != 47) {
                    if (b == 62) {
                        i4 = i5 + 1;
                        int i8 = i5 - 1;
                        if (i8 > -1) {
                            byte b3 = byteBuf.getByte(i8);
                            if (!z2) {
                                if (b3 == 63 || (b3 == 45 && (i2 = i5 - 2) > -1 && byteBuf.getByte(i2) == 45)) {
                                    j--;
                                }
                            } else if (b3 == 93 && (i = i5 - 2) > -1 && byteBuf.getByte(i) == 93) {
                                j--;
                                z2 = false;
                            }
                        }
                        if (z3 && j == 0) {
                            break;
                        }
                    } else {
                        continue;
                    }
                } else if (i5 < iWriterIndex - 1 && byteBuf.getByte(i5 + 1) == 62) {
                    j--;
                }
            }
        }
        int i9 = byteBuf.readerIndex();
        int i10 = i4 - i9;
        if (j != 0 || i10 <= 0) {
            return;
        }
        if (i9 + i10 >= iWriterIndex) {
            i10 = byteBuf.readableBytes();
        }
        ByteBuf byteBufExtractFrame = extractFrame(byteBuf, i9 + i3, i10 - i3);
        byteBuf.skipBytes(i10);
        list.add(byteBufExtractFrame);
    }

    private void fail(long j) {
        if (j > 0) {
            throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + ": " + j + " - discarded");
        }
        throw new TooLongFrameException("frame length exceeds " + this.maxFrameLength + " - discarding");
    }

    private static void fail(ChannelHandlerContext channelHandlerContext) {
        channelHandlerContext.fireExceptionCaught((Throwable) new CorruptedFrameException("frame contains content before the xml starts"));
    }

    private static ByteBuf extractFrame(ByteBuf byteBuf, int i, int i2) {
        return byteBuf.copy(i, i2);
    }

    private static boolean isCommentBlockStart(ByteBuf byteBuf, int i) {
        return i < byteBuf.writerIndex() + (-3) && byteBuf.getByte(i + 2) == 45 && byteBuf.getByte(i + 3) == 45;
    }

    private static boolean isCDATABlockStart(ByteBuf byteBuf, int i) {
        return i < byteBuf.writerIndex() + (-8) && byteBuf.getByte(i + 2) == 91 && byteBuf.getByte(i + 3) == 67 && byteBuf.getByte(i + 4) == 68 && byteBuf.getByte(i + 5) == 65 && byteBuf.getByte(i + 6) == 84 && byteBuf.getByte(i + 7) == 65 && byteBuf.getByte(i + 8) == 91;
    }
}
