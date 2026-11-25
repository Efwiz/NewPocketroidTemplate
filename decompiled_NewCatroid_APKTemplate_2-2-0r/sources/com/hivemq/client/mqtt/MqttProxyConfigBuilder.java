package com.hivemq.client.mqtt;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttProxyConfigBuilder extends MqttProxyConfigBuilderBase<MqttProxyConfigBuilder> {

    public interface Nested<P> extends MqttProxyConfigBuilderBase<Nested<P>> {
        P applyProxyConfig();
    }

    MqttProxyConfig build();
}
