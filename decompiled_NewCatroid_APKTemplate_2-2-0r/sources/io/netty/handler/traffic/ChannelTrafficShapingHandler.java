package io.netty.handler.traffic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import java.util.ArrayDeque;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes11.dex */
public class ChannelTrafficShapingHandler extends AbstractTrafficShapingHandler {
    private final ArrayDeque<ToSend> messagesQueue;
    private long queueSize;

    public ChannelTrafficShapingHandler(long j, long j2, long j3, long j4) {
        super(j, j2, j3, j4);
        this.messagesQueue = new ArrayDeque<>();
    }

    public ChannelTrafficShapingHandler(long j, long j2, long j3) {
        super(j, j2, j3);
        this.messagesQueue = new ArrayDeque<>();
    }

    public ChannelTrafficShapingHandler(long j, long j2) {
        super(j, j2);
        this.messagesQueue = new ArrayDeque<>();
    }

    public ChannelTrafficShapingHandler(long j) {
        super(j);
        this.messagesQueue = new ArrayDeque<>();
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) throws Exception {
        TrafficCounter trafficCounter = new TrafficCounter(this, channelHandlerContext.executor(), "ChannelTC" + channelHandlerContext.channel().hashCode(), this.checkInterval);
        setTrafficCounter(trafficCounter);
        trafficCounter.start();
        super.handlerAdded(channelHandlerContext);
    }

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerRemoved(ChannelHandlerContext channelHandlerContext) throws Exception {
        this.trafficCounter.stop();
        synchronized (this) {
            if (channelHandlerContext.channel().isActive()) {
                for (ToSend toSend : this.messagesQueue) {
                    long jCalculateSize = calculateSize(toSend.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(jCalculateSize);
                    this.queueSize -= jCalculateSize;
                    channelHandlerContext.write(toSend.toSend, toSend.promise);
                }
            } else {
                for (ToSend toSend2 : this.messagesQueue) {
                    if (toSend2.toSend instanceof ByteBuf) {
                        ((ByteBuf) toSend2.toSend).release();
                    }
                }
            }
            this.messagesQueue.clear();
        }
        releaseWriteSuspended(channelHandlerContext);
        releaseReadSuspended(channelHandlerContext);
        super.handlerRemoved(channelHandlerContext);
    }

    private static final class ToSend {
        final ChannelPromise promise;
        final long relativeTimeAction;
        final Object toSend;

        private ToSend(long j, Object obj, ChannelPromise channelPromise) {
            this.relativeTimeAction = j;
            this.toSend = obj;
            this.promise = channelPromise;
        }
    }

    @Override // io.netty.handler.traffic.AbstractTrafficShapingHandler
    void submitWrite(final ChannelHandlerContext channelHandlerContext, Object obj, long j, long j2, long j3, ChannelPromise channelPromise) {
        synchronized (this) {
            if (j2 == 0) {
                if (this.messagesQueue.isEmpty()) {
                    this.trafficCounter.bytesRealWriteFlowControl(j);
                    channelHandlerContext.write(obj, channelPromise);
                    return;
                }
            }
            ToSend toSend = new ToSend(j2 + j3, obj, channelPromise);
            this.messagesQueue.addLast(toSend);
            long j4 = this.queueSize + j;
            this.queueSize = j4;
            checkWriteSuspend(channelHandlerContext, j2, j4);
            final long j5 = toSend.relativeTimeAction;
            channelHandlerContext.executor().schedule(new Runnable() { // from class: io.netty.handler.traffic.ChannelTrafficShapingHandler.1
                @Override // java.lang.Runnable
                public void run() {
                    ChannelTrafficShapingHandler.this.sendAllValid(channelHandlerContext, j5);
                }
            }, j2, TimeUnit.MILLISECONDS);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAllValid(ChannelHandlerContext channelHandlerContext, long j) {
        synchronized (this) {
            ToSend toSendPollFirst = this.messagesQueue.pollFirst();
            while (toSendPollFirst != null) {
                if (toSendPollFirst.relativeTimeAction <= j) {
                    long jCalculateSize = calculateSize(toSendPollFirst.toSend);
                    this.trafficCounter.bytesRealWriteFlowControl(jCalculateSize);
                    this.queueSize -= jCalculateSize;
                    channelHandlerContext.write(toSendPollFirst.toSend, toSendPollFirst.promise);
                    toSendPollFirst = this.messagesQueue.pollFirst();
                } else {
                    this.messagesQueue.addFirst(toSendPollFirst);
                    break;
                }
            }
            if (this.messagesQueue.isEmpty()) {
                releaseWriteSuspended(channelHandlerContext);
            }
        }
        channelHandlerContext.flush();
    }

    public long queueSize() {
        return this.queueSize;
    }
}
