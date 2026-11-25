package com.hivemq.client.internal.mqtt.mqtt3;

import com.hivemq.client.internal.mqtt.MqttRxClient;
import com.hivemq.client.internal.mqtt.exceptions.mqtt3.Mqtt3ExceptionFactory;
import com.hivemq.client.internal.mqtt.message.connect.connack.mqtt3.Mqtt3ConnAckView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectView;
import com.hivemq.client.internal.mqtt.message.connect.mqtt3.Mqtt3ConnectViewBuilder;
import com.hivemq.client.internal.mqtt.message.disconnect.mqtt3.Mqtt3DisconnectView;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishResultView;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeView;
import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.mqtt3.Mqtt3SubAckView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeView;
import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishResult;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.rx.FlowableWithSingle;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.functions.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3RxClientView implements Mqtt3RxClient {
    private final Mqtt3ClientConfigView clientConfig;
    private final MqttRxClient delegate;
    private static final Function<Mqtt3Publish, MqttPublish> PUBLISH_MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda4
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return MqttChecks.publish((Mqtt3Publish) obj);
        }
    };
    private static final Function<Throwable, Completable> EXCEPTION_MAPPER_COMPLETABLE = new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda5
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Completable.error(Mqtt3ExceptionFactory.map((Throwable) obj));
        }
    };
    private static final Function<Throwable, Single<Mqtt5ConnAck>> EXCEPTION_MAPPER_SINGLE_CONNACK = new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda6
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Single.error(Mqtt3ExceptionFactory.map((Throwable) obj));
        }
    };
    private static final Function<Throwable, Single<Mqtt5SubAck>> EXCEPTION_MAPPER_SINGLE_SUBACK = new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda7
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Single.error(Mqtt3ExceptionFactory.map((Throwable) obj));
        }
    };
    private static final Function<Throwable, Flowable<Mqtt5Publish>> EXCEPTION_MAPPER_FLOWABLE_PUBLISH = new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda8
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Flowable.error(Mqtt3ExceptionFactory.map((Throwable) obj));
        }
    };
    private static final Function<Throwable, Flowable<Mqtt5PublishResult>> EXCEPTION_MAPPER_FLOWABLE_PUBLISH_RESULT = new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda9
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Flowable.error(Mqtt3ExceptionFactory.map((Throwable) obj));
        }
    };

    Mqtt3RxClientView(MqttRxClient mqttRxClient) {
        this.delegate = mqttRxClient;
        this.clientConfig = new Mqtt3ClientConfigView(mqttRxClient.getConfig());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Single<Mqtt3ConnAck> connect() {
        return connect(Mqtt3ConnectView.DEFAULT);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Single<Mqtt3ConnAck> connect(Mqtt3Connect mqtt3Connect) {
        return this.delegate.connect((Mqtt5Connect) MqttChecks.connect(mqtt3Connect)).onErrorResumeNext(EXCEPTION_MAPPER_SINGLE_CONNACK).map(Mqtt3ConnAckView.MAPPER);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Mqtt3ConnectViewBuilder.Nested<Single<Mqtt3ConnAck>> connectWith() {
        return new Mqtt3ConnectViewBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.connect((Mqtt3ConnectView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Single<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe) {
        return this.delegate.subscribe((Mqtt5Subscribe) MqttChecks.subscribe(mqtt3Subscribe)).onErrorResumeNext(EXCEPTION_MAPPER_SINGLE_SUBACK).map(Mqtt3SubAckView.MAPPER);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Mqtt3SubscribeViewBuilder.Nested<Single<Mqtt3SubAck>> subscribeWith() {
        return new Mqtt3SubscribeViewBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.subscribe((Mqtt3SubscribeView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subscribeStream(Mqtt3Subscribe mqtt3Subscribe) {
        return subscribePublishes(mqtt3Subscribe);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Mqtt3SubscribeViewBuilder.Nested<FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck>> subscribeStreamWith() {
        return new Mqtt3SubscribeViewBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.subscribeStream((Mqtt3SubscribeView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subscribePublishes(Mqtt3Subscribe mqtt3Subscribe) {
        return subscribePublishes(mqtt3Subscribe, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subscribePublishes(Mqtt3Subscribe mqtt3Subscribe, boolean z) {
        return this.delegate.subscribePublishes((Mqtt5Subscribe) MqttChecks.subscribe(mqtt3Subscribe), z).mapError(Mqtt3ExceptionFactory.MAPPER).mapBoth(Mqtt3PublishView.MAPPER, Mqtt3SubAckView.MAPPER);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Mqtt3SubscribeViewPublishesBuilder subscribePublishesWith() {
        return new Mqtt3SubscribeViewPublishesBuilder();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Flowable<Mqtt3Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter) {
        return publishes(mqttGlobalPublishFilter, false);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Flowable<Mqtt3Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z) {
        Checks.notNull(mqttGlobalPublishFilter, "Global publish filter");
        return this.delegate.publishes(mqttGlobalPublishFilter, z).onErrorResumeNext(EXCEPTION_MAPPER_FLOWABLE_PUBLISH).map(Mqtt3PublishView.MAPPER);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Completable unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe) {
        return this.delegate.unsubscribe((Mqtt5Unsubscribe) MqttChecks.unsubscribe(mqtt3Unsubscribe)).ignoreElement().onErrorResumeNext(EXCEPTION_MAPPER_COMPLETABLE);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Mqtt3UnsubscribeViewBuilder.Nested<Completable> unsubscribeWith() {
        return new Mqtt3UnsubscribeViewBuilder.Nested<>(new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientView$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.unsubscribe((Mqtt3UnsubscribeView) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Flowable<Mqtt3PublishResult> publish(Flowable<Mqtt3Publish> flowable) {
        Checks.notNull(flowable, "Publish flowable");
        return this.delegate.publish(flowable, PUBLISH_MAPPER).onErrorResumeNext(EXCEPTION_MAPPER_FLOWABLE_PUBLISH_RESULT).map(Mqtt3PublishResultView.MAPPER);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3RxClient
    public Completable disconnect() {
        return this.delegate.disconnect((Mqtt5Disconnect) Mqtt3DisconnectView.DELEGATE).onErrorResumeNext(EXCEPTION_MAPPER_COMPLETABLE);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client, com.hivemq.client.mqtt.MqttClient
    public Mqtt3ClientConfigView getConfig() {
        return this.clientConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    public Mqtt3AsyncClientView toAsync() {
        return new Mqtt3AsyncClientView(this.delegate.toAsync());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    public Mqtt3BlockingClientView toBlocking() {
        return new Mqtt3BlockingClientView(this.delegate.toBlocking());
    }

    private class Mqtt3SubscribeViewPublishesBuilder extends Mqtt3SubscribeViewBuilder.Publishes<FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck>> {
        private Mqtt3SubscribeViewPublishesBuilder() {
        }

        @Override // com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder.Publishes.Args
        public FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> applySubscribe() {
            return Mqtt3RxClientView.this.subscribePublishes(build(), this.manualAcknowledgement);
        }
    }
}
