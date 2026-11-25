package com.hivemq.client.mqtt;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientSslConfigBuilder extends MqttClientSslConfigBuilderBase<MqttClientSslConfigBuilder> {

    public interface Nested<P> extends MqttClientSslConfigBuilderBase<Nested<P>> {
        P applySslConfig();
    }

    MqttClientSslConfig build();
}
