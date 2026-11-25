package com.hivemq.client.internal.mqtt.message.ping;

import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.ping.Mqtt5PingReq;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPingReq implements MqttMessage, Mqtt5PingReq {
    public static final MqttPingReq INSTANCE = new MqttPingReq();

    public boolean equals(Object obj) {
        return this == obj;
    }

    private MqttPingReq() {
    }

    public String toString() {
        return "MqttPingReq{}";
    }

    public int hashCode() {
        return Mqtt5MessageType.PINGREQ.ordinal();
    }
}
