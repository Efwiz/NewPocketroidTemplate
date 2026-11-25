package com.hivemq.client.internal.mqtt.codec;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3ClientMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5ClientMessageDecoders;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttCodecModule_ProvideMessageDecodersFactory implements Factory<MqttMessageDecoders> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<Mqtt3ClientMessageDecoders> mqtt3ClientMessageDecodersProvider;
    private final Provider<Mqtt5ClientMessageDecoders> mqtt5ClientMessageDecodersProvider;

    public MqttCodecModule_ProvideMessageDecodersFactory(Provider<MqttClientConfig> provider, Provider<Mqtt5ClientMessageDecoders> provider2, Provider<Mqtt3ClientMessageDecoders> provider3) {
        this.clientConfigProvider = provider;
        this.mqtt5ClientMessageDecodersProvider = provider2;
        this.mqtt3ClientMessageDecodersProvider = provider3;
    }

    @Override // javax.inject.Provider
    public MqttMessageDecoders get() {
        return provideMessageDecoders(this.clientConfigProvider.get(), DoubleCheck.lazy(this.mqtt5ClientMessageDecodersProvider), DoubleCheck.lazy(this.mqtt3ClientMessageDecodersProvider));
    }

    public static MqttCodecModule_ProvideMessageDecodersFactory create(Provider<MqttClientConfig> provider, Provider<Mqtt5ClientMessageDecoders> provider2, Provider<Mqtt3ClientMessageDecoders> provider3) {
        return new MqttCodecModule_ProvideMessageDecodersFactory(provider, provider2, provider3);
    }

    public static MqttMessageDecoders provideMessageDecoders(MqttClientConfig mqttClientConfig, Lazy<Mqtt5ClientMessageDecoders> lazy, Lazy<Mqtt3ClientMessageDecoders> lazy2) {
        return (MqttMessageDecoders) Preconditions.checkNotNull(MqttCodecModule.provideMessageDecoders(mqttClientConfig, lazy, lazy2), "Cannot return null from a non-@Nullable @Provides method");
    }
}
