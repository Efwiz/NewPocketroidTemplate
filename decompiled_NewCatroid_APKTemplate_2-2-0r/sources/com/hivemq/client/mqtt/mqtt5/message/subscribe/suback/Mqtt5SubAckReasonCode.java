package com.hivemq.client.mqtt.mqtt5.message.subscribe.suback;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5SubAckReasonCode implements Mqtt5ReasonCode {
    GRANTED_QOS_0(0),
    GRANTED_QOS_1(1),
    GRANTED_QOS_2(2),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    TOPIC_FILTER_INVALID(MqttCommonReasonCode.TOPIC_FILTER_INVALID),
    PACKET_IDENTIFIER_IN_USE(MqttCommonReasonCode.PACKET_IDENTIFIER_IN_USE),
    QUOTA_EXCEEDED(MqttCommonReasonCode.QUOTA_EXCEEDED),
    SHARED_SUBSCRIPTIONS_NOT_SUPPORTED(MqttCommonReasonCode.SHARED_SUBSCRIPTIONS_NOT_SUPPORTED),
    SUBSCRIPTION_IDENTIFIERS_NOT_SUPPORTED(MqttCommonReasonCode.SUBSCRIPTION_IDENTIFIERS_NOT_SUPPORTED),
    WILDCARD_SUBSCRIPTIONS_NOT_SUPPORTED(MqttCommonReasonCode.WILDCARD_SUBSCRIPTIONS_NOT_SUPPORTED);

    private static final Mqtt5SubAckReasonCode[] VALUES = values();
    private final int code;

    Mqtt5SubAckReasonCode(int i) {
        this.code = i;
    }

    Mqtt5SubAckReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5SubAckReasonCode fromCode(int i) {
        for (Mqtt5SubAckReasonCode mqtt5SubAckReasonCode : VALUES) {
            if (mqtt5SubAckReasonCode.code == i) {
                return mqtt5SubAckReasonCode;
            }
        }
        return null;
    }
}
