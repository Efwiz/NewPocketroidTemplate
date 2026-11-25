package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoderContext;
import com.hivemq.client.internal.mqtt.datatypes.MqttVariableByteInteger;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.nio.ByteBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3PublishEncoder extends Mqtt3MessageEncoder<MqttStatefulPublish> {
    private static final int FIXED_HEADER = Mqtt3MessageType.PUBLISH.getCode() << 4;

    @Inject
    Mqtt3PublishEncoder() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public int remainingLength(MqttStatefulPublish mqttStatefulPublish) {
        MqttPublish mqttPublish = (MqttPublish) mqttStatefulPublish.stateless();
        int iEncodedLength = mqttPublish.getTopic().encodedLength();
        if (mqttPublish.getQos() != MqttQos.AT_MOST_ONCE) {
            iEncodedLength += 2;
        }
        ByteBuffer rawPayload = mqttPublish.getRawPayload();
        return rawPayload != null ? iEncodedLength + rawPayload.remaining() : iEncodedLength;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public ByteBuf encode(MqttStatefulPublish mqttStatefulPublish, MqttEncoderContext mqttEncoderContext, int i, int i2) {
        ByteBuffer rawPayload = ((MqttPublish) mqttStatefulPublish.stateless()).getRawPayload();
        if (rawPayload != null && rawPayload.isDirect()) {
            int iRemaining = i - rawPayload.remaining();
            ByteBuf byteBufIoBuffer = mqttEncoderContext.getAllocator().ioBuffer(iRemaining, iRemaining);
            encode(mqttStatefulPublish, byteBufIoBuffer, i2);
            return Unpooled.wrappedUnmodifiableBuffer(byteBufIoBuffer, Unpooled.wrappedBuffer(rawPayload));
        }
        ByteBuf byteBufIoBuffer2 = mqttEncoderContext.getAllocator().ioBuffer(i, i);
        encode(mqttStatefulPublish, byteBufIoBuffer2, i2);
        return byteBufIoBuffer2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3MessageEncoder
    public void encode(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf, int i) {
        encodeFixedHeader(mqttStatefulPublish, byteBuf, i);
        encodeVariableHeader(mqttStatefulPublish, byteBuf);
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
    private void encodeVariableHeader(MqttStatefulPublish mqttStatefulPublish, ByteBuf byteBuf) {
        MqttPublish mqttPublish = (MqttPublish) mqttStatefulPublish.stateless();
        mqttPublish.getTopic().encode(byteBuf);
        if (mqttPublish.getQos() != MqttQos.AT_MOST_ONCE) {
            byteBuf.writeShort(mqttStatefulPublish.getPacketIdentifier());
        }
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
