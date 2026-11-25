package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3ConnectEncoder_Factory implements Factory<Mqtt3ConnectEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3ConnectEncoder get() {
        return newInstance();
    }

    public static Mqtt3ConnectEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3ConnectEncoder newInstance() {
        return new Mqtt3ConnectEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3ConnectEncoder_Factory INSTANCE = new Mqtt3ConnectEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
