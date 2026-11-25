package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoderContext;
import com.hivemq.client.internal.mqtt.datatypes.MqttBinaryData;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.util.collections.ImmutableIntList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5PublishEncoder extends Mqtt5MessageWithUserPropertiesEncoder<MqttStatefulPublish> {
    private static final int FIXED_HEADER = Mqtt5MessageType.PUBLISH.getCode() << 4;

    @Inject
    Mqtt5PublishEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int remainingLengthWithoutProperties(MqttStatefulPublish mqttStatefulPublish) {
        MqttPublish mqttPublish = (MqttPublish) mqttStatefulPublish.stateless();
        int iEncodedLength = (mqttStatefulPublish.getTopicAlias() == 0 || mqttStatefulPublish.isNewTopicAlias()) ? mqttPublish.getTopic().encodedLength() : 2;
        if (mqttPublish.getQos() != MqttQos.AT_MOST_ONCE) {
            iEncodedLength += 2;
        }
        ByteBuffer rawPayload = mqttPublish.getRawPayload();
        return rawPayload != null ? iEncodedLength + rawPayload.remaining() : iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public int propertyLength(MqttStatefulPublish mqttStatefulPublish) {
        int iFixedPropertyLength = fixedPropertyLength((MqttPublish) mqttStatefulPublish.stateless()) + omissiblePropertyLength(mqttStatefulPublish) + Mqtt5MessageEncoderUtil.shortPropertyEncodedLength(mqttStatefulPublish.getTopicAlias(), 0);
        ImmutableIntList subscriptionIdentifiers = mqttStatefulPublish.getSubscriptionIdentifiers();
        for (int i = 0; i < subscriptionIdentifiers.size(); i++) {
            iFixedPropertyLength += Mqtt5MessageEncoderUtil.variableByteIntegerPropertyEncodedLength(subscriptionIdentifiers.get(i));
        }
        return iFixedPropertyLength;
    }

    final int fixedPropertyLength(MqttPublish mqttPublish) {
        return Mqtt5MessageEncoderUtil.intPropertyEncodedLength(mqttPublish.getRawMessageExpiryInterval(), Long.MAX_VALUE) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(mqttPublish.getRawPayloadFormatIndicator()) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(mqttPublish.getRawContentType()) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(mqttPublish.getRawResponseTopic()) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(mqttPublish.getRawCorrelationData());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public ByteBuf encode(MqttStatefulPublish mqttStatefulPublish, MqttEncoderContext mqttEncoderContext, int i, int i2, int i3, int i4) {
        ByteBuffer rawPayload = ((MqttPublish) mqttStatefulPublish.stateless()).getRawPayload();
        if (rawPayload != null && rawPayload.isDirect()) {
            int iRemaining = i - rawPayload.remaining();
            ByteBuf byteBufIoBuffer = mqttEncoderContext.getAllocator().ioBuffer(iRemaining, iRemaining);
            encode(mqttStatefulPublish, byteBufIoBuffer, i2, i3, i4);
            return Unpooled.wrappedUnmodifiableBuffer(byteBufIoBuffer, Unpooled.wrappedBuffer(rawPayload));
        }
        ByteBuf byteBufIoBuffer2 = mqttEncoderContext.getAllocator().ioBuffer(i, i);
        encode(mqttStatefulPublish, byteBufIoBuffer2, i2, i3, i4);
        return byteBufIoBuffer2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5MessageWithUserPropertiesEncoder
    public void encode(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf, int i, int i2, int i3) {
        encodeFixedHeader(mqttStatefulPublish, byteBuf, i);
        encodeVariableHeader(mqttStatefulPublish, byteBuf, i2, i3);
        encodePayload(mqttStatefulPublish, byteBuf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodeFixedHeader(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf, int i) {
        MqttPublish mqttPublish = (MqttPublish) mqttStatefulPublish.stateless();
        int code = (mqttStatefulPublish.isDup() ? 8 : 0) | (mqttPublish.getQos().getCode() << 1);
        if (mqttPublish.isRetain()) {
            code |= 1;
        }
        byteBuf.writeByte(code | FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodeVariableHeader(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf, int i, int i2) {
        MqttPublish mqttPublish = (MqttPublish) mqttStatefulPublish.stateless();
        if (mqttStatefulPublish.getTopicAlias() == 0 || mqttStatefulPublish.isNewTopicAlias()) {
            mqttPublish.getTopic().encode(byteBuf);
        } else {
            MqttBinaryData.encodeEmpty(byteBuf);
        }
        if (mqttPublish.getQos() != MqttQos.AT_MOST_ONCE) {
            byteBuf.writeShort(mqttStatefulPublish.getPacketIdentifier());
        }
        encodeProperties(mqttStatefulPublish, byteBuf, i, i2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodeProperties(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf, int i, int i2) {
        MqttVariableByteInteger.encode(i, byteBuf);
        encodeFixedProperties((MqttPublish) mqttStatefulPublish.stateless(), byteBuf);
        encodeOmissibleProperties(mqttStatefulPublish, byteBuf, i2);
        Mqtt5MessageEncoderUtil.encodeShortProperty(35, mqttStatefulPublish.getTopicAlias(), 0, byteBuf);
        ImmutableIntList subscriptionIdentifiers = mqttStatefulPublish.getSubscriptionIdentifiers();
        for (int i3 = 0; i3 < subscriptionIdentifiers.size(); i3++) {
            Mqtt5MessageEncoderUtil.encodeVariableByteIntegerProperty(11, subscriptionIdentifiers.get(i3), byteBuf);
        }
    }

    final void encodeFixedProperties(MqttPublish mqttPublish, ByteBuf byteBuf) {
        Mqtt5MessageEncoderUtil.encodeIntProperty(2, mqttPublish.getRawMessageExpiryInterval(), Long.MAX_VALUE, byteBuf);
        Mqtt5MessageEncoderUtil.encodeNullableProperty(1, mqttPublish.getRawPayloadFormatIndicator(), byteBuf);
        Mqtt5MessageEncoderUtil.encodeNullableProperty(3, mqttPublish.getRawContentType(), byteBuf);
        Mqtt5MessageEncoderUtil.encodeNullableProperty(8, mqttPublish.getRawResponseTopic(), byteBuf);
        Mqtt5MessageEncoderUtil.encodeNullableProperty(9, mqttPublish.getRawCorrelationData(), byteBuf);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void encodePayload(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf) {
        ByteBuffer rawPayload = ((MqttPublish) mqttStatefulPublish.stateless()).getRawPayload();
        if (rawPayload == null || rawPayload.isDirect()) {
            return;
        }
        byteBuf.writeBytes(rawPayload.duplicate());
    }
}
