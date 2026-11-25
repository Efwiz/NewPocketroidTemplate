package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.MqttClientBuilderBase;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilder;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientBuilderBase<B extends MqttClientBuilderBase<B>> {
    B addConnectedListener(MqttClientConnectedListener mqttClientConnectedListener);

    B addDisconnectedListener(MqttClientDisconnectedListener mqttClientDisconnectedListener);

    B automaticReconnect(MqttClientAutoReconnect mqttClientAutoReconnect);

    MqttClientAutoReconnectBuilder.Nested<? extends B> automaticReconnect();

    B automaticReconnectWithDefaultConfig();

    B executorConfig(MqttClientExecutorConfig mqttClientExecutorConfig);

    MqttClientExecutorConfigBuilder.Nested<? extends B> executorConfig();

    B identifier(MqttClientIdentifier mqttClientIdentifier);

    B identifier(String str);

    /* JADX INFO: renamed from: serverAddress */
    B mo2673serverAddress(InetSocketAddress inetSocketAddress);

    /* JADX INFO: renamed from: serverHost */
    B mo2674serverHost(String str);

    /* JADX INFO: renamed from: serverHost */
    B mo2675serverHost(InetAddress inetAddress);

    /* JADX INFO: renamed from: serverPort */
    B mo2676serverPort(int i);

    /* JADX INFO: renamed from: sslConfig */
    B mo2677sslConfig(MqttClientSslConfig mqttClientSslConfig);

    MqttClientSslConfigBuilder.Nested<? extends B> sslConfig();

    /* JADX INFO: renamed from: sslWithDefaultConfig */
    B mo2678sslWithDefaultConfig();

    B transportConfig(MqttClientTransportConfig mqttClientTransportConfig);

    MqttClientTransportConfigBuilder.Nested<? extends B> transportConfig();

    /* JADX INFO: renamed from: webSocketConfig */
    B mo2679webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig);

    MqttWebSocketConfigBuilder.Nested<? extends B> webSocketConfig();

    /* JADX INFO: renamed from: webSocketWithDefaultConfig */
    B mo2680webSocketWithDefaultConfig();

    @Deprecated
    default B useSslWithDefaultConfig() {
        return (B) mo2678sslWithDefaultConfig();
    }

    @Deprecated
    default B useSsl(MqttClientSslConfig mqttClientSslConfig) {
        return (B) mo2677sslConfig(mqttClientSslConfig);
    }

    @Deprecated
    default MqttClientSslConfigBuilder.Nested<? extends B> useSsl() {
        return sslConfig();
    }

    @Deprecated
    default B useWebSocketWithDefaultConfig() {
        return (B) mo2680webSocketWithDefaultConfig();
    }

    @Deprecated
    default B useWebSocket(MqttWebSocketConfig mqttWebSocketConfig) {
        return (B) mo2679webSocketConfig(mqttWebSocketConfig);
    }

    @Deprecated
    default MqttWebSocketConfigBuilder.Nested<? extends B> useWebSocket() {
        return webSocketConfig();
    }
}
