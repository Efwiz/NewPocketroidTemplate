package com.hivemq.client.internal.mqtt.codec.encoder;

import com.hivemq.client.internal.mqtt.message.MqttMessage;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttMessageEncoder<M extends MqttMessage> {
    protected abstract ByteBuf encode(M m, MqttEncoderContext mqttEncoderContext);

    ByteBuf castAndEncode(MqttMessage mqttMessage, MqttEncoderContext mqttEncoderContext) {
        return encode(mqttMessage, mqttEncoderContext);
    }
}
