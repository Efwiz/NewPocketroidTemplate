package com.hivemq.client.mqtt;

import com.hivemq.client.internal.mqtt.MqttClientExecutorConfigImplBuilder;
import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientExecutorConfig {
    public static final Scheduler DEFAULT_APPLICATION_SCHEDULER = Schedulers.computation();

    MqttClientExecutorConfigBuilder extend();

    Scheduler getApplicationScheduler();

    Optional<Executor> getNettyExecutor();

    OptionalInt getNettyThreads();

    static MqttClientExecutorConfigBuilder builder() {
        return new MqttClientExecutorConfigImplBuilder.Default();
    }
}
