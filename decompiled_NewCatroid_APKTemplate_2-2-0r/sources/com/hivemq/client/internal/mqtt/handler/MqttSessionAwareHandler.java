package com.hivemq.client.internal.mqtt.handler;

import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttSessionAwareHandler extends MqttConnectionAwareHandler {
    protected boolean hasSession;

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler
    protected void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, MqttDisconnectEvent mqttDisconnectEvent) {
    }

    public void onSessionStartOrResume(MqttClientConnectionConfig mqttClientConnectionConfig, EventLoop eventLoop) {
        this.hasSession = true;
    }

    public void onSessionEnd(Throwable th) {
        this.hasSession = false;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.MqttConnectionAwareHandler, io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return this.ctx == null;
    }
}
