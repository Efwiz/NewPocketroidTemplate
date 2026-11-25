package com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5UnsubAckReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    NO_SUBSCRIPTIONS_EXISTED(17),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    TOPIC_FILTER_INVALID(MqttCommonReasonCode.TOPIC_FILTER_INVALID),
    PACKET_IDENTIFIER_IN_USE(MqttCommonReasonCode.PACKET_IDENTIFIER_IN_USE);

    private static final Mqtt5UnsubAckReasonCode[] VALUES = values();
    private final int code;

    Mqtt5UnsubAckReasonCode(int i) {
        this.code = i;
    }

    Mqtt5UnsubAckReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5UnsubAckReasonCode fromCode(int i) {
        for (Mqtt5UnsubAckReasonCode mqtt5UnsubAckReasonCode : VALUES) {
            if (mqtt5UnsubAckReasonCode.code == i) {
                return mqtt5UnsubAckReasonCode;
            }
        }
        return null;
    }
}
