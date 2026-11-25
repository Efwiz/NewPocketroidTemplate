package com.hivemq.client.internal.mqtt.handler.websocket;

import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttWebSocketInitializer_Factory implements Factory<MqttWebSocketInitializer> {
    private final Provider<MqttWebSocketCodec> mqttWebSocketCodecProvider;

    public MqttWebSocketInitializer_Factory(Provider<MqttWebSocketCodec> provider) {
        this.mqttWebSocketCodecProvider = provider;
    }

    @Override // javax.inject.Provider
    public MqttWebSocketInitializer get() {
        return newInstance(this.mqttWebSocketCodecProvider.get());
    }

    public static MqttWebSocketInitializer_Factory create(Provider<MqttWebSocketCodec> provider) {
        return new MqttWebSocketInitializer_Factory(provider);
    }

    public static MqttWebSocketInitializer newInstance(MqttWebSocketCodec mqttWebSocketCodec) {
        return new MqttWebSocketInitializer(mqttWebSocketCodec);
    }
}
