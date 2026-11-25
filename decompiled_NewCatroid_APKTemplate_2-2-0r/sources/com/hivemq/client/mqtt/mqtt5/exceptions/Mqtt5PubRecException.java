package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRec;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5PubRecException extends Mqtt5MessageException {
    private final Mqtt5PubRec pubRec;

    public Mqtt5PubRecException(Mqtt5PubRec mqtt5PubRec, String str) {
        super(str);
        this.pubRec = mqtt5PubRec;
    }

    private Mqtt5PubRecException(Mqtt5PubRecException mqtt5PubRecException) {
        super((Mqtt5MessageException) mqtt5PubRecException);
        this.pubRec = mqtt5PubRecException.pubRec;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5PubRecException copy() {
        return new Mqtt5PubRecException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5PubRec getMqttMessage() {
        return this.pubRec;
    }
}
