package com.hivemq.client.mqtt.lifecycle;

import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilderBase;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientAutoReconnectBuilderBase<B extends MqttClientAutoReconnectBuilderBase<B>> {
    B initialDelay(long j, TimeUnit timeUnit);

    B maxDelay(long j, TimeUnit timeUnit);
}
