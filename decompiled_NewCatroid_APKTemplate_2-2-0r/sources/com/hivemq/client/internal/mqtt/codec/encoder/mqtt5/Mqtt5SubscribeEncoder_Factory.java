package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5SubscribeEncoder_Factory implements Factory<Mqtt5SubscribeEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5SubscribeEncoder get() {
        return newInstance();
    }

    public static Mqtt5SubscribeEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5SubscribeEncoder newInstance() {
        return new Mqtt5SubscribeEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5SubscribeEncoder_Factory INSTANCE = new Mqtt5SubscribeEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
