package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5PubAckException extends Mqtt5MessageException {
    private final Mqtt5PubAck pubAck;

    public Mqtt5PubAckException(Mqtt5PubAck mqtt5PubAck, String str) {
        super(str);
        this.pubAck = mqtt5PubAck;
    }

    private Mqtt5PubAckException(Mqtt5PubAckException mqtt5PubAckException) {
        super((Mqtt5MessageException) mqtt5PubAckException);
        this.pubAck = mqtt5PubAckException.pubAck;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5PubAckException copy() {
        return new Mqtt5PubAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5PubAck getMqttMessage() {
        return this.pubAck;
    }
}
