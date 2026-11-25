package com.hivemq.client.mqtt.mqtt3.message.connect.connack;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3ReturnCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt3ConnAckReturnCode implements Mqtt3ReturnCode {
    SUCCESS,
    UNSUPPORTED_PROTOCOL_VERSION,
    IDENTIFIER_REJECTED,
    SERVER_UNAVAILABLE,
    BAD_USER_NAME_OR_PASSWORD,
    NOT_AUTHORIZED;

    private static final Mqtt3ConnAckReturnCode[] VALUES = values();

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3ReturnCode
    public int getCode() {
        return ordinal();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3ReturnCode
    public boolean isError() {
        return this != SUCCESS;
    }

    public static Mqtt3ConnAckReturnCode fromCode(int i) {
        if (i < 0) {
            return null;
        }
        Mqtt3ConnAckReturnCode[] mqtt3ConnAckReturnCodeArr = VALUES;
        if (i >= mqtt3ConnAckReturnCodeArr.length) {
            return null;
        }
        return mqtt3ConnAckReturnCodeArr[i];
    }
}
