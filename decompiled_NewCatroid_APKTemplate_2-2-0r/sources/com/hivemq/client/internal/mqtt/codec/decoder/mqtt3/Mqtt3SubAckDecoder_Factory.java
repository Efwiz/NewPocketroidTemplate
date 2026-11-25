package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3SubAckDecoder_Factory implements Factory<Mqtt3SubAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3SubAckDecoder get() {
        return newInstance();
    }

    public static Mqtt3SubAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3SubAckDecoder newInstance() {
        return new Mqtt3SubAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3SubAckDecoder_Factory INSTANCE = new Mqtt3SubAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
