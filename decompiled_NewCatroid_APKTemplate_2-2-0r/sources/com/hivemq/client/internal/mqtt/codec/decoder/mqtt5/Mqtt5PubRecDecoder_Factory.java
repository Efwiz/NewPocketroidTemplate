package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5PubRecDecoder_Factory implements Factory<Mqtt5PubRecDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5PubRecDecoder get() {
        return newInstance();
    }

    public static Mqtt5PubRecDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5PubRecDecoder newInstance() {
        return new Mqtt5PubRecDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5PubRecDecoder_Factory INSTANCE = new Mqtt5PubRecDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
