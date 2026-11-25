package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.mqtt.MqttClientBuilderBase;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfigBuilder;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublishBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientBuilder extends MqttClientBuilderBase<Mqtt5ClientBuilder> {
    Mqtt5ClientBuilder advancedConfig(Mqtt5ClientAdvancedConfig mqtt5ClientAdvancedConfig);

    Mqtt5ClientAdvancedConfigBuilder.Nested<? extends Mqtt5ClientBuilder> advancedConfig();

    Mqtt5Client build();

    Mqtt5AsyncClient buildAsync();

    Mqtt5BlockingClient buildBlocking();

    Mqtt5RxClient buildRx();

    Mqtt5ClientBuilder enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism);

    Mqtt5ClientBuilder simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth);

    Mqtt5SimpleAuthBuilder.Nested<? extends Mqtt5ClientBuilder> simpleAuth();

    Mqtt5ClientBuilder willPublish(Mqtt5Publish mqtt5Publish);

    Mqtt5WillPublishBuilder.Nested<? extends Mqtt5ClientBuilder> willPublish();
}
