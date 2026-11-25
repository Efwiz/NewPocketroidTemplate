package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import io.netty.buffer.ByteBuf;
import java.nio.ByteBuffer;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3PublishDecoder implements MqttMessageDecoder {
    private static final int MIN_REMAINING_LENGTH = 2;

    @Inject
    Mqtt3PublishDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttStatefulPublish decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        ByteBuffer byteBufferAllocate;
        boolean z = (i & 8) != 0;
        MqttQos mqttQosDecodePublishQos = MqttMessageDecoderUtil.decodePublishQos(i, z);
        boolean z2 = (i & 1) != 0;
        if (byteBuf.readableBytes() < 2) {
            throw MqttMessageDecoderUtil.remainingLengthTooShort();
        }
        MqttTopicImpl mqttTopicImplDecode = MqttTopicImpl.decode(byteBuf);
        if (mqttTopicImplDecode == null) {
            throw MqttMessageDecoderUtil.malformedTopic();
        }
        int iDecodePublishPacketIdentifier = MqttMessageDecoderUtil.decodePublishPacketIdentifier(mqttQosDecodePublishQos, byteBuf);
        int i2 = byteBuf.readableBytes();
        if (i2 > 0) {
            byteBufferAllocate = ByteBufferUtil.allocate(i2, mqttDecoderContext.useDirectBufferPayload());
            byteBuf.readBytes(byteBufferAllocate);
            byteBufferAllocate.position(0);
        } else {
            byteBufferAllocate = null;
        }
        return Mqtt3PublishView.statefulDelegate(Mqtt3PublishView.delegate(mqttTopicImplDecode, byteBufferAllocate, mqttQosDecodePublishQos, z2), iDecodePublishPacketIdentifier, z);
    }
}
