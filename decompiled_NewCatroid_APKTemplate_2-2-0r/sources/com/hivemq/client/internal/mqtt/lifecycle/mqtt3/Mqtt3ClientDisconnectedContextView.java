package com.hivemq.client.internal.mqtt.lifecycle.mqtt3;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientReconnector;
import com.hivemq.client.internal.mqtt.mqtt3.Mqtt3ClientConfigView;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientDisconnectedContext;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ClientDisconnectedContextView implements Mqtt3ClientDisconnectedContext {
    private final Throwable cause;
    private final Mqtt3ClientConfigView clientConfig;
    private final Mqtt3ClientReconnectorView reconnector;
    private final MqttDisconnectSource source;

    public static MqttClientDisconnectedContext of(MqttClientConfig mqttClientConfig, MqttDisconnectSource mqttDisconnectSource, Throwable th, MqttClientReconnector mqttClientReconnector) {
        return new Mqtt3ClientDisconnectedContextView(new Mqtt3ClientConfigView(mqttClientConfig), mqttDisconnectSource, Mqtt3ExceptionFactory.map(th), new Mqtt3ClientReconnectorView(mqttClientReconnector));
    }

    private Mqtt3ClientDisconnectedContextView(Mqtt3ClientConfigView mqtt3ClientConfigView, MqttDisconnectSource mqttDisconnectSource, Throwable th, Mqtt3ClientReconnectorView mqtt3ClientReconnectorView) {
        this.clientConfig = mqtt3ClientConfigView;
        this.source = mqttDisconnectSource;
        this.cause = th;
        this.reconnector = mqtt3ClientReconnectorView;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientDisconnectedContext, com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    public Mqtt3ClientConfigView getClientConfig() {
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

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientDisconnectedContext, com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedContext
    public Mqtt3ClientReconnectorView getReconnector() {
        return this.reconnector;
    }
}
