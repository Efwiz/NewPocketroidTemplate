package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlows;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttSubscriptionHandler_Factory implements Factory<MqttSubscriptionHandler> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttIncomingPublishFlows> incomingPublishFlowsProvider;

    public MqttSubscriptionHandler_Factory(Provider<MqttClientConfig> provider, Provider<MqttIncomingPublishFlows> provider2) {
        this.clientConfigProvider = provider;
        this.incomingPublishFlowsProvider = provider2;
    }

    @Override // javax.inject.Provider
    public MqttSubscriptionHandler get() {
        return newInstance(this.clientConfigProvider.get(), this.incomingPublishFlowsProvider.get());
    }

    public static MqttSubscriptionHandler_Factory create(Provider<MqttClientConfig> provider, Provider<MqttIncomingPublishFlows> provider2) {
        return new MqttSubscriptionHandler_Factory(provider, provider2);
    }

    public static MqttSubscriptionHandler newInstance(MqttClientConfig mqttClientConfig, MqttIncomingPublishFlows mqttIncomingPublishFlows) {
        return new MqttSubscriptionHandler(mqttClientConfig, mqttIncomingPublishFlows);
    }
}
