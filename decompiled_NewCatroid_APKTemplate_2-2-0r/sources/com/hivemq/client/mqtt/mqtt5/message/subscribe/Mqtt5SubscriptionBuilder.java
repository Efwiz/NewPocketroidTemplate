package com.hivemq.client.mqtt.mqtt5.message.subscribe;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SubscriptionBuilder extends Mqtt5SubscriptionBuilderBase<Complete> {

    public interface Complete extends Mqtt5SubscriptionBuilder, Mqtt5SubscriptionBuilderBase.Complete<Complete> {
        Mqtt5Subscription build();
    }

    public interface Nested<P> extends Mqtt5SubscriptionBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt5SubscriptionBuilderBase.Complete<Complete<P>> {
            P applySubscription();
        }
    }
}
