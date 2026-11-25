package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PublishEncoder_Factory implements Factory<Mqtt5PublishEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5PublishEncoder get() {
        return newInstance();
    }

    public static Mqtt5PublishEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PublishEncoder newInstance() {
        return new Mqtt5PublishEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PublishEncoder_Factory INSTANCE = new Mqtt5PublishEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
