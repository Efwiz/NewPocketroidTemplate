package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubRelEncoder_Factory implements Factory<Mqtt5PubRelEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubRelEncoder get() {
        return newInstance();
    }

    public static Mqtt5PubRelEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubRelEncoder newInstance() {
        return new Mqtt5PubRelEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubRelEncoder_Factory INSTANCE = new Mqtt5PubRelEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
