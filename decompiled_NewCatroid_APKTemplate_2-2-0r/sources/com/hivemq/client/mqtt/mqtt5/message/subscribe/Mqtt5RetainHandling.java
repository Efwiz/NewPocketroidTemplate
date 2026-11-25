package com.hivemq.client.mqtt.mqtt5.message.subscribe;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5RetainHandling {
    SEND,
    SEND_IF_SUBSCRIPTION_DOES_NOT_EXIST,
    DO_NOT_SEND;

    public int getCode() {
        return ordinal();
    }

    public static Mqtt5RetainHandling fromCode(int i) {
        Mqtt5RetainHandling mqtt5RetainHandling = SEND;
        if (i == mqtt5RetainHandling.getCode()) {
            return mqtt5RetainHandling;
        }
        Mqtt5RetainHandling mqtt5RetainHandling2 = SEND_IF_SUBSCRIPTION_DOES_NOT_EXIST;
        if (i == mqtt5RetainHandling2.getCode()) {
            return mqtt5RetainHandling2;
        }
        Mqtt5RetainHandling mqtt5RetainHandling3 = DO_NOT_SEND;
        if (i == mqtt5RetainHandling3.getCode()) {
            return mqtt5RetainHandling3;
        }
        return null;
    }
}
