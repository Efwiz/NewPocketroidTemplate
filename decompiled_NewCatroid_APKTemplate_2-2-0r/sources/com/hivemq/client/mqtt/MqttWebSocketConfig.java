package com.hivemq.client.mqtt;

import com.hivemq.client.internal.mqtt.MqttWebSocketConfigImplBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttWebSocketConfig {
    public static final int DEFAULT_HANDSHAKE_TIMEOUT_MS = 10000;
    public static final String DEFAULT_MQTT_SUBPROTOCOL = "mqtt";
    public static final String DEFAULT_QUERY_STRING = "";
    public static final String DEFAULT_SERVER_PATH = "";

    MqttWebSocketConfigBuilder extend();

    int getHandshakeTimeoutMs();

    String getQueryString();

    String getServerPath();

    String getSubprotocol();

    static MqttWebSocketConfigBuilder builder() {
        return new MqttWebSocketConfigImplBuilder.Default();
    }
}
