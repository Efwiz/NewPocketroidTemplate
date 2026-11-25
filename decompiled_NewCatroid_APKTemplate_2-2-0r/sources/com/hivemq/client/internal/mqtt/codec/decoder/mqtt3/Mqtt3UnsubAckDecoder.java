package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderContext;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoderUtil;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.MqttUnsubAck;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.mqtt3.Mqtt3UnsubAckView;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3UnsubAckDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int REMAINING_LENGTH = 2;

    @Inject
    Mqtt3UnsubAckDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttUnsubAck decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        MqttMessageDecoderUtil.checkRemainingLength(2, byteBuf.readableBytes());
        return Mqtt3UnsubAckView.delegate(byteBuf.readUnsignedShort());
    }
}
