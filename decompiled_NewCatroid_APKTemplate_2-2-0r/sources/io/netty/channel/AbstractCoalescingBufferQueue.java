package io.netty.channel;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.CompositeByteBuf;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.internal.ObjectUtil;
import io.netty.util.internal.PlatformDependent;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;
import java.util.ArrayDeque;

/* JADX INFO: loaded from: classes11.dex */
public abstract class AbstractCoalescingBufferQueue {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final InternalLogger logger = InternalLoggerFactory.getInstance((Class<?>) AbstractCoalescingBufferQueue.class);
    private final ArrayDeque<Object> bufAndListenerPairs;
    private int readableBytes;
    private final PendingBytesTracker tracker;

    protected abstract ByteBuf compose(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2);

    protected ByteBuf composeFirst(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf) {
        return byteBuf;
    }

    protected abstract ByteBuf removeEmptyValue();

    protected AbstractCoalescingBufferQueue(Channel channel, int i) {
        this.bufAndListenerPairs = new ArrayDeque<>(i);
        this.tracker = channel == null ? null : PendingBytesTracker.newTracker(channel);
    }

    public final void addFirst(ByteBuf byteBuf, ChannelPromise channelPromise) {
        addFirst(byteBuf, toChannelFutureListener(channelPromise));
    }

    private void addFirst(ByteBuf byteBuf, ChannelFutureListener channelFutureListener) {
        if (channelFutureListener != null) {
            this.bufAndListenerPairs.addFirst(channelFutureListener);
        }
        this.bufAndListenerPairs.addFirst(byteBuf);
        incrementReadableBytes(byteBuf.readableBytes());
    }

    public final void add(ByteBuf byteBuf) {
        add(byteBuf, (ChannelFutureListener) null);
    }

    public final void add(ByteBuf byteBuf, ChannelPromise channelPromise) {
        add(byteBuf, toChannelFutureListener(channelPromise));
    }

    public final void add(ByteBuf byteBuf, ChannelFutureListener channelFutureListener) {
        this.bufAndListenerPairs.add(byteBuf);
        if (channelFutureListener != null) {
            this.bufAndListenerPairs.add(channelFutureListener);
        }
        incrementReadableBytes(byteBuf.readableBytes());
    }

