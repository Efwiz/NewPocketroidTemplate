package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.rx.RxFutureConverter;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5RetainHandling;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscription;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscriptionBuilderBase;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import io.reactivex.FlowableSubscriber;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class MqttAsyncClient implements Mqtt5AsyncClient {
    private final MqttRxClient delegate;

    private static CompletableFuture<Mqtt5SubAck> handleSubAck(CompletableFuture<Mqtt5SubAck> completableFuture, MqttSubscribe mqttSubscribe) {
        if (mqttSubscribe.getSubscriptions().size() == 1) {
            return completableFuture;
        }
        final CompletableFuture<Mqtt5SubAck> completableFuture2 = new CompletableFuture<>();
        completableFuture.whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.MqttAsyncClient$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MqttAsyncClient.lambda$handleSubAck$0(completableFuture2, (Mqtt5SubAck) obj, (Throwable) obj2);
            }
        });
        return completableFuture2;
    }

    static /* synthetic */ void lambda$handleSubAck$0(CompletableFuture completableFuture, Mqtt5SubAck mqtt5SubAck, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(th);
            return;
        }
        try {
            completableFuture.complete(MqttBlockingClient.handleSubAck(mqtt5SubAck));
        } catch (Throwable th2) {
            completableFuture.completeExceptionally(th2);
        }
    }

    private static CompletableFuture<Mqtt5UnsubAck> handleUnsubAck(CompletableFuture<Mqtt5UnsubAck> completableFuture, MqttUnsubscribe mqttUnsubscribe) {
        if (mqttUnsubscribe.getTopicFilters().size() == 1) {
            return completableFuture;
        }
        final CompletableFuture<Mqtt5UnsubAck> completableFuture2 = new CompletableFuture<>();
        completableFuture.whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.MqttAsyncClient$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MqttAsyncClient.lambda$handleUnsubAck$1(completableFuture2, (Mqtt5UnsubAck) obj, (Throwable) obj2);
            }
        });
        return completableFuture2;
    }

    static /* synthetic */ void lambda$handleUnsubAck$1(CompletableFuture completableFuture, Mqtt5UnsubAck mqtt5UnsubAck, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(th);
            return;
        }
        try {
            completableFuture.complete(MqttBlockingClient.handleUnsubAck(mqtt5UnsubAck));
        } catch (Throwable th2) {
            completableFuture.completeExceptionally(th2);
        }
    }

    MqttAsyncClient(MqttRxClient mqttRxClient) {
        this.delegate = mqttRxClient;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5ConnAck> connect() {
        return connect(MqttConnect.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5ConnAck> connect(Mqtt5Connect mqtt5Connect) {
        return RxFutureConverter.toFuture(this.delegate.connect(MqttChecks.connect(mqtt5Connect)));
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public MqttConnectBuilder.Send<CompletableFuture<Mqtt5ConnAck>> connectWith() {
        return new MqttConnectBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttAsyncClient$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((MqttConnect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe) {
        MqttSubscribe mqttSubscribeSubscribe = MqttChecks.subscribe(mqtt5Subscribe);
        return handleSubAck(RxFutureConverter.toFuture(this.delegate.subscribe(mqttSubscribeSubscribe)), mqttSubscribeSubscribe);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer) {
        return subscribe(mqtt5Subscribe, consumer, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer, Executor executor) {
        return subscribe(mqtt5Subscribe, consumer, executor, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer, boolean z) {
        MqttSubscribe mqttSubscribeSubscribe = MqttChecks.subscribe(mqtt5Subscribe);
        Checks.notNull(consumer, "Callback");
        return handleSubAck(this.delegate.subscribePublishes(mqttSubscribeSubscribe, z).subscribeSingleFuture(new CallbackSubscriber(consumer)), mqttSubscribeSubscribe);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe, Consumer<Mqtt5Publish> consumer, Executor executor, boolean z) {
        MqttSubscribe mqttSubscribeSubscribe = MqttChecks.subscribe(mqtt5Subscribe);
        Checks.notNull(consumer, "Callback");
        Checks.notNull(executor, "Executor");
        return handleSubAck(this.delegate.subscribePublishesUnsafe(mqttSubscribeSubscribe, z).observeOnBoth(Schedulers.from(executor), true).subscribeSingleFuture(new CallbackSubscriber(consumer)), mqttSubscribeSubscribe);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public MqttSubscribeAndCallbackBuilder subscribeWith() {
        return new MqttSubscribeAndCallbackBuilder();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer) {
        publishes(mqttGlobalPublishFilter, consumer, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer, Executor executor) {
        publishes(mqttGlobalPublishFilter, consumer, executor, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        Checks.notNull(consumer, "Callback");
        this.delegate.publishes(mqttGlobalPublishFilter, z).subscribe((FlowableSubscriber<? super Mqtt5Publish>) new CallbackSubscriber(consumer));
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt5Publish> consumer, Executor executor, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        Checks.notNull(consumer, "Callback");
        Checks.notNull(executor, "Executor");
        this.delegate.publishesUnsafe(mqttGlobalPublishFilter, z).observeOn(Schedulers.from(executor), true).subscribe((FlowableSubscriber<? super Mqtt5Publish>) new CallbackSubscriber(consumer));
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5UnsubAck> unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe) {
        MqttUnsubscribe mqttUnsubscribeUnsubscribe = MqttChecks.unsubscribe(mqtt5Unsubscribe);
        return handleUnsubAck(RxFutureConverter.toFuture(this.delegate.unsubscribe(mqttUnsubscribeUnsubscribe)), mqttUnsubscribeUnsubscribe);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public MqttUnsubscribeBuilder.Send<CompletableFuture<Mqtt5UnsubAck>> unsubscribeWith() {
        return new MqttUnsubscribeBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttAsyncClient$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.unsubscribe((MqttUnsubscribe) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Mqtt5PublishResult> publish(Mqtt5Publish mqtt5Publish) {
        return RxFutureConverter.toFuture(this.delegate.publish(MqttChecks.publish(mqtt5Publish)));
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public MqttPublishBuilder.Send<CompletableFuture<Mqtt5PublishResult>> publishWith() {
        return new MqttPublishBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttAsyncClient$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.publish((MqttPublish) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Void> reauth() {
        return RxFutureConverter.toFuture(this.delegate.reauth());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Void> disconnect() {
        return disconnect(MqttDisconnect.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public CompletableFuture<Void> disconnect(Mqtt5Disconnect mqtt5Disconnect) {
        return RxFutureConverter.toFuture(this.delegate.disconnect(MqttChecks.disconnect(mqtt5Disconnect)));
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient
    public MqttDisconnectBuilder.Send<CompletableFuture<Void>> disconnectWith() {
        return new MqttDisconnectBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttAsyncClient$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.disconnect((MqttDisconnect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client, com.hivemq.client.mqtt.MqttClient
    public MqttClientConfig getConfig() {
        return this.delegate.getConfig();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    public MqttRxClient toRx() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    public MqttBlockingClient toBlocking() {
        return this.delegate.toBlocking();
    }

    private static class CallbackSubscriber implements FlowableSubscriber<Mqtt5Publish> {
        private final Consumer<Mqtt5Publish> callback;

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
        }

        private CallbackSubscriber(Consumer<Mqtt5Publish> consumer) {
            this.callback = consumer;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            subscription.request(Long.MAX_VALUE);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(Mqtt5Publish mqtt5Publish) {
            this.callback.accept(mqtt5Publish);
        }
    }

    private class MqttSubscribeAndCallbackBuilder extends MqttSubscribeBuilder<MqttSubscribeAndCallbackBuilder> implements Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Start.Complete, Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call.Ex {
        private Consumer<Mqtt5Publish> callback;
        private Executor executor;
        private boolean manualAcknowledgement;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder
        public MqttSubscribeAndCallbackBuilder self() {
            return this;
        }

        private MqttSubscribeAndCallbackBuilder() {
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

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call
        public /* bridge */ /* synthetic */ Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call.Ex callback(Consumer consumer) {
            return callback((Consumer<Mqtt5Publish>) consumer);
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

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call
        public MqttSubscribeAndCallbackBuilder callback(Consumer<Mqtt5Publish> consumer) {
            this.callback = (Consumer) Checks.notNull(consumer, "Callback");
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call.Ex
        public MqttSubscribeAndCallbackBuilder executor(Executor executor) {
            this.executor = (Executor) Checks.notNull(executor, "Executor");
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call.Ex
        public MqttSubscribeAndCallbackBuilder manualAcknowledgement(boolean z) {
            this.manualAcknowledgement = z;
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient.Mqtt5SubscribeAndCallbackBuilder.Call
        public CompletableFuture<Mqtt5SubAck> send() {
            MqttSubscribe mqttSubscribeBuild = build();
            Consumer<Mqtt5Publish> consumer = this.callback;
            if (consumer == null) {
                Checks.state(this.executor == null, "Executor must not be given if callback is null.");
                Checks.state(!this.manualAcknowledgement, "Manual acknowledgement must not be true if callback is null.");
                return MqttAsyncClient.this.subscribe(mqttSubscribeBuild);
            }
            Executor executor = this.executor;
            if (executor == null) {
                return MqttAsyncClient.this.subscribe(mqttSubscribeBuild, consumer, this.manualAcknowledgement);
            }
            return MqttAsyncClient.this.subscribe(mqttSubscribeBuild, consumer, executor, this.manualAcknowledgement);
        }
    }
}
