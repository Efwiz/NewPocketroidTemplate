package com.hivemq.client.mqtt;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientExecutorConfigBuilder extends MqttClientExecutorConfigBuilderBase<MqttClientExecutorConfigBuilder> {

    public interface Nested<P> extends MqttClientExecutorConfigBuilderBase<Nested<P>> {
        P applyExecutorConfig();
    }

    MqttClientExecutorConfig build();
}
