package com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase;
import java.util.Collection;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3UnsubscribeViewBuilder<B extends Mqtt3UnsubscribeViewBuilder<B>> {
    private final ImmutableList.Builder<MqttTopicFilterImpl> topicFiltersBuilder;

    abstract B self();

    Mqtt3UnsubscribeViewBuilder() {
        this.topicFiltersBuilder = ImmutableList.builder();
    }

    Mqtt3UnsubscribeViewBuilder(Mqtt3UnsubscribeView mqtt3UnsubscribeView) {
        ImmutableList<MqttTopicFilterImpl> topicFilters = mqtt3UnsubscribeView.getDelegate().getTopicFilters();
        ImmutableList.Builder<MqttTopicFilterImpl> builder = ImmutableList.builder(topicFilters.size() + 1);
        this.topicFiltersBuilder = builder;
        builder.addAll(topicFilters);
    }

    public B addTopicFilter(String str) {
        this.topicFiltersBuilder.add(MqttTopicFilterImpl.of(str));
        return (B) self();
    }

    public B addTopicFilter(MqttTopicFilter mqttTopicFilter) {
        this.topicFiltersBuilder.add(MqttChecks.topicFilter(mqttTopicFilter));
        return (B) self();
    }

    public MqttTopicFilterImplBuilder.Nested<B> addTopicFilter() {
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.addTopicFilter((MqttTopicFilterImpl) obj);
            }
        });
    }

    public B addTopicFilters(MqttTopicFilter... mqttTopicFilterArr) {
        Checks.notNull(mqttTopicFilterArr, "Topic Filters");
        this.topicFiltersBuilder.ensureFree(mqttTopicFilterArr.length);
        for (MqttTopicFilter mqttTopicFilter : mqttTopicFilterArr) {
            addTopicFilter(mqttTopicFilter);
        }
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addTopicFilters(Collection<? extends MqttTopicFilter> collection) {
        Checks.notNull(collection, "Topic Filters");
        this.topicFiltersBuilder.ensureFree(collection.size());
        collection.forEach(new Mqtt3UnsubscribeViewBuilder$$ExternalSyntheticLambda2(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addTopicFilters(Stream<? extends MqttTopicFilter> stream) {
        Checks.notNull(stream, "Topic Filters");
        stream.forEach(new Mqtt3UnsubscribeViewBuilder$$ExternalSyntheticLambda2(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B reverse(Mqtt3Subscribe mqtt3Subscribe) {
        ImmutableList.ImmutableListIterator<MqttSubscription> it = MqttChecks.subscribe(mqtt3Subscribe).getSubscriptions().iterator();
        while (it.hasNext()) {
            this.topicFiltersBuilder.add(it.next().getTopicFilter());
        }
        return (B) self();
    }

    public B topicFilter(String str) {
        return (B) addTopicFilter(str);
    }

    public B topicFilter(MqttTopicFilter mqttTopicFilter) {
        return (B) addTopicFilter(mqttTopicFilter);
    }

    public MqttTopicFilterImplBuilder.Nested<B> topicFilter() {
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.topicFilter((MqttTopicFilterImpl) obj);
            }
        });
    }

    private void ensureAtLeastOneSubscription() {
        Checks.state(this.topicFiltersBuilder.getSize() > 0, "At least one topic filter must be added.");
    }

    public Mqtt3UnsubscribeView build() {
        ensureAtLeastOneSubscription();
        return Mqtt3UnsubscribeView.of(this.topicFiltersBuilder.build());
    }

    public static class Default extends Mqtt3UnsubscribeViewBuilder<Default> implements Mqtt3UnsubscribeBuilder.Complete, Mqtt3UnsubscribeBuilder.Start {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Collection collection) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Stream stream) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3Unsubscribe build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase reverse(Mqtt3Subscribe mqtt3Subscribe) {
            return (Mqtt3UnsubscribeBuilderBase) super.reverse(mqtt3Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(str);
        }

        public Default() {
        }

        Default(Mqtt3UnsubscribeView mqtt3UnsubscribeView) {
            super(mqtt3UnsubscribeView);
        }
    }

    public static class Nested<P> extends Mqtt3UnsubscribeViewBuilder<Nested<P>> implements Mqtt3UnsubscribeBuilder.Nested.Complete<P>, Mqtt3UnsubscribeBuilder.Nested.Start<P> {
        private final Function<? super Mqtt3UnsubscribeView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Collection collection) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Stream stream) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase reverse(Mqtt3Subscribe mqtt3Subscribe) {
            return (Mqtt3UnsubscribeBuilderBase) super.reverse(mqtt3Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(str);
        }

        public Nested(Function<? super Mqtt3UnsubscribeView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder.Nested.Complete
        public P applyUnsubscribe() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends Mqtt3UnsubscribeViewBuilder<Send<P>> implements Mqtt3UnsubscribeBuilder.Send.Complete<P>, Mqtt3UnsubscribeBuilder.Send.Start<P> {
        private final Function<? super Mqtt3UnsubscribeView, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Collection collection) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Stream stream) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase reverse(Mqtt3Subscribe mqtt3Subscribe) {
            return (Mqtt3UnsubscribeBuilderBase) super.reverse(mqtt3Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(str);
        }

        public Send(Function<? super Mqtt3UnsubscribeView, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder.Send.Complete
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class SendVoid extends Mqtt3UnsubscribeViewBuilder<SendVoid> implements Mqtt3UnsubscribeBuilder.SendVoid.Complete, Mqtt3UnsubscribeBuilder.SendVoid.Start {
        private final Consumer<? super Mqtt3UnsubscribeView> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder
        public SendVoid self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Collection collection) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(Stream stream) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt3UnsubscribeBuilderBase) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase reverse(Mqtt3Subscribe mqtt3Subscribe) {
            return (Mqtt3UnsubscribeBuilderBase) super.reverse(mqtt3Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt3UnsubscribeBuilderBase topicFilter(String str) {
            return (Mqtt3UnsubscribeBuilderBase) super.topicFilter(str);
        }

        public SendVoid(Consumer<? super Mqtt3UnsubscribeView> consumer) {
            this.parentConsumer = consumer;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder.SendVoid.Complete
        public void send() {
            this.parentConsumer.accept(build());
        }
    }
}
