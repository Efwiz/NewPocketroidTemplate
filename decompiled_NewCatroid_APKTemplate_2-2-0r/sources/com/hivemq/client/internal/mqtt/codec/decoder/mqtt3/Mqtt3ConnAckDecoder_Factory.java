package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3ConnAckDecoder_Factory implements Factory<Mqtt3ConnAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3ConnAckDecoder get() {
        return newInstance();
    }

    public static Mqtt3ConnAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3ConnAckDecoder newInstance() {
        return new Mqtt3ConnAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3ConnAckDecoder_Factory INSTANCE = new Mqtt3ConnAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
