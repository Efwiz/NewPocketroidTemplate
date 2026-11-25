package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubCompEncoder_Factory implements Factory<Mqtt5PubCompEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubCompEncoder get() {
        return newInstance();
    }

    public static Mqtt5PubCompEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubCompEncoder newInstance() {
        return new Mqtt5PubCompEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubCompEncoder_Factory INSTANCE = new Mqtt5PubCompEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
