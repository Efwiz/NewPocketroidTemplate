package com.hivemq.client.mqtt.mqtt3.message;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt3MessageType {
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
    DISCONNECT;

    private static final Mqtt3MessageType[] VALUES = values();

    public int getCode() {
        return ordinal() + 1;
    }

    public static Mqtt3MessageType fromCode(int i) {
        if (i < 1) {
            return null;
        }
        Mqtt3MessageType[] mqtt3MessageTypeArr = VALUES;
        if (i > mqtt3MessageTypeArr.length) {
            return null;
        }
        return mqtt3MessageTypeArr[i - 1];
    }
}
