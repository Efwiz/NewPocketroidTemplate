package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5AuthException extends Mqtt5MessageException {
    private final Mqtt5Auth auth;

    public Mqtt5AuthException(Mqtt5Auth mqtt5Auth, String str) {
        super(str);
        this.auth = mqtt5Auth;
    }

    private Mqtt5AuthException(Mqtt5AuthException mqtt5AuthException) {
        super((Mqtt5MessageException) mqtt5AuthException);
        this.auth = mqtt5AuthException.auth;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5AuthException copy() {
        return new Mqtt5AuthException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5Auth getMqttMessage() {
        return this.auth;
    }
}
