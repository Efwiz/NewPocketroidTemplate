package com.hivemq.client.internal.mqtt.ioc;

import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttDisconnectOnAuthHandler;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class ConnectionModule_ProvideAuthHandlerFactory implements Factory<MqttAuthHandler> {
    private final Provider<MqttConnectAuthHandler> connectAuthHandlerLazyProvider;
    private final Provider<MqttConnect> connectProvider;
    private final Provider<MqttDisconnectOnAuthHandler> disconnectOnAuthHandlerLazyProvider;

    public ConnectionModule_ProvideAuthHandlerFactory(Provider<MqttConnect> provider, Provider<MqttConnectAuthHandler> provider2, Provider<MqttDisconnectOnAuthHandler> provider3) {
        this.connectProvider = provider;
        this.connectAuthHandlerLazyProvider = provider2;
        this.disconnectOnAuthHandlerLazyProvider = provider3;
    }

    @Override // javax.inject.Provider
    public MqttAuthHandler get() {
        return provideAuthHandler(this.connectProvider.get(), DoubleCheck.lazy(this.connectAuthHandlerLazyProvider), DoubleCheck.lazy(this.disconnectOnAuthHandlerLazyProvider));
    }

    public static ConnectionModule_ProvideAuthHandlerFactory create(Provider<MqttConnect> provider, Provider<MqttConnectAuthHandler> provider2, Provider<MqttDisconnectOnAuthHandler> provider3) {
        return new ConnectionModule_ProvideAuthHandlerFactory(provider, provider2, provider3);
    }

    public static MqttAuthHandler provideAuthHandler(MqttConnect mqttConnect, Lazy<MqttConnectAuthHandler> lazy, Lazy<MqttDisconnectOnAuthHandler> lazy2) {
        return (MqttAuthHandler) Preconditions.checkNotNull(ConnectionModule.provideAuthHandler(mqttConnect, lazy, lazy2), "Cannot return null from a non-@Nullable @Provides method");
    }
}
