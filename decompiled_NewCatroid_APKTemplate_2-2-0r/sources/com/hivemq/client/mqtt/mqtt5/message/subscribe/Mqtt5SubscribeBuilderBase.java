package com.hivemq.client.mqtt.mqtt5.message.subscribe;

import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete;
import java.util.Collection;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SubscribeBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt5SubscribeBuilderBase<C> {
        Mqtt5UserPropertiesBuilder.Nested<? extends C> userProperties();

        C userProperties(Mqtt5UserProperties mqtt5UserProperties);
    }

    public interface Start<C extends Complete<C>, SC extends Complete<C, SC>> extends Mqtt5SubscribeBuilderBase<C>, Mqtt5SubscriptionBuilderBase<SC> {

        public interface Complete<C extends Complete<C>, SC extends Complete<C, SC>> extends Start<C, SC>, Complete<C>, Mqtt5SubscriptionBuilderBase.Complete<SC> {
        }
    }

    C addSubscription(Mqtt5Subscription mqtt5Subscription);

    Mqtt5SubscriptionBuilder.Nested<? extends C> addSubscription();

    C addSubscriptions(Collection<? extends Mqtt5Subscription> collection);

    C addSubscriptions(Stream<? extends Mqtt5Subscription> stream);

    C addSubscriptions(Mqtt5Subscription... mqtt5SubscriptionArr);
}
