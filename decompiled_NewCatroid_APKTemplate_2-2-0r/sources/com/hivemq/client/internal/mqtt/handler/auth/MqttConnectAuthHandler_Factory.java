package com.hivemq.client.internal.mqtt.handler.auth;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttConnectAuthHandler_Factory implements Factory<MqttConnectAuthHandler> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttConnect> connectProvider;

    public MqttConnectAuthHandler_Factory(Provider<MqttClientConfig> provider, Provider<MqttConnect> provider2) {
        this.clientConfigProvider = provider;
        this.connectProvider = provider2;
    }

    @Override // javax.inject.Provider
    public MqttConnectAuthHandler get() {
        return newInstance(this.clientConfigProvider.get(), this.connectProvider.get());
    }

    public static MqttConnectAuthHandler_Factory create(Provider<MqttClientConfig> provider, Provider<MqttConnect> provider2) {
        return new MqttConnectAuthHandler_Factory(provider, provider2);
    }

    public static MqttConnectAuthHandler newInstance(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect) {
        return new MqttConnectAuthHandler(mqttClientConfig, mqttConnect);
    }
}
