package com.hivemq.client.internal.mqtt.lifecycle;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.lifecycle.mqtt3.Mqtt3ClientConnectedContextView;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientConnectedContext;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientConnectedContextImpl implements Mqtt5ClientConnectedContext {
    private final MqttClientConfig clientConfig;
    private final MqttConnAck connAck;
    private final MqttConnect connect;

    public static MqttClientConnectedContext of(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect, MqttConnAck mqttConnAck) {
        if (mqttClientConfig.getMqttVersion() == MqttVersion.MQTT_3_1_1) {
            return Mqtt3ClientConnectedContextView.of(mqttClientConfig, mqttConnect, mqttConnAck);
        }
        return new MqttClientConnectedContextImpl(mqttClientConfig, mqttConnect, mqttConnAck);
    }

    private MqttClientConnectedContextImpl(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect, MqttConnAck mqttConnAck) {
        this.clientConfig = mqttClientConfig;
        this.connect = mqttConnect;
        this.connAck = mqttConnAck;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientConnectedContext, com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext
    public MqttClientConfig getClientConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientConnectedContext
    public MqttConnect getConnect() {
        return this.connect;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientConnectedContext
    public MqttConnAck getConnAck() {
        return this.connAck;
    }
}
