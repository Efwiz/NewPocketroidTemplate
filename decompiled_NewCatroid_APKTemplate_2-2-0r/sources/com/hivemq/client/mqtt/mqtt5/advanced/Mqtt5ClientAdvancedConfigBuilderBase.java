package com.hivemq.client.mqtt.mqtt5.advanced;

import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientAdvancedConfigBuilderBase<B extends Mqtt5ClientAdvancedConfigBuilderBase<B>> {
    B allowServerReAuth(boolean z);

    B interceptors(Mqtt5ClientInterceptors mqtt5ClientInterceptors);

    Mqtt5ClientInterceptorsBuilder.Nested<? extends B> interceptors();

    B validatePayloadFormat(boolean z);
}
