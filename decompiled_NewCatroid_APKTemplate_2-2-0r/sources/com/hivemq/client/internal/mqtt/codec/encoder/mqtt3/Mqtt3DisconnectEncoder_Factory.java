package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3DisconnectEncoder_Factory implements Factory<Mqtt3DisconnectEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3DisconnectEncoder get() {
        return newInstance();
    }

    public static Mqtt3DisconnectEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3DisconnectEncoder newInstance() {
        return new Mqtt3DisconnectEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3DisconnectEncoder_Factory INSTANCE = new Mqtt3DisconnectEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
