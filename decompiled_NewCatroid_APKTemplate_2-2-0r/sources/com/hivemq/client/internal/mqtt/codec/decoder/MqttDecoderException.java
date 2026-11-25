package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class MqttDecoderException extends Exception {
    private final Mqtt5DisconnectReasonCode reasonCode;

    public MqttDecoderException(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode, String str) {
        super(str, null, false, false);
        this.reasonCode = mqtt5DisconnectReasonCode;
    }

    public MqttDecoderException(String str) {
        this(Mqtt5DisconnectReasonCode.MALFORMED_PACKET, str);
    }

    public Mqtt5DisconnectReasonCode getReasonCode() {
        return this.reasonCode;
    }
}
