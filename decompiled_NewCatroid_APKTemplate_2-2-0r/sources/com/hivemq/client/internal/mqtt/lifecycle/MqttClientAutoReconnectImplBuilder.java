package com.hivemq.client.internal.mqtt.lifecycle;

import com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilder;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilderBase;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttClientAutoReconnectImplBuilder<B extends MqttClientAutoReconnectImplBuilder<B>> {
    private long initialDelayNanos;
    private long maxDelayNanos;

    abstract B self();

    MqttClientAutoReconnectImplBuilder() {
        this.initialDelayNanos = MqttClientAutoReconnectImpl.DEFAULT_START_DELAY_NANOS;
        this.maxDelayNanos = MqttClientAutoReconnectImpl.DEFAULT_MAX_DELAY_NANOS;
    }

    MqttClientAutoReconnectImplBuilder(MqttClientAutoReconnectImpl mqttClientAutoReconnectImpl) {
        this.initialDelayNanos = MqttClientAutoReconnectImpl.DEFAULT_START_DELAY_NANOS;
        this.maxDelayNanos = MqttClientAutoReconnectImpl.DEFAULT_MAX_DELAY_NANOS;
        if (mqttClientAutoReconnectImpl != null) {
            this.initialDelayNanos = mqttClientAutoReconnectImpl.getInitialDelay(TimeUnit.NANOSECONDS);
            this.maxDelayNanos = mqttClientAutoReconnectImpl.getMaxDelay(TimeUnit.NANOSECONDS);
        }
    }

    public B initialDelay(long j, TimeUnit timeUnit) {
        if (j <= 0) {
            throw new IllegalArgumentException("Initial delay must be positive.");
        }
        Checks.notNull(timeUnit, "Time unit");
        this.initialDelayNanos = timeUnit.toNanos(j);
        return (B) self();
    }

    public B maxDelay(long j, TimeUnit timeUnit) {
        if (j < 0) {
            throw new IllegalArgumentException("Maximum delay must be positive or zero.");
        }
        Checks.notNull(timeUnit, "Time unit");
        this.maxDelayNanos = timeUnit.toNanos(j);
        return (B) self();
    }

    public MqttClientAutoReconnectImpl build() {
        return new MqttClientAutoReconnectImpl(this.initialDelayNanos, this.maxDelayNanos);
    }

    public static class Default extends MqttClientAutoReconnectImplBuilder<Default> implements MqttClientAutoReconnectBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilder
        public /* bridge */ /* synthetic */ MqttClientAutoReconnect build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilderBase
        public /* bridge */ /* synthetic */ MqttClientAutoReconnectBuilderBase initialDelay(long j, TimeUnit timeUnit) {
            return (MqttClientAutoReconnectBuilderBase) super.initialDelay(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilderBase
        public /* bridge */ /* synthetic */ MqttClientAutoReconnectBuilderBase maxDelay(long j, TimeUnit timeUnit) {
            return (MqttClientAutoReconnectBuilderBase) super.maxDelay(j, timeUnit);
        }

        public Default() {
        }

        Default(MqttClientAutoReconnectImpl mqttClientAutoReconnectImpl) {
            super(mqttClientAutoReconnectImpl);
        }
    }

    public static class Nested<P> extends MqttClientAutoReconnectImplBuilder<Nested<P>> implements MqttClientAutoReconnectBuilder.Nested<P> {
        private final Function<? super MqttClientAutoReconnectImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilderBase
        public /* bridge */ /* synthetic */ MqttClientAutoReconnectBuilderBase initialDelay(long j, TimeUnit timeUnit) {
            return (MqttClientAutoReconnectBuilderBase) super.initialDelay(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilderBase
        public /* bridge */ /* synthetic */ MqttClientAutoReconnectBuilderBase maxDelay(long j, TimeUnit timeUnit) {
            return (MqttClientAutoReconnectBuilderBase) super.maxDelay(j, timeUnit);
        }

        public Nested(MqttClientAutoReconnectImpl mqttClientAutoReconnectImpl, Function<? super MqttClientAutoReconnectImpl, P> function) {
            super(mqttClientAutoReconnectImpl);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilder.Nested
        public P applyAutomaticReconnect() {
            return this.parentConsumer.apply(build());
        }
    }
}
