package com.hivemq.client.internal.mqtt.lifecycle;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.lifecycle.mqtt3.Mqtt3ClientDisconnectedContextView;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientDisconnectedContext;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientDisconnectedContextImpl implements Mqtt5ClientDisconnectedContext {
    private final Throwable cause;
    private final MqttClientConfig clientConfig;
    private final MqttClientReconnector reconnector;
    private final MqttDisconnectSource source;

    public static MqttClientDisconnectedContext of(MqttClientConfig mqttClientConfig, MqttDisconnectSource mqttDisconnectSource, Throwable th, MqttClientReconnector mqttClientReconnector) {
        if (mqttClientConfig.getMqttVersion() == MqttVersion.MQTT_3_1_1) {
            return Mqtt3ClientDisconnectedContextView.of(mqttClientConfig, mqttDisconnectSource, th, mqttClientReconnector);
        }
        return new MqttClientDisconnectedContextImpl(mqttClientConfig, mqttDisconnectSource, th, mqttClientReconnector);
    }

    private MqttClientDisconnectedContextImpl(MqttClientConfig mqttClientConfig, MqttDisconnectSource mqttDisconnectSource, Throwable th, MqttClientReconnector mqttClientReconnector) {
        this.clientConfig = mqttClientConfig;
        this.source = mqttDisconnectSource;
        this.cause = th;
        this.reconnector = mqttClientReconnector;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientDisconnectedContext, com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    public MqttClientConfig getClientConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    public MqttDisconnectSource getSource() {
        return this.source;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    public Throwable getCause() {
        return this.cause;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientDisconnectedContext, com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    public MqttClientReconnector getReconnector() {
        return this.reconnector;
    }
}
