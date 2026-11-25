package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoderContext;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.auth.MqttEnhancedAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictions;
import com.hivemq.client.internal.mqtt.message.connect.MqttStatefulConnect;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.mqtt.exceptions.MqttEncodeException;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5ConnectEncoder extends MqttMessageEncoder<MqttStatefulConnect> {
    private static final int FIXED_HEADER = Mqtt5MessageType.CONNECT.getCode() << 4;
    private static final byte PROTOCOL_VERSION = 5;
    private static final int VARIABLE_HEADER_FIXED_LENGTH = 10;
    private final Mqtt5PublishEncoder publishEncoder;

    @Inject
    Mqtt5ConnectEncoder(Mqtt5PublishEncoder mqtt5PublishEncoder) {
        this.publishEncoder = mqtt5PublishEncoder;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
    public ByteBuf encode(MqttStatefulConnect mqttStatefulConnect, MqttEncoderContext mqttEncoderContext) {
        int iPropertyLength = propertyLength(mqttStatefulConnect);
        int iWillPropertyLength = willPropertyLength(mqttStatefulConnect);
        int iRemainingLengthWithoutProperties = remainingLengthWithoutProperties(mqttStatefulConnect);
        int iRemainingLength = remainingLength(iRemainingLengthWithoutProperties, iPropertyLength, iWillPropertyLength);
        int iPropertyLength2 = iPropertyLength;
        int iRemainingLength2 = iRemainingLength;
        int i = 0;
        int iEncodedPacketLength = MqttMessageEncoderUtil.encodedPacketLength(iRemainingLength);
        while (iEncodedPacketLength > mqttEncoderContext.getMaximumPacketSize()) {
            i++;
            iPropertyLength2 = propertyLength(mqttStatefulConnect, iPropertyLength2, i);
            if (iPropertyLength2 < 0) {
                throw MqttMessageEncoderUtil.maximumPacketSizeExceeded(mqttStatefulConnect, iEncodedPacketLength, mqttEncoderContext.getMaximumPacketSize());
            }
            iRemainingLength2 = remainingLength(iRemainingLengthWithoutProperties, iPropertyLength2, iWillPropertyLength);
            iEncodedPacketLength = MqttMessageEncoderUtil.encodedPacketLength(iRemainingLength2);
        }
        return encode(mqttStatefulConnect, mqttEncoderContext, iEncodedPacketLength, iRemainingLength2, iPropertyLength2, iWillPropertyLength, i);
    }

    private ByteBuf encode(MqttStatefulConnect mqttStatefulConnect, MqttEncoderContext mqttEncoderContext, int i, int i2, int i3, int i4, int i5) {
        ByteBuf byteBufIoBuffer = mqttEncoderContext.getAllocator().ioBuffer(i, i);
        encode(mqttStatefulConnect, byteBufIoBuffer, i2, i3, i4, i5);
        return byteBufIoBuffer;
    }

    private int remainingLength(int i, int i2, int i3) {
        return i + MqttMessageEncoderUtil.encodedLengthWithHeader(i2) + (i3 == -1 ? 0 : MqttMessageEncoderUtil.encodedLengthWithHeader(i3));
    }

    private int remainingLengthWithoutProperties(MqttStatefulConnect mqttStatefulConnect) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        int iEncodedLength = mqttStatefulConnect.getClientIdentifier().encodedLength() + 10;
        MqttSimpleAuth rawSimpleAuth = mqttConnectStateless.getRawSimpleAuth();
        if (rawSimpleAuth != null) {
            iEncodedLength = iEncodedLength + MqttMessageEncoderUtil.nullableEncodedLength(rawSimpleAuth.getRawUsername()) + MqttMessageEncoderUtil.nullableEncodedLength(rawSimpleAuth.getRawPassword());
        }
        MqttWillPublish rawWillPublish = mqttConnectStateless.getRawWillPublish();
        return rawWillPublish != null ? iEncodedLength + rawWillPublish.getTopic().encodedLength() + MqttMessageEncoderUtil.encodedOrEmptyLength(rawWillPublish.getRawPayload()) : iEncodedLength;
    }

    private int propertyLength(MqttStatefulConnect mqttStatefulConnect) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        int iIntPropertyEncodedLength = Mqtt5MessageEncoderUtil.intPropertyEncodedLength(mqttConnectStateless.getSessionExpiryInterval(), 0L);
        MqttConnectRestrictions restrictions = mqttConnectStateless.getRestrictions();
        if (restrictions != MqttConnectRestrictions.DEFAULT) {
            iIntPropertyEncodedLength = iIntPropertyEncodedLength + Mqtt5MessageEncoderUtil.shortPropertyEncodedLength(restrictions.getReceiveMaximum(), 65535) + Mqtt5MessageEncoderUtil.intPropertyEncodedLength(restrictions.getMaximumPacketSize(), 268435460L) + Mqtt5MessageEncoderUtil.shortPropertyEncodedLength(restrictions.getTopicAliasMaximum(), 0) + Mqtt5MessageEncoderUtil.booleanPropertyEncodedLength(restrictions.isRequestResponseInformation(), false) + Mqtt5MessageEncoderUtil.booleanPropertyEncodedLength(restrictions.isRequestProblemInformation(), true);
        }
        int iEncodedLength = iIntPropertyEncodedLength + mqttStatefulConnect.getUserProperties().encodedLength();
        MqttEnhancedAuth enhancedAuth = mqttStatefulConnect.getEnhancedAuth();
        return enhancedAuth != null ? iEncodedLength + Mqtt5MessageEncoderUtil.propertyEncodedLength(enhancedAuth.getMethod()) + Mqtt5MessageEncoderUtil.nullablePropertyEncodedLength(enhancedAuth.getRawData()) : iEncodedLength;
    }

    private int propertyLength(MqttStatefulConnect mqttStatefulConnect, int i, int i2) {
        if (i2 == 0) {
            return i;
        }
        if (i2 != 1) {
            return -1;
        }
        return i - mqttStatefulConnect.getUserProperties().encodedLength();
    }

    private int willPropertyLength(MqttStatefulConnect mqttStatefulConnect) {
        MqttWillPublish rawWillPublish = mqttStatefulConnect.stateless().getRawWillPublish();
        if (rawWillPublish == null) {
            return -1;
        }
        int iFixedPropertyLength = this.publishEncoder.fixedPropertyLength(rawWillPublish) + rawWillPublish.getUserProperties().encodedLength() + Mqtt5MessageEncoderUtil.intPropertyEncodedLength(rawWillPublish.getDelayInterval(), 0L);
        if (MqttVariableByteInteger.isInRange(iFixedPropertyLength)) {
            return iFixedPropertyLength;
        }
        throw new MqttEncodeException("Will properties exceeded maximum length.");
    }

    private void encode(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf, int i, int i2, int i3, int i4) {
        encodeFixedHeader(byteBuf, i);
        encodeVariableHeader(mqttStatefulConnect, byteBuf, i2, i4);
        encodePayload(mqttStatefulConnect, byteBuf, i3);
    }

    private void encodeFixedHeader(ByteBuf byteBuf, int i) {
        byteBuf.writeByte(FIXED_HEADER);
        MqttVariableByteInteger.encode(i, byteBuf);
    }

    private void encodeVariableHeader(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf, int i, int i2) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        MqttUtf8StringImpl.PROTOCOL_NAME.encode(byteBuf);
        byteBuf.writeByte(5);
        MqttSimpleAuth rawSimpleAuth = mqttConnectStateless.getRawSimpleAuth();
        int code = 0;
        if (rawSimpleAuth != null) {
            code = rawSimpleAuth.getRawUsername() != null ? 128 : 0;
            if (rawSimpleAuth.getRawPassword() != null) {
                code |= 64;
            }
        }
        MqttWillPublish rawWillPublish = mqttConnectStateless.getRawWillPublish();
        if (rawWillPublish != null) {
            if (rawWillPublish.isRetain()) {
                code |= 32;
            }
            code = (rawWillPublish.getQos().getCode() << 3) | code | 4;
        }
        if (mqttConnectStateless.isCleanStart()) {
            code |= 2;
        }
        byteBuf.writeByte(code);
        byteBuf.writeShort(mqttConnectStateless.getKeepAlive());
        encodeProperties(mqttStatefulConnect, byteBuf, i, i2);
    }

    private void encodeProperties(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf, int i, int i2) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        MqttVariableByteInteger.encode(i, byteBuf);
        Mqtt5MessageEncoderUtil.encodeIntProperty(17, mqttConnectStateless.getSessionExpiryInterval(), 0L, byteBuf);
        MqttEnhancedAuth enhancedAuth = mqttStatefulConnect.getEnhancedAuth();
        if (enhancedAuth != null) {
            Mqtt5MessageEncoderUtil.encodeProperty(21, enhancedAuth.getMethod(), byteBuf);
            Mqtt5MessageEncoderUtil.encodeNullableProperty(22, enhancedAuth.getRawData(), byteBuf);
        }
        MqttConnectRestrictions restrictions = mqttConnectStateless.getRestrictions();
        if (restrictions != MqttConnectRestrictions.DEFAULT) {
            Mqtt5MessageEncoderUtil.encodeShortProperty(33, restrictions.getReceiveMaximum(), 65535, byteBuf);
            Mqtt5MessageEncoderUtil.encodeIntProperty(39, restrictions.getMaximumPacketSize(), 268435460L, byteBuf);
            Mqtt5MessageEncoderUtil.encodeShortProperty(34, restrictions.getTopicAliasMaximum(), 0, byteBuf);
            Mqtt5MessageEncoderUtil.encodeBooleanProperty(25, restrictions.isRequestResponseInformation(), false, byteBuf);
            Mqtt5MessageEncoderUtil.encodeBooleanProperty(23, restrictions.isRequestProblemInformation(), true, byteBuf);
        }
        if (i2 == 0) {
            mqttStatefulConnect.getUserProperties().encode(byteBuf);
        }
    }

    private void encodePayload(MqttStatefulConnect mqttStatefulConnect, ByteBuf byteBuf, int i) {
        MqttConnect mqttConnectStateless = mqttStatefulConnect.stateless();
        mqttStatefulConnect.getClientIdentifier().encode(byteBuf);
        encodeWillPublish(mqttConnectStateless, byteBuf, i);
        MqttSimpleAuth rawSimpleAuth = mqttConnectStateless.getRawSimpleAuth();
        if (rawSimpleAuth != null) {
            MqttMessageEncoderUtil.encodeNullable(rawSimpleAuth.getRawUsername(), byteBuf);
            MqttMessageEncoderUtil.encodeNullable(rawSimpleAuth.getRawPassword(), byteBuf);
        }
    }

    private void encodeWillPublish(MqttConnect mqttConnect, ByteBuf byteBuf, int i) {
        MqttWillPublish rawWillPublish = mqttConnect.getRawWillPublish();
        if (rawWillPublish != null) {
            MqttVariableByteInteger.encode(i, byteBuf);
            this.publishEncoder.encodeFixedProperties(rawWillPublish, byteBuf);
            rawWillPublish.getUserProperties().encode(byteBuf);
            Mqtt5MessageEncoderUtil.encodeIntProperty(24, rawWillPublish.getDelayInterval(), 0L, byteBuf);
            rawWillPublish.getTopic().encode(byteBuf);
            MqttMessageEncoderUtil.encodeOrEmpty(rawWillPublish.getRawPayload(), byteBuf);
        }
    }
}
