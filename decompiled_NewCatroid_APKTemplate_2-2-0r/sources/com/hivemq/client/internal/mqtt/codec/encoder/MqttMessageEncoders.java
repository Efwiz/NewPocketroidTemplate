package com.hivemq.client.internal.mqtt.codec.encoder;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttMessageEncoders {
    protected final MqttMessageEncoder<?>[] encoders = new MqttMessageEncoder[16];

    public final MqttMessageEncoder<?> get(int i) {
        if (i < 0) {
            return null;
        }
        MqttMessageEncoder<?>[] mqttMessageEncoderArr = this.encoders;
        if (i >= mqttMessageEncoderArr.length) {
            return null;
        }
        return mqttMessageEncoderArr[i];
    }
}
