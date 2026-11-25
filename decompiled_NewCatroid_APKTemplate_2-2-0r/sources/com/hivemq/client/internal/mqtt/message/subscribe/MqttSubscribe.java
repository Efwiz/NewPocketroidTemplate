package com.hivemq.client.internal.mqtt.message.subscribe;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubscribe extends MqttMessageWithUserProperties implements Mqtt5Subscribe {
    private final ImmutableList<MqttSubscription> subscriptions;

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttSubscribe(ImmutableList<MqttSubscription> immutableList, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(mqttUserPropertiesImpl);
        this.subscriptions = immutableList;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe
    public ImmutableList<MqttSubscription> getSubscriptions() {
        return this.subscriptions;
    }

    public MqttStatefulSubscribe createStateful(int i, int i2) {
        return new MqttStatefulSubscribe(this, i, i2);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe
    public MqttSubscribeBuilder.Default extend() {
        return new MqttSubscribeBuilder.Default(this);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "subscriptions=" + this.subscriptions + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttSubscribe{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttSubscribe)) {
            return false;
        }
        MqttSubscribe mqttSubscribe = (MqttSubscribe) obj;
        return partialEquals(mqttSubscribe) && this.subscriptions.equals(mqttSubscribe.subscriptions);
    }

    public int hashCode() {
        return (partialHashCode() * 31) + this.subscriptions.hashCode();
    }
}
