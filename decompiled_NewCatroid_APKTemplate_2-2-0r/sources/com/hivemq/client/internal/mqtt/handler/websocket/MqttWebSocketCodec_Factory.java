package com.hivemq.client.internal.mqtt.handler.websocket;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttWebSocketCodec_Factory implements Factory<MqttWebSocketCodec> {
    @Override // javax.inject.Provider
    public MqttWebSocketCodec get() {
        return newInstance();
    }

    public static MqttWebSocketCodec_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MqttWebSocketCodec newInstance() {
        return new MqttWebSocketCodec();
    }

    private static final class InstanceHolder {
        private static final MqttWebSocketCodec_Factory INSTANCE = new MqttWebSocketCodec_Factory();

        private InstanceHolder() {
        }
    }
}
