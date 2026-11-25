package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.internal.mqtt.message.disconnect.mqtt3.Mqtt3DisconnectView;
import com.hivemq.client.mqtt.mqtt3.message.disconnect.Mqtt3Disconnect;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3DisconnectException extends Mqtt3MessageException {
    public Mqtt3DisconnectException(String str, Throwable th) {
        super(str, th);
    }

    private Mqtt3DisconnectException(Mqtt3DisconnectException mqtt3DisconnectException) {
        super(mqtt3DisconnectException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt3DisconnectException copy() {
        return new Mqtt3DisconnectException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException
    public Mqtt3Disconnect getMqttMessage() {
        return Mqtt3DisconnectView.INSTANCE;
    }
}
