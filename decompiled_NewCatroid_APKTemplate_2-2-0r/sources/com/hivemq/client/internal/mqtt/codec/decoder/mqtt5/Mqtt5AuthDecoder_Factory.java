package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5AuthDecoder_Factory implements Factory<Mqtt5AuthDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5AuthDecoder get() {
        return newInstance();
    }

    public static Mqtt5AuthDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5AuthDecoder newInstance() {
        return new Mqtt5AuthDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5AuthDecoder_Factory INSTANCE = new Mqtt5AuthDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
