package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.internal.mqtt.MqttRxClientBuilder;
import com.hivemq.client.mqtt.MqttClient;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Client extends MqttClient {
    @Override // com.hivemq.client.mqtt.MqttClient
    Mqtt5ClientConfig getConfig();

    Mqtt5AsyncClient toAsync();

    Mqtt5BlockingClient toBlocking();

    Mqtt5RxClient toRx();

    static Mqtt5ClientBuilder builder() {
        return new MqttRxClientBuilder();
    }
}
