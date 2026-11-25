package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3PubRelEncoder extends Mqtt3MessageEncoder.Mqtt3MessageWithIdEncoder<MqttPubRel> {
    private static final int FIXED_HEADER = (Mqtt3MessageType.PUBREL.getCode() << 4) | 2;

    @Inject
    Mqtt3PubRelEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder.Mqtt3MessageWithIdEncoder
    int getFixedHeader() {
        return FIXED_HEADER;
    }
}
