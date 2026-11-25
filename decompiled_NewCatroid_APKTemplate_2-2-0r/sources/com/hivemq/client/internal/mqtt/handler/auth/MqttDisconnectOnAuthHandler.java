package com.hivemq.client.internal.mqtt.handler.auth;

import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectUtil;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuth;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5AuthException;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5ConnAckException;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
@ChannelHandler.Sharable
public class MqttDisconnectOnAuthHandler extends ChannelInboundHandlerAdapter implements MqttAuthHandler {
    @Override // io.netty.channel.ChannelHandlerAdapter
    public boolean isSharable() {
        return true;
    }

    @Inject
    MqttDisconnectOnAuthHandler() {
    }

    @Override // io.netty.channel.ChannelInboundHandlerAdapter, io.netty.channel.ChannelInboundHandler
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object obj) {
        if (obj instanceof MqttAuth) {
            readAuth(channelHandlerContext, (MqttAuth) obj);
        } else if (obj instanceof MqttConnAck) {
            readConnAck(channelHandlerContext, (MqttConnAck) obj);
        } else {
            channelHandlerContext.fireChannelRead(obj);
        }
    }

    private void readAuth(ChannelHandlerContext channelHandlerContext, MqttAuth mqttAuth) {
        MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5AuthException(mqttAuth, "Server must not send AUTH"));
    }

    private void readConnAck(ChannelHandlerContext channelHandlerContext, MqttConnAck mqttConnAck) {
        if (mqttConnAck.getRawEnhancedAuth() != null) {
            MqttDisconnectUtil.disconnect(channelHandlerContext.channel(), Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, new Mqtt5ConnAckException(mqttConnAck, "Server must not include auth in CONNACK"));
        } else {
            channelHandlerContext.fireChannelRead((Object) mqttConnAck);
        }
    }
}
