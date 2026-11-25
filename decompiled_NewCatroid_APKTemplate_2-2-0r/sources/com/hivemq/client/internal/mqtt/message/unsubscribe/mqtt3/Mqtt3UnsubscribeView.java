package com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3UnsubscribeView implements Mqtt3Unsubscribe {
    private final MqttUnsubscribe delegate;

    private static MqttUnsubscribe delegate(ImmutableList<MqttTopicFilterImpl> immutableList) {
        return new MqttUnsubscribe(immutableList, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    static Mqtt3UnsubscribeView of(ImmutableList<MqttTopicFilterImpl> immutableList) {
        return new Mqtt3UnsubscribeView(delegate(immutableList));
    }

    public static Mqtt3UnsubscribeView of(MqttUnsubscribe mqttUnsubscribe) {
        return new Mqtt3UnsubscribeView(mqttUnsubscribe);
    }

    private Mqtt3UnsubscribeView(MqttUnsubscribe mqttUnsubscribe) {
        this.delegate = mqttUnsubscribe;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe
    public ImmutableList<MqttTopicFilterImpl> getTopicFilters() {
        return this.delegate.getTopicFilters();
    }

    public MqttUnsubscribe getDelegate() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe
    public Mqtt3UnsubscribeViewBuilder.Default extend() {
        return new Mqtt3UnsubscribeViewBuilder.Default(this);
    }

    private String toAttributeString() {
        return "topicFilters=" + getTopicFilters();
    }

    public String toString() {
        return "MqttUnsubscribe{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3UnsubscribeView) {
            return this.delegate.equals(((Mqtt3UnsubscribeView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
