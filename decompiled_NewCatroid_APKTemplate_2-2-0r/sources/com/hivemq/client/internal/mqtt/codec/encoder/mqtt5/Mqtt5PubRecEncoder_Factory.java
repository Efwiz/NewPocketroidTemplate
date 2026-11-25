package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubRecEncoder_Factory implements Factory<Mqtt5PubRecEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubRecEncoder get() {
        return newInstance();
    }

    public static Mqtt5PubRecEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubRecEncoder newInstance() {
        return new Mqtt5PubRecEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubRecEncoder_Factory INSTANCE = new Mqtt5PubRecEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
