package com.hivemq.client.mqtt.lifecycle;

import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientReconnector {
    public static final long DEFAULT_DELAY_MS = 0;
    public static final boolean DEFAULT_RECONNECT = false;
    public static final boolean DEFAULT_REPUBLISH_IF_SESSION_EXPIRED = false;
    public static final boolean DEFAULT_RESUBSCRIBE_IF_SESSION_EXPIRED = true;

    MqttClientReconnector delay(long j, TimeUnit timeUnit);

    int getAttempts();

    long getDelay(TimeUnit timeUnit);

    MqttClientTransportConfig getTransportConfig();

    boolean isReconnect();

    boolean isRepublishIfSessionExpired();

    boolean isResubscribeIfSessionExpired();

    MqttClientReconnector reconnect(boolean z);

    <T> MqttClientReconnector reconnectWhen(CompletableFuture<T> completableFuture, BiConsumer<? super T, ? super Throwable> biConsumer);

    MqttClientReconnector republishIfSessionExpired(boolean z);

    MqttClientReconnector resubscribeIfSessionExpired(boolean z);

    MqttClientTransportConfigBuilder.Nested<? extends MqttClientReconnector> transportConfig();

    MqttClientReconnector transportConfig(MqttClientTransportConfig mqttClientTransportConfig);
}
