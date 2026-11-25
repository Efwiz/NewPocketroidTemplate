package com.hivemq.client.mqtt.mqtt5.message;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ReasonCode {
    default boolean canBeSentByClient() {
        return false;
    }

    default boolean canBeSentByServer() {
        return true;
    }

    default boolean canBeSetByUser() {
        return false;
    }

    int getCode();

    default boolean isError() {
        return getCode() >= 128;
    }
}
