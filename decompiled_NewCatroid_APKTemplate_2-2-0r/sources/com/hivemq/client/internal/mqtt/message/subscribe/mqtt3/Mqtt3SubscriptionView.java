package com.hivemq.client.internal.mqtt.message.subscribe.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5RetainHandling;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3SubscriptionView implements Mqtt3Subscription {
    private final MqttSubscription delegate;

    private static MqttSubscription delegate(MqttTopicFilterImpl mqttTopicFilterImpl, MqttQos mqttQos) {
        return new MqttSubscription(mqttTopicFilterImpl, mqttQos, false, Mqtt5RetainHandling.SEND, false);
    }

    static Mqtt3SubscriptionView of(MqttTopicFilterImpl mqttTopicFilterImpl, MqttQos mqttQos) {
        return new Mqtt3SubscriptionView(delegate(mqttTopicFilterImpl, mqttQos));
    }

    static Mqtt3SubscriptionView of(MqttSubscription mqttSubscription) {
        return new Mqtt3SubscriptionView(mqttSubscription);
    }

    private Mqtt3SubscriptionView(MqttSubscription mqttSubscription) {
        this.delegate = mqttSubscription;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription
    public MqttTopicFilter getTopicFilter() {
        return this.delegate.getTopicFilter();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription
    public MqttQos getQos() {
        return this.delegate.getQos();
    }

    public MqttSubscription getDelegate() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription
    public Mqtt3SubscriptionViewBuilder.Default extend() {
        return new Mqtt3SubscriptionViewBuilder.Default(this);
    }

    private String toAttributeString() {
        return "topicFilter=" + getTopicFilter() + ", qos=" + getQos();
    }

    public String toString() {
        return "MqttSubscription{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3SubscriptionView) {
            return this.delegate.equals(((Mqtt3SubscriptionView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
