package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5UnsubscribeEncoder_Factory implements Factory<Mqtt5UnsubscribeEncoder> {
    @Override // javax.inject.Provider
    public Mqtt5UnsubscribeEncoder get() {
        return newInstance();
    }

    public static Mqtt5UnsubscribeEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5UnsubscribeEncoder newInstance() {
        return new Mqtt5UnsubscribeEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5UnsubscribeEncoder_Factory INSTANCE = new Mqtt5UnsubscribeEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
