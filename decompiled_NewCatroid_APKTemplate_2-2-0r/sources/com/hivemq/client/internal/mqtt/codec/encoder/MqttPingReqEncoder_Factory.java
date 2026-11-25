package com.hivemq.client.internal.mqtt.codec.encoder;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttPingReqEncoder_Factory implements Factory<MqttPingReqEncoder> {
    @Override // javax.inject.Provider
    public MqttPingReqEncoder get() {
        return newInstance();
    }

    public static MqttPingReqEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MqttPingReqEncoder newInstance() {
        return new MqttPingReqEncoder();
    }

    private static final class InstanceHolder {
        private static final MqttPingReqEncoder_Factory INSTANCE = new MqttPingReqEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
