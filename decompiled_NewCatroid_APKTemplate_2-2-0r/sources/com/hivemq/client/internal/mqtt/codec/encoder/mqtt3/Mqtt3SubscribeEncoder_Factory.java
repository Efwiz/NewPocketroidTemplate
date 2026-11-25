package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3SubscribeEncoder_Factory implements Factory<Mqtt3SubscribeEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3SubscribeEncoder get() {
        return newInstance();
    }

    public static Mqtt3SubscribeEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3SubscribeEncoder newInstance() {
        return new Mqtt3SubscribeEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3SubscribeEncoder_Factory INSTANCE = new Mqtt3SubscribeEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
