package com.hivemq.client.internal.mqtt.handler.ping;

import com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.ioc.ConnectionScope;
import com.hivemq.client.internal.mqtt.message.ping.MqttPingReq;
import com.hivemq.client.internal.mqtt.message.ping.MqttPingResp;
import com.hivemq.client.internal.netty.DefaultChannelOutboundHandler;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import io.netty.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
@ConnectionScope
public class MqttPingHandler extends MqttConnectionAwareHandler implements DefaultChannelOutboundHandler, Runnable, ChannelFutureListener {
    public static final String NAME = "ping";
    private static final boolean PINGRESP_REQUIRED = false;
    private final long keepAliveNanos;
    private long lastFlushTimeNanos;
    private long lastReadTimeNanos;
    private boolean messageRead;
    private boolean pingReqFlushed;
    private boolean pingReqWritten;
    private ScheduledFuture<?> timeoutFuture;

    public MqttPingHandler(int i, long j, long j2) {
        this.keepAliveNanos = TimeUnit.SECONDS.toNanos(i) - TimeUnit.MILLISECONDS.toNanos(100L);
        this.lastFlushTimeNanos = j;
        this.lastReadTimeNanos = j2;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler, io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        super.handlerAdded(channelHandlerContext);
        schedule(channelHandlerContext, nextDelay(System.nanoTime()));
    }

    @Override // com.hivemq.client.internal.netty.DefaultChannelOutboundHandler, io.netty.channel.ChannelOutboundHandler
    public void flush(ChannelHandlerContext channelHandlerContext) {
        this.lastFlushTimeNanos = System.nanoTime();
        channelHandlerContext.flush();
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        this.lastReadTimeNanos = System.nanoTime();
        if (obj instanceof MqttPingResp) {
            this.messageRead = true;
        } else {
            this.messageRead = true;
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void schedule(ChannelHandlerContext channelHandlerContext, long j) {
        this.timeoutFuture = channelHandlerContext.executor().schedule((Runnable) this, j, TimeUnit.NANOSECONDS);
    }

    private long nextDelay(long j) {
        return this.keepAliveNanos - (j - Math.min(this.lastReadTimeNanos, this.lastFlushTimeNanos));
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.ctx == null) {
            return;
        }
        if (this.pingReqWritten) {
            if (!this.pingReqFlushed) {
                MqttDisconnectUtil.close(this.ctx.channel(), "Timeout while writing PINGREQ");
                return;
            } else if (!this.messageRead) {
                MqttDisconnectUtil.close(this.ctx.channel(), "Timeout while waiting for PINGRESP");
                return;
            }
        }
        this.pingReqFlushed = false;
        this.messageRead = false;
        long jNanoTime = System.nanoTime();
        long jNextDelay = nextDelay(jNanoTime);
        if (jNextDelay > 1000) {
            this.pingReqWritten = false;
            schedule(this.ctx, jNextDelay);
        } else {
            this.pingReqWritten = true;
            schedule(this.ctx, this.keepAliveNanos);
            this.lastFlushTimeNanos = jNanoTime;
            this.ctx.writeAndFlush(MqttPingReq.INSTANCE).addListener((GenericFutureListener<? extends Future<? super Void>>) this);
        }
    }

    @Override // io.netty.util.concurrent.GenericFutureListener
    public void operationComplete(ChannelFuture channelFuture) {
        if (channelFuture.isSuccess()) {
            this.pingReqFlushed = true;
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, MqttDisconnectEvent mqttDisconnectEvent) {
        ScheduledFuture<?> scheduledFuture = this.timeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.timeoutFuture = null;
        }
    }
}
