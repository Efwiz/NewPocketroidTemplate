package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3PubRecEncoder extends Mqtt3MessageEncoder.Mqtt3MessageWithIdEncoder<MqttPubRec> {
    private static final int FIXED_HEADER = Mqtt3MessageType.PUBREC.getCode() << 4;

    @Inject
    Mqtt3PubRecEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder.Mqtt3MessageWithIdEncoder
    int getFixedHeader() {
        return FIXED_HEADER;
    }
}
