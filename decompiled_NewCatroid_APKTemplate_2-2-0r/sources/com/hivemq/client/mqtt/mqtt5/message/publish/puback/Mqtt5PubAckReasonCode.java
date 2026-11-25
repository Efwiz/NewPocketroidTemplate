package com.hivemq.client.mqtt.mqtt5.message.publish.puback;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;
import java.util.EnumSet;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5PubAckReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    NO_MATCHING_SUBSCRIBERS(MqttCommonReasonCode.NO_MATCHING_SUBSCRIBERS),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    TOPIC_NAME_INVALID(MqttCommonReasonCode.TOPIC_NAME_INVALID),
    PACKET_IDENTIFIER_IN_USE(MqttCommonReasonCode.PACKET_IDENTIFIER_IN_USE),
    QUOTA_EXCEEDED(MqttCommonReasonCode.QUOTA_EXCEEDED),
    PAYLOAD_FORMAT_INVALID(MqttCommonReasonCode.PAYLOAD_FORMAT_INVALID);

    private static final EnumSet<Mqtt5PubAckReasonCode> BY_USER;
    private static final Mqtt5PubAckReasonCode[] VALUES;
    private final int code;

    static {
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode = SUCCESS;
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode2 = UNSPECIFIED_ERROR;
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode3 = IMPLEMENTATION_SPECIFIC_ERROR;
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode4 = NOT_AUTHORIZED;
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode5 = TOPIC_NAME_INVALID;
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode6 = QUOTA_EXCEEDED;
        Mqtt5PubAckReasonCode mqtt5PubAckReasonCode7 = PAYLOAD_FORMAT_INVALID;
        VALUES = values();
        BY_USER = EnumSet.of(mqtt5PubAckReasonCode, mqtt5PubAckReasonCode2, mqtt5PubAckReasonCode3, mqtt5PubAckReasonCode4, mqtt5PubAckReasonCode5, mqtt5PubAckReasonCode6, mqtt5PubAckReasonCode7);
    }

    Mqtt5PubAckReasonCode(int i) {
        this.code = i;
    }

    Mqtt5PubAckReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5PubAckReasonCode fromCode(int i) {
        for (Mqtt5PubAckReasonCode mqtt5PubAckReasonCode : VALUES) {
            if (mqtt5PubAckReasonCode.code == i) {
                return mqtt5PubAckReasonCode;
            }
        }
        return null;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByClient() {
        return this != NO_MATCHING_SUBSCRIBERS;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSetByUser() {
        return BY_USER.contains(this);
    }
}
