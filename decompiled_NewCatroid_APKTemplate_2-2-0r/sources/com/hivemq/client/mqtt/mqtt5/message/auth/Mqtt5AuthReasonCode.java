package com.hivemq.client.mqtt.mqtt5.message.auth;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5AuthReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    CONTINUE_AUTHENTICATION(24),
    REAUTHENTICATE(25);

    private final int code;

    Mqtt5AuthReasonCode(int i) {
        this.code = i;
    }

    Mqtt5AuthReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5AuthReasonCode fromCode(int i) {
        Mqtt5AuthReasonCode mqtt5AuthReasonCode = SUCCESS;
        if (i == mqtt5AuthReasonCode.code) {
            return mqtt5AuthReasonCode;
        }
        Mqtt5AuthReasonCode mqtt5AuthReasonCode2 = CONTINUE_AUTHENTICATION;
        if (i == mqtt5AuthReasonCode2.code) {
            return mqtt5AuthReasonCode2;
        }
        Mqtt5AuthReasonCode mqtt5AuthReasonCode3 = REAUTHENTICATE;
        if (i == mqtt5AuthReasonCode3.code) {
            return mqtt5AuthReasonCode3;
        }
        return null;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByServer() {
        return this != REAUTHENTICATE;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByClient() {
        return this != SUCCESS;
    }
}
