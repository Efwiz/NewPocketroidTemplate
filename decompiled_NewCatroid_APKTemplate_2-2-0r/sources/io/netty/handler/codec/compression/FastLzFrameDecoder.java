package io.netty.handler.codec.compression;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.internal.EmptyArrays;
import java.util.List;
import java.util.zip.Adler32;
import java.util.zip.Checksum;

/* JADX INFO: loaded from: classes11.dex */
public class FastLzFrameDecoder extends ByteToMessageDecoder {
    private final Checksum checksum;
    private int chunkLength;
    private int currentChecksum;
    private State currentState;
    private boolean hasChecksum;
    private boolean isCompressed;
    private int originalLength;

    private enum State {
        INIT_BLOCK,
        INIT_BLOCK_PARAMS,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public FastLzFrameDecoder() {
        this(false);
    }

    public FastLzFrameDecoder(boolean z) {
        this(z ? new Adler32() : null);
    }

    public FastLzFrameDecoder(Checksum checksum) {
        this.currentState = State.INIT_BLOCK;
        this.checksum = checksum;
    }

    /* JADX INFO: renamed from: io.netty.handler.codec.compression.FastLzFrameDecoder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State = iArr;
            try {
                iArr[State.INIT_BLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State[State.INIT_BLOCK_PARAMS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State[State.DECOMPRESS_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State[State.CORRUPTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:45:0x008e  */
    /* JADX WARN: Code duplicated, block: B:46:0x0090 A[Catch: Exception -> 0x014b, TryCatch #0 {Exception -> 0x014b, blocks: (B:2:0x0000, B:8:0x0017, B:9:0x0020, B:10:0x0025, B:43:0x0086, B:46:0x0090, B:48:0x0098, B:77:0x013f, B:78:0x0142, B:49:0x00af, B:25:0x0051, B:29:0x005c, B:33:0x0063, B:37:0x006a, B:39:0x0070, B:41:0x007c, B:42:0x0080, B:11:0x0026, B:14:0x002e, B:16:0x0037, B:20:0x0042, B:24:0x004b, B:79:0x0143, B:80:0x014a, B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:84:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:48:0x0098 A[Catch: Exception -> 0x014b, TryCatch #0 {Exception -> 0x014b, blocks: (B:2:0x0000, B:8:0x0017, B:9:0x0020, B:10:0x0025, B:43:0x0086, B:46:0x0090, B:48:0x0098, B:77:0x013f, B:78:0x0142, B:49:0x00af, B:25:0x0051, B:29:0x005c, B:33:0x0063, B:37:0x006a, B:39:0x0070, B:41:0x007c, B:42:0x0080, B:11:0x0026, B:14:0x002e, B:16:0x0037, B:20:0x0042, B:24:0x004b, B:79:0x0143, B:80:0x014a, B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:84:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00af A[Catch: Exception -> 0x014b, TRY_LEAVE, TryCatch #0 {Exception -> 0x014b, blocks: (B:2:0x0000, B:8:0x0017, B:9:0x0020, B:10:0x0025, B:43:0x0086, B:46:0x0090, B:48:0x0098, B:77:0x013f, B:78:0x0142, B:49:0x00af, B:25:0x0051, B:29:0x005c, B:33:0x0063, B:37:0x006a, B:39:0x0070, B:41:0x007c, B:42:0x0080, B:11:0x0026, B:14:0x002e, B:16:0x0037, B:20:0x0042, B:24:0x004b, B:79:0x0143, B:80:0x014a, B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:84:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:53:0x00b7 A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:55:0x00bd A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:56:0x00c9 A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:59:0x00d9  */
    /* JADX WARN: Code duplicated, block: B:60:0x00da A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:62:0x00f2 A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:68:0x010c  */
    /* JADX WARN: Code duplicated, block: B:69:0x010d A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    /* JADX WARN: Code duplicated, block: B:72:0x0129 A[Catch: all -> 0x013c, TryCatch #1 {all -> 0x013c, blocks: (B:51:0x00b3, B:53:0x00b7, B:55:0x00bd, B:57:0x00cf, B:63:0x00f5, B:66:0x00fd, B:69:0x010d, B:70:0x0126, B:72:0x0129, B:73:0x0134, B:60:0x00da, B:61:0x00f1, B:56:0x00c9, B:62:0x00f2), top: B:85:0x00b3, outer: #0 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i;
        int i2;
        int i3;
        byte[] bArrArray;
        ByteBuf byteBufHeapBuffer;
        int iArrayOffset;
        Checksum checksum;
        int value;
        byte[] bArr;
        int iArrayOffset2;
        int iDecompress;
        try {
            int i4 = AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$FastLzFrameDecoder$State[this.currentState.ordinal()];
            int i5 = 4;
            boolean z = true;
            if (i4 == 1) {
                if (byteBuf.readableBytes() < 4) {
                    return;
                }
                if (byteBuf.readUnsignedMedium() != 4607066) {
                    throw new DecompressionException("unexpected block identifier");
                }
                byte b = byteBuf.readByte();
                this.isCompressed = (b & 1) == 1;
                if ((b & 16) != 16) {
                    z = false;
                }
                this.hasChecksum = z;
                this.currentState = State.INIT_BLOCK_PARAMS;
            } else {
                if (i4 != 2) {
                    if (i4 != 3) {
                        if (i4 == 4) {
                            byteBuf.skipBytes(byteBuf.readableBytes());
                            return;
                        }
                        throw new IllegalStateException();
                    }
                }
                i = this.chunkLength;
                if (byteBuf.readableBytes() < i) {
                    return;
                }
                i2 = byteBuf.readerIndex();
                i3 = this.originalLength;
                if (i3 != 0) {
                    byteBufHeapBuffer = channelHandlerContext.alloc().heapBuffer(i3, i3);
                    bArrArray = byteBufHeapBuffer.array();
                    iArrayOffset = byteBufHeapBuffer.arrayOffset() + byteBufHeapBuffer.writerIndex();
                } else {
                    bArrArray = EmptyArrays.EMPTY_BYTES;
                    byteBufHeapBuffer = null;
                    iArrayOffset = 0;
                }
                try {
                    if (this.isCompressed) {
                        if (byteBuf.hasArray()) {
                            byte[] bArrArray2 = byteBuf.array();
                            iArrayOffset2 = i2 + byteBuf.arrayOffset();
                            bArr = bArrArray2;
                        } else {
                            bArr = new byte[i];
                            byteBuf.getBytes(i2, bArr);
                            iArrayOffset2 = 0;
                        }
                        iDecompress = FastLz.decompress(bArr, iArrayOffset2, i, bArrArray, iArrayOffset, i3);
                        if (i3 == iDecompress) {
                            throw new DecompressionException(String.format("stream corrupted: originalLength(%d) and actual length(%d) mismatch", Integer.valueOf(i3), Integer.valueOf(iDecompress)));
                        }
                    } else {
                        byteBuf.getBytes(i2, bArrArray, iArrayOffset, i);
                    }
                    checksum = this.checksum;
                    if (this.hasChecksum && checksum != null) {
                        checksum.reset();
                        checksum.update(bArrArray, iArrayOffset, i3);
                        value = (int) checksum.getValue();
                        if (value == this.currentChecksum) {
                            throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", Integer.valueOf(value), Integer.valueOf(this.currentChecksum)));
                        }
                    }
                    if (byteBufHeapBuffer != null) {
                        byteBufHeapBuffer.writerIndex(byteBufHeapBuffer.writerIndex() + i3);
                        list.add(byteBufHeapBuffer);
                    }
                    byteBuf.skipBytes(i);
                    this.currentState = State.INIT_BLOCK;
                } catch (Throwable th) {
                    if (byteBufHeapBuffer != null) {
                        byteBufHeapBuffer.release();
                    }
                    throw th;
                }
            }
            int i6 = byteBuf.readableBytes();
            int i7 = 2 + (this.isCompressed ? 2 : 0);
            boolean z2 = this.hasChecksum;
            if (!z2) {
                i5 = 0;
            }
            if (i6 < i7 + i5) {
                return;
            }
            this.currentChecksum = z2 ? byteBuf.readInt() : 0;
            int unsignedShort = byteBuf.readUnsignedShort();
            this.chunkLength = unsignedShort;
            if (this.isCompressed) {
                unsignedShort = byteBuf.readUnsignedShort();
            }
            this.originalLength = unsignedShort;
            this.currentState = State.DECOMPRESS_DATA;
            i = this.chunkLength;
            if (byteBuf.readableBytes() < i) {
                return;
            }
            i2 = byteBuf.readerIndex();
            i3 = this.originalLength;
            if (i3 != 0) {
                byteBufHeapBuffer = channelHandlerContext.alloc().heapBuffer(i3, i3);
                bArrArray = byteBufHeapBuffer.array();
                iArrayOffset = byteBufHeapBuffer.arrayOffset() + byteBufHeapBuffer.writerIndex();
            } else {
                bArrArray = EmptyArrays.EMPTY_BYTES;
                byteBufHeapBuffer = null;
                iArrayOffset = 0;
            }
            if (this.isCompressed) {
                if (byteBuf.hasArray()) {
                    byte[] bArrArray3 = byteBuf.array();
                    iArrayOffset2 = i2 + byteBuf.arrayOffset();
                    bArr = bArrArray3;
                } else {
                    bArr = new byte[i];
                    byteBuf.getBytes(i2, bArr);
                    iArrayOffset2 = 0;
                }
                iDecompress = FastLz.decompress(bArr, iArrayOffset2, i, bArrArray, iArrayOffset, i3);
                if (i3 == iDecompress) {
                    throw new DecompressionException(String.format("stream corrupted: originalLength(%d) and actual length(%d) mismatch", Integer.valueOf(i3), Integer.valueOf(iDecompress)));
                }
            } else {
                byteBuf.getBytes(i2, bArrArray, iArrayOffset, i);
            }
            checksum = this.checksum;
            if (this.hasChecksum) {
                checksum.reset();
                checksum.update(bArrArray, iArrayOffset, i3);
                value = (int) checksum.getValue();
                if (value == this.currentChecksum) {
                    throw new DecompressionException(String.format("stream corrupted: mismatching checksum: %d (expected: %d)", Integer.valueOf(value), Integer.valueOf(this.currentChecksum)));
                }
            }
            if (byteBufHeapBuffer != null) {
                byteBufHeapBuffer.writerIndex(byteBufHeapBuffer.writerIndex() + i3);
                list.add(byteBufHeapBuffer);
            }
            byteBuf.skipBytes(i);
            this.currentState = State.INIT_BLOCK;
        } catch (Exception e) {
            this.currentState = State.CORRUPTED;
            throw e;
        }
    }
}
