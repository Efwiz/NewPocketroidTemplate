package com.hivemq.client.mqtt.mqtt3.message.subscribe;

import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase;
import java.util.Collection;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SubscribeBuilderBase<C extends Mqtt3SubscribeBuilderBase<C>> {

    public interface Start<C extends Mqtt3SubscribeBuilderBase<C>, SC extends Complete<C, SC>> extends Mqtt3SubscribeBuilderBase<C>, Mqtt3SubscriptionBuilderBase<SC> {

        public interface Complete<C extends Mqtt3SubscribeBuilderBase<C>, SC extends Complete<C, SC>> extends Start<C, SC>, Mqtt3SubscriptionBuilderBase.Complete<SC> {
        }
    }

    C addSubscription(Mqtt3Subscription mqtt3Subscription);

    Mqtt3SubscriptionBuilder.Nested<? extends C> addSubscription();

    C addSubscriptions(Collection<? extends Mqtt3Subscription> collection);

    C addSubscriptions(Stream<? extends Mqtt3Subscription> stream);

    C addSubscriptions(Mqtt3Subscription... mqtt3SubscriptionArr);
}
