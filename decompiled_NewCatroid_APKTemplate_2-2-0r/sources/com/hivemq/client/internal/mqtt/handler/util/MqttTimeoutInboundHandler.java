package com.hivemq.client.internal.mqtt.handler.util;

import com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.concurrent.ScheduledFuture;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttTimeoutInboundHandler extends MqttConnectionAwareHandler implements Runnable, ChannelFutureListener {
    private ScheduledFuture<?> timeoutFuture;

    protected abstract long getTimeoutMs();

    protected abstract Mqtt5DisconnectReasonCode getTimeoutReasonCode();

    protected abstract String getTimeoutReasonString();

    @Override // io.netty.util.concurrent.GenericFutureListener
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        if (this.ctx == null) {
            return;
        }
        Throwable thCause = channelFuture.cause();
        if (thCause == null) {
            operationSuccessful(this.ctx);
        } else {
            if (thCause instanceof IOException) {
                return;
            }
            exceptionCaught(this.ctx, thCause);
        }
    }

    protected void operationSuccessful(ChannelHandlerContext channelHandlerContext) {
        scheduleTimeout(channelHandlerContext.channel());
    }

    @Override // java.lang.Runnable
    public void run() {
        if (this.ctx == null) {
            return;
        }
        Channel channel = this.ctx.channel();
        if (channel.isActive()) {
            MqttDisconnectUtil.disconnect(channel, getTimeoutReasonCode(), getTimeoutReasonString());
        } else {
            MqttDisconnectUtil.close(channel, getTimeoutReasonString());
        }
    }

    protected void scheduleTimeout(Channel channel) {
        long timeoutMs = getTimeoutMs();
        if (timeoutMs > 0) {
            this.timeoutFuture = channel.eventLoop().schedule((Runnable) this, timeoutMs, TimeUnit.MILLISECONDS);
        }
    }

    protected void cancelTimeout() {
        ScheduledFuture<?> scheduledFuture = this.timeoutFuture;
        if (scheduledFuture != null) {
            scheduledFuture.cancel(false);
            this.timeoutFuture = null;
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, MqttDisconnectEvent mqttDisconnectEvent) {
        cancelTimeout();
    }
}
