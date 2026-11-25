package com.hivemq.client.mqtt.mqtt5.message.publish.pubrel;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5PubRelReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    PACKET_IDENTIFIER_NOT_FOUND(MqttCommonReasonCode.PACKET_IDENTIFIER_NOT_FOUND);

    private final int code;

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByClient() {
        return true;
    }

    Mqtt5PubRelReasonCode(int i) {
        this.code = i;
    }

    Mqtt5PubRelReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5PubRelReasonCode fromCode(int i) {
        Mqtt5PubRelReasonCode mqtt5PubRelReasonCode = SUCCESS;
        if (i == mqtt5PubRelReasonCode.code) {
            return mqtt5PubRelReasonCode;
        }
        Mqtt5PubRelReasonCode mqtt5PubRelReasonCode2 = PACKET_IDENTIFIER_NOT_FOUND;
        if (i == mqtt5PubRelReasonCode2.code) {
            return mqtt5PubRelReasonCode2;
        }
        return null;
    }
}
