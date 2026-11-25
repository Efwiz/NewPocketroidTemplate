package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubAckDecoder_Factory implements Factory<Mqtt5PubAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubAckDecoder get() {
        return newInstance();
    }

    public static Mqtt5PubAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubAckDecoder newInstance() {
        return new Mqtt5PubAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubAckDecoder_Factory INSTANCE = new Mqtt5PubAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
