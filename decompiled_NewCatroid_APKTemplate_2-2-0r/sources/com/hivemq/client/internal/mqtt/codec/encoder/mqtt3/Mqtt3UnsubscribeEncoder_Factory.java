package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3UnsubscribeEncoder_Factory implements Factory<Mqtt3UnsubscribeEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3UnsubscribeEncoder get() {
        return newInstance();
    }

    public static Mqtt3UnsubscribeEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3UnsubscribeEncoder newInstance() {
        return new Mqtt3UnsubscribeEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3UnsubscribeEncoder_Factory INSTANCE = new Mqtt3UnsubscribeEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
