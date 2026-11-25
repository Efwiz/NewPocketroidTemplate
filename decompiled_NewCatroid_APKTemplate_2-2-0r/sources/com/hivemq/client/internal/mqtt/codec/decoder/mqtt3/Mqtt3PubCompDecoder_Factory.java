package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubCompDecoder_Factory implements Factory<Mqtt3PubCompDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubCompDecoder get() {
        return newInstance();
    }

    public static Mqtt3PubCompDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubCompDecoder newInstance() {
        return new Mqtt3PubCompDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubCompDecoder_Factory INSTANCE = new Mqtt3PubCompDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
