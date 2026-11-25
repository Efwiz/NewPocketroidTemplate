package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientBuilder extends MqttClientBuilderBase<MqttClientBuilder> {
    Mqtt3ClientBuilder useMqttVersion3();

    Mqtt5ClientBuilder useMqttVersion5();
}
