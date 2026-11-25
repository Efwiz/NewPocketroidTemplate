package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.mqtt.MqttClientConfig;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ClientConfig extends MqttClientConfig {
    @Override // com.hivemq.client.mqtt.MqttClientConfig
    Optional<Mqtt3ClientConnectionConfig> getConnectionConfig();

    Optional<Mqtt3SimpleAuth> getSimpleAuth();

    Optional<Mqtt3Publish> getWillPublish();
}
