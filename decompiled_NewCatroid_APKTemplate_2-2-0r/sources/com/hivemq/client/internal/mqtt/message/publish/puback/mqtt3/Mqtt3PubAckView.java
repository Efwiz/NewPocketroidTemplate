package com.hivemq.client.internal.mqtt.message.publish.puback.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAck;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.publish.puback.Mqtt3PubAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PubAckView implements Mqtt3PubAck {
    public static final Mqtt3PubAckView INSTANCE = new Mqtt3PubAckView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    public static MqttPubAck delegate(int i) {
        return new MqttPubAck(i, Mqtt5PubAckReasonCode.SUCCESS, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private Mqtt3PubAckView() {
    }

    public String toString() {
        return "MqttPubAck{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.PUBACK.ordinal();
    }
}
