package com.hivemq.client.mqtt.mqtt5.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientDisconnectedContext extends MqttClientDisconnectedContext {
    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    Mqtt5ClientConfig getClientConfig();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    Mqtt5ClientReconnector getReconnector();
}
