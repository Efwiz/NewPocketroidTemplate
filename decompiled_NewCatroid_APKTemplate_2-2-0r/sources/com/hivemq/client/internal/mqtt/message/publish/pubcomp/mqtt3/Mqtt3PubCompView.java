package com.hivemq.client.internal.mqtt.message.publish.pubcomp.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubComp;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.publish.pubcomp.Mqtt3PubComp;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PubCompView implements Mqtt3PubComp {
    public static final Mqtt3PubCompView INSTANCE = new Mqtt3PubCompView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    public static MqttPubComp delegate(int i) {
        return new MqttPubComp(i, Mqtt5PubCompReasonCode.SUCCESS, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private Mqtt3PubCompView() {
    }

    public String toString() {
        return "MqttPubComp{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.PUBCOMP.ordinal();
    }
}
