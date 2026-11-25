package com.hivemq.client.mqtt.lifecycle;

import com.hivemq.client.mqtt.MqttClientConfig;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientDisconnectedContext {
    Throwable getCause();

    MqttClientConfig getClientConfig();

    MqttClientReconnector getReconnector();

    MqttDisconnectSource getSource();
}
