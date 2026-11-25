package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubRelDecoder_Factory implements Factory<Mqtt3PubRelDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubRelDecoder get() {
        return newInstance();
    }

    public static Mqtt3PubRelDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubRelDecoder newInstance() {
        return new Mqtt3PubRelDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubRelDecoder_Factory INSTANCE = new Mqtt3PubRelDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
