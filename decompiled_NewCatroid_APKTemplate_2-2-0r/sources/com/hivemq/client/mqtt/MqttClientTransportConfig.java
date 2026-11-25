package com.hivemq.client.mqtt;

import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder;
import java.net.InetSocketAddress;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientTransportConfig {
    public static final int DEFAULT_MQTT_CONNECT_TIMEOUT_MS = 60000;
    public static final int DEFAULT_SOCKET_CONNECT_TIMEOUT_MS = 10000;

    MqttClientTransportConfigBuilder extend();

    Optional<InetSocketAddress> getLocalAddress();

    int getMqttConnectTimeoutMs();

    Optional<MqttProxyConfig> getProxyConfig();

    InetSocketAddress getServerAddress();

    int getSocketConnectTimeoutMs();

    Optional<MqttClientSslConfig> getSslConfig();

    Optional<MqttWebSocketConfig> getWebSocketConfig();

    static MqttClientTransportConfigBuilder builder() {
        return new MqttClientTransportConfigImplBuilder.Default();
    }
}
