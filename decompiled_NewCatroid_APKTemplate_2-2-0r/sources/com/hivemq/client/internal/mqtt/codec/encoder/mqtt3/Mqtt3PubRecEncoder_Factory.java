package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubRecEncoder_Factory implements Factory<Mqtt3PubRecEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubRecEncoder get() {
        return newInstance();
    }

    public static Mqtt3PubRecEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubRecEncoder newInstance() {
        return new Mqtt3PubRecEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubRecEncoder_Factory INSTANCE = new Mqtt3PubRecEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
