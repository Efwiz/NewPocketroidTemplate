package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5UnsubAckDecoder_Factory implements Factory<Mqtt5UnsubAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt5UnsubAckDecoder get() {
        return newInstance();
    }

    public static Mqtt5UnsubAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt5UnsubAckDecoder newInstance() {
        return new Mqtt5UnsubAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt5UnsubAckDecoder_Factory INSTANCE = new Mqtt5UnsubAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
