package com.hivemq.client.internal.mqtt;

import com.hivemq.client.mqtt.MqttClientExecutorConfig;
import io.reactivex.Scheduler;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientExecutorConfigImpl implements MqttClientExecutorConfig {
    public static final MqttClientExecutorConfigImpl DEFAULT = new MqttClientExecutorConfigImpl(null, 0, DEFAULT_APPLICATION_SCHEDULER);
    public static final int DEFAULT_NETTY_THREADS = 0;
    private final Scheduler applicationScheduler;
    private final Executor nettyExecutor;
    private final int nettyThreads;

    MqttClientExecutorConfigImpl(Executor executor, int i, Scheduler scheduler) {
        this.nettyExecutor = executor;
        this.nettyThreads = i;
        this.applicationScheduler = scheduler;
    }

    @Override // com.hivemq.client.mqtt.MqttClientExecutorConfig
    public Optional<Executor> getNettyExecutor() {
        return Optional.ofNullable(this.nettyExecutor);
    }

    public Executor getRawNettyExecutor() {
        return this.nettyExecutor;
    }

    @Override // com.hivemq.client.mqtt.MqttClientExecutorConfig
    public OptionalInt getNettyThreads() {
        int i = this.nettyThreads;
        return i == 0 ? OptionalInt.empty() : OptionalInt.of(i);
    }

    public int getRawNettyThreads() {
        return this.nettyThreads;
    }

    @Override // com.hivemq.client.mqtt.MqttClientExecutorConfig
    public Scheduler getApplicationScheduler() {
        return this.applicationScheduler;
    }

    @Override // com.hivemq.client.mqtt.MqttClientExecutorConfig
    public MqttClientExecutorConfigImplBuilder.Default extend() {
        return new MqttClientExecutorConfigImplBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttClientExecutorConfigImpl)) {
            return false;
        }
        MqttClientExecutorConfigImpl mqttClientExecutorConfigImpl = (MqttClientExecutorConfigImpl) obj;
        return Objects.equals(this.nettyExecutor, mqttClientExecutorConfigImpl.nettyExecutor) && this.nettyThreads == mqttClientExecutorConfigImpl.nettyThreads && this.applicationScheduler.equals(mqttClientExecutorConfigImpl.applicationScheduler);
    }

    public int hashCode() {
        return (((Objects.hashCode(this.nettyExecutor) * 31) + this.nettyThreads) * 31) + this.applicationScheduler.hashCode();
    }
}
