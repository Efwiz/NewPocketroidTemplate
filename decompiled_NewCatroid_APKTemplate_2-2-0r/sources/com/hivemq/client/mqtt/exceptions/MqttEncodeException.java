package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;

/* JADX INFO: loaded from: classes10.dex */
public class MqttEncodeException extends AsyncRuntimeException {
    public MqttEncodeException(String str) {
        super(str);
    }

    private MqttEncodeException(MqttEncodeException mqttEncodeException) {
        super((AsyncRuntimeException) mqttEncodeException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public MqttEncodeException copy() {
        return new MqttEncodeException(this);
    }
}
