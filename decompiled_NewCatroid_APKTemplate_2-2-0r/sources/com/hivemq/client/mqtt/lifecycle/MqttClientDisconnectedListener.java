package com.hivemq.client.mqtt.lifecycle;

/* JADX INFO: loaded from: classes10.dex */
@FunctionalInterface
public interface MqttClientDisconnectedListener {
    void onDisconnected(MqttClientDisconnectedContext mqttClientDisconnectedContext);
}
