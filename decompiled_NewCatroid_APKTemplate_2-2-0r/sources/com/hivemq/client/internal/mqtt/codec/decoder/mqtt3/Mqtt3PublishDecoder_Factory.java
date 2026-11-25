package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PublishDecoder_Factory implements Factory<Mqtt3PublishDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3PublishDecoder get() {
        return newInstance();
    }

    public static Mqtt3PublishDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PublishDecoder newInstance() {
        return new Mqtt3PublishDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PublishDecoder_Factory INSTANCE = new Mqtt3PublishDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
