package com.hivemq.client.mqtt.lifecycle;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientAutoReconnectBuilder extends MqttClientAutoReconnectBuilderBase<MqttClientAutoReconnectBuilder> {

    public interface Nested<P> extends MqttClientAutoReconnectBuilderBase<Nested<P>> {
        P applyAutomaticReconnect();
    }

    MqttClientAutoReconnect build();
}
