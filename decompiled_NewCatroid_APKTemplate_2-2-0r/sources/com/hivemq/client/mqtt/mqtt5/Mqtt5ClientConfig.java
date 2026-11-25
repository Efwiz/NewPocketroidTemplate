package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.mqtt.MqttClientConfig;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ClientConfig extends MqttClientConfig {
    Mqtt5ClientAdvancedConfig getAdvancedConfig();

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    Optional<Mqtt5ClientConnectionConfig> getConnectionConfig();

    Optional<Mqtt5EnhancedAuthMechanism> getEnhancedAuthMechanism();

    Optional<Mqtt5SimpleAuth> getSimpleAuth();

    Optional<Mqtt5WillPublish> getWillPublish();
}
