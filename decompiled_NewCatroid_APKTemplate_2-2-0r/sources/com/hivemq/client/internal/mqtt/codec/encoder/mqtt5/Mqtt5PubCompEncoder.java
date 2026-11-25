package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.message.publish.pubcomp.MqttPubComp;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompReasonCode;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PubCompEncoder extends Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode.WithId<MqttPubComp, Mqtt5PubCompReasonCode> {
    private static final int FIXED_HEADER = Mqtt5MessageType.PUBCOMP.getCode() << 4;

    @Inject
    Mqtt5PubCompEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    int getFixedHeader() {
        return FIXED_HEADER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public Mqtt5PubCompReasonCode getDefaultReasonCode() {
        return MqttPubComp.DEFAULT_REASON_CODE;
    }
}
