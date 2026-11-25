package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3UnsubAckDecoder_Factory implements Factory<Mqtt3UnsubAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3UnsubAckDecoder get() {
        return newInstance();
    }

    public static Mqtt3UnsubAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3UnsubAckDecoder newInstance() {
        return new Mqtt3UnsubAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3UnsubAckDecoder_Factory INSTANCE = new Mqtt3UnsubAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
