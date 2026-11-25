package com.hivemq.client.mqtt;

import com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClient {
    public static final String DEFAULT_SERVER_HOST = "localhost";
    public static final int DEFAULT_SERVER_PORT = 1883;
    public static final int DEFAULT_SERVER_PORT_SSL = 8883;
    public static final int DEFAULT_SERVER_PORT_WEBSOCKET = 80;
    public static final int DEFAULT_SERVER_PORT_WEBSOCKET_SSL = 443;

    MqttClientConfig getConfig();

    static MqttClientBuilder builder() {
        return new MqttRxClientBuilderBase.Choose();
    }

    default MqttClientState getState() {
        return getConfig().getState();
    }
}
