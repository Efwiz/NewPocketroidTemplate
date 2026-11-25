package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttIncomingQosHandler_Factory implements Factory<MqttIncomingQosHandler> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttIncomingPublishFlows> incomingPublishFlowsProvider;

    public MqttIncomingQosHandler_Factory(Provider<MqttClientConfig> provider, Provider<MqttIncomingPublishFlows> provider2) {
        this.clientConfigProvider = provider;
        this.incomingPublishFlowsProvider = provider2;
    }

    @Override // javax.inject.Provider
    public MqttIncomingQosHandler get() {
        return newInstance(this.clientConfigProvider.get(), this.incomingPublishFlowsProvider.get());
    }

    public static MqttIncomingQosHandler_Factory create(Provider<MqttClientConfig> provider, Provider<MqttIncomingPublishFlows> provider2) {
        return new MqttIncomingQosHandler_Factory(provider, provider2);
    }

    public static MqttIncomingQosHandler newInstance(MqttClientConfig mqttClientConfig, MqttIncomingPublishFlows mqttIncomingPublishFlows) {
        return new MqttIncomingQosHandler(mqttClientConfig, mqttIncomingPublishFlows);
    }
}
