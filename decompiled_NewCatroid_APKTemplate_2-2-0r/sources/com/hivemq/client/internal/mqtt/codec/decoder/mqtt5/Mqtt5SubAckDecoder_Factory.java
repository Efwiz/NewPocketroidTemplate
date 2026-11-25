package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5SubAckDecoder_Factory implements Factory<Mqtt5SubAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5SubAckDecoder get() {
        return newInstance();
    }

    public static Mqtt5SubAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5SubAckDecoder newInstance() {
        return new Mqtt5SubAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5SubAckDecoder_Factory INSTANCE = new Mqtt5SubAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
