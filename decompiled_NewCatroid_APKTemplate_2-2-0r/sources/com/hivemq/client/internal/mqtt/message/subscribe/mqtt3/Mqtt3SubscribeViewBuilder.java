package com.hivemq.client.internal.mqtt.message.subscribe.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3SubscribeViewBuilder<B extends Mqtt3SubscribeViewBuilder<B>> {
    private Mqtt3SubscriptionViewBuilder.Default firstSubscriptionBuilder;
    private final ImmutableList.Builder<MqttSubscription> subscriptionsBuilder;

    protected abstract B self();

    protected Mqtt3SubscribeViewBuilder() {
        this.subscriptionsBuilder = ImmutableList.builder();
    }

    Mqtt3SubscribeViewBuilder(Mqtt3SubscribeView mqtt3SubscribeView) {
        ImmutableList<MqttSubscription> subscriptions = mqtt3SubscribeView.getDelegate().getSubscriptions();
        ImmutableList.Builder<MqttSubscription> builder = ImmutableList.builder(subscriptions.size() + 1);
        this.subscriptionsBuilder = builder;
        builder.addAll(subscriptions);
    }

    public B addSubscription(Mqtt3Subscription mqtt3Subscription) {
        buildFirstSubscription();
        this.subscriptionsBuilder.add(((Mqtt3SubscriptionView) Checks.notImplemented(mqtt3Subscription, Mqtt3SubscriptionView.class, "Subscription")).getDelegate());
        return (B) self();
    }

    public Mqtt3SubscriptionViewBuilder.Nested<B> addSubscription() {
        return new Mqtt3SubscriptionViewBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.addSubscription((Mqtt3Subscription) obj);
            }
        });
    }

    public B addSubscriptions(Mqtt3Subscription... mqtt3SubscriptionArr) {
        Checks.notNull(mqtt3SubscriptionArr, "Subscriptions");
        buildFirstSubscription();
        this.subscriptionsBuilder.ensureFree(mqtt3SubscriptionArr.length);
        for (Mqtt3Subscription mqtt3Subscription : mqtt3SubscriptionArr) {
            addSubscription(mqtt3Subscription);
        }
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addSubscriptions(Collection<? extends Mqtt3Subscription> collection) {
        Checks.notNull(collection, "Subscriptions");
        buildFirstSubscription();
        this.subscriptionsBuilder.ensureFree(collection.size());
        collection.forEach(new Mqtt3SubscribeViewBuilder$$ExternalSyntheticLambda1(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addSubscriptions(Stream<? extends Mqtt3Subscription> stream) {
        Checks.notNull(stream, "Subscriptions");
        buildFirstSubscription();
        stream.forEach(new Mqtt3SubscribeViewBuilder$$ExternalSyntheticLambda1(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    private Mqtt3SubscriptionViewBuilder.Default getFirstSubscriptionBuilder() {
        if (this.firstSubscriptionBuilder == null) {
            this.firstSubscriptionBuilder = new Mqtt3SubscriptionViewBuilder.Default();
        }
        return this.firstSubscriptionBuilder;
    }

    private void buildFirstSubscription() {
        Mqtt3SubscriptionViewBuilder.Default r0 = this.firstSubscriptionBuilder;
        if (r0 != null) {
            this.subscriptionsBuilder.add(r0.build().getDelegate());
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
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder$$ExternalSyntheticLambda2
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

    private void ensureAtLeastOneSubscription() {
        Checks.state(this.subscriptionsBuilder.getSize() > 0, "At least one subscription must be added.");
    }

    public Mqtt3SubscribeView build() {
        buildFirstSubscription();
        ensureAtLeastOneSubscription();
        return Mqtt3SubscribeView.of(this.subscriptionsBuilder.build());
    }

    public static class Default extends Mqtt3SubscribeViewBuilder<Default> implements Mqtt3SubscribeBuilder.Start.Complete {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscription(Mqtt3Subscription mqtt3Subscription) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscription(mqtt3Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Collection collection) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Collection<? extends Mqtt3Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Stream stream) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Stream<? extends Mqtt3Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Mqtt3Subscription[] mqtt3SubscriptionArr) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions(mqtt3SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3Subscribe build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        public Default() {
        }

        Default(Mqtt3SubscribeView mqtt3SubscribeView) {
            super(mqtt3SubscribeView);
        }
    }

    public static class Nested<P> extends Mqtt3SubscribeViewBuilder<Nested<P>> implements Mqtt3SubscribeBuilder.Nested.Start.Complete<P> {
        private final Function<? super Mqtt3SubscribeView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscription(Mqtt3Subscription mqtt3Subscription) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscription(mqtt3Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Collection collection) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Collection<? extends Mqtt3Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Stream stream) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Stream<? extends Mqtt3Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Mqtt3Subscription[] mqtt3SubscriptionArr) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions(mqtt3SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        public Nested(Function<? super Mqtt3SubscribeView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder.Nested.Complete
        public P applySubscribe() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends Mqtt3SubscribeViewBuilder<Send<P>> implements Mqtt3SubscribeBuilder.Send.Start.Complete<P> {
        private final Function<? super Mqtt3SubscribeView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscription(Mqtt3Subscription mqtt3Subscription) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscription(mqtt3Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Collection collection) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Collection<? extends Mqtt3Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Stream stream) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Stream<? extends Mqtt3Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Mqtt3Subscription[] mqtt3SubscriptionArr) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions(mqtt3SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        public Send(Function<? super Mqtt3SubscribeView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder.Send.Complete
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }

    public static abstract class Publishes<P> extends Mqtt3SubscribeViewBuilder<Publishes<P>> implements Mqtt3SubscribeBuilder.Publishes.Start.Complete<P>, Mqtt3SubscribeBuilder.Publishes.Args<P> {
        protected boolean manualAcknowledgement;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder
        public Publishes<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscription(Mqtt3Subscription mqtt3Subscription) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscription(mqtt3Subscription);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilder.Nested addSubscription() {
            return super.addSubscription();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Collection collection) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Collection<? extends Mqtt3Subscription>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Stream stream) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions((Stream<? extends Mqtt3Subscription>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscribeBuilderBase addSubscriptions(Mqtt3Subscription[] mqtt3SubscriptionArr) {
            return (Mqtt3SubscribeBuilderBase) super.addSubscriptions(mqtt3SubscriptionArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete qos(MqttQos mqttQos) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.qos(mqttQos);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3SubscriptionBuilderBase.Complete topicFilter(String str) {
            return (Mqtt3SubscriptionBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder.Publishes.Args
        public Publishes<P> manualAcknowledgement(boolean z) {
            this.manualAcknowledgement = z;
            return this;
        }
    }
}
