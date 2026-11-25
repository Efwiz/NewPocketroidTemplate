package com.hivemq.client.internal.mqtt.handler;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoder;
import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckFlow;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler;
import com.hivemq.client.internal.mqtt.handler.websocket.MqttWebSocketInitializer;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.Lazy;
import dagger.internal.DoubleCheck;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttChannelInitializer_Factory implements Factory<MqttChannelInitializer> {
    private final Provider<MqttAuthHandler> authHandlerProvider;
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttConnAckFlow> connAckFlowProvider;
    private final Provider<MqttConnectHandler> connectHandlerProvider;
    private final Provider<MqttConnect> connectProvider;
    private final Provider<MqttDisconnectHandler> disconnectHandlerProvider;
    private final Provider<MqttEncoder> encoderProvider;
    private final Provider<MqttWebSocketInitializer> webSocketInitializerProvider;

    public MqttChannelInitializer_Factory(Provider<MqttClientConfig> provider, Provider<MqttConnect> provider2, Provider<MqttConnAckFlow> provider3, Provider<MqttEncoder> provider4, Provider<MqttConnectHandler> provider5, Provider<MqttDisconnectHandler> provider6, Provider<MqttAuthHandler> provider7, Provider<MqttWebSocketInitializer> provider8) {
        this.clientConfigProvider = provider;
        this.connectProvider = provider2;
        this.connAckFlowProvider = provider3;
        this.encoderProvider = provider4;
        this.connectHandlerProvider = provider5;
        this.disconnectHandlerProvider = provider6;
        this.authHandlerProvider = provider7;
        this.webSocketInitializerProvider = provider8;
    }

    @Override // javax.inject.Provider
    public MqttChannelInitializer get() {
        return newInstance(this.clientConfigProvider.get(), this.connectProvider.get(), this.connAckFlowProvider.get(), this.encoderProvider.get(), this.connectHandlerProvider.get(), this.disconnectHandlerProvider.get(), this.authHandlerProvider.get(), DoubleCheck.lazy(this.webSocketInitializerProvider));
    }

    public static MqttChannelInitializer_Factory create(Provider<MqttClientConfig> provider, Provider<MqttConnect> provider2, Provider<MqttConnAckFlow> provider3, Provider<MqttEncoder> provider4, Provider<MqttConnectHandler> provider5, Provider<MqttDisconnectHandler> provider6, Provider<MqttAuthHandler> provider7, Provider<MqttWebSocketInitializer> provider8) {
        return new MqttChannelInitializer_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8);
    }

    public static MqttChannelInitializer newInstance(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow, MqttEncoder mqttEncoder, MqttConnectHandler mqttConnectHandler, MqttDisconnectHandler mqttDisconnectHandler, MqttAuthHandler mqttAuthHandler, Lazy<MqttWebSocketInitializer> lazy) {
        return new MqttChannelInitializer(mqttClientConfig, mqttConnect, mqttConnAckFlow, mqttEncoder, mqttConnectHandler, mqttDisconnectHandler, mqttAuthHandler, lazy);
    }
}
