package io.netty.channel.socket.nio;

import io.netty.buffer.ByteBuf;
import io.netty.channel.AbstractChannel;
import io.netty.channel.Channel;
import io.netty.channel.ChannelException;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelOutboundBuffer;
import io.netty.channel.ChannelPromise;
import io.netty.channel.FileRegion;
import io.netty.channel.RecvByteBufAllocator;
import io.netty.channel.nio.AbstractNioByteChannel;
import io.netty.channel.nio.AbstractNioChannel;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.socket.DefaultSocketChannelConfig;
import io.netty.channel.socket.ServerSocketChannel;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.SocketChannelConfig;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.GlobalEventExecutor;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.SocketUtils;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.spi.SelectorProvider;
import java.util.Map;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes11.dex */
public class NioSocketChannel extends AbstractNioByteChannel implements SocketChannel {
    private final SocketChannelConfig config;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) NioSocketChannel.class);
    private static final SelectorProvider DEFAULT_SELECTOR_PROVIDER = SelectorProvider.provider();

    private static java.nio.channels.SocketChannel newSocket(SelectorProvider selectorProvider) {
        try {
            return selectorProvider.openSocketChannel();
        } catch (IOException e) {
            throw new ChannelException("Failed to open a socket.", e);
        }
    }

    public NioSocketChannel() {
        this(DEFAULT_SELECTOR_PROVIDER);
    }

    public NioSocketChannel(SelectorProvider selectorProvider) {
        this(newSocket(selectorProvider));
    }

    public NioSocketChannel(java.nio.channels.SocketChannel socketChannel) {
        this(null, socketChannel);
    }

    public NioSocketChannel(Channel channel, java.nio.channels.SocketChannel socketChannel) {
        super(channel, socketChannel);
        this.config = new NioSocketChannelConfig(this, socketChannel.socket());
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public ServerSocketChannel parent() {
        return (ServerSocketChannel) super.parent();
    }

    @Override // io.netty.channel.Channel
    public SocketChannelConfig config() {
        return this.config;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioChannel
    public java.nio.channels.SocketChannel javaChannel() {
        return (java.nio.channels.SocketChannel) super.javaChannel();
    }

    @Override // io.netty.channel.Channel
    public boolean isActive() {
        java.nio.channels.SocketChannel socketChannelJavaChannel = javaChannel();
        return socketChannelJavaChannel.isOpen() && socketChannelJavaChannel.isConnected();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isOutputShutdown() {
        return javaChannel().socket().isOutputShutdown() || !isActive();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isInputShutdown() {
        return javaChannel().socket().isInputShutdown() || !isActive();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public boolean isShutdown() {
        Socket socket = javaChannel().socket();
        return (socket.isInputShutdown() && socket.isOutputShutdown()) || !isActive();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress localAddress() {
        return (InetSocketAddress) super.localAddress();
    }

    @Override // io.netty.channel.AbstractChannel, io.netty.channel.Channel
    public InetSocketAddress remoteAddress() {
        return (InetSocketAddress) super.remoteAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected final void doShutdownOutput() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownOutput();
        } else {
            javaChannel().socket().shutdownOutput();
        }
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput() {
        return shutdownOutput(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownOutput(final ChannelPromise channelPromise) {
        NioEventLoop nioEventLoopEventLoop = eventLoop();
        if (nioEventLoopEventLoop.inEventLoop()) {
            ((AbstractChannel.AbstractUnsafe) unsafe()).shutdownOutput(channelPromise);
        } else {
            nioEventLoopEventLoop.execute(new Runnable() { // from class: io.netty.channel.socket.nio.NioSocketChannel.1
                @Override // java.lang.Runnable
                public void run() {
                    ((AbstractChannel.AbstractUnsafe) NioSocketChannel.this.unsafe()).shutdownOutput(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput() {
        return shutdownInput(newPromise());
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected boolean isInputShutdown0() {
        return isInputShutdown();
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdownInput(final ChannelPromise channelPromise) {
        NioEventLoop nioEventLoopEventLoop = eventLoop();
        if (nioEventLoopEventLoop.inEventLoop()) {
            shutdownInput0(channelPromise);
        } else {
            nioEventLoopEventLoop.execute(new Runnable() { // from class: io.netty.channel.socket.nio.NioSocketChannel.2
                @Override // java.lang.Runnable
                public void run() {
                    NioSocketChannel.this.shutdownInput0(channelPromise);
                }
            });
        }
        return channelPromise;
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown() {
        return shutdown(newPromise());
    }

    @Override // io.netty.channel.socket.DuplexChannel
    public ChannelFuture shutdown(final ChannelPromise channelPromise) {
        ChannelFuture channelFutureShutdownOutput = shutdownOutput();
        if (channelFutureShutdownOutput.isDone()) {
            shutdownOutputDone(channelFutureShutdownOutput, channelPromise);
        } else {
            channelFutureShutdownOutput.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.socket.nio.NioSocketChannel.3
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(ChannelFuture channelFuture) throws Exception {
                    NioSocketChannel.this.shutdownOutputDone(channelFuture, channelPromise);
                }
            });
        }
        return channelPromise;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdownOutputDone(final ChannelFuture channelFuture, final ChannelPromise channelPromise) {
        ChannelFuture channelFutureShutdownInput = shutdownInput();
        if (channelFutureShutdownInput.isDone()) {
            shutdownDone(channelFuture, channelFutureShutdownInput, channelPromise);
        } else {
            channelFutureShutdownInput.addListener((GenericFutureListener<? extends Future<? super Void>>) new ChannelFutureListener() { // from class: io.netty.channel.socket.nio.NioSocketChannel.4
                @Override // io.netty.util.concurrent.GenericFutureListener
                public void operationComplete(ChannelFuture channelFuture2) throws Exception {
                    NioSocketChannel.shutdownDone(channelFuture, channelFuture2, channelPromise);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void shutdownDone(ChannelFuture channelFuture, ChannelFuture channelFuture2, ChannelPromise channelPromise) {
        Throwable thCause = channelFuture.cause();
        Throwable thCause2 = channelFuture2.cause();
        if (thCause != null) {
            if (thCause2 != null) {
                logger.debug("Exception suppressed because a previous exception occurred.", thCause2);
            }
            channelPromise.setFailure(thCause);
        } else if (thCause2 != null) {
            channelPromise.setFailure(thCause2);
        } else {
            channelPromise.setSuccess();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void shutdownInput0(ChannelPromise channelPromise) {
        try {
            shutdownInput0();
            channelPromise.setSuccess();
        } catch (Throwable th) {
            channelPromise.setFailure(th);
        }
    }

    private void shutdownInput0() throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            javaChannel().shutdownInput();
        } else {
            javaChannel().socket().shutdownInput();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress localAddress0() {
        return javaChannel().socket().getLocalSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected SocketAddress remoteAddress0() {
        return javaChannel().socket().getRemoteSocketAddress();
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doBind(SocketAddress socketAddress) throws Exception {
        doBind0(socketAddress);
    }

    private void doBind0(SocketAddress socketAddress) throws Exception {
        if (PlatformDependent.javaVersion() >= 7) {
            SocketUtils.bind(javaChannel(), socketAddress);
        } else {
            SocketUtils.bind(javaChannel().socket(), socketAddress);
        }
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected boolean doConnect(SocketAddress socketAddress, SocketAddress socketAddress2) throws Exception {
        if (socketAddress2 != null) {
            doBind0(socketAddress2);
        }
        try {
            boolean zConnect = SocketUtils.connect(javaChannel(), socketAddress);
            if (!zConnect) {
                selectionKey().interestOps(8);
            }
            return zConnect;
        } catch (Throwable th) {
            doClose();
            throw th;
        }
    }

    @Override // io.netty.channel.nio.AbstractNioChannel
    protected void doFinishConnect() throws Exception {
        if (!javaChannel().finishConnect()) {
            throw new Error();
        }
    }

    @Override // io.netty.channel.AbstractChannel
    protected void doDisconnect() throws Exception {
        doClose();
    }

    @Override // io.netty.channel.nio.AbstractNioChannel, io.netty.channel.AbstractChannel
    protected void doClose() throws Exception {
        super.doClose();
        javaChannel().close();
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected int doReadBytes(ByteBuf byteBuf) throws Exception {
        RecvByteBufAllocator.Handle handleRecvBufAllocHandle = unsafe().recvBufAllocHandle();
        handleRecvBufAllocHandle.attemptedBytesRead(byteBuf.writableBytes());
        return byteBuf.writeBytes(javaChannel(), handleRecvBufAllocHandle.attemptedBytesRead());
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected int doWriteBytes(ByteBuf byteBuf) throws Exception {
        return byteBuf.readBytes(javaChannel(), byteBuf.readableBytes());
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel
    protected long doWriteFileRegion(FileRegion fileRegion) throws Exception {
        return fileRegion.transferTo(javaChannel(), fileRegion.transferred());
    }

    private void adjustMaxBytesPerGatheringWrite(int i, int i2, int i3) {
        int i4;
        if (i == i2) {
            int i5 = i << 1;
            if (i5 > i3) {
                ((NioSocketChannelConfig) this.config).setMaxBytesPerGatheringWrite(i5);
                return;
            }
            return;
        }
        if (i <= 4096 || i2 >= (i4 = i >>> 1)) {
            return;
        }
        ((NioSocketChannelConfig) this.config).setMaxBytesPerGatheringWrite(i4);
    }

    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.AbstractChannel
    protected void doWrite(ChannelOutboundBuffer channelOutboundBuffer) throws Exception {
        java.nio.channels.SocketChannel socketChannelJavaChannel = javaChannel();
        int writeSpinCount = config().getWriteSpinCount();
        while (!channelOutboundBuffer.isEmpty()) {
            int maxBytesPerGatheringWrite = ((NioSocketChannelConfig) this.config).getMaxBytesPerGatheringWrite();
            ByteBuffer[] byteBufferArrNioBuffers = channelOutboundBuffer.nioBuffers(1024, maxBytesPerGatheringWrite);
            int iNioBufferCount = channelOutboundBuffer.nioBufferCount();
            if (iNioBufferCount != 0) {
                if (iNioBufferCount == 1) {
                    ByteBuffer byteBuffer = byteBufferArrNioBuffers[0];
                    int iRemaining = byteBuffer.remaining();
                    int iWrite = socketChannelJavaChannel.write(byteBuffer);
                    if (iWrite <= 0) {
                        incompleteWrite(true);
                        return;
                    } else {
                        adjustMaxBytesPerGatheringWrite(iRemaining, iWrite, maxBytesPerGatheringWrite);
                        channelOutboundBuffer.removeBytes(iWrite);
                    }
                } else {
                    long jNioBufferSize = channelOutboundBuffer.nioBufferSize();
                    long jWrite = socketChannelJavaChannel.write(byteBufferArrNioBuffers, 0, iNioBufferCount);
                    if (jWrite <= 0) {
                        incompleteWrite(true);
                        return;
                    } else {
                        adjustMaxBytesPerGatheringWrite((int) jNioBufferSize, (int) jWrite, maxBytesPerGatheringWrite);
                        channelOutboundBuffer.removeBytes(jWrite);
                    }
                }
                writeSpinCount--;
            } else {
                writeSpinCount -= doWrite0(channelOutboundBuffer);
            }
            if (writeSpinCount <= 0) {
                incompleteWrite(writeSpinCount < 0);
                return;
            }
        }
        clearOpWrite();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // io.netty.channel.nio.AbstractNioByteChannel, io.netty.channel.AbstractChannel
    public AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
        return new NioSocketChannelUnsafe();
    }

    private final class NioSocketChannelUnsafe extends AbstractNioByteChannel.NioByteUnsafe {
        private NioSocketChannelUnsafe() {
            super();
        }

        @Override // io.netty.channel.AbstractChannel.AbstractUnsafe
        protected Executor prepareToClose() {
            try {
                if (!NioSocketChannel.this.javaChannel().isOpen() || NioSocketChannel.this.config().getSoLinger() <= 0) {
                    return null;
                }
                NioSocketChannel.this.doDeregister();
                return GlobalEventExecutor.INSTANCE;
            } catch (Throwable unused) {
                return null;
            }
        }
    }

    private final class NioSocketChannelConfig extends DefaultSocketChannelConfig {
        private volatile int maxBytesPerGatheringWrite;

        private NioSocketChannelConfig(NioSocketChannel nioSocketChannel, Socket socket) {
            super(nioSocketChannel, socket);
            this.maxBytesPerGatheringWrite = Integer.MAX_VALUE;
            calculateMaxBytesPerGatheringWrite();
        }

        @Override // io.netty.channel.DefaultChannelConfig
        protected void autoReadCleared() {
            NioSocketChannel.this.clearReadPending();
        }

        @Override // io.netty.channel.socket.DefaultSocketChannelConfig, io.netty.channel.socket.SocketChannelConfig
        public NioSocketChannelConfig setSendBufferSize(int i) {
            super.setSendBufferSize(i);
            calculateMaxBytesPerGatheringWrite();
            return this;
        }

        @Override // io.netty.channel.socket.DefaultSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
        public <T> boolean setOption(ChannelOption<T> channelOption, T t) {
            if (PlatformDependent.javaVersion() >= 7 && (channelOption instanceof NioChannelOption)) {
                return NioChannelOption.setOption(jdkChannel(), (NioChannelOption) channelOption, t);
            }
            return super.setOption(channelOption, t);
        }

        @Override // io.netty.channel.socket.DefaultSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
        public <T> T getOption(ChannelOption<T> channelOption) {
            if (PlatformDependent.javaVersion() >= 7 && (channelOption instanceof NioChannelOption)) {
                return (T) NioChannelOption.getOption(jdkChannel(), (NioChannelOption) channelOption);
            }
            return (T) super.getOption(channelOption);
        }

        @Override // io.netty.channel.socket.DefaultSocketChannelConfig, io.netty.channel.DefaultChannelConfig, io.netty.channel.ChannelConfig
        public Map<ChannelOption<?>, Object> getOptions() {
            if (PlatformDependent.javaVersion() >= 7) {
                return getOptions(super.getOptions(), NioChannelOption.getOptions(jdkChannel()));
            }
            return super.getOptions();
        }

        void setMaxBytesPerGatheringWrite(int i) {
            this.maxBytesPerGatheringWrite = i;
        }

        int getMaxBytesPerGatheringWrite() {
            return this.maxBytesPerGatheringWrite;
        }

        private void calculateMaxBytesPerGatheringWrite() {
            int sendBufferSize = getSendBufferSize() << 1;
            if (sendBufferSize > 0) {
                setMaxBytesPerGatheringWrite(sendBufferSize);
            }
        }

        private java.nio.channels.SocketChannel jdkChannel() {
            return ((NioSocketChannel) this.channel).javaChannel();
        }
    }
}
