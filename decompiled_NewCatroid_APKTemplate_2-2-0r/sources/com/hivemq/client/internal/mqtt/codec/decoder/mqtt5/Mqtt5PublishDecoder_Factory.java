package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PublishDecoder_Factory implements Factory<Mqtt5PublishDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5PublishDecoder get() {
        return newInstance();
    }

    public static Mqtt5PublishDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PublishDecoder newInstance() {
        return new Mqtt5PublishDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PublishDecoder_Factory INSTANCE = new Mqtt5PublishDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
