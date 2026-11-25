package com.hivemq.client.mqtt.mqtt3.lifecycle;

import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import com.hivemq.client.mqtt.lifecycle.MqttClientReconnector;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ClientReconnector extends MqttClientReconnector {
    Mqtt3ClientReconnector connect(Mqtt3Connect mqtt3Connect);

    Mqtt3ConnectBuilder.Nested<? extends Mqtt3ClientReconnector> connectWith();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt3ClientReconnector delay(long j, TimeUnit timeUnit);

    Mqtt3Connect getConnect();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt3ClientReconnector reconnect(boolean z);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    <T> Mqtt3ClientReconnector reconnectWhen(CompletableFuture<T> completableFuture, BiConsumer<? super T, ? super Throwable> biConsumer);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt3ClientReconnector republishIfSessionExpired(boolean z);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt3ClientReconnector resubscribeIfSessionExpired(boolean z);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    MqttClientTransportConfigBuilder.Nested<? extends Mqtt3ClientReconnector> transportConfig();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt3ClientReconnector transportConfig(MqttClientTransportConfig mqttClientTransportConfig);
}
