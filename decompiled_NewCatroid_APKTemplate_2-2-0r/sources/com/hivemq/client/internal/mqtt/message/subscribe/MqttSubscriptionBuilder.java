package com.hivemq.client.internal.mqtt.message.subscribe;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscriptionBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5RetainHandling;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttSubscriptionBuilder<B extends MqttSubscriptionBuilder<B>> {
    private boolean noLocal;
    private MqttQos qos;
    private boolean retainAsPublished;
    private Mqtt5RetainHandling retainHandling;
    private MqttTopicFilterImpl topicFilter;

    abstract B self();

    MqttSubscriptionBuilder() {
        this.qos = MqttSubscription.DEFAULT_QOS;
        this.noLocal = false;
        this.retainHandling = MqttSubscription.DEFAULT_RETAIN_HANDLING;
        this.retainAsPublished = false;
    }

    MqttSubscriptionBuilder(MqttSubscription mqttSubscription) {
        this.qos = MqttSubscription.DEFAULT_QOS;
        this.noLocal = false;
        this.retainHandling = MqttSubscription.DEFAULT_RETAIN_HANDLING;
        this.retainAsPublished = false;
        this.topicFilter = mqttSubscription.getTopicFilter();
        this.qos = mqttSubscription.getQos();
        this.noLocal = mqttSubscription.isNoLocal();
        this.retainHandling = mqttSubscription.getRetainHandling();
        this.retainAsPublished = mqttSubscription.isRetainAsPublished();
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
        return new MqttTopicFilterImplBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscriptionBuilder$$ExternalSyntheticLambda0
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

    public B noLocal(boolean z) {
        this.noLocal = z;
        return (B) self();
    }

    public B retainHandling(Mqtt5RetainHandling mqtt5RetainHandling) {
        this.retainHandling = (Mqtt5RetainHandling) Checks.notNull(mqtt5RetainHandling, "Retain handling");
        return (B) self();
    }

    public B retainAsPublished(boolean z) {
        this.retainAsPublished = z;
        return (B) self();
    }

    public MqttSubscription build() {
        Checks.notNull(this.topicFilter, "Topic filter");
        Checks.state((this.topicFilter.isShared() && this.noLocal) ? false : true, "It is a Protocol Error to set no local to true on a Shared Subscription.");
        return new MqttSubscription(this.topicFilter, this.qos, this.noLocal, this.retainHandling, this.retainAsPublished);
    }

    public static class Default extends MqttSubscriptionBuilder<Default> implements Mqtt5SubscriptionBuilder.Complete {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscriptionBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilder.Complete
        public /* bridge */ /* synthetic */ Mqtt5Subscription build() {
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

        public Default() {
        }

        Default(MqttSubscription mqttSubscription) {
            super(mqttSubscription);
        }
    }

    public static class Nested<P> extends MqttSubscriptionBuilder<Nested<P>> implements Mqtt5SubscriptionBuilder.Nested.Complete<P> {
        private final Function<? super Mqtt5Subscription, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscriptionBuilder
        public Nested<P> self() {
            return this;
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

        public Nested(Function<? super Mqtt5Subscription, P> function) {
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilder.Nested.Complete
        public P applySubscription() {
            return this.parentConsumer.apply(build());
        }
    }
}
