package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5AuthEncoder_Factory implements Factory<Mqtt5AuthEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5AuthEncoder get() {
        return newInstance();
    }

    public static Mqtt5AuthEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5AuthEncoder newInstance() {
        return new Mqtt5AuthEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5AuthEncoder_Factory INSTANCE = new Mqtt5AuthEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
