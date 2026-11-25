package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5ConnAckDecoder_Factory implements Factory<Mqtt5ConnAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5ConnAckDecoder get() {
        return newInstance();
    }

    public static Mqtt5ConnAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5ConnAckDecoder newInstance() {
        return new Mqtt5ConnAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5ConnAckDecoder_Factory INSTANCE = new Mqtt5ConnAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
