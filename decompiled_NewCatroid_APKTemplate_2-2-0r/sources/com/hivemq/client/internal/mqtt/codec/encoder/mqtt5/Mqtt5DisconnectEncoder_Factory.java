package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5DisconnectEncoder_Factory implements Factory<Mqtt5DisconnectEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5DisconnectEncoder get() {
        return newInstance();
    }

    public static Mqtt5DisconnectEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5DisconnectEncoder newInstance() {
        return new Mqtt5DisconnectEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5DisconnectEncoder_Factory INSTANCE = new Mqtt5DisconnectEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
