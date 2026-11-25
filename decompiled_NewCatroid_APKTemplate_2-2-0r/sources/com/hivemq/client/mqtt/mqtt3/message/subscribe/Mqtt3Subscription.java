package com.hivemq.client.mqtt.mqtt3.message.subscribe;

import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscriptionViewBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Subscription {
    public static final MqttQos DEFAULT_QOS = MqttQos.EXACTLY_ONCE;

    Mqtt3SubscriptionBuilder extend();

    MqttQos getQos();

    MqttTopicFilter getTopicFilter();

    static Mqtt3SubscriptionBuilder builder() {
        return new Mqtt3SubscriptionViewBuilder.Default();
    }
}
