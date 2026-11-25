package com.hivemq.client.mqtt.datatypes;

/* JADX INFO: loaded from: classes10.dex */
public enum MqttQos {
    AT_MOST_ONCE,
    AT_LEAST_ONCE,
    EXACTLY_ONCE;

    private static final MqttQos[] VALUES = values();

    public int getCode() {
        return ordinal();
    }

    public static MqttQos fromCode(int i) {
        if (i < 0) {
            return null;
        }
        MqttQos[] mqttQosArr = VALUES;
        if (i >= mqttQosArr.length) {
            return null;
        }
        return mqttQosArr[i];
    }
}
