package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5ConnectEncoder_Factory implements Factory<Mqtt5ConnectEncoder> {
    private final Provider<Mqtt5PublishEncoder> publishEncoderProvider;

    public Mqtt5ConnectEncoder_Factory(Provider<Mqtt5PublishEncoder> provider) {
        this.publishEncoderProvider = provider;
    }

    @Override // javax.inject.Provider
    public Mqtt5ConnectEncoder get() {
        return newInstance(this.publishEncoderProvider.get());
    }

    public static Mqtt5ConnectEncoder_Factory create(Provider<Mqtt5PublishEncoder> provider) {
        return new Mqtt5ConnectEncoder_Factory(provider);
    }

    public static Mqtt5ConnectEncoder newInstance(Mqtt5PublishEncoder mqtt5PublishEncoder) {
        return new Mqtt5ConnectEncoder(mqtt5PublishEncoder);
    }
}
