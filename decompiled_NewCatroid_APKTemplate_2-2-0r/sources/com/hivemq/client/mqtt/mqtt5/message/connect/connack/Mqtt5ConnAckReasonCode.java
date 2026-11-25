package com.hivemq.client.mqtt.mqtt5.message.connect.connack;

import com.hivemq.client.internal.mqtt.message.MqttCommonReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt5ConnAckReasonCode implements Mqtt5ReasonCode {
    SUCCESS(MqttCommonReasonCode.SUCCESS),
    UNSPECIFIED_ERROR(MqttCommonReasonCode.UNSPECIFIED_ERROR),
    MALFORMED_PACKET(MqttCommonReasonCode.MALFORMED_PACKET),
    PROTOCOL_ERROR(MqttCommonReasonCode.PROTOCOL_ERROR),
    IMPLEMENTATION_SPECIFIC_ERROR(MqttCommonReasonCode.IMPLEMENTATION_SPECIFIC_ERROR),
    UNSUPPORTED_PROTOCOL_VERSION(132),
    CLIENT_IDENTIFIER_NOT_VALID(133),
    BAD_USER_NAME_OR_PASSWORD(134),
    NOT_AUTHORIZED(MqttCommonReasonCode.NOT_AUTHORIZED),
    SERVER_UNAVAILABLE(136),
    SERVER_BUSY(MqttCommonReasonCode.SERVER_BUSY),
    BANNED(138),
    BAD_AUTHENTICATION_METHOD(MqttCommonReasonCode.BAD_AUTHENTICATION_METHOD),
    TOPIC_NAME_INVALID(MqttCommonReasonCode.TOPIC_NAME_INVALID),
    PACKET_TOO_LARGE(MqttCommonReasonCode.PACKET_TOO_LARGE),
    QUOTA_EXCEEDED(MqttCommonReasonCode.QUOTA_EXCEEDED),
    PAYLOAD_FORMAT_INVALID(MqttCommonReasonCode.PAYLOAD_FORMAT_INVALID),
    RETAIN_NOT_SUPPORTED(MqttCommonReasonCode.RETAIN_NOT_SUPPORTED),
    QOS_NOT_SUPPORTED(MqttCommonReasonCode.QOS_NOT_SUPPORTED),
    USE_ANOTHER_SERVER(MqttCommonReasonCode.USE_ANOTHER_SERVER),
    SERVER_MOVED(MqttCommonReasonCode.SERVER_MOVED),
    CONNECTION_RATE_EXCEEDED(MqttCommonReasonCode.CONNECTION_RATE_EXCEEDED);

    private static final Mqtt5ConnAckReasonCode[] ERROR_CODE_LOOKUP;
    private static final int ERROR_CODE_MAX;
    private static final int ERROR_CODE_MIN;
    private final int code;

    static {
        Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode = UNSPECIFIED_ERROR;
        Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode2 = CONNECTION_RATE_EXCEEDED;
        int i = mqtt5ConnAckReasonCode.code;
        ERROR_CODE_MIN = i;
        int i2 = mqtt5ConnAckReasonCode2.code;
        ERROR_CODE_MAX = i2;
        ERROR_CODE_LOOKUP = new Mqtt5ConnAckReasonCode[(i2 - i) + 1];
        for (Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode3 : values()) {
            if (mqtt5ConnAckReasonCode3 != SUCCESS) {
                ERROR_CODE_LOOKUP[mqtt5ConnAckReasonCode3.code - ERROR_CODE_MIN] = mqtt5ConnAckReasonCode3;
            }
        }
    }

    Mqtt5ConnAckReasonCode(int i) {
        this.code = i;
    }

    Mqtt5ConnAckReasonCode(MqttCommonReasonCode mqttCommonReasonCode) {
        this(mqttCommonReasonCode.getCode());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5ReasonCode
    public int getCode() {
        return this.code;
    }

    public static Mqtt5ConnAckReasonCode fromCode(int i) {
        Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode = SUCCESS;
        if (i == mqtt5ConnAckReasonCode.code) {
            return mqtt5ConnAckReasonCode;
        }
        int i2 = ERROR_CODE_MIN;
        if (i < i2 || i > ERROR_CODE_MAX) {
            return null;
        }
        return ERROR_CODE_LOOKUP[i - i2];
    }
}
