package com.hivemq.client.internal.mqtt.handler.disconnect;

import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.mqtt.exceptions.ConnectionClosedException;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5DisconnectException;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.channel.Channel;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttDisconnectUtil {
    public static void close(Channel channel, String str) {
        fireDisconnectEvent(channel, new ConnectionClosedException(str), MqttDisconnectSource.CLIENT);
    }

    public static void close(Channel channel, Throwable th) {
        fireDisconnectEvent(channel, th, MqttDisconnectSource.CLIENT);
    }

    public static void disconnect(Channel channel, Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode, String str) {
        fireDisconnectEvent(channel, new Mqtt5DisconnectException(((MqttDisconnectBuilder.Default) ((MqttDisconnectBuilder.Default) new MqttDisconnectBuilder.Default().reasonCode(mqtt5DisconnectReasonCode)).reasonString(str)).build(), str), MqttDisconnectSource.CLIENT);
    }

    public static void disconnect(Channel channel, Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode, Throwable th) {
        fireDisconnectEvent(channel, new Mqtt5DisconnectException(((MqttDisconnectBuilder.Default) ((MqttDisconnectBuilder.Default) new MqttDisconnectBuilder.Default().reasonCode(mqtt5DisconnectReasonCode)).reasonString(th.getMessage())).build(), th), MqttDisconnectSource.CLIENT);
    }

    public static void fireDisconnectEvent(Channel channel, Throwable th, MqttDisconnectSource mqttDisconnectSource) {
        fireDisconnectEvent(channel, new MqttDisconnectEvent(th, mqttDisconnectSource));
    }

    static void fireDisconnectEvent(Channel channel, MqttDisconnectEvent mqttDisconnectEvent) {
        channel.pipeline().fireUserEventTriggered((Object) mqttDisconnectEvent);
    }

    private MqttDisconnectUtil() {
    }
}
