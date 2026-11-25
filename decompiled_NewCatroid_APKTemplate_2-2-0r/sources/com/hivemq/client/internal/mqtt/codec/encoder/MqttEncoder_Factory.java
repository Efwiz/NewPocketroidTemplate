package com.hivemq.client.internal.mqtt.codec.encoder;

import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttEncoder_Factory implements Factory<MqttEncoder> {
    private final Provider<MqttMessageEncoders> encodersProvider;

    public MqttEncoder_Factory(Provider<MqttMessageEncoders> provider) {
        this.encodersProvider = provider;
    }

    @Override // javax.inject.Provider
    public MqttEncoder get() {
        return newInstance(this.encodersProvider.get());
    }

    public static MqttEncoder_Factory create(Provider<MqttMessageEncoders> provider) {
        return new MqttEncoder_Factory(provider);
    }

    public static MqttEncoder newInstance(MqttMessageEncoders mqttMessageEncoders) {
        return new MqttEncoder(mqttMessageEncoders);
    }
}
