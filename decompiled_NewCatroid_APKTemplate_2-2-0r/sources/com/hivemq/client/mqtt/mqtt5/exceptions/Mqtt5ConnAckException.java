package com.hivemq.client.mqtt.mqtt5.exceptions;

import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt5ConnAckException extends Mqtt5MessageException {
    private final Mqtt5ConnAck connAck;

    public Mqtt5ConnAckException(Mqtt5ConnAck mqtt5ConnAck, String str) {
        super(str);
        this.connAck = mqtt5ConnAck;
    }

    private Mqtt5ConnAckException(Mqtt5ConnAckException mqtt5ConnAckException) {
        super((Mqtt5MessageException) mqtt5ConnAckException);
        this.connAck = mqtt5ConnAckException.connAck;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt5ConnAckException copy() {
        return new Mqtt5ConnAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5MessageException
    public Mqtt5ConnAck getMqttMessage() {
        return this.connAck;
    }
}
