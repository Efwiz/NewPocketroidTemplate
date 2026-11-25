package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import dagger.internal.Factory;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3PubRecDecoder_Factory implements Factory<Mqtt3PubRecDecoder> {
    @Override // javax.inject.Provider
    public Mqtt3PubRecDecoder get() {
        return newInstance();
    }

    public static Mqtt3PubRecDecoder_Factory create() {
        return InstanceHolder.INSTANCE;
    }

    public static Mqtt3PubRecDecoder newInstance() {
        return new Mqtt3PubRecDecoder();
    }

    private static final class InstanceHolder {
        private static final Mqtt3PubRecDecoder_Factory INSTANCE = new Mqtt3PubRecDecoder_Factory();

        private InstanceHolder() {
        }
    }
}
