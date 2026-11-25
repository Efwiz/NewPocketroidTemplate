package com.hivemq.client.internal.mqtt.handler.proxy;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttProxyConfigImpl;
import io.netty.channel.Channel;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* JADX INFO: loaded from: classes10.dex */
public final class MqttProxyInitializer {
    public static void initChannel(Channel channel, MqttClientConfig mqttClientConfig, MqttProxyConfigImpl mqttProxyConfigImpl, Consumer<Channel> consumer, BiConsumer<Channel, Throwable> biConsumer) {
        channel.pipeline().addLast(MqttProxyAdapterHandler.NAME, new MqttProxyAdapterHandler(mqttProxyConfigImpl, mqttClientConfig.getCurrentTransportConfig().getServerAddress(), consumer, biConsumer));
    }

    private MqttProxyInitializer() {
    }
}
