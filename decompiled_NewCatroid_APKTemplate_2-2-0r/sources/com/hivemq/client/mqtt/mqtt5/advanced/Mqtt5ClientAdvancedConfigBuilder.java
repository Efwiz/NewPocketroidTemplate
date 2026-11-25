package com.hivemq.client.mqtt.mqtt5.advanced;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientAdvancedConfigBuilder extends Mqtt5ClientAdvancedConfigBuilderBase<Mqtt5ClientAdvancedConfigBuilder> {

    public interface Nested<P> extends Mqtt5ClientAdvancedConfigBuilderBase<Nested<P>> {
        P applyAdvancedConfig();
    }

    Mqtt5ClientAdvancedConfig build();
}
