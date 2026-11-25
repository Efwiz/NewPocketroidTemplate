package com.hivemq.client.internal.mqtt.message.subscribe;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5RetainHandling;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubscription implements Mqtt5Subscription {
    private final boolean noLocal;
    private final MqttQos qos;
    private final boolean retainAsPublished;
    private final Mqtt5RetainHandling retainHandling;
    private final MqttTopicFilterImpl topicFilter;

    public static boolean decodeNoLocal(byte b) {
        return (b & 4) != 0;
    }

    public static boolean decodeRetainAsPublished(byte b) {
        return (b & 8) != 0;
    }

    public MqttSubscription(MqttTopicFilterImpl mqttTopicFilterImpl, MqttQos mqttQos, boolean z, Mqtt5RetainHandling mqtt5RetainHandling, boolean z2) {
        this.topicFilter = mqttTopicFilterImpl;
        this.qos = mqttQos;
        this.noLocal = z;
        this.retainHandling = mqtt5RetainHandling;
        this.retainAsPublished = z2;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription
    public MqttTopicFilterImpl getTopicFilter() {
        return this.topicFilter;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription
    public MqttQos getQos() {
        return this.qos;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription
    public boolean isNoLocal() {
        return this.noLocal;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription
    public Mqtt5RetainHandling getRetainHandling() {
        return this.retainHandling;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription
    public boolean isRetainAsPublished() {
        return this.retainAsPublished;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription
    public MqttSubscriptionBuilder.Default extend() {
        return new MqttSubscriptionBuilder.Default(this);
    }

    private String toAttributeString() {
        return "topicFilter=" + this.topicFilter + ", qos=" + this.qos + ", noLocal=" + this.noLocal + ", retainHandling=" + this.retainHandling + ", retainAsPublished=" + this.retainAsPublished;
    }

    public String toString() {
        return "MqttSubscription{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttSubscription)) {
            return false;
        }
        MqttSubscription mqttSubscription = (MqttSubscription) obj;
        return this.topicFilter.equals(mqttSubscription.topicFilter) && this.qos == mqttSubscription.qos && this.noLocal == mqttSubscription.noLocal && this.retainHandling == mqttSubscription.retainHandling && this.retainAsPublished == mqttSubscription.retainAsPublished;
    }

    public int hashCode() {
        return (((((((this.topicFilter.hashCode() * 31) + this.qos.hashCode()) * 31) + Boolean.hashCode(this.noLocal)) * 31) + this.retainHandling.hashCode()) * 31) + Boolean.hashCode(this.retainAsPublished);
    }

    public byte encodeSubscriptionOptions() {
        byte code = (byte) (this.retainHandling.getCode() << 4);
        if (this.retainAsPublished) {
            code = (byte) (code | 8);
        }
        if (this.noLocal) {
            code = (byte) (code | 4);
        }
        return (byte) (code | this.qos.getCode());
    }

    public static MqttQos decodeQos(byte b) {
        return MqttQos.fromCode(b & 3);
    }

    public static Mqtt5RetainHandling decodeRetainHandling(byte b) {
        return Mqtt5RetainHandling.fromCode((b & 48) >> 4);
    }
}
