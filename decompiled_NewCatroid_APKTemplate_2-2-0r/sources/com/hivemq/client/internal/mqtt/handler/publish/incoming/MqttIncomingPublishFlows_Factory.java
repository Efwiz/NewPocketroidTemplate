package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttIncomingPublishFlows_Factory implements Factory<MqttIncomingPublishFlows> {
    @Override // javax.inject.Provider
    public MqttIncomingPublishFlows get() {
        return newInstance();
    }

    public static MqttIncomingPublishFlows_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MqttIncomingPublishFlows newInstance() {
        return new MqttIncomingPublishFlows();
    }

    private static final class InstanceHolder {
        private static final MqttIncomingPublishFlows_Factory INSTANCE = new MqttIncomingPublishFlows_Factory();

        private InstanceHolder() {
        }
    }
}
