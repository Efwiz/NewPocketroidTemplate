package com.hivemq.client.internal.mqtt.codec.encoder;

import com.hivemq.client.internal.mqtt.datatypes.MqttBinaryData;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.MqttMessage;
import com.hivemq.client.mqtt.exceptions.MqttEncodeException;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttMessageEncoderUtil {
    public static int encodedPacketLength(int i) {
        return encodedLengthWithHeader(i) + 1;
    }

    public static int encodedLengthWithHeader(int i) {
        return MqttVariableByteInteger.encodedLength(i) + i;
    }

    public static int nullableEncodedLength(MqttUtf8StringImpl mqttUtf8StringImpl) {
        if (mqttUtf8StringImpl == null) {
            return 0;
        }
        return mqttUtf8StringImpl.encodedLength();
    }

    public static int nullableEncodedLength(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return 0;
        }
        return MqttBinaryData.encodedLength(byteBuffer);
    }

    public static int encodedOrEmptyLength(ByteBuffer byteBuffer) {
        if (byteBuffer == null) {
            return 2;
        }
        return MqttBinaryData.encodedLength(byteBuffer);
    }

    public static void encodeNullable(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuf byteBuf) {
        if (mqttUtf8StringImpl != null) {
            mqttUtf8StringImpl.encode(byteBuf);
        }
    }

    public static void encodeNullable(ByteBuffer byteBuffer, ByteBuf byteBuf) {
        if (byteBuffer != null) {
            MqttBinaryData.encode(byteBuffer, byteBuf);
        }
    }

    public static void encodeOrEmpty(ByteBuffer byteBuffer, ByteBuf byteBuf) {
        if (byteBuffer != null) {
            MqttBinaryData.encode(byteBuffer, byteBuf);
        } else {
            MqttBinaryData.encodeEmpty(byteBuf);
        }
    }

    public static MqttEncodeException maximumPacketSizeExceeded(MqttMessage mqttMessage, int i, int i2) {
        return new MqttEncodeException(mqttMessage.getType() + " exceeded maximum packet size, minimal possible encoded length: " + i + ", maximum: " + i2 + ".");
    }

    private MqttMessageEncoderUtil() {
    }
}
