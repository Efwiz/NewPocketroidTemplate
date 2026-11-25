package com.hivemq.client.mqtt.mqtt3.message.subscribe;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase.Complete;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SubscriptionBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt3SubscriptionBuilderBase<C> {
        C qos(MqttQos mqttQos);
    }

    MqttTopicFilterBuilder.Nested<? extends C> topicFilter();

    C topicFilter(MqttTopicFilter mqttTopicFilter);

    C topicFilter(String str);
}
