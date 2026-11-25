package com.hivemq.client.mqtt.mqtt5.message.subscribe;

import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscriptionBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Subscription {
    public static final boolean DEFAULT_NO_LOCAL = false;
    public static final boolean DEFAULT_RETAIN_AS_PUBLISHED = false;
    public static final MqttQos DEFAULT_QOS = MqttQos.EXACTLY_ONCE;
    public static final Mqtt5RetainHandling DEFAULT_RETAIN_HANDLING = Mqtt5RetainHandling.SEND;

    Mqtt5SubscriptionBuilder extend();

    MqttQos getQos();

    Mqtt5RetainHandling getRetainHandling();

    MqttTopicFilter getTopicFilter();

    boolean isNoLocal();

    boolean isRetainAsPublished();

    static Mqtt5SubscriptionBuilder builder() {
        return new MqttSubscriptionBuilder.Default();
    }
}
