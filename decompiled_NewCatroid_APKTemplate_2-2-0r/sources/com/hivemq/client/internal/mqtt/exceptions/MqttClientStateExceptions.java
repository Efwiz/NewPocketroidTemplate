package com.hivemq.client.internal.mqtt.exceptions;

import com.hivemq.client.mqtt.exceptions.MqttClientStateException;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttClientStateExceptions {
    public static MqttClientStateException notConnected() {
        return new MqttClientStateException("MQTT client is not connected.");
    }

    public static MqttClientStateException alreadyConnected() {
        return new MqttClientStateException("MQTT client is already connected or connecting.");
    }

    private MqttClientStateExceptions() {
    }
}
