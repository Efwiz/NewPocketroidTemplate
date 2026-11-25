package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubCompDecoder_Factory implements Factory<Mqtt5PubCompDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubCompDecoder get() {
        return newInstance();
    }

    public static Mqtt5PubCompDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubCompDecoder newInstance() {
        return new Mqtt5PubCompDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubCompDecoder_Factory INSTANCE = new Mqtt5PubCompDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
