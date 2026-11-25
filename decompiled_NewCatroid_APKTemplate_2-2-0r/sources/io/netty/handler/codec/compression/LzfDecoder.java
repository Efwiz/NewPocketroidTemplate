package io.netty.handler.codec.compression;

import com.ning.compress.BufferRecycler;
import com.ning.compress.lzf.ChunkDecoder;
import com.ning.compress.lzf.util.ChunkDecoderFactory;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.util.List;

/* JADX INFO: loaded from: classes11.dex */
public class LzfDecoder extends ByteToMessageDecoder {
    private static final short MAGIC_NUMBER = 23126;
    private int chunkLength;
    private State currentState;
    private ChunkDecoder decoder;
    private boolean isCompressed;
    private int originalLength;
    private BufferRecycler recycler;

    private enum State {
        INIT_BLOCK,
        INIT_ORIGINAL_LENGTH,
        DECOMPRESS_DATA,
        CORRUPTED
    }

    public LzfDecoder() {
        this(false);
    }

    public LzfDecoder(boolean z) {
        ChunkDecoder chunkDecoderOptimalInstance;
        this.currentState = State.INIT_BLOCK;
        if (z) {
            chunkDecoderOptimalInstance = ChunkDecoderFactory.safeInstance();
        } else {
            chunkDecoderOptimalInstance = ChunkDecoderFactory.optimalInstance();
        }
        this.decoder = chunkDecoderOptimalInstance;
        this.recycler = BufferRecycler.instance();
    }

