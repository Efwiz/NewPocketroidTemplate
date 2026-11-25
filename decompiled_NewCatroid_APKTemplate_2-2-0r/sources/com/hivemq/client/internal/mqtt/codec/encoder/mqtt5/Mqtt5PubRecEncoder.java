package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PubRecEncoder extends Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode.WithId<MqttPubRec, Mqtt5PubRecReasonCode> {
    private static final int FIXED_HEADER = Mqtt5MessageType.PUBREC.getCode() << 4;

    @Inject
    Mqtt5PubRecEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    int getFixedHeader() {
        return FIXED_HEADER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public Mqtt5PubRecReasonCode getDefaultReasonCode() {
        return MqttPubRec.DEFAULT_REASON_CODE;
    }
}
