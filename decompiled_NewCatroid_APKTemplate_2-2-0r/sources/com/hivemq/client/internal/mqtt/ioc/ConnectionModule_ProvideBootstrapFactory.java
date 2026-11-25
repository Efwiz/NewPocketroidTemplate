package com.hivemq.client.internal.mqtt.ioc;

import com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import io.netty.bootstrap.Bootstrap;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class ConnectionModule_ProvideBootstrapFactory implements Factory<Bootstrap> {
    private final Provider<MqttChannelInitializer> channelInitializerProvider;

    public ConnectionModule_ProvideBootstrapFactory(Provider<MqttChannelInitializer> provider) {
        this.channelInitializerProvider = provider;
    }

    @Override // javax.inject.Provider
    public Bootstrap get() {
        return provideBootstrap(this.channelInitializerProvider.get());
    }

    public static ConnectionModule_ProvideBootstrapFactory create(Provider<MqttChannelInitializer> provider) {
        return new ConnectionModule_ProvideBootstrapFactory(provider);
    }

    public static Bootstrap provideBootstrap(MqttChannelInitializer mqttChannelInitializer) {
        return (Bootstrap) Preconditions.checkNotNull(ConnectionModule.provideBootstrap(mqttChannelInitializer), "Cannot return null from a non-@Nullable @Provides method");
    }
}
