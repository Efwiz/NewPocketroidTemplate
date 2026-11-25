package com.hivemq.client.internal.mqtt.message.ping;

import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.ping.Mqtt5PingResp;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPingResp implements MqttMessage, Mqtt5PingResp {
    public static final MqttPingResp INSTANCE = new MqttPingResp();

    public boolean equals(Object obj) {
        return this == obj;
    }

    private MqttPingResp() {
    }

    public String toString() {
        return "MqttPingResp{}";
    }

    public int hashCode() {
        return Mqtt5MessageType.PINGRESP.ordinal();
    }
}
