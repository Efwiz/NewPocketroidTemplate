package com.hivemq.client.internal.mqtt.advanced;

import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptors;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig;
import java.util.Objects;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientAdvancedConfig implements Mqtt5ClientAdvancedConfig {
    public static final MqttClientAdvancedConfig DEFAULT = new MqttClientAdvancedConfig(false, false, null);
    private final boolean allowServerReAuth;
    private final MqttClientInterceptors interceptors;
    private final boolean validatePayloadFormat;

    MqttClientAdvancedConfig(boolean z, boolean z2, MqttClientInterceptors mqttClientInterceptors) {
        this.allowServerReAuth = z;
        this.validatePayloadFormat = z2;
        this.interceptors = mqttClientInterceptors;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig
    public boolean isAllowServerReAuth() {
        return this.allowServerReAuth;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig
    public boolean isValidatePayloadFormat() {
        return this.validatePayloadFormat;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig
    public MqttClientInterceptors getInterceptors() {
        return this.interceptors;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig
    public MqttClientAdvancedConfigBuilder.Default extend() {
        return new MqttClientAdvancedConfigBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttClientAdvancedConfig)) {
            return false;
        }
        MqttClientAdvancedConfig mqttClientAdvancedConfig = (MqttClientAdvancedConfig) obj;
        return this.allowServerReAuth == mqttClientAdvancedConfig.allowServerReAuth && this.validatePayloadFormat == mqttClientAdvancedConfig.validatePayloadFormat && Objects.equals(this.interceptors, mqttClientAdvancedConfig.interceptors);
    }

    public int hashCode() {
        return (((Boolean.hashCode(this.allowServerReAuth) * 31) + Boolean.hashCode(this.validatePayloadFormat)) * 31) + Objects.hashCode(this.interceptors);
    }
}
