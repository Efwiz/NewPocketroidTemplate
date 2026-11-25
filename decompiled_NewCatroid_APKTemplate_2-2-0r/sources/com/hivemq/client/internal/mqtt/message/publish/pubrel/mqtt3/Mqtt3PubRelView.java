package com.hivemq.client.internal.mqtt.message.publish.pubrel.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.publish.pubrel.Mqtt3PubRel;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PubRelView implements Mqtt3PubRel {
    public static final Mqtt3PubRelView INSTANCE = new Mqtt3PubRelView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    public static MqttPubRel delegate(int i) {
        return new MqttPubRel(i, Mqtt5PubRelReasonCode.SUCCESS, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private Mqtt3PubRelView() {
    }

    public String toString() {
        return "MqttPubRel{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.PUBREL.ordinal();
    }
}
