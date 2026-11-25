package com.hivemq.client.mqtt.mqtt5.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientConnectedContext extends MqttClientConnectedContext {
    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext
    Mqtt5ClientConfig getClientConfig();

    Mqtt5ConnAck getConnAck();

    Mqtt5Connect getConnect();
}
