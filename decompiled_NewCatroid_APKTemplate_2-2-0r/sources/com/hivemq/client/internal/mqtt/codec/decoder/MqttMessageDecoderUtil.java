package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttMessageDecoderUtil {
    public static void checkFixedHeaderFlags(int i, int i2) throws MqttDecoderException {
        if (i != i2) {
            throw new MqttDecoderException("fixed header flags must be " + i + " but were " + i2);
        }
    }

    public static void checkRemainingLength(int i, int i2) throws MqttDecoderException {
        if (i != i2) {
            throw new MqttDecoderException("remaining length must be " + i + " but was " + i2);
        }
    }

    public static MqttDecoderException remainingLengthTooShort() {
        return new MqttDecoderException("remaining length too short");
    }

    public static MqttDecoderException malformedUTF8String(String str) {
        return new MqttDecoderException("malformed UTF-8 string for " + str);
    }

    public static MqttDecoderException malformedTopic() {
        return new MqttDecoderException(Mqtt5DisconnectReasonCode.TOPIC_NAME_INVALID, "malformed topic");
    }

    public static MqttQos decodePublishQos(int i, boolean z) throws MqttDecoderException {
        MqttQos mqttQosFromCode = MqttQos.fromCode((i & 6) >> 1);
        if (mqttQosFromCode == null) {
            throw new MqttDecoderException("wrong QoS");
        }
        if (mqttQosFromCode == MqttQos.AT_MOST_ONCE && z) {
            throw new MqttDecoderException(Mqtt5DisconnectReasonCode.PROTOCOL_ERROR, "DUP flag must be 0 if QoS is 0");
        }
        return mqttQosFromCode;
    }

    public static int decodePublishPacketIdentifier(MqttQos mqttQos, ByteBuf byteBuf) throws MqttDecoderException {
        if (mqttQos == MqttQos.AT_MOST_ONCE) {
            return -1;
        }
        if (byteBuf.readableBytes() < 2) {
            throw remainingLengthTooShort();
        }
        return byteBuf.readUnsignedShort();
    }

    private MqttMessageDecoderUtil() {
    }
}
