package com.hivemq.client.internal.mqtt.codec;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3ClientMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5ClientMessageEncoders;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttCodecModule_ProvideMessageEncodersFactory implements Factory<MqttMessageEncoders> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<Mqtt3ClientMessageEncoders> mqtt3ClientMessageEncodersProvider;
    private final Provider<Mqtt5ClientMessageEncoders> mqtt5ClientMessageEncodersProvider;

    public MqttCodecModule_ProvideMessageEncodersFactory(Provider<MqttClientConfig> provider, Provider<Mqtt5ClientMessageEncoders> provider2, Provider<Mqtt3ClientMessageEncoders> provider3) {
        this.clientConfigProvider = provider;
        this.mqtt5ClientMessageEncodersProvider = provider2;
        this.mqtt3ClientMessageEncodersProvider = provider3;
    }

    @Override // javax.inject.Provider
    public MqttMessageEncoders get() {
        return provideMessageEncoders(this.clientConfigProvider.get(), DoubleCheck.lazy(this.mqtt5ClientMessageEncodersProvider), DoubleCheck.lazy(this.mqtt3ClientMessageEncodersProvider));
    }

    public static MqttCodecModule_ProvideMessageEncodersFactory create(Provider<MqttClientConfig> provider, Provider<Mqtt5ClientMessageEncoders> provider2, Provider<Mqtt3ClientMessageEncoders> provider3) {
        return new MqttCodecModule_ProvideMessageEncodersFactory(provider, provider2, provider3);
    }

    public static MqttMessageEncoders provideMessageEncoders(MqttClientConfig mqttClientConfig, Lazy<Mqtt5ClientMessageEncoders> lazy, Lazy<Mqtt3ClientMessageEncoders> lazy2) {
        return (MqttMessageEncoders) Preconditions.checkNotNull(MqttCodecModule.provideMessageEncoders(mqttClientConfig, lazy, lazy2), "Cannot return null from a non-@Nullable @Provides method");
    }
}
