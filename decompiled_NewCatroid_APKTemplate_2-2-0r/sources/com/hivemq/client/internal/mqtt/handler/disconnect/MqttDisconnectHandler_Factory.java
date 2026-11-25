package com.hivemq.client.internal.mqtt.handler.disconnect;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.MqttSession;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttDisconnectHandler_Factory implements Factory<MqttDisconnectHandler> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttSession> sessionProvider;

    public MqttDisconnectHandler_Factory(Provider<MqttClientConfig> provider, Provider<MqttSession> provider2) {
        this.clientConfigProvider = provider;
        this.sessionProvider = provider2;
    }

    @Override // javax.inject.Provider
    public MqttDisconnectHandler get() {
        return newInstance(this.clientConfigProvider.get(), this.sessionProvider.get());
    }

    public static MqttDisconnectHandler_Factory create(Provider<MqttClientConfig> provider, Provider<MqttSession> provider2) {
        return new MqttDisconnectHandler_Factory(provider, provider2);
    }

    public static MqttDisconnectHandler newInstance(MqttClientConfig mqttClientConfig, MqttSession mqttSession) {
        return new MqttDisconnectHandler(mqttClientConfig, mqttSession);
    }
}
