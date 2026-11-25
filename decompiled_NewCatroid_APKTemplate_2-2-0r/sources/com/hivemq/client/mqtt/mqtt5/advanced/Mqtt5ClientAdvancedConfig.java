package com.hivemq.client.mqtt.mqtt5.advanced;

import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfigBuilder;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientAdvancedConfig {
    Mqtt5ClientAdvancedConfigBuilder extend();

    Mqtt5ClientInterceptors getInterceptors();

    boolean isAllowServerReAuth();

    boolean isValidatePayloadFormat();

    static Mqtt5ClientAdvancedConfigBuilder builder() {
        return new MqttClientAdvancedConfigBuilder.Default();
    }
}
