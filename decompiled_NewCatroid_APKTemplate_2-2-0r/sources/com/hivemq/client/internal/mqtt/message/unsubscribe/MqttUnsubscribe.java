package com.hivemq.client.internal.mqtt.message.unsubscribe;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;

/* JADX INFO: loaded from: classes10.dex */
public class MqttUnsubscribe extends MqttMessageWithUserProperties implements Mqtt5Unsubscribe {
    private final ImmutableList<MqttTopicFilterImpl> topicFilters;

    @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttUnsubscribe(ImmutableList<MqttTopicFilterImpl> immutableList, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(mqttUserPropertiesImpl);
        this.topicFilters = immutableList;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe
    public ImmutableList<MqttTopicFilterImpl> getTopicFilters() {
        return this.topicFilters;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe
    public MqttUnsubscribeBuilder.Default extend() {
        return new MqttUnsubscribeBuilder.Default(this);
    }

    public MqttStatefulUnsubscribe createStateful(int i) {
        return new MqttStatefulUnsubscribe(this, i);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "topicFilters=" + this.topicFilters + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttUnsubscribe{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttUnsubscribe)) {
            return false;
        }
        MqttUnsubscribe mqttUnsubscribe = (MqttUnsubscribe) obj;
        return partialEquals(mqttUnsubscribe) && this.topicFilters.equals(mqttUnsubscribe.topicFilters);
    }

    public int hashCode() {
        return (partialHashCode() * 31) + this.topicFilters.hashCode();
    }
}
