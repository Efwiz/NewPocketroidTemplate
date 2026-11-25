package com.hivemq.client.mqtt;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientTransportConfigBuilder extends MqttClientTransportConfigBuilderBase<MqttClientTransportConfigBuilder> {

    public interface Nested<P> extends MqttClientTransportConfigBuilderBase<Nested<P>> {
        P applyTransportConfig();
    }

    MqttClientTransportConfig build();
}
