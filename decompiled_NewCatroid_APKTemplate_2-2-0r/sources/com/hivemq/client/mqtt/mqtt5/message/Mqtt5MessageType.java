package com.hivemq.client.mqtt.mqtt5.message;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5MessageType {
    CONNECT,
    CONNACK,
    PUBLISH,
    PUBACK,
    PUBREC,
    PUBREL,
    PUBCOMP,
    SUBSCRIBE,
    SUBACK,
    UNSUBSCRIBE,
    UNSUBACK,
    PINGREQ,
    PINGRESP,
    DISCONNECT,
    AUTH;

    private static final Mqtt5MessageType[] VALUES = values();

    public int getCode() {
        return ordinal() + 1;
    }

    public static Mqtt5MessageType fromCode(int i) {
        if (i < 1) {
            return null;
        }
        Mqtt5MessageType[] mqtt5MessageTypeArr = VALUES;
        if (i > mqtt5MessageTypeArr.length) {
            return null;
        }
        return mqtt5MessageTypeArr[i - 1];
    }
}
