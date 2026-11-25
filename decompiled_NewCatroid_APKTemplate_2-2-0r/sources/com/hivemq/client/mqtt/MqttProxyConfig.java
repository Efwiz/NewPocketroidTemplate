package com.hivemq.client.mqtt;

import com.hivemq.client.internal.mqtt.MqttProxyConfigImplBuilder;
import java.net.InetSocketAddress;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttProxyConfig {
    public static final int DEFAULT_HANDSHAKE_TIMEOUT_MS = 10000;
    public static final int DEFAULT_HTTP_PROXY_PORT = 80;
    public static final String DEFAULT_PROXY_HOST = "localhost";
    public static final MqttProxyProtocol DEFAULT_PROXY_PROTOCOL = MqttProxyProtocol.SOCKS_5;
    public static final int DEFAULT_SOCKS_PROXY_PORT = 1080;

    MqttProxyConfigBuilder extend();

    InetSocketAddress getAddress();

    int getHandshakeTimeoutMs();

    Optional<String> getPassword();

    MqttProxyProtocol getProtocol();

    Optional<String> getUsername();

    static MqttProxyConfigBuilder builder() {
        return new MqttProxyConfigImplBuilder.Default();
    }
}
