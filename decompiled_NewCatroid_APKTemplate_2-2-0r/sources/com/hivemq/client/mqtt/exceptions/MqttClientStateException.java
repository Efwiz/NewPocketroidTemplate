package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientStateException extends AsyncRuntimeException {
    public MqttClientStateException(String str) {
        super(str);
    }

    private MqttClientStateException(MqttClientStateException mqttClientStateException) {
        super((AsyncRuntimeException) mqttClientStateException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public MqttClientStateException copy() {
        return new MqttClientStateException(this);
    }
}
