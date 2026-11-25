package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientViewBuilder;
import com.hivemq.client.mqtt.MqttClient;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Client extends MqttClient {
    @Override // com.hivemq.client.mqtt.MqttClient
    Mqtt3ClientConfig getConfig();

    Mqtt3AsyncClient toAsync();

    Mqtt3BlockingClient toBlocking();

    Mqtt3RxClient toRx();

    static Mqtt3ClientBuilder builder() {
        return new Mqtt3RxClientViewBuilder();
    }
}
