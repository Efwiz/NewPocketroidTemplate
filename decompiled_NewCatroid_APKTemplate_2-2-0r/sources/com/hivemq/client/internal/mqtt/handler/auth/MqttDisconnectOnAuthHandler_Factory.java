package com.hivemq.client.internal.mqtt.handler.auth;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttDisconnectOnAuthHandler_Factory implements Factory<MqttDisconnectOnAuthHandler> {
    @Override // javax.inject.Provider
    public MqttDisconnectOnAuthHandler get() {
        return newInstance();
    }

    public static MqttDisconnectOnAuthHandler_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MqttDisconnectOnAuthHandler newInstance() {
        return new MqttDisconnectOnAuthHandler();
    }

    private static final class InstanceHolder {
        private static final MqttDisconnectOnAuthHandler_Factory INSTANCE = new MqttDisconnectOnAuthHandler_Factory();

        private InstanceHolder() {
        }
    }
}
