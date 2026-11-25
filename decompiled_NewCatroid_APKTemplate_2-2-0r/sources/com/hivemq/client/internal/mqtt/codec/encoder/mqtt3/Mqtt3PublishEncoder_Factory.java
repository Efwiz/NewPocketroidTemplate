package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PublishEncoder_Factory implements Factory<Mqtt3PublishEncoder> {
    @Override // javax.inject.Provider
    public Mqtt3PublishEncoder get() {
        return newInstance();
    }

    public static Mqtt3PublishEncoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PublishEncoder newInstance() {
        return new Mqtt3PublishEncoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PublishEncoder_Factory INSTANCE = new Mqtt3PublishEncoder_Factory();

        private InstanceHolder() {
        }
    }
}
