package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAck;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3PubAckEncoder extends Mqtt3MessageEncoder.Mqtt3MessageWithIdEncoder<MqttPubAck> {
    private static final int FIXED_HEADER = Mqtt3MessageType.PUBACK.getCode() << 4;

    @Inject
    Mqtt3PubAckEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder.Mqtt3MessageWithIdEncoder
    int getFixedHeader() {
        return FIXED_HEADER;
    }
}
