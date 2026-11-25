package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5DisconnectEncoder extends Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode<MqttDisconnect, Mqtt5DisconnectReasonCode> {
    private static final int FIXED_HEADER = Mqtt5MessageType.DISCONNECT.getCode() << 4;

    @Inject
    Mqtt5DisconnectEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    int getFixedHeader() {
        return FIXED_HEADER;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public Mqtt5DisconnectReasonCode getDefaultReasonCode() {
        return MqttDisconnect.DEFAULT_REASON_CODE;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public int additionalPropertyLength(MqttDisconnect mqttDisconnect) {
        return Mqtt5MessageEncoderUtil.intPropertyEncodedLength(mqttDisconnect.getRawSessionExpiryInterval(), -1L) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(mqttDisconnect.getRawServerReference());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder.WithReason.WithOmissibleCode
    public void encodeAdditionalProperties(MqttDisconnect mqttDisconnect, ByteBuf byteBuf) {
        Mqtt5MessageEncoderUtil.encodeIntProperty(17, mqttDisconnect.getRawSessionExpiryInterval(), -1L, byteBuf);
        Mqtt5MessageEncoderUtil.encodeNullableProperty(28, mqttDisconnect.getRawServerReference(), byteBuf);
    }
}
