package com.hivemq.client.internal.mqtt.message.subscribe.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3SubscribeView implements Mqtt3Subscribe {
    private final MqttSubscribe delegate;

    private static MqttSubscribe delegate(ImmutableList<MqttSubscription> immutableList) {
        return new MqttSubscribe(immutableList, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    }

    static Mqtt3SubscribeView of(ImmutableList<MqttSubscription> immutableList) {
        return new Mqtt3SubscribeView(delegate(immutableList));
    }

    public static Mqtt3SubscribeView of(MqttSubscribe mqttSubscribe) {
        return new Mqtt3SubscribeView(mqttSubscribe);
    }

    private Mqtt3SubscribeView(MqttSubscribe mqttSubscribe) {
        this.delegate = mqttSubscribe;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe
    public ImmutableList<Mqtt3SubscriptionView> getSubscriptions() {
        ImmutableList<MqttSubscription> subscriptions = this.delegate.getSubscriptions();
        ImmutableList.Builder builder = ImmutableList.builder(subscriptions.size());
        for (int i = 0; i < subscriptions.size(); i++) {
            builder.add(Mqtt3SubscriptionView.of(subscriptions.get(i)));
        }
        return builder.build();
    }

    public MqttSubscribe getDelegate() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe
    public Mqtt3SubscribeViewBuilder.Default extend() {
        return new Mqtt3SubscribeViewBuilder.Default(this);
    }

    private String toAttributeString() {
        return "subscriptions=" + getSubscriptions();
    }

    public String toString() {
        return "MqttSubscribe{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3SubscribeView) {
            return this.delegate.equals(((Mqtt3SubscribeView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
