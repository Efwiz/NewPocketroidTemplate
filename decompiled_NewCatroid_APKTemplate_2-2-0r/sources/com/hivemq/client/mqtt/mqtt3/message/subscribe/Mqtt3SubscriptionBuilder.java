package com.hivemq.client.mqtt.mqtt3.message.subscribe;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SubscriptionBuilder extends Mqtt3SubscriptionBuilderBase<Complete> {

    public interface Complete extends Mqtt3SubscriptionBuilder, Mqtt3SubscriptionBuilderBase.Complete<Complete> {
        Mqtt3Subscription build();
    }

    public interface Nested<P> extends Mqtt3SubscriptionBuilderBase<Complete<P>> {

        public interface Complete<P> extends Nested<P>, Mqtt3SubscriptionBuilderBase.Complete<Complete<P>> {
            P applySubscription();
        }
    }
}
