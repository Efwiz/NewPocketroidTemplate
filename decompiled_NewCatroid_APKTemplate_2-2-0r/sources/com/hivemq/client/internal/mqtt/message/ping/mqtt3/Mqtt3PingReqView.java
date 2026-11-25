package com.hivemq.client.internal.mqtt.message.ping.mqtt3;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.ping.Mqtt3PingReq;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PingReqView implements Mqtt3PingReq {
    public static final Mqtt3PingReqView INSTANCE = new Mqtt3PingReqView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    private Mqtt3PingReqView() {
    }

    public String toString() {
        return "MqttPingReq{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.PINGREQ.ordinal();
    }
}
