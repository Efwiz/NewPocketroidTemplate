package com.hivemq.client.mqtt;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientConnectionConfig {
    int getKeepAlive();

    MqttClientTransportConfig getTransportConfig();

    boolean hasSimpleAuth();

    boolean hasWillPublish();
}
