package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.datatypes.MqttBinaryData;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
final class Mqtt5MessageEncoderUtil {
    static int booleanPropertyEncodedLength(boolean z, boolean z2) {
        return z == z2 ? 0 : 2;
    }

    static int intPropertyEncodedLength(long j, long j2) {
        return j == j2 ? 0 : 5;
    }

    static int nullablePropertyEncodedLength(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator) {
        return mqtt5PayloadFormatIndicator == null ? 0 : 2;
    }

    static int shortPropertyEncodedLength(int i, int i2) {
        return i == i2 ? 0 : 3;
    }

    static int propertyEncodedLength(MqttUtf8StringImpl mqttUtf8StringImpl) {
        return mqttUtf8StringImpl.encodedLength() + 1;
    }

    static int nullablePropertyEncodedLength(MqttUtf8StringImpl mqttUtf8StringImpl) {
        if (mqttUtf8StringImpl == null) {
            return 0;
        }
        return propertyEncodedLength(mqttUtf8StringImpl);
    }

    static int nullablePropertyEncodedLength(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return 0;
        }
        return MqttBinaryData.encodedLength(byteBuffer) + 1;
    }

    static int variableByteIntegerPropertyEncodedLength(int i) {
        return MqttVariableByteInteger.encodedLength(i) + 1;
    }

    static int variableByteIntegerPropertyEncodedLength(int i, int i2) {
        if (i == i2) {
            return 0;
        }
        return variableByteIntegerPropertyEncodedLength(i);
    }

    static void encodeProperty(int i, MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf) {
        byteBuf.writeByte(i);
        mqttUtf8StringImpl.encode(byteBuf);
    }

    static void encodeNullableProperty(int i, MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf) {
        if (mqttUtf8StringImpl != null) {
            encodeProperty(i, mqttUtf8StringImpl, byteBuf);
        }
    }

    static void encodeNullableProperty(int i, ByteBuffer byteBuffer, ByteBuf byteBuf) {
        if (byteBuffer != null) {
            byteBuf.writeByte(i);
            MqttBinaryData.encode(byteBuffer, byteBuf);
        }
    }

    static void encodeNullableProperty(int i, Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator, ByteBuf byteBuf) {
        if (mqtt5PayloadFormatIndicator != null) {
            byteBuf.writeByte(i);
            byteBuf.writeByte(mqtt5PayloadFormatIndicator.getCode());
        }
    }

    static void encodeBooleanProperty(int i, boolean z, boolean z2, ByteBuf byteBuf) {
        if (z != z2) {
            byteBuf.writeByte(i);
            byteBuf.writeByte(z ? 1 : 0);
        }
    }

    static void encodeShortProperty(int i, int i2, int i3, ByteBuf byteBuf) {
        if (i2 != i3) {
            byteBuf.writeByte(i);
            byteBuf.writeShort(i2);
        }
    }

    static void encodeIntProperty(int i, long j, long j2, ByteBuf byteBuf) {
        if (j != j2) {
            byteBuf.writeByte(i);
            byteBuf.writeInt((int) j);
        }
    }

    static void encodeVariableByteIntegerProperty(int i, int i2, ByteBuf byteBuf) {
        byteBuf.writeByte(i);
        MqttVariableByteInteger.encode(i2, byteBuf);
    }

    static void encodeVariableByteIntegerProperty(int i, int i2, long j, ByteBuf byteBuf) {
        if (i2 != j) {
            encodeVariableByteIntegerProperty(i, i2, byteBuf);
        }
    }

    private Mqtt5MessageEncoderUtil() {
    }
}
