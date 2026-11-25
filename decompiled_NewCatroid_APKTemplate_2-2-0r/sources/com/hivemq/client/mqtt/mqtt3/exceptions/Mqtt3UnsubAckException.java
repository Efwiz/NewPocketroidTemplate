package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.mqtt3.Mqtt3UnsubAckView;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.unsuback.Mqtt3UnsubAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3UnsubAckException extends Mqtt3MessageException {
    public Mqtt3UnsubAckException(String str, Throwable th) {
        super(str, th);
    }

    private Mqtt3UnsubAckException(Mqtt3UnsubAckException mqtt3UnsubAckException) {
        super(mqtt3UnsubAckException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt3UnsubAckException copy() {
        return new Mqtt3UnsubAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException
    public Mqtt3UnsubAck getMqttMessage() {
        return Mqtt3UnsubAckView.INSTANCE;
    }
}
