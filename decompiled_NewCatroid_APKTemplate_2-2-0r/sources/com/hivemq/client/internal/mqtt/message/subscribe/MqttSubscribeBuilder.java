package com.hivemq.client.internal.mqtt.message.subscribe;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5RetainHandling;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttSubscribeBuilder<B extends MqttSubscribeBuilder<B>> {
    private MqttSubscriptionBuilder.Default firstSubscriptionBuilder;
    private final ImmutableList.Builder<MqttSubscription> subscriptionsBuilder;
    private MqttUserPropertiesImpl userProperties;

    protected abstract B self();

    protected MqttSubscribeBuilder() {
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        this.subscriptionsBuilder = ImmutableList.builder();
    }

    MqttSubscribeBuilder(MqttSubscribe mqttSubscribe) {
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        ImmutableList<MqttSubscription> subscriptions = mqttSubscribe.getSubscriptions();
        ImmutableList.Builder<MqttSubscription> builder = ImmutableList.builder(subscriptions.size() + 1);
        this.subscriptionsBuilder = builder;
        builder.addAll(subscriptions);
    }

    public B addSubscription(Mqtt5Subscription mqtt5Subscription) {
        buildFirstSubscription();
        this.subscriptionsBuilder.add((MqttSubscription) Checks.notImplemented(mqtt5Subscription, MqttSubscription.class, "Subscription"));
        return (B) self();
    }

    public MqttSubscriptionBuilder.Nested<B> addSubscription() {
        return new MqttSubscriptionBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.addSubscription((Mqtt5Subscription) obj);
            }
        });
    }

    public B addSubscriptions(Mqtt5Subscription... mqtt5SubscriptionArr) {
        Checks.notNull(mqtt5SubscriptionArr, "Subscriptions");
        buildFirstSubscription();
        this.subscriptionsBuilder.ensureFree(mqtt5SubscriptionArr.length);
        for (Mqtt5Subscription mqtt5Subscription : mqtt5SubscriptionArr) {
            addSubscription(mqtt5Subscription);
        }
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addSubscriptions(Collection<? extends Mqtt5Subscription> collection) {
        Checks.notNull(collection, "Subscriptions");
        buildFirstSubscription();
        this.subscriptionsBuilder.ensureFree(collection.size());
        collection.forEach(new MqttSubscribeBuilder$$ExternalSyntheticLambda1(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addSubscriptions(Stream<? extends Mqtt5Subscription> stream) {
        Checks.notNull(stream, "Subscriptions");
        buildFirstSubscription();
        stream.forEach(new MqttSubscribeBuilder$$ExternalSyntheticLambda1(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return (B) self();
    }

    public MqttUserPropertiesImplBuilder.Nested<B> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((MqttUserPropertiesImpl) obj);
            }
        });
    }

    private MqttSubscriptionBuilder.Default getFirstSubscriptionBuilder() {
        if (this.firstSubscriptionBuilder == null) {
            this.firstSubscriptionBuilder = new MqttSubscriptionBuilder.Default();
        }
        return this.firstSubscriptionBuilder;
    }

    private void buildFirstSubscription() {
        MqttSubscriptionBuilder.Default r0 = this.firstSubscriptionBuilder;
        if (r0 != null) {
            this.subscriptionsBuilder.add(r0.build());
            this.firstSubscriptionBuilder = null;
        }
    }

    public B topicFilter(String str) {
        getFirstSubscriptionBuilder().topicFilter(str);
        return (B) self();
    }

    public B topicFilter(MqttTopicFilter mqttTopicFilter) {
        getFirstSubscriptionBuilder().topicFilter(mqttTopicFilter);
        return (B) self();
    }

    public MqttTopicFilterImplBuilder.Nested<B> topicFilter() {
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.topicFilter((MqttTopicFilterImpl) obj);
            }
        });
    }

    public B qos(MqttQos mqttQos) {
        getFirstSubscriptionBuilder().qos(mqttQos);
        return (B) self();
    }

    public B noLocal(boolean z) {
        getFirstSubscriptionBuilder().noLocal(z);
        return (B) self();
    }

    public B retainHandling(Mqtt5RetainHandling mqtt5RetainHandling) {
        getFirstSubscriptionBuilder().retainHandling(mqtt5RetainHandling);
        return (B) self();
    }

    public B retainAsPublished(boolean z) {
        getFirstSubscriptionBuilder().retainAsPublished(z);
        return (B) self();
    }

    private void ensureAtLeastOneSubscription() {
        Checks.state(this.subscriptionsBuilder.getSize() > 0, "At least one subscription must be added.");
    }

    public MqttSubscribe build() {
        buildFirstSubscription();
        ensureAtLeastOneSubscription();
        return new MqttSubscribe(this.subscriptionsBuilder.build(), this.userProperties);
    }

    public static class Default extends MqttSubscribeBuilder<Default> implements Mqtt5SubscribeBuilder.Start.Complete {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscription(Mqtt5Subscription mqtt5Subscription) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscription(mqtt5Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Collection collection) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Collection<? extends Mqtt5Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Stream stream) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Stream<? extends Mqtt5Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Mqtt5Subscription[] mqtt5SubscriptionArr) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions(mqtt5SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt5Subscribe build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete noLocal(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.noLocal(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainAsPublished(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainAsPublished(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainHandling(Mqtt5RetainHandling mqtt5RetainHandling) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainHandling(mqtt5RetainHandling);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Default() {
        }

        Default(MqttSubscribe mqttSubscribe) {
            super(mqttSubscribe);
        }
    }

    public static class Nested<P> extends MqttSubscribeBuilder<Nested<P>> implements Mqtt5SubscribeBuilder.Nested.Start.Complete<P> {
        private final Function<? super MqttSubscribe, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscription(Mqtt5Subscription mqtt5Subscription) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscription(mqtt5Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Collection collection) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Collection<? extends Mqtt5Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Stream stream) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Stream<? extends Mqtt5Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Mqtt5Subscription[] mqtt5SubscriptionArr) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions(mqtt5SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete noLocal(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.noLocal(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainAsPublished(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainAsPublished(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainHandling(Mqtt5RetainHandling mqtt5RetainHandling) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainHandling(mqtt5RetainHandling);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Nested(Function<? super MqttSubscribe, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder.Nested.Complete
        public P applySubscribe() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends MqttSubscribeBuilder<Send<P>> implements Mqtt5SubscribeBuilder.Send.Start.Complete<P> {
        private final Function<? super MqttSubscribe, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscription(Mqtt5Subscription mqtt5Subscription) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscription(mqtt5Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Collection collection) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Collection<? extends Mqtt5Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Stream stream) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Stream<? extends Mqtt5Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Mqtt5Subscription[] mqtt5SubscriptionArr) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions(mqtt5SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete noLocal(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.noLocal(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainAsPublished(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainAsPublished(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainHandling(Mqtt5RetainHandling mqtt5RetainHandling) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainHandling(mqtt5RetainHandling);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Send(Function<? super MqttSubscribe, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder.Send.Complete
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }

    public static abstract class Publishes<P> extends MqttSubscribeBuilder<Publishes<P>> implements Mqtt5SubscribeBuilder.Publishes.Start.Complete<P>, Mqtt5SubscribeBuilder.Publishes.Args<P> {
        protected boolean manualAcknowledgement;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder
        public Publishes<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscription(Mqtt5Subscription mqtt5Subscription) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscription(mqtt5Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Collection collection) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Collection<? extends Mqtt5Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Stream stream) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions((Stream<? extends Mqtt5Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete addSubscriptions(Mqtt5Subscription[] mqtt5SubscriptionArr) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.addSubscriptions(mqtt5SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete noLocal(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.noLocal(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainAsPublished(boolean z) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainAsPublished(z);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete retainHandling(Mqtt5RetainHandling mqtt5RetainHandling) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.retainHandling(mqtt5RetainHandling);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5SubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5SubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder.Publishes.Args
        public Publishes<P> manualAcknowledgement(boolean z) {
            this.manualAcknowledgement = z;
            return this;
        }
    }
}
