package com.hivemq.client.internal.mqtt.message.subscribe;

import com.hivemq.client.internal.mqtt.message.MqttStatefulMessage;

/* JADX INFO: loaded from: classes10.dex */
public class MqttStatefulSubscribe extends MqttStatefulMessage.WithId<MqttSubscribe> {
    public static final int DEFAULT_NO_SUBSCRIPTION_IDENTIFIER = -1;
    private final int subscriptionIdentifier;

    MqttStatefulSubscribe(MqttSubscribe mqttSubscribe, int i, int i2) {
        super(mqttSubscribe, i);
        this.subscriptionIdentifier = i2;
    }

    public int getSubscriptionIdentifier() {
        return this.subscriptionIdentifier;
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttStatefulMessage.WithId, com.hivemq.client.internal.mqtt.message.MqttStatefulMessage
    protected String toAttributeString() {
        return super.toAttributeString() + (this.subscriptionIdentifier == -1 ? "" : "subscriptionIdentifier=" + this.subscriptionIdentifier);
    }

    public String toString() {
        return "MqttStatefulSubscribe{" + toAttributeString() + '}';
    }
}
