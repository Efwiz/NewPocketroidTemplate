package com.hivemq.client.mqtt;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttWebSocketConfigBuilder extends MqttWebSocketConfigBuilderBase<MqttWebSocketConfigBuilder> {

    public interface Nested<P> extends MqttWebSocketConfigBuilderBase<Nested<P>> {
        P applyWebSocketConfig();
    }

    MqttWebSocketConfig build();
}
