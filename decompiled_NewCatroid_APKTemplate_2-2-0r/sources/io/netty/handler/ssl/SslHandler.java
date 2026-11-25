package io.netty.handler.ssl;

import com.huawei.hms.ml.language.common.utils.LanguageCodeUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.ByteBufUtil;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.AbstractCoalescingBufferQueue;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandler;
import io.netty.channel.ChannelPromise;
import io.netty.channel.ChannelPromiseNotifier;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.codec.UnsupportedMessageTypeException;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.ReferenceCounted;
import io.netty.util.concurrent.DefaultPromise;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.FutureListener;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ImmediateExecutor;
import io.netty.util.concurrent.Promise;
import io.netty.util.concurrent.PromiseNotifier;
import io.netty.util.concurrent.ScheduledFuture;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLEngineResult;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import org.jetbrains.kotlin.konan.util.DependencyDownloader;

/* JADX INFO: loaded from: classes11.dex */
public class SslHandler extends ByteToMessageDecoder implements ChannelOutboundHandler {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int MAX_PLAINTEXT_LENGTH = 16384;
    private boolean closeNotify;
    private volatile long closeNotifyFlushTimeoutMillis;
    private volatile long closeNotifyReadTimeoutMillis;
    private volatile ChannelHandlerContext ctx;
    private final Executor delegatedTaskExecutor;
    private final SSLEngine engine;
    private final SslEngineType engineType;
    private boolean firedChannelRead;
    private boolean flushedBeforeHandshake;
    private Promise<Channel> handshakePromise;
    private boolean handshakeStarted;
    private volatile long handshakeTimeoutMillis;
    private final boolean jdkCompatibilityMode;
    private boolean needsFlush;
    private boolean outboundClosed;
    private int packetLength;
    private SslHandlerCoalescingBufferQueue pendingUnencryptedWrites;
    private boolean processTask;
    private boolean readDuringHandshake;
    private boolean sentFirstMessage;
    private final ByteBuffer[] singleBuffer;
    private final LazyChannelPromise sslClosePromise;
    private final boolean startTls;
    volatile int wrapDataSize;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) SslHandler.class);
    private static final Pattern IGNORABLE_CLASS_IN_STACK = Pattern.compile("^.*(?:Socket|Datagram|Sctp|Udt)Channel.*$");
    private static final Pattern IGNORABLE_ERROR_MESSAGE = Pattern.compile("^.*(?:connection.*(?:reset|closed|abort|broken)|broken.*pipe).*$", 2);

    private enum SslEngineType {
        TCNATIVE(true, ByteToMessageDecoder.COMPOSITE_CUMULATOR) { // from class: io.netty.handler.ssl.SslHandler.SslEngineType.1
            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
                SSLEngineResult sSLEngineResultUnwrap;
                int iNioBufferCount = byteBuf.nioBufferCount();
                int iWriterIndex = byteBuf2.writerIndex();
                if (iNioBufferCount > 1) {
                    ReferenceCountedOpenSslEngine referenceCountedOpenSslEngine = (ReferenceCountedOpenSslEngine) sslHandler.engine;
                    try {
                        sslHandler.singleBuffer[0] = SslHandler.toByteBuffer(byteBuf2, iWriterIndex, byteBuf2.writableBytes());
                        sSLEngineResultUnwrap = referenceCountedOpenSslEngine.unwrap(byteBuf.nioBuffers(i, i2), sslHandler.singleBuffer);
                        sslHandler.singleBuffer[0] = null;
                    } catch (Throwable th) {
                        sslHandler.singleBuffer[0] = null;
                        throw th;
                    }
                } else {
                    sSLEngineResultUnwrap = sslHandler.engine.unwrap(SslHandler.toByteBuffer(byteBuf, i, i2), SslHandler.toByteBuffer(byteBuf2, iWriterIndex, byteBuf2.writableBytes()));
                }
                byteBuf2.writerIndex(iWriterIndex + sSLEngineResultUnwrap.bytesProduced());
                return sSLEngineResultUnwrap;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            ByteBuf allocateWrapBuffer(SslHandler sslHandler, ByteBufAllocator byteBufAllocator, int i, int i2) {
                return byteBufAllocator.directBuffer(((ReferenceCountedOpenSslEngine) sslHandler.engine).calculateMaxLengthForWrap(i, i2));
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            int calculatePendingData(SslHandler sslHandler, int i) {
                int iSslPending = ((ReferenceCountedOpenSslEngine) sslHandler.engine).sslPending();
                return iSslPending > 0 ? iSslPending : i;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            boolean jdkCompatibilityMode(SSLEngine sSLEngine) {
                return ((ReferenceCountedOpenSslEngine) sSLEngine).jdkCompatibilityMode;
            }
        },
        CONSCRYPT(1 == true ? 1 : 0, ByteToMessageDecoder.COMPOSITE_CUMULATOR) { // from class: io.netty.handler.ssl.SslHandler.SslEngineType.2
            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            int calculatePendingData(SslHandler sslHandler, int i) {
                return i;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            boolean jdkCompatibilityMode(SSLEngine sSLEngine) {
                return true;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
                SSLEngineResult sSLEngineResultUnwrap;
                int iNioBufferCount = byteBuf.nioBufferCount();
                int iWriterIndex = byteBuf2.writerIndex();
                if (iNioBufferCount > 1) {
                    try {
                        sslHandler.singleBuffer[0] = SslHandler.toByteBuffer(byteBuf2, iWriterIndex, byteBuf2.writableBytes());
                        sSLEngineResultUnwrap = ((ConscryptAlpnSslEngine) sslHandler.engine).unwrap(byteBuf.nioBuffers(i, i2), sslHandler.singleBuffer);
                        sslHandler.singleBuffer[0] = null;
                    } catch (Throwable th) {
                        sslHandler.singleBuffer[0] = null;
                        throw th;
                    }
                } else {
                    sSLEngineResultUnwrap = sslHandler.engine.unwrap(SslHandler.toByteBuffer(byteBuf, i, i2), SslHandler.toByteBuffer(byteBuf2, iWriterIndex, byteBuf2.writableBytes()));
                }
                byteBuf2.writerIndex(iWriterIndex + sSLEngineResultUnwrap.bytesProduced());
                return sSLEngineResultUnwrap;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            ByteBuf allocateWrapBuffer(SslHandler sslHandler, ByteBufAllocator byteBufAllocator, int i, int i2) {
                return byteBufAllocator.directBuffer(((ConscryptAlpnSslEngine) sslHandler.engine).calculateOutNetBufSize(i, i2));
            }
        },
        JDK(0 == true ? 1 : 0, ByteToMessageDecoder.MERGE_CUMULATOR) { // from class: io.netty.handler.ssl.SslHandler.SslEngineType.3
            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            int calculatePendingData(SslHandler sslHandler, int i) {
                return i;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            boolean jdkCompatibilityMode(SSLEngine sSLEngine) {
                return true;
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException {
                int iPosition;
                int iWriterIndex = byteBuf2.writerIndex();
                ByteBuffer byteBuffer = SslHandler.toByteBuffer(byteBuf, i, i2);
                int iPosition2 = byteBuffer.position();
                SSLEngineResult sSLEngineResultUnwrap = sslHandler.engine.unwrap(byteBuffer, SslHandler.toByteBuffer(byteBuf2, iWriterIndex, byteBuf2.writableBytes()));
                byteBuf2.writerIndex(iWriterIndex + sSLEngineResultUnwrap.bytesProduced());
                return (sSLEngineResultUnwrap.bytesConsumed() != 0 || (iPosition = byteBuffer.position() - iPosition2) == sSLEngineResultUnwrap.bytesConsumed()) ? sSLEngineResultUnwrap : new SSLEngineResult(sSLEngineResultUnwrap.getStatus(), sSLEngineResultUnwrap.getHandshakeStatus(), iPosition, sSLEngineResultUnwrap.bytesProduced());
            }

            @Override // io.netty.handler.ssl.SslHandler.SslEngineType
            ByteBuf allocateWrapBuffer(SslHandler sslHandler, ByteBufAllocator byteBufAllocator, int i, int i2) {
                return byteBufAllocator.heapBuffer(sslHandler.engine.getSession().getPacketBufferSize());
            }
        };

        final ByteToMessageDecoder.Cumulator cumulator;
        final boolean wantsDirectBuffer;

        abstract ByteBuf allocateWrapBuffer(SslHandler sslHandler, ByteBufAllocator byteBufAllocator, int i, int i2);

        abstract int calculatePendingData(SslHandler sslHandler, int i);

        abstract boolean jdkCompatibilityMode(SSLEngine sSLEngine);

        abstract SSLEngineResult unwrap(SslHandler sslHandler, ByteBuf byteBuf, int i, int i2, ByteBuf byteBuf2) throws SSLException;

        static SslEngineType forEngine(SSLEngine sSLEngine) {
            if (sSLEngine instanceof ReferenceCountedOpenSslEngine) {
                return TCNATIVE;
            }
            return sSLEngine instanceof ConscryptAlpnSslEngine ? CONSCRYPT : JDK;
        }

        SslEngineType(boolean z, ByteToMessageDecoder.Cumulator cumulator) {
            this.wantsDirectBuffer = z;
            this.cumulator = cumulator;
        }
    }

    public SslHandler(SSLEngine sSLEngine) {
        this(sSLEngine, false);
    }

    public SslHandler(SSLEngine sSLEngine, boolean z) {
        this(sSLEngine, z, ImmediateExecutor.INSTANCE);
    }

    public SslHandler(SSLEngine sSLEngine, Executor executor) {
        this(sSLEngine, false, executor);
    }

    public SslHandler(SSLEngine sSLEngine, boolean z, Executor executor) {
        this.singleBuffer = new ByteBuffer[1];
        this.handshakePromise = new LazyChannelPromise();
        this.sslClosePromise = new LazyChannelPromise();
        this.handshakeTimeoutMillis = 10000L;
        this.closeNotifyFlushTimeoutMillis = DependencyDownloader.DEFAULT_ATTEMPT_INTERVAL_MS;
        this.wrapDataSize = 16384;
        this.engine = (SSLEngine) ObjectUtil.checkNotNull(sSLEngine, "engine");
        this.delegatedTaskExecutor = (Executor) ObjectUtil.checkNotNull(executor, "delegatedTaskExecutor");
        SslEngineType sslEngineTypeForEngine = SslEngineType.forEngine(sSLEngine);
        this.engineType = sslEngineTypeForEngine;
        this.startTls = z;
        this.jdkCompatibilityMode = sslEngineTypeForEngine.jdkCompatibilityMode(sSLEngine);
        setCumulator(sslEngineTypeForEngine.cumulator);
    }

    public long getHandshakeTimeoutMillis() {
        return this.handshakeTimeoutMillis;
    }

    public void setHandshakeTimeout(long j, TimeUnit timeUnit) {
        ObjectUtil.checkNotNull(timeUnit, "unit");
        setHandshakeTimeoutMillis(timeUnit.toMillis(j));
    }

    public void setHandshakeTimeoutMillis(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("handshakeTimeoutMillis: " + j + " (expected: >= 0)");
        }
        this.handshakeTimeoutMillis = j;
    }

    public final void setWrapDataSize(int i) {
        this.wrapDataSize = i;
    }

    @Deprecated
    public long getCloseNotifyTimeoutMillis() {
        return getCloseNotifyFlushTimeoutMillis();
    }

    @Deprecated
    public void setCloseNotifyTimeout(long j, TimeUnit timeUnit) {
        setCloseNotifyFlushTimeout(j, timeUnit);
    }

    @Deprecated
    public void setCloseNotifyTimeoutMillis(long j) {
        setCloseNotifyFlushTimeoutMillis(j);
    }

    public final long getCloseNotifyFlushTimeoutMillis() {
        return this.closeNotifyFlushTimeoutMillis;
    }

    public final void setCloseNotifyFlushTimeout(long j, TimeUnit timeUnit) {
        setCloseNotifyFlushTimeoutMillis(timeUnit.toMillis(j));
    }

    public final void setCloseNotifyFlushTimeoutMillis(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("closeNotifyFlushTimeoutMillis: " + j + " (expected: >= 0)");
        }
        this.closeNotifyFlushTimeoutMillis = j;
    }

    public final long getCloseNotifyReadTimeoutMillis() {
        return this.closeNotifyReadTimeoutMillis;
    }

    public final void setCloseNotifyReadTimeout(long j, TimeUnit timeUnit) {
        setCloseNotifyReadTimeoutMillis(timeUnit.toMillis(j));
    }

    public final void setCloseNotifyReadTimeoutMillis(long j) {
        if (j < 0) {
            throw new IllegalArgumentException("closeNotifyReadTimeoutMillis: " + j + " (expected: >= 0)");
        }
        this.closeNotifyReadTimeoutMillis = j;
    }

    public SSLEngine engine() {
        return this.engine;
    }

    public String applicationProtocol() {
        Object objEngine = engine();
        if (objEngine instanceof ApplicationProtocolAccessor) {
            return ((ApplicationProtocolAccessor) objEngine).getNegotiatedApplicationProtocol();
        }
        return null;
    }

    public Future<Channel> handshakeFuture() {
        return this.handshakePromise;
    }

    @Deprecated
    public ChannelFuture close() {
        return closeOutbound();
    }

    @Deprecated
    public ChannelFuture close(ChannelPromise channelPromise) {
        return closeOutbound(channelPromise);
    }

    public ChannelFuture closeOutbound() {
        return closeOutbound(this.ctx.newPromise());
    }

    public ChannelFuture closeOutbound(final ChannelPromise channelPromise) {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext.executor().inEventLoop()) {
            closeOutbound0(channelPromise);
        } else {
            channelHandlerContext.executor().execute(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    SslHandler.this.closeOutbound0(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void closeOutbound0(ChannelPromise channelPromise) {
        this.outboundClosed = true;
        this.engine.closeOutbound();
        try {
            flush(this.ctx, channelPromise);
        } catch (Exception e) {
            if (channelPromise.tryFailure(e)) {
                return;
            }
            logger.warn("{} flush() raised a masked exception.", this.ctx.channel(), e);
        }
    }

    public Future<Channel> sslCloseFuture() {
        return this.sslClosePromise;
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    public void handlerRemoved0(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.releaseAndFailAll(channelHandlerContext, new ChannelException("Pending write on removal of SslHandler"));
        }
        this.pendingUnencryptedWrites = null;
        Object obj = this.engine;
        if (obj instanceof ReferenceCounted) {
            ((ReferenceCounted) obj).release();
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void bind(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void connect(ChannelHandlerContext channelHandlerContext, SocketAddress socketAddress, SocketAddress socketAddress2, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void deregister(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void disconnect(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        closeOutboundAndChannel(channelHandlerContext, channelPromise, true);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void close(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        closeOutboundAndChannel(channelHandlerContext, channelPromise, false);
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void read(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.handshakePromise.isDone()) {
            this.readDuringHandshake = true;
        }
        channelHandlerContext.read();
    }

    private static IllegalStateException newPendingWritesNullException() {
        return new IllegalStateException("pendingUnencryptedWrites is null, handlerRemoved0 called?");
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void write(ChannelHandlerContext channelHandlerContext, Object obj, ChannelPromise channelPromise) throws Exception {
        if (!(obj instanceof ByteBuf)) {
            UnsupportedMessageTypeException unsupportedMessageTypeException = new UnsupportedMessageTypeException(obj, (Class<?>[]) new Class[]{ByteBuf.class});
            ReferenceCountUtil.safeRelease(obj);
            channelPromise.setFailure((Throwable) unsupportedMessageTypeException);
        } else {
            SslHandlerCoalescingBufferQueue sslHandlerCoalescingBufferQueue = this.pendingUnencryptedWrites;
            if (sslHandlerCoalescingBufferQueue == null) {
                ReferenceCountUtil.safeRelease(obj);
                channelPromise.setFailure((Throwable) newPendingWritesNullException());
            } else {
                sslHandlerCoalescingBufferQueue.add((ByteBuf) obj, channelPromise);
            }
        }
    }

    @Override // io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.startTls && !this.sentFirstMessage) {
            this.sentFirstMessage = true;
            this.pendingUnencryptedWrites.writeAndRemoveAll(channelHandlerContext);
            forceFlush(channelHandlerContext);
            startHandshakeProcessing();
            return;
        }
        if (this.processTask) {
            return;
        }
        try {
            wrapAndFlush(channelHandlerContext);
        } catch (Throwable th) {
            setHandshakeFailure(channelHandlerContext, th);
            PlatformDependent.throwException(th);
        }
    }

    private void wrapAndFlush(ChannelHandlerContext channelHandlerContext) throws SSLException {
        if (this.pendingUnencryptedWrites.isEmpty()) {
            this.pendingUnencryptedWrites.add(Unpooled.EMPTY_BUFFER, channelHandlerContext.newPromise());
        }
        if (!this.handshakePromise.isDone()) {
            this.flushedBeforeHandshake = true;
        }
        try {
            wrap(channelHandlerContext, false);
        } finally {
            forceFlush(channelHandlerContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Code duplicated, block: B:101:0x004e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:102:0x00e3 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:103:0x0097 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:104:0x008e A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:105:0x00e9 A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:30:0x006e A[Catch: all -> 0x00f4, TRY_ENTER, TryCatch #1 {all -> 0x00f4, blocks: (B:19:0x0040, B:21:0x004e, B:30:0x006e, B:32:0x0074, B:33:0x007b), top: B:85:0x0040 }] */
    /* JADX WARN: Code duplicated, block: B:32:0x0074 A[Catch: all -> 0x00f4, TryCatch #1 {all -> 0x00f4, blocks: (B:19:0x0040, B:21:0x004e, B:30:0x006e, B:32:0x0074, B:33:0x007b), top: B:85:0x0040 }] */
    /* JADX WARN: Code duplicated, block: B:33:0x007b A[Catch: all -> 0x00f4, TRY_LEAVE, TryCatch #1 {all -> 0x00f4, blocks: (B:19:0x0040, B:21:0x004e, B:30:0x006e, B:32:0x0074, B:33:0x007b), top: B:85:0x0040 }] */
    /* JADX WARN: Code duplicated, block: B:39:0x0091  */
    /* JADX WARN: Code duplicated, block: B:41:0x0094  */
    /* JADX WARN: Code duplicated, block: B:45:0x009a  */
    /* JADX WARN: Code duplicated, block: B:46:0x00a1 A[Catch: all -> 0x00f0, TryCatch #0 {all -> 0x00f0, blocks: (B:24:0x005c, B:35:0x007f, B:46:0x00a1, B:47:0x00bd, B:49:0x00c1, B:48:0x00be, B:61:0x00e3), top: B:84:0x007f }] */
    /* JADX WARN: Code duplicated, block: B:48:0x00be A[Catch: all -> 0x00f0, TryCatch #0 {all -> 0x00f0, blocks: (B:24:0x005c, B:35:0x007f, B:46:0x00a1, B:47:0x00bd, B:49:0x00c1, B:48:0x00be, B:61:0x00e3), top: B:84:0x007f }] */
    /* JADX WARN: Code duplicated, block: B:52:0x00ca  */
    /* JADX WARN: Code duplicated, block: B:53:0x00cd  */
    /* JADX WARN: Code duplicated, block: B:64:0x00ec  */
    /* JADX WARN: Code duplicated, block: B:81:0x0110  */
    public void wrap(ChannelHandlerContext channelHandlerContext, boolean z) throws Throwable {
        ByteBuf byteBuf;
        ChannelPromise channelPromise;
        ByteBuf byteBuf2;
        ChannelPromise channelPromise2;
        ByteBuf byteBufRemoveFirst;
        ByteBuf byteBuf3;
        SSLEngineResult sSLEngineResultWrap;
        ChannelPromise channelPromise3;
        boolean z2;
        ChannelPromise channelPromise4;
        int i;
        ByteBuf byteBuf4;
        ByteBuf byteBuf5;
        ByteBufAllocator byteBufAllocatorAlloc = channelHandlerContext.alloc();
        ByteBuf byteBuf6 = null;
        try {
            int i2 = this.wrapDataSize;
            ByteBuf byteBufAllocateOutNetBuf = null;
            ChannelPromise channelPromiseNewPromise = null;
            while (true) {
                try {
                    if (channelHandlerContext.isRemoved()) {
                        byteBuf2 = byteBufAllocateOutNetBuf;
                        channelPromise2 = channelPromiseNewPromise;
                        break;
                    }
                    channelPromiseNewPromise = channelHandlerContext.newPromise();
                    if (i2 > 0) {
                        byteBufRemoveFirst = this.pendingUnencryptedWrites.remove(byteBufAllocatorAlloc, i2, channelPromiseNewPromise);
                    } else {
                        byteBufRemoveFirst = this.pendingUnencryptedWrites.removeFirst(channelPromiseNewPromise);
                    }
                    if (byteBufRemoveFirst == null) {
                        channelPromise2 = channelPromiseNewPromise;
                        byteBuf6 = byteBufRemoveFirst;
                        byteBuf2 = byteBufAllocateOutNetBuf;
                        break;
                    }
                    if (byteBufAllocateOutNetBuf == null) {
                        try {
                            byteBufAllocateOutNetBuf = allocateOutNetBuf(channelHandlerContext, byteBufRemoveFirst.readableBytes(), byteBufRemoveFirst.nioBufferCount());
                            byteBuf3 = byteBufAllocateOutNetBuf;
                            try {
                                sSLEngineResultWrap = wrap(byteBufAllocatorAlloc, this.engine, byteBufRemoveFirst, byteBuf3);
                                if (sSLEngineResultWrap.getStatus() == SSLEngineResult.Status.CLOSED) {
                                    byteBufRemoveFirst.release();
                                    try {
                                        SSLException sSLException = new SSLException("SSLEngine closed already");
                                        channelPromiseNewPromise.tryFailure(sSLException);
                                        this.pendingUnencryptedWrites.releaseAndFailAll(channelHandlerContext, sSLException);
                                        channelPromise3 = null;
                                        z2 = false;
                                    } catch (Throwable th) {
                                        th = th;
                                        byteBuf = byteBuf3;
                                        channelPromise = channelPromiseNewPromise;
                                        if (byteBuf6 != null) {
                                            byteBuf6.release();
                                        }
                                        finishWrap(channelHandlerContext, byteBuf, channelPromise, z, false);
                                        throw th;
                                    }
                                } else {
                                    if (byteBufRemoveFirst.isReadable()) {
                                        this.pendingUnencryptedWrites.addFirst(byteBufRemoveFirst, channelPromiseNewPromise);
                                        channelPromise4 = null;
                                    } else {
                                        byteBufRemoveFirst.release();
                                        channelPromise4 = channelPromiseNewPromise;
                                    }
                                    try {
                                        i = AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[sSLEngineResultWrap.getHandshakeStatus().ordinal()];
                                        if (i != 1) {
                                            if (!runDelegatedTasks(z)) {
                                                byteBuf2 = byteBuf3;
                                                channelPromise2 = channelPromise4;
                                                break;
                                            } else {
                                                byteBufAllocateOutNetBuf = byteBuf3;
                                                channelPromiseNewPromise = channelPromise4;
                                            }
                                        } else {
                                            try {
                                                try {
                                                    if (i != 2) {
                                                        setHandshakeSuccess();
                                                    } else {
                                                        if (i != 3) {
                                                            if (i != 4) {
                                                                if (i == 5) {
                                                                    throw new IllegalStateException("Unknown handshake status: " + sSLEngineResultWrap.getHandshakeStatus());
                                                                }
                                                                z2 = true;
                                                                channelPromise3 = channelPromise4;
                                                            }
                                                        }
                                                        if (byteBuf3.isReadable()) {
                                                            byteBuf5 = byteBuf3;
                                                            byteBuf4 = null;
                                                        } else {
                                                            byteBuf4 = byteBuf3;
                                                            byteBuf5 = null;
                                                        }
                                                        finishWrap(channelHandlerContext, byteBuf5, channelPromise4, z, false);
                                                        channelPromiseNewPromise = null;
                                                        byteBufAllocateOutNetBuf = byteBuf4;
                                                    }
                                                    finishWrap(channelHandlerContext, byteBuf5, channelPromise4, z, false);
                                                    channelPromiseNewPromise = null;
                                                    byteBufAllocateOutNetBuf = byteBuf4;
                                                } catch (Throwable th2) {
                                                    th = th2;
                                                    channelPromise = null;
                                                    byteBuf = byteBuf4;
                                                }
                                                if (byteBuf3.isReadable()) {
                                                    byteBuf5 = byteBuf3;
                                                    byteBuf4 = null;
                                                } else {
                                                    byteBuf4 = byteBuf3;
                                                    byteBuf5 = null;
                                                }
                                            } catch (Throwable th3) {
                                                th = th3;
                                                byteBuf = byteBuf3;
                                                channelPromise = null;
                                            }
                                            setHandshakeSuccessIfStillHandshaking();
                                        }
                                    } catch (Throwable th4) {
                                        th = th4;
                                        byteBuf = byteBuf3;
                                        channelPromise = channelPromise4;
                                    }
                                }
                                finishWrap(channelHandlerContext, byteBuf3, channelPromise3, z, z2);
                                return;
                            } catch (Throwable th5) {
                                th = th5;
                                byteBuf6 = byteBufRemoveFirst;
                            }
                        } catch (Throwable th6) {
                            th = th6;
                            channelPromise = channelPromiseNewPromise;
                            byteBuf6 = byteBufRemoveFirst;
                            byteBuf = byteBufAllocateOutNetBuf;
                        }
                    } else {
                        byteBuf3 = byteBufAllocateOutNetBuf;
                        sSLEngineResultWrap = wrap(byteBufAllocatorAlloc, this.engine, byteBufRemoveFirst, byteBuf3);
                        if (sSLEngineResultWrap.getStatus() == SSLEngineResult.Status.CLOSED) {
                            byteBufRemoveFirst.release();
                            SSLException sSLException2 = new SSLException("SSLEngine closed already");
                            channelPromiseNewPromise.tryFailure(sSLException2);
                            this.pendingUnencryptedWrites.releaseAndFailAll(channelHandlerContext, sSLException2);
                            channelPromise3 = null;
                            z2 = false;
                        } else {
                            if (byteBufRemoveFirst.isReadable()) {
                                this.pendingUnencryptedWrites.addFirst(byteBufRemoveFirst, channelPromiseNewPromise);
                                channelPromise4 = null;
                            } else {
                                byteBufRemoveFirst.release();
                                channelPromise4 = channelPromiseNewPromise;
                            }
                            i = AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[sSLEngineResultWrap.getHandshakeStatus().ordinal()];
                            if (i != 1) {
                                if (!runDelegatedTasks(z)) {
                                    byteBuf2 = byteBuf3;
                                    channelPromise2 = channelPromise4;
                                    break;
                                } else {
                                    byteBufAllocateOutNetBuf = byteBuf3;
                                    channelPromiseNewPromise = channelPromise4;
                                }
                            } else {
                                if (i != 2) {
                                    setHandshakeSuccess();
                                } else {
                                    if (i != 3) {
                                        if (i != 4) {
                                            if (i == 5) {
                                                throw new IllegalStateException("Unknown handshake status: " + sSLEngineResultWrap.getHandshakeStatus());
                                            }
                                            z2 = true;
                                            channelPromise3 = channelPromise4;
                                        }
                                    }
                                    if (byteBuf3.isReadable()) {
                                        byteBuf5 = byteBuf3;
                                        byteBuf4 = null;
                                    } else {
                                        byteBuf4 = byteBuf3;
                                        byteBuf5 = null;
                                    }
                                    finishWrap(channelHandlerContext, byteBuf5, channelPromise4, z, false);
                                    channelPromiseNewPromise = null;
                                    byteBufAllocateOutNetBuf = byteBuf4;
                                }
                                setHandshakeSuccessIfStillHandshaking();
                                if (byteBuf3.isReadable()) {
                                    byteBuf5 = byteBuf3;
                                    byteBuf4 = null;
                                } else {
                                    byteBuf4 = byteBuf3;
                                    byteBuf5 = null;
                                }
                                finishWrap(channelHandlerContext, byteBuf5, channelPromise4, z, false);
                                channelPromiseNewPromise = null;
                                byteBufAllocateOutNetBuf = byteBuf4;
                            }
                        }
                        finishWrap(channelHandlerContext, byteBuf3, channelPromise3, z, z2);
                        return;
                    }
                    if (byteBuf6 != null) {
                        byteBuf6.release();
                    }
                    finishWrap(channelHandlerContext, byteBuf, channelPromise, z, false);
                    throw th;
                } catch (Throwable th7) {
                    th = th7;
                    byteBuf = byteBufAllocateOutNetBuf;
                }
            }
            if (byteBuf6 != null) {
                byteBuf6.release();
            }
            finishWrap(channelHandlerContext, byteBuf2, channelPromise2, z, false);
        } catch (Throwable th8) {
            th = th8;
            byteBuf = null;
            channelPromise = null;
        }
    }

    private void finishWrap(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, ChannelPromise channelPromise, boolean z, boolean z2) {
        if (byteBuf == null) {
            byteBuf = Unpooled.EMPTY_BUFFER;
        } else if (!byteBuf.isReadable()) {
            byteBuf.release();
            byteBuf = Unpooled.EMPTY_BUFFER;
        }
        if (channelPromise != null) {
            channelHandlerContext.write(byteBuf, channelPromise);
        } else {
            channelHandlerContext.write(byteBuf);
        }
        if (z) {
            this.needsFlush = true;
        }
        if (z2) {
            readIfNeeded(channelHandlerContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean wrapNonAppData(final ChannelHandlerContext channelHandlerContext, boolean z) throws SSLException {
        ByteBufAllocator byteBufAllocatorAlloc = channelHandlerContext.alloc();
        ByteBuf byteBufAllocateOutNetBuf = null;
        while (!channelHandlerContext.isRemoved()) {
            try {
                if (byteBufAllocateOutNetBuf == null) {
                    byteBufAllocateOutNetBuf = allocateOutNetBuf(channelHandlerContext, 2048, 1);
                }
                SSLEngineResult sSLEngineResultWrap = wrap(byteBufAllocatorAlloc, this.engine, Unpooled.EMPTY_BUFFER, byteBufAllocateOutNetBuf);
                if (sSLEngineResultWrap.bytesProduced() > 0) {
                    channelHandlerContext.write(byteBufAllocateOutNetBuf).addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.ssl.SslHandler.2
                        @Override // io.netty.util.concurrent.GenericFutureListener
                        public void operationComplete(ChannelFuture channelFuture) {
                            Throwable thCause = channelFuture.cause();
                            if (thCause != null) {
                                SslHandler.this.setHandshakeFailureTransportFailure(channelHandlerContext, thCause);
                            }
                        }
                    });
                    if (z) {
                        this.needsFlush = true;
                    }
                    byteBufAllocateOutNetBuf = null;
                }
                SSLEngineResult.HandshakeStatus handshakeStatus = sSLEngineResultWrap.getHandshakeStatus();
                int i = AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[handshakeStatus.ordinal()];
                if (i == 1) {
                    if (!runDelegatedTasks(z)) {
                        break;
                    }
                    break;
                }
                if (i == 2) {
                    setHandshakeSuccess();
                    if (byteBufAllocateOutNetBuf != null) {
                        byteBufAllocateOutNetBuf.release();
                    }
                    return false;
                }
                if (i == 3) {
                    setHandshakeSuccessIfStillHandshaking();
                    if (!z) {
                        unwrapNonAppData(channelHandlerContext);
                    }
                    if (byteBufAllocateOutNetBuf != null) {
                        byteBufAllocateOutNetBuf.release();
                    }
                    return true;
                }
                if (i != 4) {
                    if (i != 5) {
                        throw new IllegalStateException("Unknown handshake status: " + sSLEngineResultWrap.getHandshakeStatus());
                    }
                    if (z) {
                        if (byteBufAllocateOutNetBuf != null) {
                            byteBufAllocateOutNetBuf.release();
                        }
                        return false;
                    }
                    unwrapNonAppData(channelHandlerContext);
                }
                if ((sSLEngineResultWrap.bytesProduced() == 0 && handshakeStatus != SSLEngineResult.HandshakeStatus.NEED_TASK) || (sSLEngineResultWrap.bytesConsumed() == 0 && sSLEngineResultWrap.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING)) {
                    break;
                    break;
                }
            } catch (Throwable th) {
                if (byteBufAllocateOutNetBuf != null) {
                    byteBufAllocateOutNetBuf.release();
                }
                throw th;
            }
        }
        if (byteBufAllocateOutNetBuf != null) {
            byteBufAllocateOutNetBuf.release();
        }
        return false;
    }

    private SSLEngineResult wrap(ByteBufAllocator byteBufAllocator, SSLEngine sSLEngine, ByteBuf byteBuf, ByteBuf byteBuf2) throws Throwable {
        ByteBuf byteBufDirectBuffer;
        ByteBuffer[] byteBufferArrNioBuffers;
        SSLEngineResult sSLEngineResultWrap;
        try {
            int i = byteBuf.readerIndex();
            int i2 = byteBuf.readableBytes();
            if (byteBuf.isDirect() || !this.engineType.wantsDirectBuffer) {
                if (!(byteBuf instanceof CompositeByteBuf) && byteBuf.nioBufferCount() == 1) {
                    ByteBuffer[] byteBufferArr = this.singleBuffer;
                    byteBufferArr[0] = byteBuf.internalNioBuffer(i, i2);
                    byteBufferArrNioBuffers = byteBufferArr;
                } else {
                    byteBufferArrNioBuffers = byteBuf.nioBuffers();
                }
                byteBufDirectBuffer = null;
            } else {
                byteBufDirectBuffer = byteBufAllocator.directBuffer(i2);
                try {
                    byteBufDirectBuffer.writeBytes(byteBuf, i, i2);
                    byteBufferArrNioBuffers = this.singleBuffer;
                    byteBufferArrNioBuffers[0] = byteBufDirectBuffer.internalNioBuffer(byteBufDirectBuffer.readerIndex(), i2);
                } catch (Throwable th) {
                    th = th;
                    this.singleBuffer[0] = null;
                    if (byteBufDirectBuffer != null) {
                        byteBufDirectBuffer.release();
                    }
                    throw th;
                }
            }
            while (true) {
                sSLEngineResultWrap = sSLEngine.wrap(byteBufferArrNioBuffers, byteBuf2.nioBuffer(byteBuf2.writerIndex(), byteBuf2.writableBytes()));
                byteBuf.skipBytes(sSLEngineResultWrap.bytesConsumed());
                byteBuf2.writerIndex(byteBuf2.writerIndex() + sSLEngineResultWrap.bytesProduced());
                if (AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[sSLEngineResultWrap.getStatus().ordinal()] != 1) {
                    break;
                }
                byteBuf2.ensureWritable(sSLEngine.getSession().getPacketBufferSize());
            }
            this.singleBuffer[0] = null;
            if (byteBufDirectBuffer != null) {
                byteBufDirectBuffer.release();
            }
            return sSLEngineResultWrap;
        } catch (Throwable th2) {
            th = th2;
            byteBufDirectBuffer = null;
        }
    }

    /* JADX INFO: renamed from: io.netty.handler.ssl.SslHandler$9, reason: invalid class name */
    static /* synthetic */ class AnonymousClass9 {
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus;
        static final /* synthetic */ int[] $SwitchMap$javax$net$ssl$SSLEngineResult$Status;

        static {
            int[] iArr = new int[SSLEngineResult.Status.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$Status = iArr;
            try {
                iArr[SSLEngineResult.Status.BUFFER_OVERFLOW.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$Status[SSLEngineResult.Status.CLOSED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            int[] iArr2 = new int[SSLEngineResult.HandshakeStatus.values().length];
            $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus = iArr2;
            try {
                iArr2[SSLEngineResult.HandshakeStatus.NEED_TASK.ordinal()] = 1;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.FINISHED.ordinal()] = 2;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING.ordinal()] = 3;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_WRAP.ordinal()] = 4;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SSLEngineResult.HandshakeStatus.NEED_UNWRAP.ordinal()] = 5;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
        ClosedChannelException closedChannelException = new ClosedChannelException();
        setHandshakeFailure(channelHandlerContext, closedChannelException, !this.outboundClosed, this.handshakeStarted, false);
        notifyClosePromise(closedChannelException);
        super.channelInactive(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler, io.netty.channel.ChannelInboundHandler
    public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable th) throws Exception {
        if (ignoreException(th)) {
            InternalLogger internalLogger = logger;
            if (internalLogger.isDebugEnabled()) {
                internalLogger.debug("{} Swallowing a harmless 'connection reset by peer / broken pipe' error that occurred while writing close_notify in response to the peer's close_notify", channelHandlerContext.channel(), th);
            }
            if (channelHandlerContext.channel().isActive()) {
                channelHandlerContext.close();
                return;
            }
            return;
        }
        channelHandlerContext.fireExceptionCaught(th);
    }

    private boolean ignoreException(Throwable th) {
        if (!(th instanceof SSLException) && (th instanceof IOException) && this.sslClosePromise.isDone()) {
            String message = th.getMessage();
            if (message != null && IGNORABLE_ERROR_MESSAGE.matcher(message).matches()) {
                return true;
            }
            for (StackTraceElement stackTraceElement : th.getStackTrace()) {
                String className = stackTraceElement.getClassName();
                String methodName = stackTraceElement.getMethodName();
                if (!className.startsWith("io.netty.") && "read".equals(methodName)) {
                    if (IGNORABLE_CLASS_IN_STACK.matcher(className).matches()) {
                        return true;
                    }
                    try {
                        Class<?> clsLoadClass = PlatformDependent.getClassLoader(getClass()).loadClass(className);
                        if (!SocketChannel.class.isAssignableFrom(clsLoadClass) && !DatagramChannel.class.isAssignableFrom(clsLoadClass) && (PlatformDependent.javaVersion() < 7 || !"com.sun.nio.sctp.SctpChannel".equals(clsLoadClass.getSuperclass().getName()))) {
                        }
                        return true;
                    } catch (Throwable th2) {
                        InternalLogger internalLogger = logger;
                        if (internalLogger.isDebugEnabled()) {
                            internalLogger.debug("Unexpected exception while loading class {} classname {}", getClass(), className, th2);
                        }
                    }
                }
            }
        }
        return false;
    }

    public static boolean isEncrypted(ByteBuf byteBuf) {
        if (byteBuf.readableBytes() >= 5) {
            return SslUtils.getEncryptedPacketLength(byteBuf, byteBuf.readerIndex()) != -2;
        }
        throw new IllegalArgumentException("buffer must have at least 5 readable bytes");
    }

    private void decodeJdkCompatible(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Throwable {
        int i = this.packetLength;
        if (i > 0) {
            if (byteBuf.readableBytes() < i) {
                return;
            }
        } else {
            int i2 = byteBuf.readableBytes();
            if (i2 < 5) {
                return;
            }
            int encryptedPacketLength = SslUtils.getEncryptedPacketLength(byteBuf, byteBuf.readerIndex());
            if (encryptedPacketLength == -2) {
                NotSslRecordException notSslRecordException = new NotSslRecordException("not an SSL/TLS record: " + ByteBufUtil.hexDump(byteBuf));
                byteBuf.skipBytes(byteBuf.readableBytes());
                setHandshakeFailure(channelHandlerContext, notSslRecordException);
                throw notSslRecordException;
            }
            if (encryptedPacketLength > i2) {
                this.packetLength = encryptedPacketLength;
                return;
            }
            i = encryptedPacketLength;
        }
        this.packetLength = 0;
        try {
            byteBuf.skipBytes(unwrap(channelHandlerContext, byteBuf, byteBuf.readerIndex(), i));
        } catch (Throwable th) {
            handleUnwrapThrowable(channelHandlerContext, th);
        }
    }

    private void decodeNonJdkCompatible(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Throwable {
        try {
            byteBuf.skipBytes(unwrap(channelHandlerContext, byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes()));
        } catch (Throwable th) {
            handleUnwrapThrowable(channelHandlerContext, th);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void handleUnwrapThrowable(ChannelHandlerContext channelHandlerContext, Throwable th) throws Throwable {
        try {
            try {
                if (this.handshakePromise.tryFailure(th)) {
                    channelHandlerContext.fireUserEventTriggered((Object) new SslHandshakeCompletionEvent(th));
                }
                wrapAndFlush(channelHandlerContext);
                setHandshakeFailure(channelHandlerContext, th, true, false, true);
            } catch (SSLException e) {
                logger.debug("SSLException during trying to call SSLEngine.wrap(...) because of an previous SSLException, ignoring...", (Throwable) e);
                setHandshakeFailure(channelHandlerContext, th, true, false, true);
            }
            PlatformDependent.throwException(th);
        } catch (Throwable th2) {
            setHandshakeFailure(channelHandlerContext, th, true, false, true);
            throw th2;
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Throwable {
        if (this.processTask) {
            return;
        }
        if (this.jdkCompatibilityMode) {
            decodeJdkCompatible(channelHandlerContext, byteBuf);
        } else {
            decodeNonJdkCompatible(channelHandlerContext, byteBuf);
        }
    }

    @Override // io.netty.handler.codec.ByteToMessageDecoder, io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelReadComplete(ChannelHandlerContext channelHandlerContext) throws Exception {
        channelReadComplete0(channelHandlerContext);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void channelReadComplete0(ChannelHandlerContext channelHandlerContext) {
        discardSomeReadBytes();
        flushIfNeeded(channelHandlerContext);
        readIfNeeded(channelHandlerContext);
        this.firedChannelRead = false;
        channelHandlerContext.fireChannelReadComplete();
    }

    private void readIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.channel().config().isAutoRead()) {
            return;
        }
        if (this.firedChannelRead && this.handshakePromise.isDone()) {
            return;
        }
        channelHandlerContext.read();
    }

    private void flushIfNeeded(ChannelHandlerContext channelHandlerContext) {
        if (this.needsFlush) {
            forceFlush(channelHandlerContext);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void unwrapNonAppData(ChannelHandlerContext channelHandlerContext) throws Throwable {
        unwrap(channelHandlerContext, Unpooled.EMPTY_BUFFER, 0, 0);
    }

    /* JADX WARN: Code duplicated, block: B:48:0x00af A[Catch: all -> 0x0158, EDGE_INSN: B:48:0x00af->B:66:0x0129 BREAK  A[LOOP:0: B:3:0x0012->B:113:0x0012], TryCatch #1 {all -> 0x0158, blocks: (B:9:0x0026, B:15:0x004c, B:40:0x009f, B:42:0x00a3, B:46:0x00ab, B:48:0x00af, B:66:0x0129, B:68:0x012d, B:70:0x0135, B:73:0x013c, B:75:0x0141, B:25:0x0062, B:26:0x007a, B:27:0x007b, B:31:0x0085, B:36:0x0091, B:37:0x0096, B:49:0x00b4, B:51:0x00c5, B:55:0x00d7), top: B:95:0x0026 }] */
    private int unwrap(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, int i, int i2) throws Throwable {
        boolean z;
        boolean z2;
        SSLEngineResult.Status status;
        SSLEngineResult.HandshakeStatus handshakeStatus;
        int iBytesProduced;
        int iBytesConsumed;
        int i3;
        int i4 = i;
        int i5 = i2;
        ByteBuf byteBufAllocate = allocate(channelHandlerContext, i2);
        boolean z3 = false;
        boolean z4 = false;
        loop0: while (true) {
            int i6 = -1;
            while (true) {
                try {
                    if (channelHandlerContext.isRemoved()) {
                        z = true;
                        break loop0;
                    }
                    z = true;
                    try {
                        SSLEngineResult sSLEngineResultUnwrap = this.engineType.unwrap(this, byteBuf, i4, i5, byteBufAllocate);
                        status = sSLEngineResultUnwrap.getStatus();
                        handshakeStatus = sSLEngineResultUnwrap.getHandshakeStatus();
                        iBytesProduced = sSLEngineResultUnwrap.bytesProduced();
                        iBytesConsumed = sSLEngineResultUnwrap.bytesConsumed();
                        i4 += iBytesConsumed;
                        i5 -= iBytesConsumed;
                        i3 = AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$Status[status.ordinal()];
                        if (i3 != 1) {
                            break;
                        }
                        int i7 = byteBufAllocate.readableBytes();
                        int applicationBufferSize = this.engine.getSession().getApplicationBufferSize() - i7;
                        if (i7 > 0) {
                            this.firedChannelRead = true;
                            channelHandlerContext.fireChannelRead((Object) byteBufAllocate);
                            if (applicationBufferSize <= 0) {
                                try {
                                    applicationBufferSize = this.engine.getSession().getApplicationBufferSize();
                                } catch (Throwable th) {
                                    th = th;
                                    byteBufAllocate = null;
                                    if (byteBufAllocate != null) {
                                        if (byteBufAllocate.isReadable()) {
                                            this.firedChannelRead = z;
                                            channelHandlerContext.fireChannelRead((Object) byteBufAllocate);
                                        } else {
                                            byteBufAllocate.release();
                                        }
                                    }
                                    throw th;
                                }
                            }
                        } else {
                            byteBufAllocate.release();
                        }
                        if (i7 == 0 && i6 == 0) {
                            throw new IllegalStateException("Two consecutive overflows but no content was consumed. " + SSLSession.class.getSimpleName() + " getApplicationBufferSize: " + this.engine.getSession().getApplicationBufferSize() + " maybe too small.");
                        }
                        byteBufAllocate = allocate(channelHandlerContext, this.engineType.calculatePendingData(this, applicationBufferSize));
                        i6 = i7;
                    } catch (Throwable th2) {
                        th = th2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    z = true;
                }
            }
            if (i3 == 2) {
                z4 = true;
            }
            int i8 = AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[handshakeStatus.ordinal()];
            if (i8 == 1) {
                if (!runDelegatedTasks(true)) {
                    z3 = false;
                    break;
                }
                if (status != SSLEngineResult.Status.BUFFER_UNDERFLOW) {
                }
                if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                    break;
                }
                readIfNeeded(channelHandlerContext);
                break;
            }
            if (i8 == 2) {
                setHandshakeSuccess();
                z3 = true;
            } else if (i8 == 3) {
                if (!setHandshakeSuccessIfStillHandshaking()) {
                    if (i5 != 0) {
                        break;
                    }
                    break;
                }
                z3 = true;
            } else if (i8 == 4) {
                if (wrapNonAppData(channelHandlerContext, true) && i5 == 0) {
                    break;
                }
            } else if (i8 != 5) {
                throw new IllegalStateException("unknown handshake status: " + handshakeStatus);
            }
            if (status != SSLEngineResult.Status.BUFFER_UNDERFLOW || (handshakeStatus != SSLEngineResult.HandshakeStatus.NEED_TASK && iBytesConsumed == 0 && iBytesProduced == 0)) {
                if (handshakeStatus == SSLEngineResult.HandshakeStatus.NEED_UNWRAP) {
                    break;
                }
                readIfNeeded(channelHandlerContext);
                break;
            }
        }
        if (this.flushedBeforeHandshake && this.handshakePromise.isDone()) {
            this.flushedBeforeHandshake = false;
            z2 = z;
        } else {
            z2 = z3;
        }
        if (z2) {
            wrap(channelHandlerContext, z);
        }
        if (z4) {
            notifyClosePromise(null);
        }
        if (byteBufAllocate != null) {
            if (byteBufAllocate.isReadable()) {
                this.firedChannelRead = z;
                channelHandlerContext.fireChannelRead((Object) byteBufAllocate);
            } else {
                byteBufAllocate.release();
            }
        }
        return i2 - i5;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static ByteBuffer toByteBuffer(ByteBuf byteBuf, int i, int i2) {
        return byteBuf.nioBufferCount() == 1 ? byteBuf.internalNioBuffer(i, i2) : byteBuf.nioBuffer(i, i2);
    }

    private static boolean inEventLoop(Executor executor) {
        return (executor instanceof EventExecutor) && ((EventExecutor) executor).inEventLoop();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void runAllDelegatedTasks(SSLEngine sSLEngine) {
        while (true) {
            Runnable delegatedTask = sSLEngine.getDelegatedTask();
            if (delegatedTask == null) {
                return;
            } else {
                delegatedTask.run();
            }
        }
    }

    private boolean runDelegatedTasks(boolean z) {
        if (this.delegatedTaskExecutor == ImmediateExecutor.INSTANCE || inEventLoop(this.delegatedTaskExecutor)) {
            runAllDelegatedTasks(this.engine);
            return true;
        }
        executeDelegatedTasks(z);
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void executeDelegatedTasks(boolean z) {
        this.processTask = true;
        try {
            this.delegatedTaskExecutor.execute(new SslTasksRunner(z));
        } catch (RejectedExecutionException e) {
            this.processTask = false;
            throw e;
        }
    }

    private final class SslTasksRunner implements Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final boolean inUnwrap;

        SslTasksRunner(boolean z) {
            this.inUnwrap = z;
        }

        private void taskError(Throwable th) {
            if (this.inUnwrap) {
                try {
                    SslHandler sslHandler = SslHandler.this;
                    sslHandler.handleUnwrapThrowable(sslHandler.ctx, th);
                    return;
                } catch (Throwable th2) {
                    safeExceptionCaught(th2);
                    return;
                }
            }
            SslHandler sslHandler2 = SslHandler.this;
            sslHandler2.setHandshakeFailure(sslHandler2.ctx, th);
            SslHandler sslHandler3 = SslHandler.this;
            sslHandler3.forceFlush(sslHandler3.ctx);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void safeExceptionCaught(Throwable th) {
            try {
                SslHandler sslHandler = SslHandler.this;
                sslHandler.exceptionCaught(sslHandler.ctx, wrapIfNeeded(th));
            } catch (Throwable th2) {
                SslHandler.this.ctx.fireExceptionCaught(th2);
            }
        }

        private Throwable wrapIfNeeded(Throwable th) {
            return (this.inUnwrap && !(th instanceof DecoderException)) ? new DecoderException(th) : th;
        }

        private void tryDecodeAgain() {
            try {
                SslHandler sslHandler = SslHandler.this;
                sslHandler.channelRead(sslHandler.ctx, Unpooled.EMPTY_BUFFER);
            } catch (Throwable th) {
                try {
                    safeExceptionCaught(th);
                } finally {
                    SslHandler.this.channelReadComplete0(SslHandler.this.ctx);
                }
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void resumeOnEventExecutor() {
            SslHandler.this.processTask = false;
            try {
                int i = AnonymousClass9.$SwitchMap$javax$net$ssl$SSLEngineResult$HandshakeStatus[SslHandler.this.engine.getHandshakeStatus().ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        SslHandler.this.setHandshakeSuccess();
                    } else {
                        if (i != 3) {
                            if (i != 4) {
                                if (i == 5) {
                                    try {
                                        SslHandler sslHandler = SslHandler.this;
                                        sslHandler.unwrapNonAppData(sslHandler.ctx);
                                        tryDecodeAgain();
                                        return;
                                    } catch (SSLException e) {
                                        SslHandler sslHandler2 = SslHandler.this;
                                        sslHandler2.handleUnwrapThrowable(sslHandler2.ctx, e);
                                        return;
                                    }
                                }
                                throw new AssertionError();
                            }
                            try {
                                SslHandler sslHandler3 = SslHandler.this;
                                if (!sslHandler3.wrapNonAppData(sslHandler3.ctx, false) && this.inUnwrap) {
                                    SslHandler sslHandler4 = SslHandler.this;
                                    sslHandler4.unwrapNonAppData(sslHandler4.ctx);
                                }
                                SslHandler sslHandler5 = SslHandler.this;
                                sslHandler5.forceFlush(sslHandler5.ctx);
                                tryDecodeAgain();
                                return;
                            } catch (Throwable th) {
                                taskError(th);
                                return;
                            }
                        }
                        safeExceptionCaught(th);
                    }
                    SslHandler.this.setHandshakeSuccessIfStillHandshaking();
                    try {
                        SslHandler sslHandler6 = SslHandler.this;
                        sslHandler6.wrap(sslHandler6.ctx, this.inUnwrap);
                        if (this.inUnwrap) {
                            SslHandler sslHandler7 = SslHandler.this;
                            sslHandler7.unwrapNonAppData(sslHandler7.ctx);
                        }
                        SslHandler sslHandler8 = SslHandler.this;
                        sslHandler8.forceFlush(sslHandler8.ctx);
                        tryDecodeAgain();
                        return;
                    } catch (Throwable th2) {
                        taskError(th2);
                        return;
                    }
                }
                SslHandler.this.executeDelegatedTasks(this.inUnwrap);
            } catch (Throwable th3) {
                safeExceptionCaught(th3);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            try {
                SslHandler.runAllDelegatedTasks(SslHandler.this.engine);
                SslHandler.this.ctx.executor().execute(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.SslTasksRunner.1
                    @Override // java.lang.Runnable
                    public void run() {
                        SslTasksRunner.this.resumeOnEventExecutor();
                    }
                });
            } catch (Throwable th) {
                handleException(th);
            }
        }

        private void handleException(final Throwable th) {
            if (SslHandler.this.ctx.executor().inEventLoop()) {
                SslHandler.this.processTask = false;
                safeExceptionCaught(th);
            } else {
                try {
                    SslHandler.this.ctx.executor().execute(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.SslTasksRunner.2
                        @Override // java.lang.Runnable
                        public void run() {
                            SslHandler.this.processTask = false;
                            SslTasksRunner.this.safeExceptionCaught(th);
                        }
                    });
                } catch (RejectedExecutionException unused) {
                    SslHandler.this.processTask = false;
                    SslHandler.this.ctx.fireExceptionCaught(th);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean setHandshakeSuccessIfStillHandshaking() {
        if (this.handshakePromise.isDone()) {
            return false;
        }
        setHandshakeSuccess();
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHandshakeSuccess() {
        this.handshakePromise.trySuccess(this.ctx.channel());
        InternalLogger internalLogger = logger;
        if (internalLogger.isDebugEnabled()) {
            internalLogger.debug("{} HANDSHAKEN: {}", this.ctx.channel(), this.engine.getSession().getCipherSuite());
        }
        this.ctx.fireUserEventTriggered((Object) SslHandshakeCompletionEvent.SUCCESS);
        if (!this.readDuringHandshake || this.ctx.channel().config().isAutoRead()) {
            return;
        }
        this.readDuringHandshake = false;
        this.ctx.read();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th) {
        setHandshakeFailure(channelHandlerContext, th, true, true, false);
    }

    private void setHandshakeFailure(ChannelHandlerContext channelHandlerContext, Throwable th, boolean z, boolean z2, boolean z3) {
        String message;
        try {
            this.outboundClosed = true;
            this.engine.closeOutbound();
            if (z) {
                try {
                    this.engine.closeInbound();
                } catch (SSLException e) {
                    InternalLogger internalLogger = logger;
                    if (internalLogger.isDebugEnabled() && ((message = e.getMessage()) == null || (!message.contains("possible truncation attack") && !message.contains("closing inbound before receiving peer's close_notify")))) {
                        internalLogger.debug("{} SSLEngine.closeInbound() raised an exception.", channelHandlerContext.channel(), e);
                    }
                }
            }
            if (this.handshakePromise.tryFailure(th) || z3) {
                SslUtils.handleHandshakeFailure(channelHandlerContext, th, z2);
            }
            releaseAndFailAll(channelHandlerContext, th);
        } catch (Throwable th2) {
            releaseAndFailAll(channelHandlerContext, th);
            throw th2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setHandshakeFailureTransportFailure(ChannelHandlerContext channelHandlerContext, Throwable th) {
        try {
            SSLException sSLException = new SSLException("failure when writing TLS control frames", th);
            releaseAndFailAll(channelHandlerContext, sSLException);
            if (this.handshakePromise.tryFailure(sSLException)) {
                channelHandlerContext.fireUserEventTriggered((Object) new SslHandshakeCompletionEvent(sSLException));
            }
        } finally {
            channelHandlerContext.close();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void releaseAndFailAll(ChannelHandlerContext channelHandlerContext, Throwable th) {
        SslHandlerCoalescingBufferQueue sslHandlerCoalescingBufferQueue = this.pendingUnencryptedWrites;
        if (sslHandlerCoalescingBufferQueue != null) {
            sslHandlerCoalescingBufferQueue.releaseAndFailAll(channelHandlerContext, th);
        }
    }

    private void notifyClosePromise(Throwable th) {
        if (th == null) {
            if (this.sslClosePromise.trySuccess(this.ctx.channel())) {
                this.ctx.fireUserEventTriggered((Object) SslCloseCompletionEvent.SUCCESS);
            }
        } else if (this.sslClosePromise.tryFailure(th)) {
            this.ctx.fireUserEventTriggered((Object) new SslCloseCompletionEvent(th));
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r7v1, types: [io.netty.channel.ChannelPromise] */
    /* JADX WARN: Type inference failed for: r7v2, types: [io.netty.channel.ChannelPromise] */
    private void closeOutboundAndChannel(ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise, boolean z) throws Exception {
        this.outboundClosed = true;
        this.engine.closeOutbound();
        if (!channelHandlerContext.channel().isActive()) {
            if (z) {
                channelHandlerContext.disconnect(channelPromise);
                return;
            } else {
                channelHandlerContext.close(channelPromise);
                return;
            }
        }
        ChannelPromise channelPromiseNewPromise = channelHandlerContext.newPromise();
        try {
            flush(channelHandlerContext, channelPromiseNewPromise);
            if (this.closeNotify) {
                return;
            }
            this.closeNotify = true;
            ChannelPromise channelPromiseNewPromise2 = channelHandlerContext.newPromise();
            ChannelPromiseNotifier channelPromiseNotifier = new ChannelPromiseNotifier(false, channelPromise);
        } finally {
            if (!this.closeNotify) {
                this.closeNotify = true;
                safeClose(channelHandlerContext, channelPromiseNewPromise, channelHandlerContext.newPromise().addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(false, channelPromise)));
            } else {
                this.sslClosePromise.addListener((GenericFutureListener) new FutureListener<Channel>() { // from class: io.netty.handler.ssl.SslHandler.3
                    @Override // io.netty.util.concurrent.GenericFutureListener
                    public void operationComplete(Future<Channel> future) {
                        channelPromise.setSuccess();
                    }
                });
            }
        }
    }

    private void flush(ChannelHandlerContext channelHandlerContext, ChannelPromise channelPromise) throws Exception {
        SslHandlerCoalescingBufferQueue sslHandlerCoalescingBufferQueue = this.pendingUnencryptedWrites;
        if (sslHandlerCoalescingBufferQueue != null) {
            sslHandlerCoalescingBufferQueue.add(Unpooled.EMPTY_BUFFER, channelPromise);
        } else {
            channelPromise.setFailure((Throwable) newPendingWritesNullException());
        }
        flush(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.ctx = channelHandlerContext;
        this.pendingUnencryptedWrites = new SslHandlerCoalescingBufferQueue(channelHandlerContext.channel(), 16);
        if (channelHandlerContext.channel().isActive()) {
            startHandshakeProcessing();
        }
    }

    private void startHandshakeProcessing() {
        if (this.handshakeStarted) {
            return;
        }
        this.handshakeStarted = true;
        if (this.engine.getUseClientMode()) {
            handshake();
        }
        applyHandshakeTimeout();
    }

    public Future<Channel> renegotiate() {
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException();
        }
        return renegotiate(channelHandlerContext.executor().newPromise());
    }

    public Future<Channel> renegotiate(final Promise<Channel> promise) {
        ObjectUtil.checkNotNull(promise, "promise");
        ChannelHandlerContext channelHandlerContext = this.ctx;
        if (channelHandlerContext == null) {
            throw new IllegalStateException();
        }
        EventExecutor eventExecutorExecutor = channelHandlerContext.executor();
        if (!eventExecutorExecutor.inEventLoop()) {
            eventExecutorExecutor.execute(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.4
                @Override // java.lang.Runnable
                public void run() {
                    SslHandler.this.renegotiateOnEventLoop(promise);
                }
            });
            return promise;
        }
        renegotiateOnEventLoop(promise);
        return promise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void renegotiateOnEventLoop(Promise<Channel> promise) {
        Promise<Channel> promise2 = this.handshakePromise;
        if (!promise2.isDone()) {
            promise2.addListener((GenericFutureListener<? extends Future<? super Channel>>) new PromiseNotifier(promise));
            return;
        }
        this.handshakePromise = promise;
        handshake();
        applyHandshakeTimeout();
    }

    private void handshake() {
        if (this.engine.getHandshakeStatus() == SSLEngineResult.HandshakeStatus.NOT_HANDSHAKING && !this.handshakePromise.isDone()) {
            ChannelHandlerContext channelHandlerContext = this.ctx;
            try {
                this.engine.beginHandshake();
                wrapNonAppData(channelHandlerContext, false);
            } catch (Throwable th) {
                try {
                    setHandshakeFailure(channelHandlerContext, th);
                } finally {
                    forceFlush(channelHandlerContext);
                }
            }
        }
    }

    private void applyHandshakeTimeout() {
        final Promise<Channel> promise = this.handshakePromise;
        final long j = this.handshakeTimeoutMillis;
        if (j <= 0 || promise.isDone()) {
            return;
        }
        final ScheduledFuture<?> scheduledFutureSchedule = this.ctx.executor().schedule(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.5
            @Override // java.lang.Runnable
            public void run() {
                if (promise.isDone()) {
                    return;
                }
                SslHandshakeTimeoutException sslHandshakeTimeoutException = new SslHandshakeTimeoutException("handshake timed out after " + j + LanguageCodeUtil.MS);
                try {
                    if (promise.tryFailure(sslHandshakeTimeoutException)) {
                        SslUtils.handleHandshakeFailure(SslHandler.this.ctx, sslHandshakeTimeoutException, true);
                    }
                } finally {
                    SslHandler sslHandler = SslHandler.this;
                    sslHandler.releaseAndFailAll(sslHandler.ctx, sslHandshakeTimeoutException);
                }
            }
        }, j, TimeUnit.MILLISECONDS);
        promise.addListener((GenericFutureListener<? extends Future<? super Channel>>) new FutureListener<Channel>() { // from class: io.netty.handler.ssl.SslHandler.6
            @Override // io.netty.util.concurrent.GenericFutureListener
            public void operationComplete(Future<Channel> future) throws Exception {
                scheduledFutureSchedule.cancel(false);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void forceFlush(ChannelHandlerContext channelHandlerContext) {
        this.needsFlush = false;
        channelHandlerContext.flush();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelActive(ChannelHandlerContext channelHandlerContext) throws Exception {
        if (!this.startTls) {
            startHandshakeProcessing();
        }
        channelHandlerContext.fireChannelActive();
    }

    private void safeClose(final ChannelHandlerContext channelHandlerContext, final ChannelFuture channelFuture, final ChannelPromise channelPromise) {
        if (!channelHandlerContext.channel().isActive()) {
            channelHandlerContext.close(channelPromise);
            return;
        }
        final ScheduledFuture<?> scheduledFutureSchedule = null;
        if (!channelFuture.isDone()) {
            long j = this.closeNotifyFlushTimeoutMillis;
            if (j > 0) {
                scheduledFutureSchedule = channelHandlerContext.executor().schedule(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.7
                    @Override // java.lang.Runnable
                    public void run() {
                        if (channelFuture.isDone()) {
                            return;
                        }
                        SslHandler.logger.warn("{} Last write attempt timed out; force-closing the connection.", channelHandlerContext.channel());
                        ChannelHandlerContext channelHandlerContext2 = channelHandlerContext;
                        SslHandler.addCloseListener(channelHandlerContext2.close(channelHandlerContext2.newPromise()), channelPromise);
                    }
                }, j, TimeUnit.MILLISECONDS);
            }
        }
        channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.handler.ssl.SslHandler.8
            @Override // io.netty.util.concurrent.GenericFutureListener
            public void operationComplete(ChannelFuture channelFuture2) throws Exception {
                java.util.concurrent.ScheduledFuture scheduledFuture = scheduledFutureSchedule;
                if (scheduledFuture != null) {
                    scheduledFuture.cancel(false);
                }
                final long j2 = SslHandler.this.closeNotifyReadTimeoutMillis;
                if (j2 <= 0) {
                    ChannelHandlerContext channelHandlerContext2 = channelHandlerContext;
                    SslHandler.addCloseListener(channelHandlerContext2.close(channelHandlerContext2.newPromise()), channelPromise);
                } else {
                    final ScheduledFuture<?> scheduledFutureSchedule2 = !SslHandler.this.sslClosePromise.isDone() ? channelHandlerContext.executor().schedule(new Runnable() { // from class: io.netty.handler.ssl.SslHandler.8.1
                        @Override // java.lang.Runnable
                        public void run() {
                            if (SslHandler.this.sslClosePromise.isDone()) {
                                return;
                            }
                            SslHandler.logger.debug("{} did not receive close_notify in {}ms; force-closing the connection.", channelHandlerContext.channel(), Long.valueOf(j2));
                            SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), channelPromise);
                        }
                    }, j2, TimeUnit.MILLISECONDS) : null;
                    SslHandler.this.sslClosePromise.addListener((GenericFutureListener) new FutureListener<Channel>() { // from class: io.netty.handler.ssl.SslHandler.8.2
                        @Override // io.netty.util.concurrent.GenericFutureListener
                        public void operationComplete(Future<Channel> future) throws Exception {
                            java.util.concurrent.ScheduledFuture scheduledFuture2 = scheduledFutureSchedule2;
                            if (scheduledFuture2 != null) {
                                scheduledFuture2.cancel(false);
                            }
                            SslHandler.addCloseListener(channelHandlerContext.close(channelHandlerContext.newPromise()), channelPromise);
                        }
                    });
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void addCloseListener(ChannelFuture channelFuture, ChannelPromise channelPromise) {
        channelFuture.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelPromiseNotifier(false, channelPromise));
    }

    private ByteBuf allocate(ChannelHandlerContext channelHandlerContext, int i) {
        ByteBufAllocator byteBufAllocatorAlloc = channelHandlerContext.alloc();
        if (this.engineType.wantsDirectBuffer) {
            return byteBufAllocatorAlloc.directBuffer(i);
        }
        return byteBufAllocatorAlloc.buffer(i);
    }

    private ByteBuf allocateOutNetBuf(ChannelHandlerContext channelHandlerContext, int i, int i2) {
        return this.engineType.allocateWrapBuffer(this, channelHandlerContext.alloc(), i, i2);
    }

    private final class SslHandlerCoalescingBufferQueue extends AbstractCoalescingBufferQueue {
        @Override // io.netty.channel.AbstractCoalescingBufferQueue
        protected ByteBuf removeEmptyValue() {
            return null;
        }

        SslHandlerCoalescingBufferQueue(Channel channel, int i) {
            super(channel, i);
        }

        @Override // io.netty.channel.AbstractCoalescingBufferQueue
        protected ByteBuf compose(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) {
            int i = SslHandler.this.wrapDataSize;
            if (!(byteBuf instanceof CompositeByteBuf)) {
                return SslHandler.attemptCopyToCumulation(byteBuf, byteBuf2, i) ? byteBuf : copyAndCompose(byteBufAllocator, byteBuf, byteBuf2);
            }
            CompositeByteBuf compositeByteBuf = (CompositeByteBuf) byteBuf;
            int iNumComponents = compositeByteBuf.numComponents();
            if (iNumComponents == 0 || !SslHandler.attemptCopyToCumulation(compositeByteBuf.internalComponent(iNumComponents - 1), byteBuf2, i)) {
                compositeByteBuf.addComponent(true, byteBuf2);
            }
            return compositeByteBuf;
        }

        @Override // io.netty.channel.AbstractCoalescingBufferQueue
        protected ByteBuf composeFirst(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) throws Throwable {
            ByteBuf byteBufHeapBuffer;
            if (!(byteBuf instanceof CompositeByteBuf)) {
                return byteBuf;
            }
            CompositeByteBuf compositeByteBuf = (CompositeByteBuf) byteBuf;
            if (SslHandler.this.engineType.wantsDirectBuffer) {
                byteBufHeapBuffer = byteBufAllocator.directBuffer(compositeByteBuf.readableBytes());
            } else {
                byteBufHeapBuffer = byteBufAllocator.heapBuffer(compositeByteBuf.readableBytes());
            }
            try {
                byteBufHeapBuffer.writeBytes(compositeByteBuf);
            } catch (Throwable th) {
                byteBufHeapBuffer.release();
                PlatformDependent.throwException(th);
            }
            compositeByteBuf.release();
            return byteBufHeapBuffer;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean attemptCopyToCumulation(ByteBuf byteBuf, ByteBuf byteBuf2, int i) {
        int i2 = byteBuf2.readableBytes();
        int iCapacity = byteBuf.capacity();
        if (i - byteBuf.readableBytes() < i2 || ((!byteBuf.isWritable(i2) || iCapacity < i) && (iCapacity >= i || !ByteBufUtil.ensureWritableSuccess(byteBuf.ensureWritable(i2, false))))) {
            return false;
        }
        byteBuf.writeBytes(byteBuf2);
        byteBuf2.release();
        return true;
    }

    private final class LazyChannelPromise extends DefaultPromise<Channel> {
        private LazyChannelPromise() {
        }

        @Override // io.netty.util.concurrent.DefaultPromise
        protected EventExecutor executor() {
            if (SslHandler.this.ctx != null) {
                return SslHandler.this.ctx.executor();
            }
            throw new IllegalStateException();
        }

        @Override // io.netty.util.concurrent.DefaultPromise
        protected void checkDeadLock() {
            if (SslHandler.this.ctx == null) {
                return;
            }
            super.checkDeadLock();
        }
    }
}
