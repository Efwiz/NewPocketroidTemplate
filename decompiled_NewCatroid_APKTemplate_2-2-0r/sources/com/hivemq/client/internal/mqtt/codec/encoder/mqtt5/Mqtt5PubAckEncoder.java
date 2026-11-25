package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.message.publish.puback.MqttPubAck;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckReasonCode;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PubAckEncoder extends Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode.WithId<MqttPubAck, Mqtt5PubAckReasonCode> {
    private static final int FIXED_HEADER = Mqtt5MessageType.PUBACK.getCode() << 4;

    @Inject
    Mqtt5PubAckEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    int getFixedHeader() {
        return FIXED_HEADER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public Mqtt5PubAckReasonCode getDefaultReasonCode() {
        return MqttPubAck.DEFAULT_REASON_CODE;
    }
}
