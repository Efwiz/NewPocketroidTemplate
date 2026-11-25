package com.hivemq.client.internal.mqtt.lifecycle.mqtt3;

import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImpl;
import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientReconnector;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3ClientReconnectorView implements Mqtt3ClientReconnector {
    private final MqttClientReconnector delegate;

    Mqtt3ClientReconnectorView(MqttClientReconnector mqttClientReconnector) {
        this.delegate = mqttClientReconnector;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public Mqtt3ClientReconnectorView reconnect(boolean z) {
        this.delegate.reconnect(z);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public <T> Mqtt3ClientReconnectorView reconnectWhen(CompletableFuture<T> completableFuture, BiConsumer<? super T, ? super Throwable> biConsumer) {
        this.delegate.reconnectWhen((CompletableFuture) completableFuture, (BiConsumer) biConsumer);
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public boolean isReconnect() {
        return this.delegate.isReconnect();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public Mqtt3ClientReconnectorView resubscribeIfSessionExpired(boolean z) {
        this.delegate.resubscribeIfSessionExpired(z);
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public boolean isResubscribeIfSessionExpired() {
        return this.delegate.isResubscribeIfSessionExpired();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public Mqtt3ClientReconnectorView republishIfSessionExpired(boolean z) {
        this.delegate.republishIfSessionExpired(z);
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public boolean isRepublishIfSessionExpired() {
        return this.delegate.isRepublishIfSessionExpired();
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public int getAttempts() {
        return this.delegate.getAttempts();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public Mqtt3ClientReconnectorView delay(long j, TimeUnit timeUnit) {
        this.delegate.delay(j, timeUnit);
        return this;
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public long getDelay(TimeUnit timeUnit) {
        return this.delegate.getDelay(timeUnit);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public Mqtt3ClientReconnectorView transportConfig(MqttClientTransportConfig mqttClientTransportConfig) {
        this.delegate.transportConfig(mqttClientTransportConfig);
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector, com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientTransportConfigImplBuilder.Nested<Mqtt3ClientReconnectorView> transportConfig() {
        return new MqttClientTransportConfigImplBuilder.Nested<>(getTransportConfig(), new Function() { // from class: com.hivemq.client.internal.mqtt.lifecycle.mqtt3.Mqtt3ClientReconnectorView$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.transportConfig((MqttClientTransportConfig) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    public MqttClientTransportConfigImpl getTransportConfig() {
        return this.delegate.getTransportConfig();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector
    public Mqtt3ClientReconnectorView connect(Mqtt3Connect mqtt3Connect) {
        this.delegate.connect((Mqtt5Connect) MqttChecks.connect(mqtt3Connect));
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector
    public Mqtt3ConnectViewBuilder.Nested<Mqtt3ClientReconnectorView> connectWith() {
        return new Mqtt3ConnectViewBuilder.Nested<>(getConnect(), new Function() { // from class: com.hivemq.client.internal.mqtt.lifecycle.mqtt3.Mqtt3ClientReconnectorView$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((Mqtt3Connect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.lifecycle.Mqtt3ClientReconnector
    public Mqtt3ConnectView getConnect() {
        return Mqtt3ConnectView.of(this.delegate.getConnect());
    }
}
