package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubAckDecoder_Factory implements Factory<Mqtt3PubAckDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubAckDecoder get() {
        return newInstance();
    }

    public static Mqtt3PubAckDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubAckDecoder newInstance() {
        return new Mqtt3PubAckDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubAckDecoder_Factory INSTANCE = new Mqtt3PubAckDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
