package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;

/* JADX INFO: loaded from: classes10.dex */
public class MqttDecodeException extends AsyncRuntimeException {
    public MqttDecodeException(String str) {
        super(str);
    }

    private MqttDecodeException(MqttDecodeException mqttDecodeException) {
        super((AsyncRuntimeException) mqttDecodeException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public MqttDecodeException copy() {
        return new MqttDecodeException(this);
    }
}
