package com.hivemq.client.internal.mqtt.message.ping.mqtt3;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.ping.Mqtt3PingResp;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PingRespView implements Mqtt3PingResp {
    public static final Mqtt3PingRespView INSTANCE = new Mqtt3PingRespView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    private Mqtt3PingRespView() {
    }

    public String toString() {
        return "MqttPingResp{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.PINGRESP.ordinal();
    }
}
