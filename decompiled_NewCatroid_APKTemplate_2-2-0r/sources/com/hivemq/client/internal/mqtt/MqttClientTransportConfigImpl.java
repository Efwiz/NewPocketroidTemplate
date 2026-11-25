package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.util.InetSocketAddressUtil;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientSslConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttProxyConfig;
import com.hivemq.client.mqtt.MqttWebSocketConfig;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientTransportConfigImpl implements MqttClientTransportConfig {
    public static final MqttClientTransportConfigImpl DEFAULT = new MqttClientTransportConfigImpl(InetSocketAddressUtil.create("localhost", MqttClient.DEFAULT_SERVER_PORT), null, null, null, null, 10000, 60000);
    private final InetSocketAddress localAddress;
    private final int mqttConnectTimeoutMs;
    private final MqttProxyConfigImpl proxyConfig;
    private final InetSocketAddress serverAddress;
    private final int socketConnectTimeoutMs;
    private final MqttClientSslConfigImpl sslConfig;
    private final MqttWebSocketConfigImpl webSocketConfig;

    MqttClientTransportConfigImpl(InetSocketAddress inetSocketAddress, InetSocketAddress inetSocketAddress2, MqttClientSslConfigImpl mqttClientSslConfigImpl, MqttWebSocketConfigImpl mqttWebSocketConfigImpl, MqttProxyConfigImpl mqttProxyConfigImpl, int i, int i2) {
        this.serverAddress = inetSocketAddress;
        this.localAddress = inetSocketAddress2;
        this.sslConfig = mqttClientSslConfigImpl;
        this.webSocketConfig = mqttWebSocketConfigImpl;
        this.proxyConfig = mqttProxyConfigImpl;
        this.socketConnectTimeoutMs = i;
        this.mqttConnectTimeoutMs = i2;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public InetSocketAddress getServerAddress() {
        return this.serverAddress;
    }

    public InetSocketAddress getRemoteAddress() {
        MqttProxyConfigImpl mqttProxyConfigImpl = this.proxyConfig;
        return mqttProxyConfigImpl == null ? this.serverAddress : mqttProxyConfigImpl.getAddress();
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public Optional<InetSocketAddress> getLocalAddress() {
        return Optional.ofNullable(this.localAddress);
    }

    public InetSocketAddress getRawLocalAddress() {
        return this.localAddress;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public Optional<MqttClientSslConfig> getSslConfig() {
        return Optional.ofNullable(this.sslConfig);
    }

    public MqttClientSslConfigImpl getRawSslConfig() {
        return this.sslConfig;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public Optional<MqttWebSocketConfig> getWebSocketConfig() {
        return Optional.ofNullable(this.webSocketConfig);
    }

    public MqttWebSocketConfigImpl getRawWebSocketConfig() {
        return this.webSocketConfig;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public Optional<MqttProxyConfig> getProxyConfig() {
        return Optional.ofNullable(this.proxyConfig);
    }

    public MqttProxyConfigImpl getRawProxyConfig() {
        return this.proxyConfig;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public int getSocketConnectTimeoutMs() {
        return this.socketConnectTimeoutMs;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public int getMqttConnectTimeoutMs() {
        return this.mqttConnectTimeoutMs;
    }

    @Override // com.hivemq.client.mqtt.MqttClientTransportConfig
    public MqttClientTransportConfigImplBuilder.Default extend() {
        return new MqttClientTransportConfigImplBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttClientTransportConfigImpl)) {
            return false;
        }
        MqttClientTransportConfigImpl mqttClientTransportConfigImpl = (MqttClientTransportConfigImpl) obj;
        return this.serverAddress.equals(mqttClientTransportConfigImpl.serverAddress) && Objects.equals(this.localAddress, mqttClientTransportConfigImpl.localAddress) && Objects.equals(this.sslConfig, mqttClientTransportConfigImpl.sslConfig) && Objects.equals(this.webSocketConfig, mqttClientTransportConfigImpl.webSocketConfig) && Objects.equals(this.proxyConfig, mqttClientTransportConfigImpl.proxyConfig) && this.socketConnectTimeoutMs == mqttClientTransportConfigImpl.socketConnectTimeoutMs && this.mqttConnectTimeoutMs == mqttClientTransportConfigImpl.mqttConnectTimeoutMs;
    }

    public int hashCode() {
        return (((((((((((this.serverAddress.hashCode() * 31) + Objects.hashCode(this.localAddress)) * 31) + Objects.hashCode(this.sslConfig)) * 31) + Objects.hashCode(this.webSocketConfig)) * 31) + Objects.hashCode(this.proxyConfig)) * 31) + Integer.hashCode(this.socketConnectTimeoutMs)) * 31) + Integer.hashCode(this.mqttConnectTimeoutMs);
    }
}
