package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.MqttClientExecutorConfigImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttClientExecutorConfig;
import com.hivemq.client.mqtt.MqttClientExecutorConfigBuilder;
import com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase;
import io.reactivex.Scheduler;
import java.util.concurrent.Executor;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttClientExecutorConfigImplBuilder<B extends MqttClientExecutorConfigImplBuilder<B>> {
    private Scheduler applicationScheduler;
    private Executor nettyExecutor;
    private int nettyThreads;

    abstract B self();

    MqttClientExecutorConfigImplBuilder() {
        this.nettyThreads = 0;
        this.applicationScheduler = MqttClientExecutorConfigImpl.DEFAULT_APPLICATION_SCHEDULER;
    }

    MqttClientExecutorConfigImplBuilder(MqttClientExecutorConfigImpl mqttClientExecutorConfigImpl) {
        this.nettyThreads = 0;
        this.applicationScheduler = MqttClientExecutorConfigImpl.DEFAULT_APPLICATION_SCHEDULER;
        this.nettyExecutor = mqttClientExecutorConfigImpl.getRawNettyExecutor();
        this.nettyThreads = mqttClientExecutorConfigImpl.getRawNettyThreads();
        this.applicationScheduler = mqttClientExecutorConfigImpl.getApplicationScheduler();
    }

    public B nettyExecutor(Executor executor) {
        this.nettyExecutor = executor;
        return (B) self();
    }

    public B nettyThreads(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("Number of Netty threads must be greater than 0. Found: " + i);
        }
        this.nettyThreads = i;
        return (B) self();
    }

    public B applicationScheduler(Scheduler scheduler) {
        this.applicationScheduler = (Scheduler) Checks.notNull(scheduler, "Application scheduler");
        return (B) self();
    }

    public MqttClientExecutorConfigImpl build() {
        return new MqttClientExecutorConfigImpl(this.nettyExecutor, this.nettyThreads, this.applicationScheduler);
    }

    public static class Default extends MqttClientExecutorConfigImplBuilder<Default> implements MqttClientExecutorConfigBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttClientExecutorConfigImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilderBase applicationScheduler(Scheduler scheduler) {
            return (MqttClientExecutorConfigBuilderBase) super.applicationScheduler(scheduler);
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilder
        public /* bridge */ /* synthetic */ MqttClientExecutorConfig build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilderBase nettyExecutor(Executor executor) {
            return (MqttClientExecutorConfigBuilderBase) super.nettyExecutor(executor);
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilderBase nettyThreads(int i) {
            return (MqttClientExecutorConfigBuilderBase) super.nettyThreads(i);
        }

        public Default() {
        }

        Default(MqttClientExecutorConfigImpl mqttClientExecutorConfigImpl) {
            super(mqttClientExecutorConfigImpl);
        }
    }

    public static class Nested<P> extends MqttClientExecutorConfigImplBuilder<Nested<P>> implements MqttClientExecutorConfigBuilder.Nested<P> {
        private final Function<? super MqttClientExecutorConfigImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttClientExecutorConfigImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilderBase applicationScheduler(Scheduler scheduler) {
            return (MqttClientExecutorConfigBuilderBase) super.applicationScheduler(scheduler);
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilderBase nettyExecutor(Executor executor) {
            return (MqttClientExecutorConfigBuilderBase) super.nettyExecutor(executor);
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilderBase nettyThreads(int i) {
            return (MqttClientExecutorConfigBuilderBase) super.nettyThreads(i);
        }

        Nested(MqttClientExecutorConfigImpl mqttClientExecutorConfigImpl, Function<? super MqttClientExecutorConfigImpl, P> function) {
            super(mqttClientExecutorConfigImpl);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.MqttClientExecutorConfigBuilder.Nested
        public P applyExecutorConfig() {
            return this.parentConsumer.apply(build());
        }
    }
}
