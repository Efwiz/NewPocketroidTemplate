package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttOutgoingQosHandler_Factory implements Factory<MqttOutgoingQosHandler> {
    private final Provider<MqttClientConfig> clientConfigProvider;

    public MqttOutgoingQosHandler_Factory(Provider<MqttClientConfig> provider) {
        this.clientConfigProvider = provider;
    }

    @Override // javax.inject.Provider
    public MqttOutgoingQosHandler get() {
        return newInstance(this.clientConfigProvider.get());
    }

    public static MqttOutgoingQosHandler_Factory create(Provider<MqttClientConfig> provider) {
        return new MqttOutgoingQosHandler_Factory(provider);
    }

    public static MqttOutgoingQosHandler newInstance(MqttClientConfig mqttClientConfig) {
        return new MqttOutgoingQosHandler(mqttClientConfig);
    }
}
