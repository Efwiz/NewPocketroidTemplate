package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.internal.mqtt.message.MqttMessage;
import io.netty.buffer.ByteBuf;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttMessageDecoder {
    MqttMessage decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException;
}
