package com.hivemq.client.mqtt.mqtt5.message.subscribe;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SubscriptionBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt5SubscriptionBuilderBase<C> {
        C noLocal(boolean z);

        C qos(MqttQos mqttQos);

        C retainAsPublished(boolean z);

        C retainHandling(Mqtt5RetainHandling mqtt5RetainHandling);
    }

    MqttTopicFilterBuilder.Nested<? extends C> topicFilter();

    C topicFilter(MqttTopicFilter mqttTopicFilter);

    C topicFilter(String str);
}
