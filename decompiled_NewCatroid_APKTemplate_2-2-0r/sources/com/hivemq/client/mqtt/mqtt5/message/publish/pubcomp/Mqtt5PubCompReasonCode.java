package com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5PubCompReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    PACKET_IDENTIFIER_NOT_FOUND(MqttCommonReasonCode.PACKET_IDENTIFIER_NOT_FOUND);

    private final int code;

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByClient() {
        return true;
    }

    Mqtt5PubCompReasonCode(int i) {
        this.code = i;
    }

    Mqtt5PubCompReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5PubCompReasonCode fromCode(int i) {
        Mqtt5PubCompReasonCode mqtt5PubCompReasonCode = SUCCESS;
        if (i == mqtt5PubCompReasonCode.code) {
            return mqtt5PubCompReasonCode;
        }
        Mqtt5PubCompReasonCode mqtt5PubCompReasonCode2 = PACKET_IDENTIFIER_NOT_FOUND;
        if (i == mqtt5PubCompReasonCode2.code) {
            return mqtt5PubCompReasonCode2;
        }
        return null;
    }
}
