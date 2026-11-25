package com.hivemq.client.internal.mqtt.lifecycle.mqtt3;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.connack.MqttConnAck;
import com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectView;
import com.hivemq.client.internal.mqtt.mqtt3.Mqtt3ClientConfigView;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext;
import com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientConnectedContext;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ClientConnectedContextView implements Mqtt3ClientConnectedContext {
    private final Mqtt3ClientConfigView clientConfig;
    private final Mqtt3ConnAckView connAck;
    private final Mqtt3ConnectView connect;

    public static MqttClientConnectedContext of(MqttClientConfig mqttClientConfig, MqttConnect mqttConnect, MqttConnAck mqttConnAck) {
        return new Mqtt3ClientConnectedContextView(new Mqtt3ClientConfigView(mqttClientConfig), Mqtt3ConnectView.of(mqttConnect), Mqtt3ConnAckView.of(mqttConnAck));
    }

    private Mqtt3ClientConnectedContextView(Mqtt3ClientConfigView mqtt3ClientConfigView, Mqtt3ConnectView mqtt3ConnectView, Mqtt3ConnAckView mqtt3ConnAckView) {
        this.clientConfig = mqtt3ClientConfigView;
        this.connect = mqtt3ConnectView;
        this.connAck = mqtt3ConnAckView;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientConnectedContext, com.hivemq.client.mqtt.lifecycle.MqttClientConnectedContext
    public Mqtt3ClientConfigView getClientConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientConnectedContext
    public Mqtt3ConnectView getConnect() {
        return this.connect;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientConnectedContext
    public Mqtt3ConnAckView getConnAck() {
        return this.connAck;
    }
}
