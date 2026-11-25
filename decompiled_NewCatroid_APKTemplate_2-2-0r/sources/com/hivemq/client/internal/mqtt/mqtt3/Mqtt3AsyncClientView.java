package com.hivemq.client.internal.mqtt.mqtt3;

import com.hivemq.client.internal.mqtt.MqttAsyncClient;
import com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.mqtt3.Mqtt3DisconnectView;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeView;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilterBuilder;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3BlockingClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientConfig;
import com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscription;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscriptionBuilderBase;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3AsyncClientView implements Mqtt3AsyncClient {
    private final Mqtt3ClientConfigView clientConfig;
    private final MqttAsyncClient delegate;

    private static CompletableFuture<Mqtt3SubAck> handleSubAck(CompletableFuture<Mqtt5SubAck> completableFuture) {
        final CompletableFuture<Mqtt3SubAck> completableFuture2 = new CompletableFuture<>();
        completableFuture.whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Mqtt3AsyncClientView.lambda$handleSubAck$0(completableFuture2, (Mqtt5SubAck) obj, (Throwable) obj2);
            }
        });
        return completableFuture2;
    }

    static /* synthetic */ void lambda$handleSubAck$0(CompletableFuture completableFuture, Mqtt5SubAck mqtt5SubAck, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(Mqtt3ExceptionFactory.map(th));
        } else {
            completableFuture.complete(Mqtt3SubAckView.of(mqtt5SubAck));
        }
    }

    private static Consumer<Mqtt5Publish> callbackView(final Consumer<Mqtt3Publish> consumer) {
        return new Consumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                consumer.accept(Mqtt3PublishView.of((Mqtt5Publish) obj));
            }
        };
    }

    Mqtt3AsyncClientView(MqttAsyncClient mqttAsyncClient) {
        this.delegate = mqttAsyncClient;
        this.clientConfig = new Mqtt3ClientConfigView(mqttAsyncClient.getConfig());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3ConnAck> connect() {
        return connect(Mqtt3ConnectView.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3ConnAck> connect(Mqtt3Connect mqtt3Connect) {
        MqttConnect mqttConnectConnect = MqttChecks.connect(mqtt3Connect);
        final CompletableFuture<Mqtt3ConnAck> completableFuture = new CompletableFuture<>();
        this.delegate.connect(mqttConnectConnect).whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda3
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Mqtt3AsyncClientView.lambda$connect$2(completableFuture, (Mqtt5ConnAck) obj, (Throwable) obj2);
            }
        });
        return completableFuture;
    }

    static /* synthetic */ void lambda$connect$2(CompletableFuture completableFuture, Mqtt5ConnAck mqtt5ConnAck, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(Mqtt3ExceptionFactory.map(th));
        } else {
            completableFuture.complete(Mqtt3ConnAckView.of(mqtt5ConnAck));
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public Mqtt3ConnectViewBuilder.Send<CompletableFuture<Mqtt3ConnAck>> connectWith() {
        return new Mqtt3ConnectViewBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda8
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((Mqtt3ConnectView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe) {
        return handleSubAck(this.delegate.subscribe(MqttChecks.subscribe(mqtt3Subscribe)));
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer) {
        return subscribe(mqtt3Subscribe, consumer, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer, Executor executor) {
        return subscribe(mqtt3Subscribe, consumer, executor, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer, boolean z) {
        MqttSubscribe mqttSubscribeSubscribe = MqttChecks.subscribe(mqtt3Subscribe);
        Checks.notNull(consumer, "Callback");
        return handleSubAck(this.delegate.subscribe(mqttSubscribeSubscribe, callbackView(consumer), z));
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe, Consumer<Mqtt3Publish> consumer, Executor executor, boolean z) {
        MqttSubscribe mqttSubscribeSubscribe = MqttChecks.subscribe(mqtt3Subscribe);
        Checks.notNull(consumer, "Callback");
        Checks.notNull(executor, "Executor");
        return handleSubAck(this.delegate.subscribe(mqttSubscribeSubscribe, callbackView(consumer), executor, z));
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public Mqtt3SubscribeViewAndCallbackBuilder subscribeWith() {
        return new Mqtt3SubscribeViewAndCallbackBuilder();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer) {
        publishes(mqttGlobalPublishFilter, consumer, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer, Executor executor) {
        publishes(mqttGlobalPublishFilter, consumer, executor, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        Checks.notNull(consumer, "Callback");
        this.delegate.publishes(mqttGlobalPublishFilter, callbackView(consumer), z);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public void publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, Consumer<Mqtt3Publish> consumer, Executor executor, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        Checks.notNull(consumer, "Callback");
        Checks.notNull(executor, "Executor");
        this.delegate.publishes(mqttGlobalPublishFilter, callbackView(consumer), executor, z);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Void> unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe) {
        MqttUnsubscribe mqttUnsubscribeUnsubscribe = MqttChecks.unsubscribe(mqtt3Unsubscribe);
        final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        this.delegate.unsubscribe(mqttUnsubscribeUnsubscribe).whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda5
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Mqtt3AsyncClientView.lambda$unsubscribe$3(completableFuture, (Mqtt5UnsubAck) obj, (Throwable) obj2);
            }
        });
        return completableFuture;
    }

    static /* synthetic */ void lambda$unsubscribe$3(CompletableFuture completableFuture, Mqtt5UnsubAck mqtt5UnsubAck, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(Mqtt3ExceptionFactory.map(th));
        } else {
            completableFuture.complete(null);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public Mqtt3UnsubscribeViewBuilder.Send<CompletableFuture<Void>> unsubscribeWith() {
        return new Mqtt3UnsubscribeViewBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.unsubscribe((Mqtt3UnsubscribeView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Mqtt3Publish> publish(Mqtt3Publish mqtt3Publish) {
        MqttPublish mqttPublishPublish = MqttChecks.publish(mqtt3Publish);
        final CompletableFuture<Mqtt3Publish> completableFuture = new CompletableFuture<>();
        this.delegate.publish(mqttPublishPublish).whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Mqtt3AsyncClientView.lambda$publish$4(completableFuture, (Mqtt5PublishResult) obj, (Throwable) obj2);
            }
        });
        return completableFuture;
    }

    static /* synthetic */ void lambda$publish$4(CompletableFuture completableFuture, Mqtt5PublishResult mqtt5PublishResult, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(Mqtt3ExceptionFactory.map(th));
        } else {
            completableFuture.complete(Mqtt3PublishView.of(mqtt5PublishResult.getPublish()));
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public Mqtt3PublishViewBuilder.Send<CompletableFuture<Mqtt3Publish>> publishWith() {
        return new Mqtt3PublishViewBuilder.Send<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.publish((Mqtt3PublishView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient
    public CompletableFuture<Void> disconnect() {
        final CompletableFuture<Void> completableFuture = new CompletableFuture<>();
        this.delegate.disconnect(Mqtt3DisconnectView.DELEGATE).whenComplete(new BiConsumer() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3AsyncClientView$$ExternalSyntheticLambda7
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                Mqtt3AsyncClientView.lambda$disconnect$5(completableFuture, (Void) obj, (Throwable) obj2);
            }
        });
        return completableFuture;
    }

    static /* synthetic */ void lambda$disconnect$5(CompletableFuture completableFuture, Void r1, Throwable th) {
        if (th != null) {
            completableFuture.completeExceptionally(Mqtt3ExceptionFactory.map(th));
        } else {
            completableFuture.complete(null);
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client, com.hivemq.client.mqtt.MqttClient
    public Mqtt3ClientConfig getConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    public Mqtt3RxClient toRx() {
        return new Mqtt3RxClientView(this.delegate.toRx());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    public Mqtt3BlockingClient toBlocking() {
        return new Mqtt3BlockingClientView(this.delegate.toBlocking());
    }

    private class Mqtt3SubscribeViewAndCallbackBuilder extends Mqtt3SubscribeViewBuilder<Mqtt3SubscribeViewAndCallbackBuilder> implements Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Start.Complete, Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call.Ex {
        private Consumer<Mqtt3Publish> callback;
        private Executor executor;
        private boolean manualAcknowledgement;

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder
        public Mqtt3SubscribeViewAndCallbackBuilder self() {
            return this;
        }

        private Mqtt3SubscribeViewAndCallbackBuilder() {
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

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call
        public /* bridge */ /* synthetic */ Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call.Ex callback(Consumer consumer) {
            return callback((Consumer<Mqtt3Publish>) consumer);
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

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call
        public Mqtt3SubscribeViewAndCallbackBuilder callback(Consumer<Mqtt3Publish> consumer) {
            this.callback = (Consumer) Checks.notNull(consumer, "Callback");
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call.Ex
        public Mqtt3SubscribeViewAndCallbackBuilder executor(Executor executor) {
            this.executor = (Executor) Checks.notNull(executor, "Executor");
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call.Ex
        public Mqtt3SubscribeViewAndCallbackBuilder manualAcknowledgement(boolean z) {
            this.manualAcknowledgement = z;
            return this;
        }

        @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient.Mqtt3SubscribeAndCallbackBuilder.Call
        public CompletableFuture<Mqtt3SubAck> send() {
            Mqtt3SubscribeView mqtt3SubscribeViewBuild = build();
            Consumer<Mqtt3Publish> consumer = this.callback;
            if (consumer == null) {
                Checks.state(this.executor == null, "Executor must not be given if callback is null.");
                Checks.state(!this.manualAcknowledgement, "Manual acknowledgement must not be true if callback is null.");
                return Mqtt3AsyncClientView.this.subscribe(mqtt3SubscribeViewBuild);
            }
            Executor executor = this.executor;
            if (executor == null) {
                return Mqtt3AsyncClientView.this.subscribe(mqtt3SubscribeViewBuild, consumer, this.manualAcknowledgement);
            }
            return Mqtt3AsyncClientView.this.subscribe(mqtt3SubscribeViewBuild, consumer, executor, this.manualAcknowledgement);
        }
    }
}
