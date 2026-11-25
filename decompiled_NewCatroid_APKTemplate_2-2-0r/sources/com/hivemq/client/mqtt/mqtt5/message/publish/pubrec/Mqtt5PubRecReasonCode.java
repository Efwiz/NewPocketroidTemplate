package com.hivemq.client.mqtt.mqtt5.message.publish.pubrec;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;
import java.util.EnumSet;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5PubRecReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    NO_MATCHING_SUBSCRIBERS(MqttCommonReasonCode.NO_MATCHING_SUBSCRIBERS),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    TOPIC_NAME_INVALID(MqttCommonReasonCode.TOPIC_NAME_INVALID),
    PACKET_IDENTIFIER_IN_USE(MqttCommonReasonCode.PACKET_IDENTIFIER_IN_USE),
    QUOTA_EXCEEDED(MqttCommonReasonCode.QUOTA_EXCEEDED),
    PAYLOAD_FORMAT_INVALID(MqttCommonReasonCode.PAYLOAD_FORMAT_INVALID);

    private static final EnumSet<Mqtt5PubRecReasonCode> BY_USER;
    private static final Mqtt5PubRecReasonCode[] VALUES;
    private final int code;

    static {
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode = SUCCESS;
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode2 = UNSPECIFIED_ERROR;
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode3 = IMPLEMENTATION_SPECIFIC_ERROR;
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode4 = NOT_AUTHORIZED;
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode5 = TOPIC_NAME_INVALID;
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode6 = QUOTA_EXCEEDED;
        Mqtt5PubRecReasonCode mqtt5PubRecReasonCode7 = PAYLOAD_FORMAT_INVALID;
        VALUES = values();
        BY_USER = EnumSet.of(mqtt5PubRecReasonCode, mqtt5PubRecReasonCode2, mqtt5PubRecReasonCode3, mqtt5PubRecReasonCode4, mqtt5PubRecReasonCode5, mqtt5PubRecReasonCode6, mqtt5PubRecReasonCode7);
    }

    Mqtt5PubRecReasonCode(int i) {
        this.code = i;
    }

    Mqtt5PubRecReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5PubRecReasonCode fromCode(int i) {
        for (Mqtt5PubRecReasonCode mqtt5PubRecReasonCode : VALUES) {
            if (mqtt5PubRecReasonCode.code == i) {
                return mqtt5PubRecReasonCode;
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
