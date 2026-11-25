package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5SubAckException extends Mqtt5MessageException {
    private final Mqtt5SubAck subAck;

    public Mqtt5SubAckException(Mqtt5SubAck mqtt5SubAck, String str) {
        super(str);
        this.subAck = mqtt5SubAck;
    }

    private Mqtt5SubAckException(Mqtt5SubAckException mqtt5SubAckException) {
        super((Mqtt5MessageException) mqtt5SubAckException);
        this.subAck = mqtt5SubAckException.subAck;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5SubAckException copy() {
        return new Mqtt5SubAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5SubAck getMqttMessage() {
        return this.subAck;
    }
}
