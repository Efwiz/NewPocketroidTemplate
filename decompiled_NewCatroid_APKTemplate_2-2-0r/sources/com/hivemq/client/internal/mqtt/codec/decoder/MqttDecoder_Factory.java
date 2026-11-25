package com.hivemq.client.internal.mqtt.codec.decoder;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttDecoder_Factory implements Factory<MqttDecoder> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttConnect> connectProvider;
    private final Provider<MqttMessageDecoders> decodersProvider;

    public MqttDecoder_Factory(Provider<MqttMessageDecoders> provider, Provider<MqttClientConfig> provider2, Provider<MqttConnect> provider3) {
        this.decodersProvider = provider;
        this.clientConfigProvider = provider2;
        this.connectProvider = provider3;
    }

    @Override // javax.inject.Provider
    public MqttDecoder get() {
        return newInstance(this.decodersProvider.get(), this.clientConfigProvider.get(), this.connectProvider.get());
    }

    public static MqttDecoder_Factory create(Provider<MqttMessageDecoders> provider, Provider<MqttClientConfig> provider2, Provider<MqttConnect> provider3) {
        return new MqttDecoder_Factory(provider, provider2, provider3);
    }

    public static MqttDecoder newInstance(MqttMessageDecoders mqttMessageDecoders, MqttClientConfig mqttClientConfig, MqttConnect mqttConnect) {
        return new MqttDecoder(mqttMessageDecoders, mqttClientConfig, mqttConnect);
    }
}
