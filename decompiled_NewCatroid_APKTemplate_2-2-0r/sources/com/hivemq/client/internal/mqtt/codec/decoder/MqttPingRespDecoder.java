package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.internal.mqtt.message.ping.MqttPingResp;
import io.netty.buffer.ByteBuf;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class MqttPingRespDecoder implements MqttMessageDecoder {
    private static final int FLAGS = 0;
    private static final int REMAINING_LENGTH = 0;

    @Inject
    MqttPingRespDecoder() {
    }

    @Override // com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoder
    public MqttPingResp decode(int i, ByteBuf byteBuf, MqttDecoderContext mqttDecoderContext) throws MqttDecoderException {
        MqttMessageDecoderUtil.checkFixedHeaderFlags(0, i);
        MqttMessageDecoderUtil.checkRemainingLength(0, byteBuf.readableBytes());
        return MqttPingResp.INSTANCE;
    }
}
