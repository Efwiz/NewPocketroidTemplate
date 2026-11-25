package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.internal.mqtt.message.publish.pubrec.mqtt3.Mqtt3PubRecView;
import com.hivemq.client.mqtt.mqtt3.message.publish.pubrec.Mqtt3PubRec;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PubRecException extends Mqtt3MessageException {
    public Mqtt3PubRecException(String str, Throwable th) {
        super(str, th);
    }

    private Mqtt3PubRecException(Mqtt3PubRecException mqtt3PubRecException) {
        super(mqtt3PubRecException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt3PubRecException copy() {
        return new Mqtt3PubRecException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException
    public Mqtt3PubRec getMqttMessage() {
        return Mqtt3PubRecView.INSTANCE;
    }
}
