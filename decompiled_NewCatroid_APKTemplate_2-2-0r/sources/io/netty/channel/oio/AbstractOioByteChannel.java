package io.netty.channel.oio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.util.internal.StringUtil;
import java.io.IOException;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractOioByteChannel extends AbstractOioChannel {
    private static final ChannelMetadata METADATA = new ChannelMetadata(false);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) FileRegion.class) + ')';

    protected abstract int available();

    protected abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    protected abstract void doWriteBytes(ByteBuf byteBuf) throws Exception;

    protected abstract void doWriteFileRegion(FileRegion fileRegion) throws Exception;

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract boolean isInputShutdown();

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract ChannelFuture shutdownInput();

    protected AbstractOioByteChannel(Channel channel) {
        super(channel);
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return METADATA;
    }

    private void closeOnRead(ChannelPipeline channelPipeline) {
        if (isOpen()) {
            if (Boolean.TRUE.equals(config().getOption(ChannelOption.ALLOW_HALF_CLOSURE))) {
                shutdownInput();
                channelPipeline.fireUserEventTriggered((Object) ChannelInputShutdownEvent.INSTANCE);
            } else {
                unsafe().close(unsafe().voidPromise());
            }
            channelPipeline.fireUserEventTriggered((Object) ChannelInputShutdownReadComplete.INSTANCE);
        }
    }

    private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, RecvByteBufAllocator.Handle handle) {
        if (byteBuf != null) {
            if (byteBuf.isReadable()) {
                this.readPending = false;
                channelPipeline.fireChannelRead((Object) byteBuf);
            } else {
                byteBuf.release();
            }
        }
        handle.readComplete();
        channelPipeline.fireChannelReadComplete();
        channelPipeline.fireExceptionCaught(th);
        if (z || (th instanceof IOException)) {
            closeOnRead(channelPipeline);
        }
    }

    /* JADX WARN: Code duplicated, block: B:115:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:116:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:43:0x0098 A[Catch: all -> 0x00a2, TryCatch #1 {all -> 0x00a2, blocks: (B:41:0x0092, B:43:0x0098, B:44:0x009e), top: B:98:0x0092 }] */
    /* JADX WARN: Code duplicated, block: B:44:0x009e A[Catch: all -> 0x00a2, TRY_LEAVE, TryCatch #1 {all -> 0x00a2, blocks: (B:41:0x0092, B:43:0x0098, B:44:0x009e), top: B:98:0x0092 }] */
    /* JADX WARN: Code duplicated, block: B:49:0x00a8  */
    /* JADX WARN: Code duplicated, block: B:51:0x00ab A[Catch: all -> 0x00b2, TRY_ENTER, TryCatch #2 {all -> 0x00b2, blocks: (B:20:0x004d, B:51:0x00ab, B:55:0x00b6), top: B:100:0x004d }] */
    /* JADX WARN: Code duplicated, block: B:55:0x00b6 A[Catch: all -> 0x00b2, TRY_LEAVE, TryCatch #2 {all -> 0x00b2, blocks: (B:20:0x004d, B:51:0x00ab, B:55:0x00b6), top: B:100:0x004d }] */
    /* JADX WARN: Code duplicated, block: B:77:0x00e6 A[DONT_GENERATE] */
    /* JADX WARN: Code duplicated, block: B:79:0x00ec A[DONT_GENERATE, DONT_INVERT] */
    /* JADX WARN: Code duplicated, block: B:80:0x00ee A[DONT_GENERATE] */
    /* JADX WARN: Code duplicated, block: B:98:0x0092 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    @Override // io.netty.channel.oio.AbstractOioChannel
    protected void doRead() {
        Throwable th;
        boolean z;
        boolean zIsAutoRead;
        boolean zIsActive;
        boolean z2;
        Throwable th2;
        ChannelConfig channelConfigConfig = config();
        if (isInputShutdown() || !this.readPending) {
            return;
        }
        boolean z3 = false;
        this.readPending = false;
        ChannelPipeline channelPipelinePipeline = pipeline();
        ByteBufAllocator allocator = channelConfigConfig.getAllocator();
        RecvByteBufAllocator.Handle handleRecvBufAllocHandle = unsafe().recvBufAllocHandle();
        handleRecvBufAllocHandle.reset(channelConfigConfig);
        ByteBuf byteBuf = null;
        try {
            ByteBuf byteBufAllocate = handleRecvBufAllocHandle.allocate(allocator);
            boolean z4 = false;
            while (true) {
                try {
                    handleRecvBufAllocHandle.lastBytesRead(doReadBytes(byteBufAllocate));
                    if (handleRecvBufAllocHandle.lastBytesRead() <= 0) {
                        if (!byteBufAllocate.isReadable()) {
                            byteBufAllocate.release();
                            try {
                                z2 = handleRecvBufAllocHandle.lastBytesRead() < 0;
                                if (z2) {
                                    try {
                                        this.readPending = false;
                                    } catch (Throwable th3) {
                                        th2 = th3;
                                        th = th2;
                                        z3 = z4;
                                        z3 = z2;
                                        try {
                                            handleReadException(channelPipelinePipeline, byteBuf, th, z3, handleRecvBufAllocHandle);
                                            if (!z) {
                                                if (!zIsAutoRead) {
                                                    if (!z3) {
                                                        return;
                                                    } else {
                                                        if (!zIsActive) {
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                            return;
                                        } finally {
                                            if (this.readPending || channelConfigConfig.isAutoRead() || (!z3 && isActive())) {
                                                read();
                                            }
                                        }
                                    }
                                }
                                byteBufAllocate = null;
                            } catch (Throwable th4) {
                                th = th4;
                                th = th;
                                boolean z5 = z4;
                                z3 = false;
                                z3 = z5;
                                handleReadException(channelPipelinePipeline, byteBuf, th, z3, handleRecvBufAllocHandle);
                                if (!z) {
                                    if (!zIsAutoRead) {
                                        if (!z3) {
                                            return;
                                        } else {
                                            if (!zIsActive) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (byteBufAllocate == null) {
                            try {
                                if (byteBufAllocate.isReadable()) {
                                    this.readPending = false;
                                    channelPipelinePipeline.fireChannelRead((Object) byteBufAllocate);
                                } else {
                                    byteBufAllocate.release();
                                }
                            } catch (Throwable th5) {
                                th2 = th5;
                                byteBuf = byteBufAllocate;
                                th = th2;
                                z3 = z4;
                                z3 = z2;
                                handleReadException(channelPipelinePipeline, byteBuf, th, z3, handleRecvBufAllocHandle);
                                if (!z) {
                                    if (!zIsAutoRead) {
                                        if (!z3) {
                                            return;
                                        } else {
                                            if (!zIsActive) {
                                                return;
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            byteBuf = byteBufAllocate;
                        }
                        if (z4) {
                            handleRecvBufAllocHandle.readComplete();
                            channelPipelinePipeline.fireChannelReadComplete();
                        }
                        if (z2) {
                            closeOnRead(channelPipelinePipeline);
                        }
                        if (!this.readPending && !channelConfigConfig.isAutoRead() && (z4 || !isActive())) {
                            return;
                        }
                        return;
                    }
                    try {
                        int iAvailable = available();
                        if (iAvailable > 0) {
                            if (!byteBufAllocate.isWritable()) {
                                int iCapacity = byteBufAllocate.capacity();
                                int iMaxCapacity = byteBufAllocate.maxCapacity();
                                if (iCapacity == iMaxCapacity) {
                                    handleRecvBufAllocHandle.incMessagesRead(1);
                                    this.readPending = false;
                                    channelPipelinePipeline.fireChannelRead((Object) byteBufAllocate);
                                    byteBufAllocate = handleRecvBufAllocHandle.allocate(allocator);
                                } else if (byteBufAllocate.writerIndex() + iAvailable > iMaxCapacity) {
                                    byteBufAllocate.capacity(iMaxCapacity);
                                } else {
                                    byteBufAllocate.ensureWritable(iAvailable);
                                }
                            }
                            if (handleRecvBufAllocHandle.continueReading()) {
                                z4 = true;
                            }
                        }
                        z4 = true;
                    } catch (Throwable th6) {
                        th = th6;
                        byteBuf = byteBufAllocate;
                        z3 = true;
                        th = th;
                        handleReadException(channelPipelinePipeline, byteBuf, th, z3, handleRecvBufAllocHandle);
                        if (!z) {
                            if (!zIsAutoRead) {
                                if (!z3) {
                                    return;
                                } else {
                                    if (!zIsActive) {
                                        return;
                                    }
                                }
                            }
                        }
                    }
                    z2 = false;
                    if (byteBufAllocate == null) {
                        byteBuf = byteBufAllocate;
                    } else if (byteBufAllocate.isReadable()) {
                        this.readPending = false;
                        channelPipelinePipeline.fireChannelRead((Object) byteBufAllocate);
                    } else {
                        byteBufAllocate.release();
                    }
                    if (z4) {
                        handleRecvBufAllocHandle.readComplete();
                        channelPipelinePipeline.fireChannelReadComplete();
                    }
                    if (z2) {
                        closeOnRead(channelPipelinePipeline);
                    }
                    if (!this.readPending) {
                        return;
                    }
                } catch (Throwable th7) {
                    th = th7;
                    byteBuf = byteBufAllocate;
                }
                return;
            }
        } catch (Throwable th8) {
            th = th8;
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        while (true) {
            Object objCurrent = channelOutboundBuffer.current();
            if (objCurrent == null) {
                return;
            }
            if (objCurrent instanceof ByteBuf) {
                ByteBuf byteBuf = (ByteBuf) objCurrent;
                int i = byteBuf.readableBytes();
                while (i > 0) {
                    doWriteBytes(byteBuf);
                    int i2 = byteBuf.readableBytes();
                    channelOutboundBuffer.progress(i - i2);
                    i = i2;
                }
                channelOutboundBuffer.remove();
            } else if (objCurrent instanceof FileRegion) {
                FileRegion fileRegion = (FileRegion) objCurrent;
                long jTransferred = fileRegion.transferred();
                doWriteFileRegion(fileRegion);
                channelOutboundBuffer.progress(fileRegion.transferred() - jTransferred);
                channelOutboundBuffer.remove();
            } else {
                channelOutboundBuffer.remove(new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(objCurrent)));
            }
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected final Object filterOutboundMessage(Object obj) throws Exception {
        if ((obj instanceof ByteBuf) || (obj instanceof FileRegion)) {
            return obj;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + EXPECTED_TYPES);
    }
}
