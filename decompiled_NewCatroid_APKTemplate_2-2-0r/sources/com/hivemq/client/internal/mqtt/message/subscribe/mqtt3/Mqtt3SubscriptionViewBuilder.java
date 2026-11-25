package com.hivemq.client.internal.mqtt.message.subscribe.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscription;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscriptionViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class Mqtt3SubscriptionViewBuilder<B extends Mqtt3SubscriptionViewBuilder<B>> {
    private MqttQos qos;
    private MqttTopicFilterImpl topicFilter;

    abstract B self();

    Mqtt3SubscriptionViewBuilder() {
        this.qos = Mqtt3SubscriptionView.DEFAULT_QOS;
    }

    Mqtt3SubscriptionViewBuilder(Mqtt3SubscriptionView mqtt3SubscriptionView) {
        this.qos = Mqtt3SubscriptionView.DEFAULT_QOS;
        MqttSubscription delegate = mqtt3SubscriptionView.getDelegate();
        this.topicFilter = delegate.getTopicFilter();
        this.qos = delegate.getQos();
    }

    public B topicFilter(String str) {
        this.topicFilter = MqttTopicFilterImpl.of(str);
        return (B) self();
    }

    public B topicFilter(MqttTopicFilter mqttTopicFilter) {
        this.topicFilter = MqttChecks.topicFilter(mqttTopicFilter);
        return (B) self();
    }

    public MqttTopicFilterImplBuilder.Nested<B> topicFilter() {
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscriptionViewBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.topicFilter((MqttTopicFilterImpl) obj);
            }
        });
    }

    public B qos(MqttQos mqttQos) {
        this.qos = (MqttQos) Checks.notNull(mqttQos, "QoS");
        return (B) self();
    }

    public Mqtt3SubscriptionView build() {
        Checks.notNull(this.topicFilter, "Topic filter");
        return Mqtt3SubscriptionView.of(this.topicFilter, this.qos);
    }

    public static class Default extends Mqtt3SubscriptionViewBuilder<Default> implements Mqtt3SubscriptionBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscriptionViewBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt3Subscription build() {
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

        Default(Mqtt3SubscriptionView mqtt3SubscriptionView) {
            super(mqtt3SubscriptionView);
        }
    }

    public static class Nested<P> extends Mqtt3SubscriptionViewBuilder<Nested<P>> implements Mqtt3SubscriptionBuilder.Nested.Complete<P> {
        private final Function<? super Mqtt3Subscription, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscriptionViewBuilder
        public Nested<P> self() {
            return this;
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

        public Nested(Function<? super Mqtt3Subscription, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilder.Nested.Complete
        public P applySubscription() {
            return this.parentConsumer.apply(build());
        }
    }
}
