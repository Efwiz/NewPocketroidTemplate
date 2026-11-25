package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3MessageException extends AsyncRuntimeException {
    public abstract Mqtt3Message getMqttMessage();

    Mqtt3MessageException(String str, Throwable th) {
        super(str, th);
    }

    Mqtt3MessageException(Mqtt3MessageException mqtt3MessageException) {
        super((AsyncRuntimeException) mqtt3MessageException);
    }
}
