package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.MqttClientExecutorConfigBuilderBase;
import io.reactivex.Scheduler;
import java.util.concurrent.Executor;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientExecutorConfigBuilderBase<B extends MqttClientExecutorConfigBuilderBase<B>> {
    B applicationScheduler(Scheduler scheduler);

    B nettyExecutor(Executor executor);

    B nettyThreads(int i);
}
