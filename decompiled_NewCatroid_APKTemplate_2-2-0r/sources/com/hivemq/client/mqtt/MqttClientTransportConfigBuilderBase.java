package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientTransportConfigBuilderBase<B extends MqttClientTransportConfigBuilderBase<B>> {
    B localAddress(String str);

    B localAddress(InetAddress inetAddress);

    B localAddress(InetSocketAddress inetSocketAddress);

    B localPort(int i);

    B mqttConnectTimeout(long j, TimeUnit timeUnit);

    B proxyConfig(MqttProxyConfig mqttProxyConfig);

    MqttProxyConfigBuilder.Nested<? extends B> proxyConfig();

    B serverAddress(InetSocketAddress inetSocketAddress);

    B serverHost(String str);

    B serverHost(InetAddress inetAddress);

    B serverPort(int i);

    B socketConnectTimeout(long j, TimeUnit timeUnit);

    MqttClientSslConfigBuilder.Nested<? extends B> sslConfig();

    B sslConfig(MqttClientSslConfig mqttClientSslConfig);

    B sslWithDefaultConfig();

    B webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig);

    MqttWebSocketConfigBuilder.Nested<? extends B> webSocketConfig();

    B webSocketWithDefaultConfig();
}
