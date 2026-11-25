package com.hivemq.client.mqtt.mqtt5.advanced.interceptor.qos2;

import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRel;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5IncomingQos2Interceptor {
    void onPubRel(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5PubRel mqtt5PubRel, Mqtt5PubCompBuilder mqtt5PubCompBuilder);

    void onPublish(Mqtt5ClientConfig mqtt5ClientConfig, Mqtt5Publish mqtt5Publish, Mqtt5PubRecBuilder mqtt5PubRecBuilder);
}
