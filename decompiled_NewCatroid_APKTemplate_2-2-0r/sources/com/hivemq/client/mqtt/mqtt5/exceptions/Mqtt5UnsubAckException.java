package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5UnsubAckException extends Mqtt5MessageException {
    private final Mqtt5UnsubAck unsubAck;

    public Mqtt5UnsubAckException(Mqtt5UnsubAck mqtt5UnsubAck, String str) {
        super(str);
        this.unsubAck = mqtt5UnsubAck;
    }

    private Mqtt5UnsubAckException(Mqtt5UnsubAckException mqtt5UnsubAckException) {
        super((Mqtt5MessageException) mqtt5UnsubAckException);
        this.unsubAck = mqtt5UnsubAckException.unsubAck;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5UnsubAckException copy() {
        return new Mqtt5UnsubAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5UnsubAck getMqttMessage() {
        return this.unsubAck;
    }
}
