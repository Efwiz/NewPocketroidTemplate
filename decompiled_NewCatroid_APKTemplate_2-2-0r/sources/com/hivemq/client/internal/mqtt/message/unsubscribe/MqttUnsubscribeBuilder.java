package com.hivemq.client.internal.mqtt.message.unsubscribe;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImplBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase;
import java.util.Collection;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttUnsubscribeBuilder<B extends MqttUnsubscribeBuilder<B>> {
    private final ImmutableList.Builder<MqttTopicFilterImpl> topicFiltersBuilder;
    private MqttUserPropertiesImpl userProperties;

    abstract B self();

    MqttUnsubscribeBuilder() {
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        this.topicFiltersBuilder = ImmutableList.builder();
    }

    MqttUnsubscribeBuilder(MqttUnsubscribe mqttUnsubscribe) {
        this.userProperties = MqttUserPropertiesImpl.NO_USER_PROPERTIES;
        ImmutableList<MqttTopicFilterImpl> topicFilters = mqttUnsubscribe.getTopicFilters();
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
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder$$ExternalSyntheticLambda2
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
        collection.forEach(new MqttUnsubscribeBuilder$$ExternalSyntheticLambda1(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B addTopicFilters(Stream<? extends MqttTopicFilter> stream) {
        Checks.notNull(stream, "Topic Filters");
        stream.forEach(new MqttUnsubscribeBuilder$$ExternalSyntheticLambda1(this));
        ensureAtLeastOneSubscription();
        return (B) self();
    }

    public B reverse(Mqtt5Subscribe mqtt5Subscribe) {
        ImmutableList.ImmutableListIterator<MqttSubscription> it = MqttChecks.subscribe(mqtt5Subscribe).getSubscriptions().iterator();
        while (it.hasNext()) {
            this.topicFiltersBuilder.add(it.next().getTopicFilter());
        }
        return (B) self();
    }

    public B userProperties(Mqtt5UserProperties mqtt5UserProperties) {
        this.userProperties = MqttChecks.userProperties(mqtt5UserProperties);
        return (B) self();
    }

    public MqttUserPropertiesImplBuilder.Nested<B> userProperties() {
        return new MqttUserPropertiesImplBuilder.Nested<>(this.userProperties, new Function() { // from class: com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.userProperties((MqttUserPropertiesImpl) obj);
            }
        });
    }

    public B topicFilter(String str) {
        return (B) addTopicFilter(str);
    }

    public B topicFilter(MqttTopicFilter mqttTopicFilter) {
        return (B) addTopicFilter(mqttTopicFilter);
    }

    public MqttTopicFilterImplBuilder.Nested<B> topicFilter() {
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.topicFilter((MqttTopicFilterImpl) obj);
            }
        });
    }

    private void ensureAtLeastOneSubscription() {
        Checks.state(this.topicFiltersBuilder.getSize() > 0, "At least one topic filter must be added.");
    }

    public MqttUnsubscribe build() {
        ensureAtLeastOneSubscription();
        return new MqttUnsubscribe(this.topicFiltersBuilder.build(), this.userProperties);
    }

    public static class Default extends MqttUnsubscribeBuilder<Default> implements Mqtt5UnsubscribeBuilder.Complete, Mqtt5UnsubscribeBuilder.Start {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilter(String str) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(Collection collection) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(Stream stream) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt5Unsubscribe build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete reverse(Mqtt5Subscribe mqtt5Subscribe) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.reverse(mqtt5Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Default() {
        }

        Default(MqttUnsubscribe mqttUnsubscribe) {
            super(mqttUnsubscribe);
        }
    }

    public static class Nested<P> extends MqttUnsubscribeBuilder<Nested<P>> implements Mqtt5UnsubscribeBuilder.Nested.Complete<P>, Mqtt5UnsubscribeBuilder.Nested.Start<P> {
        private final Function<? super MqttUnsubscribe, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilter(String str) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(Collection collection) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(Stream stream) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete reverse(Mqtt5Subscribe mqtt5Subscribe) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.reverse(mqtt5Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Nested(Function<? super MqttUnsubscribe, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder.Nested.Complete
        public P applyUnsubscribe() {
            return this.parentConsumer.apply(build());
        }
    }

    public static class Send<P> extends MqttUnsubscribeBuilder<Send<P>> implements Mqtt5UnsubscribeBuilder.Send.Complete<P>, Mqtt5UnsubscribeBuilder.Send.Start<P> {
        private final Function<? super MqttUnsubscribe, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder
        public Send<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested addTopicFilter() {
            return super.addTopicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilter(String str) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(Collection collection) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters((Collection<? extends MqttTopicFilter>) collection);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(Stream stream) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters((Stream<? extends MqttTopicFilter>) stream);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete addTopicFilters(MqttTopicFilter[] mqttTopicFilterArr) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.addTopicFilters(mqttTopicFilterArr);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete reverse(Mqtt5Subscribe mqtt5Subscribe) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.reverse(mqtt5Subscribe);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ MqttTopicFilterBuilder.Nested topicFilter() {
            return super.topicFilter();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete topicFilter(MqttTopicFilter mqttTopicFilter) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.topicFilter(mqttTopicFilter);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Start
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete topicFilter(String str) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.topicFilter(str);
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UserPropertiesBuilder.Nested userProperties() {
            return super.userProperties();
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilderBase.Complete
        public /* bridge */ /* synthetic */ Mqtt5UnsubscribeBuilderBase.Complete userProperties(Mqtt5UserProperties mqtt5UserProperties) {
            return (Mqtt5UnsubscribeBuilderBase.Complete) super.userProperties(mqtt5UserProperties);
        }

        public Send(Function<? super MqttUnsubscribe, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder.Send.Complete
        public P send() {
            return this.parentConsumer.apply(build());
        }
    }
}
