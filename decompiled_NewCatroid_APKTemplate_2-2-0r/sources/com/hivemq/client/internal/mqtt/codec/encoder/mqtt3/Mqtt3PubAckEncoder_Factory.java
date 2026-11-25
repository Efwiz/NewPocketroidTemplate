package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubAckEncoder_Factory implements Factory<Mqtt3PubAckEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubAckEncoder get() {
        return newInstance();
    }

    public static Mqtt3PubAckEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubAckEncoder newInstance() {
        return new Mqtt3PubAckEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubAckEncoder_Factory INSTANCE = new Mqtt3PubAckEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
