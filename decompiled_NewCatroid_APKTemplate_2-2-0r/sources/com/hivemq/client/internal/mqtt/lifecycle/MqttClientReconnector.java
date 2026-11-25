package com.hivemq.client.internal.mqtt.lifecycle;

import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImpl;
import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import io.netty.channel.EventLoop;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientReconnector implements Mqtt5ClientReconnector {
    private boolean afterOnDisconnected;
    private final int attempts;
    private MqttConnect connect;
    private final EventLoop eventLoop;
    private CompletableFuture<?> future;
    private MqttClientTransportConfigImpl transportConfig;
    private boolean reconnect = false;
    private boolean resubscribeIfSessionExpired = true;
    private boolean republishIfSessionExpired = false;
    private long delayNanos = TimeUnit.MILLISECONDS.toNanos(0);

    public MqttClientReconnector(EventLoop eventLoop, int i, MqttConnect mqttConnect, MqttClientTransportConfigImpl mqttClientTransportConfigImpl) {
        this.eventLoop = eventLoop;
        this.attempts = i;
        this.connect = mqttConnect;
        this.transportConfig = mqttClientTransportConfigImpl;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public int getAttempts() {
        checkInEventLoop();
        return this.attempts;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientReconnector reconnect(boolean z) {
        checkInEventLoop();
        this.reconnect = z;
        return this;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public <T> MqttClientReconnector reconnectWhen(CompletableFuture<T> completableFuture, BiConsumer<? super T, ? super Throwable> biConsumer) {
        checkInOnDisconnected("reconnectWhen");
        Checks.notNull(completableFuture, "Future");
        this.reconnect = true;
        CompletableFuture completableFuture2 = completableFuture;
        if (biConsumer != null) {
            completableFuture2 = (CompletableFuture<T>) completableFuture.whenCompleteAsync((BiConsumer) biConsumer, (Executor) this.eventLoop);
        }
        CompletableFuture<?> completableFuture3 = this.future;
        CompletableFuture completableFutureAllOf = completableFuture2;
        if (completableFuture3 != null) {
            completableFutureAllOf = CompletableFuture.allOf(completableFuture3, completableFuture2);
        }
        this.future = completableFutureAllOf;
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public boolean isReconnect() {
        checkInEventLoop();
        return this.reconnect;
    }

    public CompletableFuture<?> getFuture() {
        checkInEventLoop();
        CompletableFuture<?> completableFuture = this.future;
        return completableFuture == null ? CompletableFuture.completedFuture(null) : completableFuture;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientReconnector resubscribeIfSessionExpired(boolean z) {
        checkInOnDisconnected("resubscribeIfSessionExpired");
        this.resubscribeIfSessionExpired = z;
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public boolean isResubscribeIfSessionExpired() {
        checkInEventLoop();
        return this.resubscribeIfSessionExpired;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientReconnector republishIfSessionExpired(boolean z) {
        checkInOnDisconnected("republishIfSessionExpired");
        this.republishIfSessionExpired = z;
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public boolean isRepublishIfSessionExpired() {
        checkInEventLoop();
        return this.republishIfSessionExpired;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientReconnector delay(long j, TimeUnit timeUnit) {
        checkInOnDisconnected("delay");
        Checks.notNull(timeUnit, "Time unit");
        this.delayNanos = timeUnit.toNanos(j);
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public long getDelay(TimeUnit timeUnit) {
        checkInEventLoop();
        Checks.notNull(timeUnit, "Time unit");
        return timeUnit.convert(this.delayNanos, TimeUnit.NANOSECONDS);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientReconnector transportConfig(MqttClientTransportConfig mqttClientTransportConfig) {
        checkInEventLoop();
        this.transportConfig = (MqttClientTransportConfigImpl) Checks.notImplemented(mqttClientTransportConfig, MqttClientTransportConfigImpl.class, "Transport config");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientTransportConfigImplBuilder.Nested<MqttClientReconnector> transportConfig() {
        checkInEventLoop();
        return new MqttClientTransportConfigImplBuilder.Nested<>(this.transportConfig, new Function() { // from class: com.hivemq.client.internal.mqtt.lifecycle.MqttClientReconnector$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.transportConfig((MqttClientTransportConfig) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientTransportConfigImpl getTransportConfig() {
        checkInEventLoop();
        return this.transportConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector
    public MqttClientReconnector connect(Mqtt5Connect mqtt5Connect) {
        checkInEventLoop();
        this.connect = MqttChecks.connect(mqtt5Connect);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector
    public MqttConnectBuilder.Nested<MqttClientReconnector> connectWith() {
        checkInEventLoop();
        return new MqttConnectBuilder.Nested<>(this.connect, new Function() { // from class: com.hivemq.client.internal.mqtt.lifecycle.MqttClientReconnector$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((Mqtt5Connect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.lifecycle.Mqtt5ClientReconnector
    public MqttConnect getConnect() {
        checkInEventLoop();
        return this.connect;
    }

    public void afterOnDisconnected() {
        this.afterOnDisconnected = true;
    }

    private void checkInEventLoop() {
        Checks.state(this.eventLoop.inEventLoop(), "MqttClientReconnector must be called from the eventLoop.");
    }

    private void checkInOnDisconnected(String str) {
        checkInEventLoop();
        if (this.afterOnDisconnected) {
            throw new UnsupportedOperationException(str + " must only be called in onDisconnected.");
        }
    }
}
