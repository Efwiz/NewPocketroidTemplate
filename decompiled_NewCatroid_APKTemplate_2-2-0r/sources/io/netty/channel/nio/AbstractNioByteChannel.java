package io.netty.channel.nio;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelConfig;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelMetadata;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.socket.ChannelInputShutdownEvent;
import io.netty.channel.socket.ChannelInputShutdownReadComplete;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.internal.StringUtil;
import java.io.IOException;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractNioByteChannel extends AbstractNioChannel {
    private final Runnable flushTask;
    private boolean inputClosedSeenErrorOnRead;
    private static final ChannelMetadata METADATA = new ChannelMetadata(false, 16);
    private static final String EXPECTED_TYPES = " (expected: " + StringUtil.simpleClassName((Class<?>) ByteBuf.class) + ", " + StringUtil.simpleClassName((Class<?>) FileRegion.class) + ')';

    protected abstract int doReadBytes(ByteBuf byteBuf) throws Exception;

    protected abstract int doWriteBytes(ByteBuf byteBuf) throws Exception;

    protected abstract long doWriteFileRegion(FileRegion fileRegion) throws Exception;

    protected boolean isInputShutdown0() {
        return false;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public abstract ChannelFuture shutdownInput();

    protected AbstractNioByteChannel(Channel channel, SelectableChannel selectableChannel) {
        super(channel, selectableChannel, 1);
        this.flushTask = new Runnable() { // from class: io.netty.channel.nio.AbstractNioByteChannel.1
            @Override // java.lang.Runnable
            public void run() {
                ((AbstractNioChannel.AbstractNioUnsafe) AbstractNioByteChannel.this.unsafe()).flush0();
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.AbstractChannel
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioByteUnsafe();
    }

    @Override // io.netty.channel.Channel
    public ChannelMetadata metadata() {
        return METADATA;
    }

    final boolean shouldBreakReadReady(ChannelConfig channelConfig) {
        return isInputShutdown0() && (this.inputClosedSeenErrorOnRead || !isAllowHalfClosure(channelConfig));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isAllowHalfClosure(ChannelConfig channelConfig) {
        return (channelConfig instanceof SocketChannelConfig) && ((SocketChannelConfig) channelConfig).isAllowHalfClosure();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public class NioByteUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
        protected NioByteUnsafe() {
            super();
        }

        private void closeOnRead(ChannelPipeline channelPipeline) {
            if (!AbstractNioByteChannel.this.isInputShutdown0()) {
                if (AbstractNioByteChannel.isAllowHalfClosure(AbstractNioByteChannel.this.config())) {
                    AbstractNioByteChannel.this.shutdownInput();
                    channelPipeline.fireUserEventTriggered((Object) ChannelInputShutdownEvent.INSTANCE);
                    return;
                } else {
                    close(voidPromise());
                    return;
                }
            }
            AbstractNioByteChannel.this.inputClosedSeenErrorOnRead = true;
            channelPipeline.fireUserEventTriggered((Object) ChannelInputShutdownReadComplete.INSTANCE);
        }

        private void handleReadException(ChannelPipeline channelPipeline, ByteBuf byteBuf, Throwable th, boolean z, RecvByteBufAllocator.Handle handle) {
            if (byteBuf != null) {
                if (byteBuf.isReadable()) {
                    AbstractNioByteChannel.this.readPending = false;
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

        @Override // io.netty.channel.nio.AbstractNioChannel.NioUnsafe
        public final void read() {
            boolean z;
            Throwable th;
            boolean z2;
            ByteBuf byteBuf;
            boolean z3;
            boolean zIsAutoRead;
            ChannelConfig channelConfigConfig = AbstractNioByteChannel.this.config();
            if (AbstractNioByteChannel.this.shouldBreakReadReady(channelConfigConfig)) {
                AbstractNioByteChannel.this.clearReadPending();
                return;
            }
            ChannelPipeline channelPipelinePipeline = AbstractNioByteChannel.this.pipeline();
            ByteBufAllocator allocator = channelConfigConfig.getAllocator();
            RecvByteBufAllocator.Handle handleRecvBufAllocHandle = recvBufAllocHandle();
            handleRecvBufAllocHandle.reset(channelConfigConfig);
            do {
                z = false;
                try {
                    ByteBuf byteBufAllocate = handleRecvBufAllocHandle.allocate(allocator);
                    try {
                        handleRecvBufAllocHandle.lastBytesRead(AbstractNioByteChannel.this.doReadBytes(byteBufAllocate));
                        if (handleRecvBufAllocHandle.lastBytesRead() <= 0) {
                            byteBufAllocate.release();
                            boolean z4 = handleRecvBufAllocHandle.lastBytesRead() < 0;
                            if (z4) {
                                try {
                                    AbstractNioByteChannel.this.readPending = false;
                                } catch (Throwable th2) {
                                    th = th2;
                                    byteBuf = null;
                                    z2 = z4;
                                    try {
                                        handleReadException(channelPipelinePipeline, byteBuf, th, z2, handleRecvBufAllocHandle);
                                        if (z3) {
                                            return;
                                        } else {
                                            if (zIsAutoRead) {
                                                return;
                                            }
                                        }
                                    } finally {
                                        if (!AbstractNioByteChannel.this.readPending && !channelConfigConfig.isAutoRead()) {
                                            removeReadOp();
                                        }
                                    }
                                }
                            }
                            z = z4;
                            break;
                        }
                        handleRecvBufAllocHandle.incMessagesRead(1);
                        AbstractNioByteChannel.this.readPending = false;
                        channelPipelinePipeline.fireChannelRead((Object) byteBufAllocate);
                    } catch (Throwable th3) {
                        z2 = false;
                        byteBuf = byteBufAllocate;
                        th = th3;
                    }
                } catch (Throwable th4) {
                    th = th4;
                    z2 = z;
                    byteBuf = null;
                }
            } while (handleRecvBufAllocHandle.continueReading());
            handleRecvBufAllocHandle.readComplete();
            channelPipelinePipeline.fireChannelReadComplete();
            if (z) {
                closeOnRead(channelPipelinePipeline);
            }
            if (AbstractNioByteChannel.this.readPending || channelConfigConfig.isAutoRead()) {
            }
        }
    }

    protected final int doWrite0(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        if (channelOutboundBuffer.current() == null) {
            return 0;
        }
        return doWriteInternal(channelOutboundBuffer, channelOutboundBuffer.current());
    }

    private int doWriteInternal(ChannelOutboundBuffer channelOutboundBuffer, Object obj) throws Exception {
        if (obj instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) obj;
            if (!byteBuf.isReadable()) {
                channelOutboundBuffer.remove();
                return 0;
            }
            int iDoWriteBytes = doWriteBytes(byteBuf);
            if (iDoWriteBytes <= 0) {
                return Integer.MAX_VALUE;
            }
            channelOutboundBuffer.progress(iDoWriteBytes);
            if (!byteBuf.isReadable()) {
                channelOutboundBuffer.remove();
            }
            return 1;
        }
        if (obj instanceof FileRegion) {
            FileRegion fileRegion = (FileRegion) obj;
            if (fileRegion.transferred() >= fileRegion.count()) {
                channelOutboundBuffer.remove();
                return 0;
            }
            long jDoWriteFileRegion = doWriteFileRegion(fileRegion);
            if (jDoWriteFileRegion <= 0) {
                return Integer.MAX_VALUE;
            }
            channelOutboundBuffer.progress(jDoWriteFileRegion);
            if (fileRegion.transferred() >= fileRegion.count()) {
                channelOutboundBuffer.remove();
            }
            return 1;
        }
        throw new Error();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        int writeSpinCount = config().getWriteSpinCount();
        do {
            Object objCurrent = channelOutboundBuffer.current();
            if (objCurrent == null) {
                clearOpWrite();
                return;
            }
            writeSpinCount -= doWriteInternal(channelOutboundBuffer, objCurrent);
        } while (writeSpinCount > 0);
        incompleteWrite(writeSpinCount < 0);
    }

    @Override // io.netty.channel.AbstractChannel
    protected final Object filterOutboundMessage(Object obj) {
        if (obj instanceof ByteBuf) {
            ByteBuf byteBuf = (ByteBuf) obj;
            return byteBuf.isDirect() ? obj : newDirectBuffer(byteBuf);
        }
        if (obj instanceof FileRegion) {
            return obj;
        }
        throw new UnsupportedOperationException("unsupported message type: " + StringUtil.simpleClassName(obj) + EXPECTED_TYPES);
    }

    protected final void incompleteWrite(boolean z) {
        if (z) {
            setOpWrite();
        } else {
            clearOpWrite();
            eventLoop().execute(this.flushTask);
        }
    }

    protected final void setOpWrite() {
        SelectionKey selectionKey = selectionKey();
        if (selectionKey.isValid()) {
            int iInterestOps = selectionKey.interestOps();
            if ((iInterestOps & 4) == 0) {
                selectionKey.interestOps(iInterestOps | 4);
            }
        }
    }

    protected final void clearOpWrite() {
        SelectionKey selectionKey = selectionKey();
        if (selectionKey.isValid()) {
            int iInterestOps = selectionKey.interestOps();
            if ((iInterestOps & 4) != 0) {
                selectionKey.interestOps(iInterestOps & (-5));
            }
        }
    }
}
