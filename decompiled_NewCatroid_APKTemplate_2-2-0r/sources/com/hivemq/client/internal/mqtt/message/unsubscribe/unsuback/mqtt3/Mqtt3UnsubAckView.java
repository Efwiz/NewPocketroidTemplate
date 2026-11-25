package com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.MqttUnsubAck;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.unsuback.Mqtt3UnsubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAckReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3UnsubAckView implements Mqtt3UnsubAck {
    public static final ImmutableList<Mqtt5UnsubAckReasonCode> REASON_CODES_ALL_SUCCESS = ImmutableList.of();
    public static final Mqtt3UnsubAckView INSTANCE = new Mqtt3UnsubAckView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    public static MqttUnsubAck delegate(int i) {
        return new MqttUnsubAck(i, REASON_CODES_ALL_SUCCESS, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private Mqtt3UnsubAckView() {
    }

    public String toString() {
        return "MqttUnsubAck{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.UNSUBACK.ordinal();
    }
}
