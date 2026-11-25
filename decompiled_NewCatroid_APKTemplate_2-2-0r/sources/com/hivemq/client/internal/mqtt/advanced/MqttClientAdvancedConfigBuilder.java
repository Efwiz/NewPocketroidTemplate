package com.hivemq.client.internal.mqtt.advanced;

import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfigBuilder;
import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptors;
import com.hivemq.client.internal.mqtt.advanced.interceptor.MqttClientInterceptorsBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilder;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptors;
import com.hivemq.client.mqtt.mqtt5.advanced.interceptor.Mqtt5ClientInterceptorsBuilder;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttClientAdvancedConfigBuilder<B extends MqttClientAdvancedConfigBuilder<B>> {
    private boolean allowServerReAuth;
    private MqttClientInterceptors interceptors;
    private boolean validatePayloadFormat;

    abstract B self();

    MqttClientAdvancedConfigBuilder() {
    }

    MqttClientAdvancedConfigBuilder(MqttClientAdvancedConfig mqttClientAdvancedConfig) {
        this.allowServerReAuth = mqttClientAdvancedConfig.isAllowServerReAuth();
        this.validatePayloadFormat = mqttClientAdvancedConfig.isValidatePayloadFormat();
        this.interceptors = mqttClientAdvancedConfig.getInterceptors();
    }

    public B allowServerReAuth(boolean z) {
        this.allowServerReAuth = z;
        return (B) self();
    }

    public B validatePayloadFormat(boolean z) {
        this.validatePayloadFormat = z;
        return (B) self();
    }

    public B interceptors(Mqtt5ClientInterceptors mqtt5ClientInterceptors) {
        this.interceptors = (MqttClientInterceptors) Checks.notImplementedOrNull(mqtt5ClientInterceptors, MqttClientInterceptors.class, "Interceptors");
        return (B) self();
    }

    public MqttClientInterceptorsBuilder.Nested<B> interceptors() {
        return new MqttClientInterceptorsBuilder.Nested<>(this.interceptors, new Function() { // from class: com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfigBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.interceptors((MqttClientInterceptors) obj);
            }
        });
    }

    public MqttClientAdvancedConfig build() {
        return new MqttClientAdvancedConfig(this.allowServerReAuth, this.validatePayloadFormat, this.interceptors);
    }

    public static class Default extends MqttClientAdvancedConfigBuilder<Default> implements Mqtt5ClientAdvancedConfigBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfigBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfigBuilderBase allowServerReAuth(boolean z) {
            return (Mqtt5ClientAdvancedConfigBuilderBase) super.allowServerReAuth(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilder
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfig build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfigBuilderBase interceptors(Mqtt5ClientInterceptors mqtt5ClientInterceptors) {
            return (Mqtt5ClientAdvancedConfigBuilderBase) super.interceptors(mqtt5ClientInterceptors);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilder.Nested interceptors() {
            return super.interceptors();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfigBuilderBase validatePayloadFormat(boolean z) {
            return (Mqtt5ClientAdvancedConfigBuilderBase) super.validatePayloadFormat(z);
        }

        public Default() {
        }

        Default(MqttClientAdvancedConfig mqttClientAdvancedConfig) {
            super(mqttClientAdvancedConfig);
        }
    }

    public static class Nested<P> extends MqttClientAdvancedConfigBuilder<Nested<P>> implements Mqtt5ClientAdvancedConfigBuilder.Nested<P> {
        private final Function<? super MqttClientAdvancedConfig, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfigBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfigBuilderBase allowServerReAuth(boolean z) {
            return (Mqtt5ClientAdvancedConfigBuilderBase) super.allowServerReAuth(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfigBuilderBase interceptors(Mqtt5ClientInterceptors mqtt5ClientInterceptors) {
            return (Mqtt5ClientAdvancedConfigBuilderBase) super.interceptors(mqtt5ClientInterceptors);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientInterceptorsBuilder.Nested interceptors() {
            return super.interceptors();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5ClientAdvancedConfigBuilderBase validatePayloadFormat(boolean z) {
            return (Mqtt5ClientAdvancedConfigBuilderBase) super.validatePayloadFormat(z);
        }

        public Nested(MqttClientAdvancedConfig mqttClientAdvancedConfig, Function<? super MqttClientAdvancedConfig, P> function) {
            super(mqttClientAdvancedConfig);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilder.Nested
        public P applyAdvancedConfig() {
            return this.parentConsumer.apply(build());
        }
    }
}
