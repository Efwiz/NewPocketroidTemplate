package com.hivemq.client.mqtt.mqtt5.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UserProperty extends Comparable<Mqtt5UserProperty> {
    MqttUtf8String getName();

    MqttUtf8String getValue();

    static Mqtt5UserProperty of(String str, String str2) {
        return MqttUserPropertyImpl.of(str, str2);
    }

    static Mqtt5UserProperty of(MqttUtf8String mqttUtf8String, MqttUtf8String mqttUtf8String2) {
        return MqttChecks.userProperty(mqttUtf8String, mqttUtf8String2);
    }
}
