package com.hivemq.client.mqtt.mqtt3.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ClientConnectedContext extends MqttClientConnectedContext {
    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext
    Mqtt3ClientConfig getClientConfig();

    Mqtt3ConnAck getConnAck();

    Mqtt3Connect getConnect();
}
