package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoderContext;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoderUtil;
import com.hivemq.client.internal.mqtt.message.MqttMessage;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
abstract class Mqtt3MessageEncoder<M extends MqttMessage> extends MqttMessageEncoder<M> {
    abstract void encode(M m, ByteBuf byteBuf, int i);

    abstract int remainingLength(M m);

    Mqtt3MessageEncoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
    protected ByteBuf encode(M m, MqttEncoderContext mqttEncoderContext) {
        int iRemainingLength = remainingLength(m);
        int iEncodedPacketLength = MqttMessageEncoderUtil.encodedPacketLength(iRemainingLength);
        if (iEncodedPacketLength > mqttEncoderContext.getMaximumPacketSize()) {
            throw MqttMessageEncoderUtil.maximumPacketSizeExceeded(m, iEncodedPacketLength, mqttEncoderContext.getMaximumPacketSize());
        }
        return encode(m, mqttEncoderContext, iEncodedPacketLength, iRemainingLength);
    }

    ByteBuf encode(M m, MqttEncoderContext mqttEncoderContext, int i, int i2) {
        ByteBuf byteBufIoBuffer = mqttEncoderContext.getAllocator().ioBuffer(i, i);
        encode(m, byteBufIoBuffer, i2);
        return byteBufIoBuffer;
    }

    static abstract class Mqtt3MessageWithIdEncoder<M extends MqttMessage.WithId> extends MqttMessageEncoder<M> {
        private static final int ENCODED_LENGTH = 4;
        private static final int REMAINING_LENGTH = 2;

        abstract int getFixedHeader();

        Mqtt3MessageWithIdEncoder() {
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoder
        public ByteBuf encode(M m, MqttEncoderContext mqttEncoderContext) {
            if (4 > mqttEncoderContext.getMaximumPacketSize()) {
                throw MqttMessageEncoderUtil.maximumPacketSizeExceeded(m, 4, mqttEncoderContext.getMaximumPacketSize());
            }
            ByteBuf byteBufIoBuffer = mqttEncoderContext.getAllocator().ioBuffer(4, 4);
            encode(m, byteBufIoBuffer);
            return byteBufIoBuffer;
        }

        private void encode(M m, ByteBuf byteBuf) {
            encodeFixedHeader(byteBuf);
            encodeVariableHeader(m, byteBuf);
        }

        private void encodeFixedHeader(ByteBuf byteBuf) {
            byteBuf.writeByte(getFixedHeader());
            byteBuf.writeByte(2);
        }

        private void encodeVariableHeader(M m, ByteBuf byteBuf) {
            byteBuf.writeShort(m.getPacketIdentifier());
        }
    }
}
