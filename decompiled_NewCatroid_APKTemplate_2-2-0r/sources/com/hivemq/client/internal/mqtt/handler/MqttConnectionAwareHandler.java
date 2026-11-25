package com.hivemq.client.internal.mqtt.handler;

import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectEvent;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttConnectionAwareHandler extends ChannelInboundHandlerAdapter {
    protected ChannelHandlerContext ctx;

    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return false;
    }

    protected abstract void onDisconnectEvent(ChannelHandlerContext channelHandlerContext, MqttDisconnectEvent mqttDisconnectEvent);

    @Override // io.netty.channel.ChannelHandlerAdapter, io.netty.channel.ChannelHandler
    public void handlerAdded(ChannelHandlerContext channelHandlerContext) {
        this.ctx = channelHandlerContext;
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object obj) {
        if ((obj instanceof MqttDisconnectEvent) && this.ctx != null) {
            this.ctx = null;
            onDisconnectEvent(channelHandlerContext, (MqttDisconnectEvent) obj);
        }
        channelHandlerContext.fireUserEventTriggered(obj);
    }
}
