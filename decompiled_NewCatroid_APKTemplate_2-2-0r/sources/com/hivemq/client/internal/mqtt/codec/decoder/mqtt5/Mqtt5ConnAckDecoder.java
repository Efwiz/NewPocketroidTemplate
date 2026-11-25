package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.auth.MqttEnhancedAuth;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAckRestrictions;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAckReturnCode;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5ConnAckDecoder implements MqttMessageDecoder {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final int FLAGS = 0;
    private static final int MIN_REMAINING_LENGTH = 3;

    @Inject
    Mqtt5ConnAckDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttConnAck decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttEnhancedAuth mqttEnhancedAuth;
        boolean z;
        boolean z2;
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        if (byteBuf.readableBytes() < 3) {
            return tryDecodeMqtt3(byteBuf);
        }
        short unsignedByte = byteBuf.readUnsignedByte();
        if ((unsignedByte & 254) != 0) {
            throw new MqttDecoderException("wrong CONNACK flags, bits 7-1 must be 0");
        }
        boolean z3 = (unsignedByte & 1) != 0;
        Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCodeFromCode = Mqtt5ConnAckReasonCode.fromCode(byteBuf.readUnsignedByte());
        if (mqtt5ConnAckReasonCodeFromCode == null) {
            throw Mqtt5MessageDecoderUtil.wrongReasonCode();
        }
        if (mqtt5ConnAckReasonCodeFromCode != Mqtt5ConnAckReasonCode.SUCCESS && z3) {
            throw new MqttDecoderException("session present must be 0 if reason code is not SUCCESS");
        }
        Mqtt5MessageDecoderUtil.checkPropertyLengthNoPayload(byteBuf);
        long jDecodeSessionExpiryInterval = -1;
        boolean z4 = false;
        int iUnsignedShortOnlyOnce = 0;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        boolean z8 = false;
        boolean z9 = false;
        boolean z10 = false;
        boolean z11 = false;
        MqttQos mqttQos = Mqtt5ConnAckRestrictions.DEFAULT_MAXIMUM_QOS;
        boolean z12 = true;
        boolean z13 = true;
        boolean z14 = true;
        boolean z15 = true;
        int i2 = 268435460;
        int iUnsignedShortOnlyOnce2 = -1;
        ByteBuffer byteBufferDecodeAuthData = null;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeAuthMethod = null;
        MqttClientIdentifierImpl mqttClientIdentifierImplDecode = null;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeServerReference = null;
        int iUnsignedShortOnlyOnce3 = 65535;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeUTF8StringOnlyOnce = null;
        ImmutableList.Builder<MqttUserPropertyImpl> builderDecodeUserProperty = null;
        boolean z16 = false;
        MqttUtf8StringImpl mqttUtf8StringImplDecodeReasonString = null;
        while (byteBuf.isReadable()) {
            int iDecodePropertyIdentifier = Mqtt5MessageDecoderUtil.decodePropertyIdentifier(byteBuf);
            switch (iDecodePropertyIdentifier) {
                case 17:
                    z = z5;
                    jDecodeSessionExpiryInterval = Mqtt5MessageDecoderUtil.decodeSessionExpiryInterval(jDecodeSessionExpiryInterval, byteBuf);
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 18:
                    z = z5;
                    if (mqttClientIdentifierImplDecode != null) {
                        throw Mqtt5MessageDecoderUtil.moreThanOnce("client identifier");
                    }
                    mqttClientIdentifierImplDecode = MqttClientIdentifierImpl.decode(byteBuf);
                    if (mqttClientIdentifierImplDecode == null) {
                        throw MqttMessageDecoderUtil.malformedUTF8String("client identifier");
                    }
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                    break;
                case 19:
                    z = z5;
                    iUnsignedShortOnlyOnce2 = Mqtt5MessageDecoderUtil.unsignedShortOnlyOnce(iUnsignedShortOnlyOnce2, -1, "server keep alive", byteBuf);
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 20:
                case 23:
                case 24:
                case 25:
                case 27:
                case 29:
                case 30:
                case 32:
                case 35:
                default:
                    throw Mqtt5MessageDecoderUtil.wrongProperty(iDecodePropertyIdentifier);
                case 21:
                    mqttUtf8StringImplDecodeAuthMethod = Mqtt5MessageDecoderUtil.decodeAuthMethod(mqttUtf8StringImplDecodeAuthMethod, byteBuf);
                    z = z5;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 22:
                    byteBufferDecodeAuthData = Mqtt5MessageDecoderUtil.decodeAuthData(byteBufferDecodeAuthData, byteBuf, mqttDecoderContext);
                    z = z5;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 26:
                    if (!mqttDecoderContext.isResponseInformationRequested()) {
                        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "response information must not be included if it was not requested");
                    }
                    mqttUtf8StringImplDecodeUTF8StringOnlyOnce = Mqtt5MessageDecoderUtil.decodeUTF8StringOnlyOnce(mqttUtf8StringImplDecodeUTF8StringOnlyOnce, "response information", byteBuf);
                    z = z5;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                    break;
                case 28:
                    mqttUtf8StringImplDecodeServerReference = Mqtt5MessageDecoderUtil.decodeServerReference(mqttUtf8StringImplDecodeServerReference, byteBuf);
                    z = z5;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 31:
                    mqttUtf8StringImplDecodeReasonString = Mqtt5MessageDecoderUtil.decodeReasonString(mqttUtf8StringImplDecodeReasonString, byteBuf);
                    z = z5;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 33:
                    z16 = z16;
                    z3 = z3;
                    boolean z17 = z5;
                    z8 = z8;
                    z9 = z9;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    iUnsignedShortOnlyOnce3 = Mqtt5MessageDecoderUtil.unsignedShortOnlyOnce(z4, "receive maximum", byteBuf);
                    if (iUnsignedShortOnlyOnce3 == 0) {
                        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "receive maximum must not be 0");
                    }
                    z6 |= iUnsignedShortOnlyOnce3 != 65535;
                    z = z17;
                    z4 = true;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                    break;
                case 34:
                    z16 = z16;
                    z3 = z3;
                    z8 = z8;
                    z9 = z9;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    iUnsignedShortOnlyOnce = Mqtt5MessageDecoderUtil.unsignedShortOnlyOnce(z5, "receive maximum", byteBuf);
                    z6 |= iUnsignedShortOnlyOnce != 0;
                    z = true;
                    z2 = z9;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 36:
                    z16 = z16;
                    z3 = z3;
                    z8 = z8;
                    boolean z18 = z9;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    short sUnsignedByteOnlyOnce = Mqtt5MessageDecoderUtil.unsignedByteOnlyOnce(z7, "maximum Qos", byteBuf);
                    if (sUnsignedByteOnlyOnce != 0 && sUnsignedByteOnlyOnce != 1) {
                        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "wrong maximum Qos");
                    }
                    MqttQos mqttQosFromCode = MqttQos.fromCode(sUnsignedByteOnlyOnce);
                    z6 |= mqttQosFromCode != Mqtt5ConnAckRestrictions.DEFAULT_MAXIMUM_QOS;
                    mqttQos = mqttQosFromCode;
                    z = z5;
                    z2 = z18;
                    z7 = true;
                    z8 = z8;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                    break;
                case 37:
                    z16 = z16;
                    z3 = z3;
                    boolean z19 = z9;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    boolean zBooleanOnlyOnce = Mqtt5MessageDecoderUtil.booleanOnlyOnce(z8, "retain available", byteBuf);
                    z6 |= !zBooleanOnlyOnce;
                    z12 = zBooleanOnlyOnce;
                    z2 = z19;
                    z8 = true;
                    z = z5;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 38:
                    z2 = z9;
                    builderDecodeUserProperty = Mqtt5MessageDecoderUtil.decodeUserProperty(builderDecodeUserProperty, byteBuf);
                    z = z5;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 39:
                    long jUnsignedIntOnlyOnce = Mqtt5MessageDecoderUtil.unsignedIntOnlyOnce(z9, "maximum packet size", byteBuf);
                    if (jUnsignedIntOnlyOnce == 0) {
                        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "maximum packet size must not be 0");
                    }
                    if (jUnsignedIntOnlyOnce < 268435460) {
                        i2 = (int) jUnsignedIntOnlyOnce;
                        z6 = true;
                    }
                    z2 = true;
                    z = z5;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                    break;
                case 40:
                    boolean zBooleanOnlyOnce2 = Mqtt5MessageDecoderUtil.booleanOnlyOnce(z10, "wildcard subscription available", byteBuf);
                    z6 |= !zBooleanOnlyOnce2;
                    z13 = zBooleanOnlyOnce2;
                    z2 = z9;
                    z10 = true;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    z = z5;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 41:
                    z16 = z16;
                    z3 = z3;
                    boolean zBooleanOnlyOnce3 = Mqtt5MessageDecoderUtil.booleanOnlyOnce(z11, "subscription identifier available", byteBuf);
                    z6 |= !zBooleanOnlyOnce3;
                    z11 = true;
                    z15 = zBooleanOnlyOnce3;
                    z2 = z9;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    z = z5;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
                case 42:
                    boolean zBooleanOnlyOnce4 = Mqtt5MessageDecoderUtil.booleanOnlyOnce(z16, "shared subscription available", byteBuf);
                    z6 |= !zBooleanOnlyOnce4;
                    z14 = zBooleanOnlyOnce4;
                    z16 = true;
                    z3 = z3;
                    z2 = z9;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    z = z5;
                    z5 = z;
                    z16 = z16;
                    z3 = z3;
                    builderDecodeUserProperty = builderDecodeUserProperty;
                    mqtt5ConnAckReasonCodeFromCode = mqtt5ConnAckReasonCodeFromCode;
                    z9 = z2;
                    break;
            }
        }
        Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode = mqtt5ConnAckReasonCodeFromCode;
        boolean z20 = z3;
        ImmutableList.Builder<MqttUserPropertyImpl> builder = builderDecodeUserProperty;
        if (mqttUtf8StringImplDecodeAuthMethod != null) {
            mqttEnhancedAuth = new MqttEnhancedAuth(mqttUtf8StringImplDecodeAuthMethod, byteBufferDecodeAuthData);
        } else {
            if (byteBufferDecodeAuthData != null) {
                throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "auth data must not be included if auth method is absent");
            }
            mqttEnhancedAuth = null;
        }
        return new MqttConnAck(mqtt5ConnAckReasonCode, z20, jDecodeSessionExpiryInterval, iUnsignedShortOnlyOnce2, mqttClientIdentifierImplDecode, mqttEnhancedAuth, z6 ? new MqttConnAckRestrictions(iUnsignedShortOnlyOnce3, i2, iUnsignedShortOnlyOnce, mqttQos, z12, z13, z14, z15) : MqttConnAckRestrictions.DEFAULT, mqttUtf8StringImplDecodeUTF8StringOnlyOnce, mqttUtf8StringImplDecodeServerReference, mqttUtf8StringImplDecodeReasonString, MqttUserPropertiesImpl.build(builder));
    }

    public MqttConnAck tryDecodeMqtt3(ByteBuf byteBuf) throws MqttDecoderException {
        if (byteBuf.readableBytes() == 2) {
            byteBuf.readUnsignedByte();
            if (Mqtt3ConnAckReturnCode.fromCode(byteBuf.readUnsignedByte()) == Mqtt3ConnAckReturnCode.UNSUPPORTED_PROTOCOL_VERSION) {
                return new MqttConnAck(Mqtt5ConnAckReasonCode.UNSUPPORTED_PROTOCOL_VERSION, false, -1L, -1, null, null, MqttConnAckRestrictions.DEFAULT, null, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
            }
        }
        throw MqttMessageDecoderUtil.remainingLengthTooShort();
    }
}
