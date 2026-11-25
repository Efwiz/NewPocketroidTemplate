package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ConnAckException extends Mqtt3MessageException {
    private final Mqtt3ConnAck connAck;

    public Mqtt3ConnAckException(Mqtt3ConnAck mqtt3ConnAck, String str, Throwable th) {
        super(str, th);
        this.connAck = mqtt3ConnAck;
    }

    private Mqtt3ConnAckException(Mqtt3ConnAckException mqtt3ConnAckException) {
        super(mqtt3ConnAckException);
        this.connAck = mqtt3ConnAckException.connAck;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt3ConnAckException copy() {
        return new Mqtt3ConnAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException
    public Mqtt3ConnAck getMqttMessage() {
        return this.connAck;
    }
}
