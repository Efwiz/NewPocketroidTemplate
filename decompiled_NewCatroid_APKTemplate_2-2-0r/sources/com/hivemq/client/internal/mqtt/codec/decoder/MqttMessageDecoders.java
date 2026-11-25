package com.hivemq.client.internal.mqtt.codec.decoder;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttMessageDecoders {
    protected final MqttMessageDecoder[] decoders = new MqttMessageDecoder[16];

    public final MqttMessageDecoder get(int i) {
        if (i < 0) {
            return null;
        }
        MqttMessageDecoder[] mqttMessageDecoderArr = this.decoders;
        if (i >= mqttMessageDecoderArr.length) {
            return null;
        }
        return mqttMessageDecoderArr[i];
    }
}