    public final ByteBuf removeFirst(ChannelPromise channelPromise) {
        Object objPoll = this.bufAndListenerPairs.poll();
        if (objPoll == null) {
            return null;
        }
        ByteBuf byteBuf = (ByteBuf) objPoll;
        decrementReadableBytes(byteBuf.readableBytes());
        Object objPeek = this.bufAndListenerPairs.peek();
        if (objPeek instanceof ChannelFutureListener) {
            channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) objPeek);
            this.bufAndListenerPairs.poll();
        }
        return byteBuf;
    }

    public final ByteBuf remove(ByteBufAllocator byteBufAllocator, int i, ChannelPromise channelPromise) throws Throwable {
        ByteBuf byteBufCompose;
        ObjectUtil.checkPositiveOrZero(i, "bytes");
        ObjectUtil.checkNotNull(channelPromise, "aggregatePromise");
        if (this.bufAndListenerPairs.isEmpty()) {
            return removeEmptyValue();
        }
        int iMin = Math.min(i, this.readableBytes);
        ByteBuf byteBuf = null;
        int i2 = iMin;
        ByteBuf byteBufComposeFirst = null;
        while (true) {
            try {
                Object objPoll = this.bufAndListenerPairs.poll();
                if (objPoll == null) {
                    break;
                }
                if (objPoll instanceof ChannelFutureListener) {
                    channelPromise.addListener((GenericFutureListener<? extends Future<? super Void>>) objPoll);
                } else {
                    ByteBuf byteBuf2 = (ByteBuf) objPoll;
                    try {
                        if (byteBuf2.readableBytes() > i2) {
                            this.bufAndListenerPairs.addFirst(byteBuf2);
                            if (i2 <= 0) {
                                break;
                            }
                            ByteBuf retainedSlice = byteBuf2.readRetainedSlice(i2);
                            if (byteBufComposeFirst == null) {
                                byteBufCompose = composeFirst(byteBufAllocator, retainedSlice);
                            } else {
                                byteBufCompose = compose(byteBufAllocator, byteBufComposeFirst, retainedSlice);
                            }
                            byteBufComposeFirst = byteBufCompose;
                            i2 = 0;
                            break;
                        }
                        i2 -= byteBuf2.readableBytes();
                        if (byteBufComposeFirst == null) {
                            byteBufComposeFirst = composeFirst(byteBufAllocator, byteBuf2);
                        } else {
                            byteBufComposeFirst = compose(byteBufAllocator, byteBufComposeFirst, byteBuf2);
                        }
                    } catch (Throwable th) {
                        th = th;
                        byteBuf = byteBuf2;
                        ReferenceCountUtil.safeRelease(byteBuf);
                        ReferenceCountUtil.safeRelease(byteBufComposeFirst);
                        channelPromise.setFailure(th);
                        PlatformDependent.throwException(th);
                    }
                }
            } catch (Throwable th2) {
                th = th2;
            }
        }
        decrementReadableBytes(iMin - i2);
        return byteBufComposeFirst;
    }

    public final int readableBytes() {
        return this.readableBytes;
    }

    public final boolean isEmpty() {
        return this.bufAndListenerPairs.isEmpty();
    }

    public final void releaseAndFailAll(ChannelOutboundInvoker channelOutboundInvoker, Throwable th) {
        releaseAndCompleteAll(channelOutboundInvoker.newFailedFuture(th));
    }

    public final void copyTo(AbstractCoalescingBufferQueue abstractCoalescingBufferQueue) {
        abstractCoalescingBufferQueue.bufAndListenerPairs.addAll(this.bufAndListenerPairs);
        abstractCoalescingBufferQueue.incrementReadableBytes(this.readableBytes);
    }

    /* JADX WARN: Code duplicated, block: B:30:0x004d A[SYNTHETIC] */
    /* JADX WARN: Code duplicated, block: B:31:0x004b A[SYNTHETIC] */
    public final void writeAndRemoveAll(ChannelHandlerContext channelHandlerContext) {
        decrementReadableBytes(this.readableBytes);
        ByteBuf byteBuf = null;
        Throwable th = null;
        while (true) {
            Object objPoll = this.bufAndListenerPairs.poll();
            if (objPoll == null) {
                if (byteBuf == null) {
                    break;
                }
                channelHandlerContext.write(byteBuf, channelHandlerContext.voidPromise());
                break;
            }
            try {
                if (objPoll instanceof ByteBuf) {
                    if (byteBuf != null) {
                        channelHandlerContext.write(byteBuf, channelHandlerContext.voidPromise());
                    }
                    byteBuf = (ByteBuf) objPoll;
                } else {
                    if (objPoll instanceof ChannelPromise) {
                        channelHandlerContext.write(byteBuf, (ChannelPromise) objPoll);
                    } else {
                        channelHandlerContext.write(byteBuf).addListener((GenericFutureListener<? extends Future<? super Void>>) objPoll);
                    }
                    byteBuf = null;
                }
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                } else {
                    logger.info("Throwable being suppressed because Throwable {} is already pending", th, th2);
                }
            }
            if (th == null) {
                th = th2;
            } else {
                logger.info("Throwable being suppressed because Throwable {} is already pending", th, th2);
            }
        }
        if (th != null) {
            throw new IllegalStateException(th);
        }
    }

    protected final ByteBuf composeIntoComposite(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) throws Throwable {
        CompositeByteBuf compositeByteBufCompositeBuffer = byteBufAllocator.compositeBuffer(size() + 2);
        try {
            compositeByteBufCompositeBuffer.addComponent(true, byteBuf);
            compositeByteBufCompositeBuffer.addComponent(true, byteBuf2);
        } catch (Throwable th) {
            compositeByteBufCompositeBuffer.release();
            ReferenceCountUtil.safeRelease(byteBuf2);
            PlatformDependent.throwException(th);
        }
        return compositeByteBufCompositeBuffer;
    }

    protected final ByteBuf copyAndCompose(ByteBufAllocator byteBufAllocator, ByteBuf byteBuf, ByteBuf byteBuf2) throws Throwable {
        ByteBuf byteBufIoBuffer = byteBufAllocator.ioBuffer(byteBuf.readableBytes() + byteBuf2.readableBytes());
        try {
            byteBufIoBuffer.writeBytes(byteBuf).writeBytes(byteBuf2);
        } catch (Throwable th) {
            byteBufIoBuffer.release();
            ReferenceCountUtil.safeRelease(byteBuf2);
            PlatformDependent.throwException(th);
        }
        byteBuf.release();
        byteBuf2.release();
        return byteBufIoBuffer;
    }

    protected final int size() {
        return this.bufAndListenerPairs.size();
    }

    private void releaseAndCompleteAll(ChannelFuture channelFuture) {
        decrementReadableBytes(this.readableBytes);
        Throwable th = null;
        while (true) {
            Object objPoll = this.bufAndListenerPairs.poll();
            if (objPoll == null) {
                break;
            }
            try {
                if (objPoll instanceof ByteBuf) {
                    ReferenceCountUtil.safeRelease(objPoll);
                } else {
                    ((ChannelFutureListener) objPoll).operationComplete(channelFuture);
                }
            } catch (Throwable th2) {
                if (th == null) {
                    th = th2;
                } else {
                    logger.info("Throwable being suppressed because Throwable {} is already pending", th, th2);
                }
            }
        }
        if (th != null) {
            throw new IllegalStateException(th);
        }
    }

    private void incrementReadableBytes(int i) {
        int i2 = this.readableBytes;
        int i3 = i2 + i;
        if (i3 < i2) {
            throw new IllegalStateException("buffer queue length overflow: " + this.readableBytes + " + " + i);
        }
        this.readableBytes = i3;
        PendingBytesTracker pendingBytesTracker = this.tracker;
        if (pendingBytesTracker != null) {
            pendingBytesTracker.incrementPendingOutboundBytes(i);
        }
    }

    private void decrementReadableBytes(int i) {
        this.readableBytes -= i;
        PendingBytesTracker pendingBytesTracker = this.tracker;
        if (pendingBytesTracker != null) {
            pendingBytesTracker.decrementPendingOutboundBytes(i);
        }
    }

    private static ChannelFutureListener toChannelFutureListener(ChannelPromise channelPromise) {
        if (channelPromise.isVoid()) {
            return null;
        }
        return new DelegatingChannelPromiseNotifier(channelPromise);
    }
}
