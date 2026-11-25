package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.MqttWebSocketConfigImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttWebSocketConfig;
import com.hivemq.client.mqtt.MqttWebSocketConfigBuilder;
import com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttWebSocketConfigImplBuilder<B extends MqttWebSocketConfigImplBuilder<B>> {
    private int handshakeTimeoutMs;
    private String queryString;
    private String serverPath;
    private String subprotocol;

    abstract B self();

    MqttWebSocketConfigImplBuilder() {
        this.serverPath = "";
        this.queryString = "";
        this.subprotocol = MqttWebSocketConfig.DEFAULT_MQTT_SUBPROTOCOL;
        this.handshakeTimeoutMs = 10000;
    }

    MqttWebSocketConfigImplBuilder(MqttWebSocketConfigImpl mqttWebSocketConfigImpl) {
        this.serverPath = "";
        this.queryString = "";
        this.subprotocol = MqttWebSocketConfig.DEFAULT_MQTT_SUBPROTOCOL;
        this.handshakeTimeoutMs = 10000;
        if (mqttWebSocketConfigImpl != null) {
            this.serverPath = mqttWebSocketConfigImpl.getServerPath();
            this.queryString = mqttWebSocketConfigImpl.getQueryString();
            this.subprotocol = mqttWebSocketConfigImpl.getSubprotocol();
            this.handshakeTimeoutMs = mqttWebSocketConfigImpl.getHandshakeTimeoutMs();
        }
    }

    public B serverPath(String str) {
        this.serverPath = ((String) Checks.notNull(str, "Server path")).replaceAll("^/+", "");
        return (B) self();
    }

    public B queryString(String str) {
        this.queryString = (String) Checks.notNull(str, "Query string");
        return (B) self();
    }

    public B subprotocol(String str) {
        this.subprotocol = (String) Checks.notNull(str, "Subprotocol");
        return (B) self();
    }

    public B handshakeTimeout(long j, TimeUnit timeUnit) {
        Checks.notNull(timeUnit, "Time unit");
        this.handshakeTimeoutMs = (int) Checks.range(timeUnit.toMillis(j), 0L, 2147483647L, "Handshake timeout in milliseconds");
        return (B) self();
    }

    public MqttWebSocketConfigImpl build() {
        return new MqttWebSocketConfigImpl(this.serverPath, this.queryString, this.subprotocol, this.handshakeTimeoutMs);
    }

    public static class Default extends MqttWebSocketConfigImplBuilder<Default> implements MqttWebSocketConfigBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttWebSocketConfigImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilder
        public /* bridge */ /* synthetic */ MqttWebSocketConfig build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase handshakeTimeout(long j, TimeUnit timeUnit) {
            return (MqttWebSocketConfigBuilderBase) super.handshakeTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase queryString(String str) {
            return (MqttWebSocketConfigBuilderBase) super.queryString(str);
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase serverPath(String str) {
            return (MqttWebSocketConfigBuilderBase) super.serverPath(str);
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase subprotocol(String str) {
            return (MqttWebSocketConfigBuilderBase) super.subprotocol(str);
        }

        public Default() {
        }

        Default(MqttWebSocketConfigImpl mqttWebSocketConfigImpl) {
            super(mqttWebSocketConfigImpl);
        }
    }

    public static class Nested<P> extends MqttWebSocketConfigImplBuilder<Nested<P>> implements MqttWebSocketConfigBuilder.Nested<P> {
        private final Function<? super MqttWebSocketConfigImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttWebSocketConfigImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase handshakeTimeout(long j, TimeUnit timeUnit) {
            return (MqttWebSocketConfigBuilderBase) super.handshakeTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase queryString(String str) {
            return (MqttWebSocketConfigBuilderBase) super.queryString(str);
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase serverPath(String str) {
            return (MqttWebSocketConfigBuilderBase) super.serverPath(str);
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilderBase subprotocol(String str) {
            return (MqttWebSocketConfigBuilderBase) super.subprotocol(str);
        }

        Nested(MqttWebSocketConfigImpl mqttWebSocketConfigImpl, Function<? super MqttWebSocketConfigImpl, P> function) {
            super(mqttWebSocketConfigImpl);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.MqttWebSocketConfigBuilder.Nested
        public P applyWebSocketConfig() {
            return this.parentConsumer.apply(build());
        }
    }
}
