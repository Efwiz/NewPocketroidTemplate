package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientConfig {
    Optional<MqttClientAutoReconnect> getAutomaticReconnect();

    Optional<MqttClientIdentifier> getClientIdentifier();

    List<MqttClientConnectedListener> getConnectedListeners();

    Optional<? extends MqttClientConnectionConfig> getConnectionConfig();

    List<MqttClientDisconnectedListener> getDisconnectedListeners();

    MqttClientExecutorConfig getExecutorConfig();

    MqttVersion getMqttVersion();

    MqttClientState getState();

    MqttClientTransportConfig getTransportConfig();

    default InetSocketAddress getServerAddress() {
        return getTransportConfig().getServerAddress();
    }

    default String getServerHost() {
        return getServerAddress().getHostString();
    }

    default int getServerPort() {
        return getServerAddress().getPort();
    }

    default Optional<MqttClientSslConfig> getSslConfig() {
        return getTransportConfig().getSslConfig();
    }

    default Optional<MqttWebSocketConfig> getWebSocketConfig() {
        return getTransportConfig().getWebSocketConfig();
    }
}
