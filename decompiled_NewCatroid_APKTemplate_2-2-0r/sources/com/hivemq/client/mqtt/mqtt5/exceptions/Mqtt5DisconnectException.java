package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5DisconnectException extends Mqtt5MessageException {
    private final Mqtt5Disconnect disconnect;

    public Mqtt5DisconnectException(Mqtt5Disconnect mqtt5Disconnect, String str) {
        super(str);
        this.disconnect = mqtt5Disconnect;
    }

    public Mqtt5DisconnectException(Mqtt5Disconnect mqtt5Disconnect, Throwable th) {
        super(th);
        this.disconnect = mqtt5Disconnect;
    }

    private Mqtt5DisconnectException(Mqtt5DisconnectException mqtt5DisconnectException) {
        super((Mqtt5MessageException) mqtt5DisconnectException);
        this.disconnect = mqtt5DisconnectException.disconnect;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5DisconnectException copy() {
        return new Mqtt5DisconnectException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5Disconnect getMqttMessage() {
        return this.disconnect;
    }
}
