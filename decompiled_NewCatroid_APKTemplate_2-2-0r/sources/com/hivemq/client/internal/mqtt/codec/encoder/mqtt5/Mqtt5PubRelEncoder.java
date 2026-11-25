package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelReasonCode;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PubRelEncoder extends Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode.WithId<MqttPubRel, Mqtt5PubRelReasonCode> {
    private static final int FIXED_HEADER = (Mqtt5MessageType.PUBREL.getCode() << 4) | 2;

    @Inject
    Mqtt5PubRelEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    int getFixedHeader() {
        return FIXED_HEADER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public Mqtt5PubRelReasonCode getDefaultReasonCode() {
        return MqttPubRel.DEFAULT_REASON_CODE;
    }
}
