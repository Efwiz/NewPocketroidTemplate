package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.auth.MqttAuth;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthReasonCode;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5AuthEncoder extends Mqtt5MessageWithUserPropertiesEncoder.WithReason<MqttAuth> {
    private static final int FIXED_HEADER = Mqtt5MessageType.AUTH.getCode() << 4;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 1;

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int remainingLengthWithoutProperties(MqttAuth mqttAuth) {
        return 1;
    }

    @Inject
    Mqtt5AuthEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int propertyLength(MqttAuth mqttAuth) {
        return Mqtt5MessageEncoderUtil.propertyEncodedLength(mqttAuth.getMethod()) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(mqttAuth.getRawData()) + omissiblePropertyLength(mqttAuth);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public void encode(MqttAuth mqttAuth, ByteBuf byteBuf, int i, int i2, int i3) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttAuth, byteBuf, i2, i3);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttAuth mqttAuth, ByteBuf byteBuf, int i, int i2) {
        byteBuf.writeByte(((Mqtt5AuthReasonCode) mqttAuth.getReasonCode()).getCode());
        encodeProperties(mqttAuth, byteBuf, i, i2);
    }

    private void encodeProperties(MqttAuth mqttAuth, ByteBuf byteBuf, int i, int i2) {
        MqttVariableByteInteger.encode(i, byteBuf);
        Mqtt5MessageEncoderUtil.encodeProperty(21, mqttAuth.getMethod(), byteBuf);
        Mqtt5MessageEncoderUtil.encodeNullableProperty(22, mqttAuth.getRawData(), byteBuf);
        encodeOmissibleProperties(mqttAuth, byteBuf, i2);
    }
}
