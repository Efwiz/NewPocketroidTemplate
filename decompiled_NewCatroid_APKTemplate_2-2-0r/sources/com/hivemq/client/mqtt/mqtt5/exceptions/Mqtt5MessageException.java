package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.internal.util.AsyncRuntimeException;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt5MessageException extends AsyncRuntimeException {
    public abstract Mqtt5Message getMqttMessage();

    Mqtt5MessageException(String str) {
        super(str);
    }

    Mqtt5MessageException(Throwable th) {
        super(th.getMessage(), th);
    }

    Mqtt5MessageException(Mqtt5MessageException mqtt5MessageException) {
        super((AsyncRuntimeException) mqtt5MessageException);
    }
}
