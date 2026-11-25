package com.hivemq.client.mqtt.mqtt5.lifecycle;

import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import com.hivemq.client.mqtt.lifecycle.MqttClientReconnector;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientReconnector extends MqttClientReconnector {
    Mqtt5ClientReconnector connect(Mqtt5Connect mqtt5Connect);

    Mqtt5ConnectBuilder.Nested<? extends Mqtt5ClientReconnector> connectWith();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt5ClientReconnector delay(long j, TimeUnit timeUnit);

    Mqtt5Connect getConnect();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt5ClientReconnector reconnect(boolean z);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    <T> Mqtt5ClientReconnector reconnectWhen(CompletableFuture<T> completableFuture, BiConsumer<? super T, ? super Throwable> biConsumer);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt5ClientReconnector republishIfSessionExpired(boolean z);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt5ClientReconnector resubscribeIfSessionExpired(boolean z);

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    MqttClientTransportConfigBuilder.Nested<? extends Mqtt5ClientReconnector> transportConfig();

    @Override // com.hivemq.client.mqtt.lifecycle.MqttClientReconnector
    Mqtt5ClientReconnector transportConfig(MqttClientTransportConfig mqttClientTransportConfig);
}
