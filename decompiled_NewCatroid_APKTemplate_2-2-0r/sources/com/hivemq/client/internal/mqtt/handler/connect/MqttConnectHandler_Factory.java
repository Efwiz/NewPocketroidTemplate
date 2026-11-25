package com.hivemq.client.internal.mqtt.handler.connect;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoder;
import com.hivemq.client.internal.mqtt.handler.MqttSession;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttConnectHandler_Factory implements Factory<MqttConnectHandler> {
    private final Provider<MqttClientConfig> clientConfigProvider;
    private final Provider<MqttConnAckFlow> connAckFlowProvider;
    private final Provider<MqttConnect> connectProvider;
    private final Provider<MqttDecoder> decoderProvider;
    private final Provider<MqttSession> sessionProvider;

    public MqttConnectHandler_Factory(Provider<MqttConnect> provider, Provider<MqttConnAckFlow> provider2, Provider<MqttClientConfig> provider3, Provider<MqttSession> provider4, Provider<MqttDecoder> provider5) {
        this.connectProvider = provider;
        this.connAckFlowProvider = provider2;
        this.clientConfigProvider = provider3;
        this.sessionProvider = provider4;
        this.decoderProvider = provider5;
    }

    @Override // javax.inject.Provider
    public MqttConnectHandler get() {
        return newInstance(this.connectProvider.get(), this.connAckFlowProvider.get(), this.clientConfigProvider.get(), this.sessionProvider.get(), this.decoderProvider.get());
    }

    public static MqttConnectHandler_Factory create(Provider<MqttConnect> provider, Provider<MqttConnAckFlow> provider2, Provider<MqttClientConfig> provider3, Provider<MqttSession> provider4, Provider<MqttDecoder> provider5) {
        return new MqttConnectHandler_Factory(provider, provider2, provider3, provider4, provider5);
    }

    public static MqttConnectHandler newInstance(MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow, MqttClientConfig mqttClientConfig, MqttSession mqttSession, MqttDecoder mqttDecoder) {
        return new MqttConnectHandler(mqttConnect, mqttConnAckFlow, mqttClientConfig, mqttSession, mqttDecoder);
    }
}
