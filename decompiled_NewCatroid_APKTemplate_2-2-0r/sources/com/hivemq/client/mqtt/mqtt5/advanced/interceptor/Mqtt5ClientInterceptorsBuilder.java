package com.hivemq.client.mqtt.mqtt5.advanced.interceptor;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientInterceptorsBuilder extends Mqtt5ClientInterceptorsBuilderBase<Mqtt5ClientInterceptorsBuilder> {

    public interface Nested<P> extends Mqtt5ClientInterceptorsBuilderBase<Nested<P>> {
        P applyInterceptors();
    }

    Mqtt5ClientInterceptors build();
}
