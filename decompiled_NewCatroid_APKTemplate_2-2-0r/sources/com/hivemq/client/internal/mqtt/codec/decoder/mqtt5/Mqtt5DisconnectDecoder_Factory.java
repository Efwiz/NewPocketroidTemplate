package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5DisconnectDecoder_Factory implements Factory<Mqtt5DisconnectDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5DisconnectDecoder get() {
        return newInstance();
    }

    public static Mqtt5DisconnectDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5DisconnectDecoder newInstance() {
        return new Mqtt5DisconnectDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5DisconnectDecoder_Factory INSTANCE = new Mqtt5DisconnectDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
