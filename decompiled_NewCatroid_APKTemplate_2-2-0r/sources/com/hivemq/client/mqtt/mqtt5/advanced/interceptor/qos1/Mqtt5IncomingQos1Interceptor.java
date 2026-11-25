package com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos1;

import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5IncomingQos1Interceptor {
    void onPublish(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Publish mqtt5Publish, Mqtt5PubAckBuilder mqtt5PubAckBuilder);
}
