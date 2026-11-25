package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.handler.auth.MqttReAuthCompletable;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckSingle;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectCompletable;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttGlobalIncomingPublishFlowable;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttSubscribedPublishFlowable;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlowable;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckSingle;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckSingleFlowable;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubAckSingle;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttUnsubAckSingle;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnectBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import com.hivemq.client.rx.FlowableWithSingle;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.internal.fuseable.ScalarCallable;

/* JADX INFO: loaded from: classes10.dex */
public class MqttRxClient implements Mqtt5RxClient {
    private static final Function<Mqtt5Publish, MqttPublish> PUBLISH_MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClient$$ExternalSyntheticLambda5
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return MqttChecks.publish((Mqtt5Publish) obj);
        }
    };
    private final MqttClientConfig clientConfig;

    public MqttRxClient(MqttClientConfig mqttClientConfig) {
        this.clientConfig = mqttClientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Single<Mqtt5ConnAck> connect() {
        return connect(MqttConnect.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Single<Mqtt5ConnAck> connect(Mqtt5Connect mqtt5Connect) {
        return connect(MqttChecks.connect(mqtt5Connect));
    }

    Single<Mqtt5ConnAck> connect(MqttConnect mqttConnect) {
        return connectUnsafe(mqttConnect).observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler());
    }

    Single<Mqtt5ConnAck> connectUnsafe(MqttConnect mqttConnect) {
        return new MqttConnAckSingle(this.clientConfig, mqttConnect);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public MqttConnectBuilder.Nested<Single<Mqtt5ConnAck>> connectWith() {
        return new MqttConnectBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClient$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((MqttConnect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Single<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe) {
        return subscribe(MqttChecks.subscribe(mqtt5Subscribe));
    }

    Single<Mqtt5SubAck> subscribe(MqttSubscribe mqttSubscribe) {
        return subscribeUnsafe(mqttSubscribe).observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler());
    }

    Single<Mqtt5SubAck> subscribeUnsafe(MqttSubscribe mqttSubscribe) {
        return new MqttSubAckSingle(mqttSubscribe, this.clientConfig);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public MqttSubscribeBuilder.Nested<Single<Mqtt5SubAck>> subscribeWith() {
        return new MqttSubscribeBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClient$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.subscribe((MqttSubscribe) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribeStream(Mqtt5Subscribe mqtt5Subscribe) {
        return subscribePublishes(mqtt5Subscribe);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public MqttSubscribeBuilder.Nested<FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck>> subscribeStreamWith() {
        return new MqttSubscribeBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClient$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.subscribeStream((MqttSubscribe) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribePublishes(Mqtt5Subscribe mqtt5Subscribe) {
        return subscribePublishes(mqtt5Subscribe, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribePublishes(Mqtt5Subscribe mqtt5Subscribe, boolean z) {
        return subscribePublishes(MqttChecks.subscribe(mqtt5Subscribe), z);
    }

    FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribePublishes(MqttSubscribe mqttSubscribe, boolean z) {
        return subscribePublishesUnsafe(mqttSubscribe, z).observeOnBoth(this.clientConfig.getExecutorConfig().getApplicationScheduler(), true);
    }

    FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribePublishesUnsafe(MqttSubscribe mqttSubscribe, boolean z) {
        return new MqttSubscribedPublishFlowable(mqttSubscribe, this.clientConfig, z);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public MqttSubscribePublishesBuilder subscribePublishesWith() {
        return new MqttSubscribePublishesBuilder();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Flowable<Mqtt5Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter) {
        return publishes(mqttGlobalPublishFilter, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Flowable<Mqtt5Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        return publishesUnsafe(mqttGlobalPublishFilter, z).observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler(), true);
    }

    Flowable<Mqtt5Publish> publishesUnsafe(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z) {
        return new MqttGlobalIncomingPublishFlowable(mqttGlobalPublishFilter, this.clientConfig, z);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Single<Mqtt5UnsubAck> unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe) {
        return unsubscribe(MqttChecks.unsubscribe(mqtt5Unsubscribe));
    }

    Single<Mqtt5UnsubAck> unsubscribe(MqttUnsubscribe mqttUnsubscribe) {
        return unsubscribeUnsafe(mqttUnsubscribe).observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler());
    }

    Single<Mqtt5UnsubAck> unsubscribeUnsafe(MqttUnsubscribe mqttUnsubscribe) {
        return new MqttUnsubAckSingle(mqttUnsubscribe, this.clientConfig);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public MqttUnsubscribeBuilder.Nested<Single<Mqtt5UnsubAck>> unsubscribeWith() {
        return new MqttUnsubscribeBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClient$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.unsubscribe((MqttUnsubscribe) obj);
            }
        });
    }

    Single<Mqtt5PublishResult> publish(MqttPublish mqttPublish) {
        return publishUnsafe(mqttPublish).observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler());
    }

    Single<Mqtt5PublishResult> publishUnsafe(MqttPublish mqttPublish) {
        return new MqttAckSingle(this.clientConfig, mqttPublish);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Flowable<Mqtt5PublishResult> publish(Flowable<Mqtt5Publish> flowable) {
        Checks.notNull(flowable, "Publish flowable");
        return publish(flowable, PUBLISH_MAPPER);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public <P> Flowable<Mqtt5PublishResult> publish(Flowable<P> flowable, Function<P, MqttPublish> function) {
        Scheduler applicationScheduler = this.clientConfig.getExecutorConfig().getApplicationScheduler();
        if (flowable instanceof ScalarCallable) {
            Object objCall = ((ScalarCallable) flowable).call();
            if (objCall == null) {
                return Flowable.empty();
            }
            try {
                return new MqttAckSingleFlowable(this.clientConfig, (MqttPublish) function.apply(objCall)).observeOn(applicationScheduler, true);
            } catch (Throwable th) {
                return Flowable.error(th);
            }
        }
        return new MqttAckFlowable(this.clientConfig, flowable.subscribeOn(applicationScheduler).map(function)).observeOn(applicationScheduler, true);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Completable reauth() {
        return reauthUnsafe().observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler());
    }

    Completable reauthUnsafe() {
        return new MqttReAuthCompletable(this.clientConfig);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Completable disconnect() {
        return disconnect(MqttDisconnect.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public Completable disconnect(Mqtt5Disconnect mqtt5Disconnect) {
        return disconnect(MqttChecks.disconnect(mqtt5Disconnect));
    }

    Completable disconnect(MqttDisconnect mqttDisconnect) {
        return disconnectUnsafe(mqttDisconnect).observeOn(this.clientConfig.getExecutorConfig().getApplicationScheduler());
    }

    Completable disconnectUnsafe(MqttDisconnect mqttDisconnect) {
        return new MqttDisconnectCompletable(this.clientConfig, mqttDisconnect);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5RxClient
    public MqttDisconnectBuilder.Nested<Completable> disconnectWith() {
        return new MqttDisconnectBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClient$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.disconnect((MqttDisconnect) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client, com.hivemq.client.mqtt.MqttClient
    public MqttClientConfig getConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    public MqttAsyncClient toAsync() {
        return new MqttAsyncClient(this);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    public MqttBlockingClient toBlocking() {
        return new MqttBlockingClient(this);
    }

    private class MqttSubscribePublishesBuilder extends MqttSubscribeBuilder.Publishes<FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck>> {
        private MqttSubscribePublishesBuilder() {
        }

        @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder.Publishes.Args
        public FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> applySubscribe() {
            return MqttRxClient.this.subscribePublishes(build(), this.manualAcknowledgement);
        }
    }
}
