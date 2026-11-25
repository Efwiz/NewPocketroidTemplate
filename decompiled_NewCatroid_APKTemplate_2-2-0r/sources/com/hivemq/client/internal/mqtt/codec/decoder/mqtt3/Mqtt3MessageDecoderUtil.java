package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoderException;

/* JADX INFO: loaded from: classes10.dex */
final class Mqtt3MessageDecoderUtil {
    static MqttDecoderException wrongReturnCode() {
        return new MqttDecoderException("wrong return code");
    }

    private Mqtt3MessageDecoderUtil() {
    }
}
