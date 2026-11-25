package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.mqtt.MqttClientBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilder;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3WillPublishBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ClientBuilder extends MqttClientBuilderBase<Mqtt3ClientBuilder> {
    Mqtt3Client build();

    Mqtt3AsyncClient buildAsync();

    Mqtt3BlockingClient buildBlocking();

    Mqtt3RxClient buildRx();

    Mqtt3ClientBuilder simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth);

    Mqtt3SimpleAuthBuilder.Nested<? extends Mqtt3ClientBuilder> simpleAuth();

    Mqtt3ClientBuilder willPublish(Mqtt3Publish mqtt3Publish);

    Mqtt3WillPublishBuilder.Nested<? extends Mqtt3ClientBuilder> willPublish();
}
