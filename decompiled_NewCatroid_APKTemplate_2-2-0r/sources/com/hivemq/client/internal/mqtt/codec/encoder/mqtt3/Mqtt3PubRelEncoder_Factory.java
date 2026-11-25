package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubRelEncoder_Factory implements Factory<Mqtt3PubRelEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubRelEncoder get() {
        return newInstance();
    }

    public static Mqtt3PubRelEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubRelEncoder newInstance() {
        return new Mqtt3PubRelEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubRelEncoder_Factory INSTANCE = new Mqtt3PubRelEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
