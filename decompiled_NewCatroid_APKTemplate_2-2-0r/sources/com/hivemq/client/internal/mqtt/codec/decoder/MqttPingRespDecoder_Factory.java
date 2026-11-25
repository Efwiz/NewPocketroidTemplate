package com.hivemq.client.internal.mqtt.codec.decoder;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttPingRespDecoder_Factory implements Factory<MqttPingRespDecoder> {
    @Override // javax.inject.Provider
    public MqttPingRespDecoder get() {
        return newInstance();
    }

    public static MqttPingRespDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static MqttPingRespDecoder newInstance() {
        return new MqttPingRespDecoder();
    }

    private static final class InstanceHolder {
        private static final MqttPingRespDecoder_Factory INSTANCE = new MqttPingRespDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
