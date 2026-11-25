package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3SubAckException extends Mqtt3MessageException {
    private final Mqtt3SubAck subAck;

    public Mqtt3SubAckException(Mqtt3SubAck mqtt3SubAck, String str, Throwable th) {
        super(str, th);
        this.subAck = mqtt3SubAck;
    }

    private Mqtt3SubAckException(Mqtt3SubAckException mqtt3SubAckException) {
        super(mqtt3SubAckException);
        this.subAck = mqtt3SubAckException.subAck;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt3SubAckException copy() {
        return new Mqtt3SubAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException
    public Mqtt3SubAck getMqttMessage() {
        return this.subAck;
    }
}
