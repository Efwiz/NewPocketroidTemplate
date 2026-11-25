package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubAckEncoder_Factory implements Factory<Mqtt5PubAckEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubAckEncoder get() {
        return newInstance();
    }

    public static Mqtt5PubAckEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubAckEncoder newInstance() {
        return new Mqtt5PubAckEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubAckEncoder_Factory INSTANCE = new Mqtt5PubAckEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
