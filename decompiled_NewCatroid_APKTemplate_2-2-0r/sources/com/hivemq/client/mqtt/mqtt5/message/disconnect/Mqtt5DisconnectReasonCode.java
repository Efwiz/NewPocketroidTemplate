package com.hivemq.client.mqtt.mqtt5.message.disconnect;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;
import java.util.EnumSet;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5DisconnectReasonCode implements Mqtt5ReasonCode {
    NORMAL_DISCONNECTION(0),
    DISCONNECT_WITH_WILL_MESSAGE(4),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    MALFORMED_PACKET(MqttCommonReasonCode.MALFORMED_PACKET),
    PROTOCOL_ERROR(MqttCommonReasonCode.PROTOCOL_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    SERVER_BUSY(MqttCommonReasonCode.SERVER_BUSY),
    SERVER_SHUTTING_DOWN(139),
    BAD_AUTHENTICATION_METHOD(MqttCommonReasonCode.BAD_AUTHENTICATION_METHOD),
    KEEP_ALIVE_TIMEOUT(141),
    SESSION_TAKEN_OVER(142),
    TOPIC_FILTER_INVALID(MqttCommonReasonCode.TOPIC_FILTER_INVALID),
    TOPIC_NAME_INVALID(MqttCommonReasonCode.TOPIC_NAME_INVALID),
    RECEIVE_MAXIMUM_EXCEEDED(147),
    TOPIC_ALIAS_INVALID(148),
    PACKET_TOO_LARGE(MqttCommonReasonCode.PACKET_TOO_LARGE),
    MESSAGE_RATE_TOO_HIGH(150),
    QUOTA_EXCEEDED(MqttCommonReasonCode.QUOTA_EXCEEDED),
    ADMINISTRATIVE_ACTION(152),
    PAYLOAD_FORMAT_INVALID(MqttCommonReasonCode.PAYLOAD_FORMAT_INVALID),
    RETAIN_NOT_SUPPORTED(MqttCommonReasonCode.RETAIN_NOT_SUPPORTED),
    QOS_NOT_SUPPORTED(MqttCommonReasonCode.QOS_NOT_SUPPORTED),
    USE_ANOTHER_SERVER(MqttCommonReasonCode.USE_ANOTHER_SERVER),
    SERVER_MOVED(MqttCommonReasonCode.SERVER_MOVED),
    SHARED_SUBSCRIPTIONS_NOT_SUPPORTED(MqttCommonReasonCode.SHARED_SUBSCRIPTIONS_NOT_SUPPORTED),
    CONNECTION_RATE_EXCEEDED(MqttCommonReasonCode.CONNECTION_RATE_EXCEEDED),
    MAXIMUM_CONNECT_TIME(160),
    SUBSCRIPTION_IDENTIFIERS_NOT_SUPPORTED(MqttCommonReasonCode.SUBSCRIPTION_IDENTIFIERS_NOT_SUPPORTED),
    WILDCARD_SUBSCRIPTIONS_NOT_SUPPORTED(MqttCommonReasonCode.WILDCARD_SUBSCRIPTIONS_NOT_SUPPORTED);

    private static final EnumSet<Mqtt5DisconnectReasonCode> BY_CLIENT;
    private static final EnumSet<Mqtt5DisconnectReasonCode> BY_USER;
    private static final Mqtt5DisconnectReasonCode[] ERROR_CODE_LOOKUP;
    private static final int ERROR_CODE_MAX;
    private static final int ERROR_CODE_MIN;
    private final int code;

    static {
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode = UNSPECIFIED_ERROR;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode2 = WILDCARD_SUBSCRIPTIONS_NOT_SUPPORTED;
        int i = mqtt5DisconnectReasonCode.code;
        ERROR_CODE_MIN = i;
        int i2 = mqtt5DisconnectReasonCode2.code;
        ERROR_CODE_MAX = i2;
        ERROR_CODE_LOOKUP = new Mqtt5DisconnectReasonCode[(i2 - i) + 1];
        for (Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode3 : values()) {
            if (mqtt5DisconnectReasonCode3 != NORMAL_DISCONNECTION && mqtt5DisconnectReasonCode3 != DISCONNECT_WITH_WILL_MESSAGE) {
                ERROR_CODE_LOOKUP[mqtt5DisconnectReasonCode3.code - ERROR_CODE_MIN] = mqtt5DisconnectReasonCode3;
            }
        }
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode4 = NORMAL_DISCONNECTION;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode5 = DISCONNECT_WITH_WILL_MESSAGE;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode6 = UNSPECIFIED_ERROR;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode7 = MALFORMED_PACKET;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode8 = PROTOCOL_ERROR;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode9 = IMPLEMENTATION_SPECIFIC_ERROR;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode10 = BAD_AUTHENTICATION_METHOD;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode11 = TOPIC_NAME_INVALID;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode12 = RECEIVE_MAXIMUM_EXCEEDED;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode13 = TOPIC_ALIAS_INVALID;
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode14 = PACKET_TOO_LARGE;
        EnumSet<Mqtt5DisconnectReasonCode> enumSetOf = EnumSet.of(mqtt5DisconnectReasonCode4, mqtt5DisconnectReasonCode5, mqtt5DisconnectReasonCode6, mqtt5DisconnectReasonCode7, mqtt5DisconnectReasonCode8, mqtt5DisconnectReasonCode9, mqtt5DisconnectReasonCode10, mqtt5DisconnectReasonCode11, mqtt5DisconnectReasonCode12, mqtt5DisconnectReasonCode13, mqtt5DisconnectReasonCode14, MESSAGE_RATE_TOO_HIGH, QUOTA_EXCEEDED, ADMINISTRATIVE_ACTION, PAYLOAD_FORMAT_INVALID);
        BY_CLIENT = enumSetOf;
        EnumSet<Mqtt5DisconnectReasonCode> enumSetCopyOf = EnumSet.copyOf((EnumSet) enumSetOf);
        BY_USER = enumSetCopyOf;
        enumSetCopyOf.removeAll(EnumSet.of(mqtt5DisconnectReasonCode7, mqtt5DisconnectReasonCode8, mqtt5DisconnectReasonCode10, mqtt5DisconnectReasonCode12, mqtt5DisconnectReasonCode13, mqtt5DisconnectReasonCode14));
    }

    Mqtt5DisconnectReasonCode(int i) {
        this.code = i;
    }

    Mqtt5DisconnectReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5DisconnectReasonCode fromCode(int i) {
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode = NORMAL_DISCONNECTION;
        if (i == mqtt5DisconnectReasonCode.code) {
            return mqtt5DisconnectReasonCode;
        }
        Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode2 = DISCONNECT_WITH_WILL_MESSAGE;
        if (i == mqtt5DisconnectReasonCode2.code) {
            return mqtt5DisconnectReasonCode2;
        }
        int i2 = ERROR_CODE_MIN;
        if (i < i2 || i > ERROR_CODE_MAX) {
            return null;
        }
        return ERROR_CODE_LOOKUP[i - i2];
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByServer() {
        return this != DISCONNECT_WITH_WILL_MESSAGE;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSentByClient() {
        return BY_CLIENT.contains(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public boolean canBeSetByUser() {
        return BY_USER.contains(this);
    }
}
