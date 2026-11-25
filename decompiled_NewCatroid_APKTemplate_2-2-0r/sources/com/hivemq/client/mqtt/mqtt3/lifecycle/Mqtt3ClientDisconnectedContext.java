package com.hivemq.client.mqtt.mqtt3.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ClientDisconnectedContext extends MqttClientDisconnectedContext {
    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    Mqtt3ClientConfig getClientConfig();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    Mqtt3ClientReconnector getReconnector();
}
