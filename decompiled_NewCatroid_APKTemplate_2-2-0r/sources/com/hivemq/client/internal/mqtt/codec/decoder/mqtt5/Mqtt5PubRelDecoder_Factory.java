package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubRelDecoder_Factory implements Factory<Mqtt5PubRelDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubRelDecoder get() {
        return newInstance();
    }

    public static Mqtt5PubRelDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubRelDecoder newInstance() {
        return new Mqtt5PubRelDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubRelDecoder_Factory INSTANCE = new Mqtt5PubRelDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