    /* JADX INFO: renamed from: io.netty.handler.codec.compression.LzfDecoder$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State = iArr;
            try {
                iArr[State.INIT_BLOCK.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State[State.INIT_ORIGINAL_LENGTH.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State[State.DECOMPRESS_DATA.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State[State.CORRUPTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    /* JADX WARN: Code duplicated, block: B:33:0x008c  */
    /* JADX WARN: Code duplicated, block: B:34:0x008e A[Catch: Exception -> 0x0107, TryCatch #0 {Exception -> 0x0107, blocks: (B:2:0x0000, B:9:0x0017, B:10:0x0020, B:11:0x0025, B:31:0x0084, B:34:0x008e, B:36:0x0094, B:38:0x009e, B:40:0x00b4, B:43:0x00e0, B:45:0x00e6, B:51:0x00fa, B:47:0x00ed, B:48:0x00f0, B:39:0x00a9, B:50:0x00f3, B:27:0x0072, B:30:0x007a, B:12:0x0026, B:15:0x002f, B:17:0x0037, B:20:0x003f, B:24:0x0068, B:21:0x0046, B:22:0x0061, B:23:0x0062, B:53:0x00ff, B:54:0x0106, B:42:0x00ca), top: B:58:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:36:0x0094 A[Catch: Exception -> 0x0107, TryCatch #0 {Exception -> 0x0107, blocks: (B:2:0x0000, B:9:0x0017, B:10:0x0020, B:11:0x0025, B:31:0x0084, B:34:0x008e, B:36:0x0094, B:38:0x009e, B:40:0x00b4, B:43:0x00e0, B:45:0x00e6, B:51:0x00fa, B:47:0x00ed, B:48:0x00f0, B:39:0x00a9, B:50:0x00f3, B:27:0x0072, B:30:0x007a, B:12:0x0026, B:15:0x002f, B:17:0x0037, B:20:0x003f, B:24:0x0068, B:21:0x0046, B:22:0x0061, B:23:0x0062, B:53:0x00ff, B:54:0x0106, B:42:0x00ca), top: B:58:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:38:0x009e A[Catch: Exception -> 0x0107, TryCatch #0 {Exception -> 0x0107, blocks: (B:2:0x0000, B:9:0x0017, B:10:0x0020, B:11:0x0025, B:31:0x0084, B:34:0x008e, B:36:0x0094, B:38:0x009e, B:40:0x00b4, B:43:0x00e0, B:45:0x00e6, B:51:0x00fa, B:47:0x00ed, B:48:0x00f0, B:39:0x00a9, B:50:0x00f3, B:27:0x0072, B:30:0x007a, B:12:0x0026, B:15:0x002f, B:17:0x0037, B:20:0x003f, B:24:0x0068, B:21:0x0046, B:22:0x0061, B:23:0x0062, B:53:0x00ff, B:54:0x0106, B:42:0x00ca), top: B:58:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x00a9 A[Catch: Exception -> 0x0107, TryCatch #0 {Exception -> 0x0107, blocks: (B:2:0x0000, B:9:0x0017, B:10:0x0020, B:11:0x0025, B:31:0x0084, B:34:0x008e, B:36:0x0094, B:38:0x009e, B:40:0x00b4, B:43:0x00e0, B:45:0x00e6, B:51:0x00fa, B:47:0x00ed, B:48:0x00f0, B:39:0x00a9, B:50:0x00f3, B:27:0x0072, B:30:0x007a, B:12:0x0026, B:15:0x002f, B:17:0x0037, B:20:0x003f, B:24:0x0068, B:21:0x0046, B:22:0x0061, B:23:0x0062, B:53:0x00ff, B:54:0x0106, B:42:0x00ca), top: B:58:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:45:0x00e6 A[Catch: Exception -> 0x0107, TryCatch #0 {Exception -> 0x0107, blocks: (B:2:0x0000, B:9:0x0017, B:10:0x0020, B:11:0x0025, B:31:0x0084, B:34:0x008e, B:36:0x0094, B:38:0x009e, B:40:0x00b4, B:43:0x00e0, B:45:0x00e6, B:51:0x00fa, B:47:0x00ed, B:48:0x00f0, B:39:0x00a9, B:50:0x00f3, B:27:0x0072, B:30:0x007a, B:12:0x0026, B:15:0x002f, B:17:0x0037, B:20:0x003f, B:24:0x0068, B:21:0x0046, B:22:0x0061, B:23:0x0062, B:53:0x00ff, B:54:0x0106, B:42:0x00ca), top: B:58:0x0000, inners: #1 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00f1 A[DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:50:0x00f3 A[Catch: Exception -> 0x0107, TryCatch #0 {Exception -> 0x0107, blocks: (B:2:0x0000, B:9:0x0017, B:10:0x0020, B:11:0x0025, B:31:0x0084, B:34:0x008e, B:36:0x0094, B:38:0x009e, B:40:0x00b4, B:43:0x00e0, B:45:0x00e6, B:51:0x00fa, B:47:0x00ed, B:48:0x00f0, B:39:0x00a9, B:50:0x00f3, B:27:0x0072, B:30:0x007a, B:12:0x0026, B:15:0x002f, B:17:0x0037, B:20:0x003f, B:24:0x0068, B:21:0x0046, B:22:0x0061, B:23:0x0062, B:53:0x00ff, B:54:0x0106, B:42:0x00ca), top: B:58:0x0000, inners: #1 }] */
    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        int i;
        int i2;
        int i3;
        int iArrayOffset;
        byte[] bArrArray;
        ByteBuf byteBufHeapBuffer;
        try {
            int i4 = AnonymousClass1.$SwitchMap$io$netty$handler$codec$compression$LzfDecoder$State[this.currentState.ordinal()];
            if (i4 == 1) {
                if (byteBuf.readableBytes() < 5) {
                    return;
                }
                if (byteBuf.readUnsignedShort() != 23126) {
                    throw new DecompressionException("unexpected block identifier");
                }
                byte b = byteBuf.readByte();
                if (b == 0) {
                    this.isCompressed = false;
                    this.currentState = State.DECOMPRESS_DATA;
                } else if (b == 1) {
                    this.isCompressed = true;
                    this.currentState = State.INIT_ORIGINAL_LENGTH;
                } else {
                    throw new DecompressionException(String.format("unknown type of chunk: %d (expected: %d or %d)", Integer.valueOf(b), 0, 1));
                }
                this.chunkLength = byteBuf.readUnsignedShort();
                if (b != 1) {
                    return;
                }
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
                i2 = this.originalLength;
                if (this.isCompressed) {
                    i3 = byteBuf.readerIndex();
                    if (byteBuf.hasArray()) {
                        bArrArray = byteBuf.array();
                        iArrayOffset = i3 + byteBuf.arrayOffset();
                    } else {
                        byte[] bArrAllocInputBuffer = this.recycler.allocInputBuffer(i);
                        byteBuf.getBytes(i3, bArrAllocInputBuffer, 0, i);
                        iArrayOffset = 0;
                        bArrArray = bArrAllocInputBuffer;
                    }
                    byteBufHeapBuffer = channelHandlerContext.alloc().heapBuffer(i2, i2);
                    byte[] bArrArray2 = byteBufHeapBuffer.array();
                    int iArrayOffset2 = byteBufHeapBuffer.arrayOffset() + byteBufHeapBuffer.writerIndex();
                    try {
                        this.decoder.decodeChunk(bArrArray, iArrayOffset, bArrArray2, iArrayOffset2, iArrayOffset2 + i2);
                        byteBufHeapBuffer.writerIndex(byteBufHeapBuffer.writerIndex() + i2);
                        list.add(byteBufHeapBuffer);
                        byteBuf.skipBytes(i);
                        if (!byteBuf.hasArray()) {
                            this.recycler.releaseInputBuffer(bArrArray);
                        }
                    } catch (Throwable th) {
                        byteBufHeapBuffer.release();
                        throw th;
                    }
                } else if (i > 0) {
                    list.add(byteBuf.readRetainedSlice(i));
                }
                this.currentState = State.INIT_BLOCK;
            }
            if (byteBuf.readableBytes() < 2) {
                return;
            }
            this.originalLength = byteBuf.readUnsignedShort();
            this.currentState = State.DECOMPRESS_DATA;
            i = this.chunkLength;
            if (byteBuf.readableBytes() < i) {
                return;
            }
            i2 = this.originalLength;
            if (this.isCompressed) {
                i3 = byteBuf.readerIndex();
                if (byteBuf.hasArray()) {
                    bArrArray = byteBuf.array();
                    iArrayOffset = i3 + byteBuf.arrayOffset();
                } else {
                    byte[] bArrAllocInputBuffer2 = this.recycler.allocInputBuffer(i);
                    byteBuf.getBytes(i3, bArrAllocInputBuffer2, 0, i);
                    iArrayOffset = 0;
                    bArrArray = bArrAllocInputBuffer2;
                }
                byteBufHeapBuffer = channelHandlerContext.alloc().heapBuffer(i2, i2);
                byte[] bArrArray3 = byteBufHeapBuffer.array();
                int iArrayOffset3 = byteBufHeapBuffer.arrayOffset() + byteBufHeapBuffer.writerIndex();
                this.decoder.decodeChunk(bArrArray, iArrayOffset, bArrArray3, iArrayOffset3, iArrayOffset3 + i2);
                byteBufHeapBuffer.writerIndex(byteBufHeapBuffer.writerIndex() + i2);
                list.add(byteBufHeapBuffer);
                byteBuf.skipBytes(i);
                if (!byteBuf.hasArray()) {
                    this.recycler.releaseInputBuffer(bArrArray);
                }
            } else if (i > 0) {
                list.add(byteBuf.readRetainedSlice(i));
            }
            this.currentState = State.INIT_BLOCK;
        } catch (Exception e) {
            this.currentState = State.CORRUPTED;
            this.decoder = null;
            this.recycler = null;
            throw e;
        }
    }
}
