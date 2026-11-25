package com.hivemq.client.internal.mqtt.message.publish.pubrec.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.publish.pubrec.Mqtt3PubRec;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PubRecView implements Mqtt3PubRec {
    public static final Mqtt3PubRecView INSTANCE = new Mqtt3PubRecView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    public static MqttPubRec delegate(int i) {
        return new MqttPubRec(i, Mqtt5PubRecReasonCode.SUCCESS, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    private Mqtt3PubRecView() {
    }

    public String toString() {
        return "MqttPubRec{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.PUBREC.ordinal();
    }
}
