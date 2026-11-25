package com.hivemq.client.mqtt.mqtt3.message.subscribe.suback;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3ReturnCode;

/* JADX INFO: loaded from: classes10.dex */
public enum Mqtt3SubAckReturnCode implements Mqtt3ReturnCode {
    SUCCESS_MAXIMUM_QOS_0(0),
    SUCCESS_MAXIMUM_QOS_1(1),
    SUCCESS_MAXIMUM_QOS_2(2),
    FAILURE(128);

    private final int code;

    Mqtt3SubAckReturnCode(int i) {
        this.code = i;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3ReturnCode
    public int getCode() {
        return this.code;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3ReturnCode
    public boolean isError() {
        return this == FAILURE;
    }

    public static Mqtt3SubAckReturnCode fromCode(int i) {
        Mqtt3SubAckReturnCode mqtt3SubAckReturnCode = SUCCESS_MAXIMUM_QOS_0;
        if (i == mqtt3SubAckReturnCode.code) {
            return mqtt3SubAckReturnCode;
        }
        Mqtt3SubAckReturnCode mqtt3SubAckReturnCode2 = SUCCESS_MAXIMUM_QOS_1;
        if (i == mqtt3SubAckReturnCode2.code) {
            return mqtt3SubAckReturnCode2;
        }
        Mqtt3SubAckReturnCode mqtt3SubAckReturnCode3 = SUCCESS_MAXIMUM_QOS_2;
        if (i == mqtt3SubAckReturnCode3.code) {
            return mqtt3SubAckReturnCode3;
        }
        Mqtt3SubAckReturnCode mqtt3SubAckReturnCode4 = FAILURE;
        if (i == mqtt3SubAckReturnCode4.code) {
            return mqtt3SubAckReturnCode4;
        }
        return null;
    }
}
