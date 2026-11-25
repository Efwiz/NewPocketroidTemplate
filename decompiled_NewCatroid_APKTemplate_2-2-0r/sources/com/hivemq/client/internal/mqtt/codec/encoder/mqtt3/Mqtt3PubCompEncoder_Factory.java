package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubCompEncoder_Factory implements Factory<Mqtt3PubCompEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubCompEncoder get() {
        return newInstance();
    }

    public static Mqtt3PubCompEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubCompEncoder newInstance() {
        return new Mqtt3PubCompEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubCompEncoder_Factory INSTANCE = new Mqtt3PubCompEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
