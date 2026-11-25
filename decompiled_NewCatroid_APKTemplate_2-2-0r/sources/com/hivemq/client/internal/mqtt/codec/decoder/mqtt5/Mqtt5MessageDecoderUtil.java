package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttBinaryData;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertyImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
final class Mqtt5MessageDecoderUtil {
    static MqttDecoderException wrongReasonCode() {
        return new MqttDecoderException("wrong reason code");
    }

    static MqttDecoderException malformedPropertyLength() {
        return new MqttDecoderException("malformed properties length");
    }

    static MqttDecoderException wrongProperty(int i) {
        return new MqttDecoderException("wrong property with identifier " + i);
    }

    static MqttDecoderException moreThanOnce(String str) {
        return new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, str + " must not be included more than once");
    }

    static MqttDecoderException noReasonCodes() {
        return new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "must contain at least one reason code");
    }

    static void checkPropertyLengthNoPayload(ByteBuf byteBuf) throws MqttDecoderException {
        int iDecode = MqttVariableByteInteger.decode(byteBuf);
        if (iDecode < 0) {
            throw malformedPropertyLength();
        }
        if (byteBuf.readableBytes() != iDecode) {
            if (byteBuf.readableBytes() < iDecode) {
                throw MqttMessageDecoderUtil.remainingLengthTooShort();
            }
            throw new MqttDecoderException("must not have a payload");
        }
    }

    static int decodePropertyLength(ByteBuf byteBuf) throws MqttDecoderException {
        int iDecode = MqttVariableByteInteger.decode(byteBuf);
        if (iDecode < 0) {
            throw malformedPropertyLength();
        }
        if (byteBuf.readableBytes() >= iDecode) {
            return iDecode;
        }
        throw MqttMessageDecoderUtil.remainingLengthTooShort();
    }

    static int decodePropertyIdentifier(ByteBuf byteBuf) throws MqttDecoderException {
        int iDecode = MqttVariableByteInteger.decode(byteBuf);
        if (iDecode >= 0) {
            return iDecode;
        }
        throw new MqttDecoderException("malformed property identifier");
    }

    static boolean booleanOnlyOnce(boolean z, String str, ByteBuf byteBuf) throws MqttDecoderException {
        if (z) {
            throw moreThanOnce(str);
        }
        if (byteBuf.readableBytes() < 1) {
            throw malformedPropertyLength();
        }
        byte b = byteBuf.readByte();
        if (b == 0) {
            return false;
        }
        if (b == 1) {
            return true;
        }
        throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "malformed boolean for " + str);
    }

    static short unsignedByteOnlyOnce(boolean z, String str, ByteBuf byteBuf) throws MqttDecoderException {
        if (z) {
            throw moreThanOnce(str);
        }
        if (byteBuf.readableBytes() < 1) {
            throw malformedPropertyLength();
        }
        return byteBuf.readUnsignedByte();
    }

    static int unsignedShortOnlyOnce(boolean z, String str, ByteBuf byteBuf) throws MqttDecoderException {
        if (z) {
            throw moreThanOnce(str);
        }
        if (byteBuf.readableBytes() < 2) {
            throw malformedPropertyLength();
        }
        return byteBuf.readUnsignedShort();
    }

    static int unsignedShortOnlyOnce(int i, int i2, String str, ByteBuf byteBuf) throws MqttDecoderException {
        return unsignedShortOnlyOnce(i != i2, str, byteBuf);
    }

    static long unsignedIntOnlyOnce(boolean z, String str, ByteBuf byteBuf) throws MqttDecoderException {
        if (z) {
            throw moreThanOnce(str);
        }
        if (byteBuf.readableBytes() < 4) {
            throw malformedPropertyLength();
        }
        return byteBuf.readUnsignedInt();
    }

    static long unsignedIntOnlyOnce(long j, long j2, String str, ByteBuf byteBuf) throws MqttDecoderException {
        return unsignedIntOnlyOnce(j != j2, str, byteBuf);
    }

    static ByteBuffer decodeBinaryDataOnlyOnce(ByteBuffer byteBuffer, String str, ByteBuf byteBuf, boolean z) throws MqttDecoderException {
        if (byteBuffer != null) {
            throw moreThanOnce(str);
        }
        ByteBuffer byteBufferDecode = MqttBinaryData.decode(byteBuf, z);
        if (byteBufferDecode != null) {
            return byteBufferDecode;
        }
        throw new MqttDecoderException("malformed binary data for " + str);
    }

    static MqttUtf8StringImpl decodeUTF8StringOnlyOnce(MqttUtf8StringImpl mqttUtf8StringImpl, String str, ByteBuf byteBuf) throws MqttDecoderException {
        if (mqttUtf8StringImpl != null) {
            throw moreThanOnce(str);
        }
        MqttUtf8StringImpl mqttUtf8StringImplDecode = MqttUtf8StringImpl.decode(byteBuf);
        if (mqttUtf8StringImplDecode != null) {
            return mqttUtf8StringImplDecode;
        }
        throw MqttMessageDecoderUtil.malformedUTF8String(str);
    }

    static MqttUtf8StringImpl decodeReasonString(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf) throws MqttDecoderException {
        return decodeUTF8StringOnlyOnce(mqttUtf8StringImpl, "reason string", byteBuf);
    }

    static ImmutableList.Builder<MqttUserPropertyImpl> decodeUserProperty(ImmutableList.Builder<MqttUserPropertyImpl> builder, ByteBuf byteBuf) throws MqttDecoderException {
        MqttUserPropertyImpl mqttUserPropertyImplDecode = MqttUserPropertyImpl.decode(byteBuf);
        if (mqttUserPropertyImplDecode == null) {
            throw new MqttDecoderException("malformed user property");
        }
        if (builder == null) {
            builder = ImmutableList.builder();
        }
        builder.add(mqttUserPropertyImplDecode);
        return builder;
    }

    private static void checkProblemInformationRequested(String str, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        if (!mqttDecoderContext.isProblemInformationRequested()) {
            throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, str + " must not be included if problem information is not requested");
        }
    }

    static MqttUtf8StringImpl decodeReasonStringIfRequested(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        checkProblemInformationRequested("reason string", mqttDecoderContext);
        return decodeReasonString(mqttUtf8StringImpl, byteBuf);
    }

    static ImmutableList.Builder<MqttUserPropertyImpl> decodeUserPropertyIfRequested(ImmutableList.Builder<MqttUserPropertyImpl> builder, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        checkProblemInformationRequested("user property", mqttDecoderContext);
        return decodeUserProperty(builder, byteBuf);
    }

    static MqttUtf8StringImpl decodeAuthMethod(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf) throws MqttDecoderException {
        return decodeUTF8StringOnlyOnce(mqttUtf8StringImpl, "auth method", byteBuf);
    }

    static ByteBuffer decodeAuthData(ByteBuffer byteBuffer, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        return decodeBinaryDataOnlyOnce(byteBuffer, "auth data", byteBuf, mqttDecoderContext.useDirectBufferAuth());
    }

    static MqttUtf8StringImpl decodeServerReference(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf) throws MqttDecoderException {
        return decodeUTF8StringOnlyOnce(mqttUtf8StringImpl, "server reference", byteBuf);
    }

    static long decodeSessionExpiryInterval(long j, ByteBuf byteBuf) throws MqttDecoderException {
        return unsignedIntOnlyOnce(j, -1L, "session expiry interval", byteBuf);
    }

    private Mqtt5MessageDecoderUtil() {
    }
}
