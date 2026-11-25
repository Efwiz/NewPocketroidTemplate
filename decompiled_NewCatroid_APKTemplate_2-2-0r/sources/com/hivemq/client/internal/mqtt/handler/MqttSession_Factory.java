package com.hivemq.client.internal.mqtt.handler;

import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttSession_Factory implements Factory<MqttSession> {
    private final Provider<MqttIncomingQosHandler> incomingQosHandlerProvider;
    private final Provider<MqttOutgoingQosHandler> outgoingQosHandlerProvider;
    private final Provider<MqttSubscriptionHandler> subscriptionHandlerProvider;

    public MqttSession_Factory(Provider<MqttSubscriptionHandler> provider, Provider<MqttIncomingQosHandler> provider2, Provider<MqttOutgoingQosHandler> provider3) {
        this.subscriptionHandlerProvider = provider;
        this.incomingQosHandlerProvider = provider2;
        this.outgoingQosHandlerProvider = provider3;
    }

    @Override // javax.inject.Provider
    public MqttSession get() {
        return newInstance(this.subscriptionHandlerProvider.get(), this.incomingQosHandlerProvider.get(), this.outgoingQosHandlerProvider.get());
    }

    public static MqttSession_Factory create(Provider<MqttSubscriptionHandler> provider, Provider<MqttIncomingQosHandler> provider2, Provider<MqttOutgoingQosHandler> provider3) {
        return new MqttSession_Factory(provider, provider2, provider3);
    }

    public static MqttSession newInstance(MqttSubscriptionHandler mqttSubscriptionHandler, MqttIncomingQosHandler mqttIncomingQosHandler, MqttOutgoingQosHandler mqttOutgoingQosHandler) {
        return new MqttSession(mqttSubscriptionHandler, mqttIncomingQosHandler, mqttOutgoingQosHandler);
    }
}
