package com.hivemq.client.mqtt.mqtt3.exceptions;

import com.hivemq.client.internal.mqtt.message.publish.puback.mqtt3.Mqtt3PubAckView;
import com.hivemq.client.mqtt.mqtt3.message.publish.puback.Mqtt3PubAck;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PubAckException extends Mqtt3MessageException {
    public Mqtt3PubAckException(String str, Throwable th) {
        super(str, th);
    }

    private Mqtt3PubAckException(Mqtt3PubAckException mqtt3PubAckException) {
        super(mqtt3PubAckException);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.util.AsyncRuntimeException
    public Mqtt3PubAckException copy() {
        return new Mqtt3PubAckException(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.exceptions.Mqtt3MessageException
    public Mqtt3PubAck getMqttMessage() {
        return Mqtt3PubAckView.INSTANCE;
    }
}
