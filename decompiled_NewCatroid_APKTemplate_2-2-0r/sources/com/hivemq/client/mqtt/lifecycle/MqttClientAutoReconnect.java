package com.hivemq.client.mqtt.lifecycle;

import com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImplBuilder;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientAutoReconnect extends MqttClientDisconnectedListener {
    public static final long DEFAULT_MAX_DELAY_S = 120;
    public static final long DEFAULT_START_DELAY_S = 1;

    MqttClientAutoReconnectBuilder extend();

    long getInitialDelay(TimeUnit timeUnit);

    long getMaxDelay(TimeUnit timeUnit);

    static MqttClientAutoReconnectBuilder builder() {
        return new MqttClientAutoReconnectImplBuilder.Default();
    }
}
