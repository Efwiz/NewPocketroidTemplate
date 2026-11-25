package com.hivemq.client.mqtt.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSessionExpiredException extends AsyncRuntimeException {
    public MqttSessionExpiredException(String str, Throwable th) {
        super(str, th);
    }

    private MqttSessionExpiredException(MqttSessionExpiredException mqttSessionExpiredException) {
        super((AsyncRuntimeException) mqttSessionExpiredException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public MqttSessionExpiredException copy() {
        return new MqttSessionExpiredException(this);
    }
}
