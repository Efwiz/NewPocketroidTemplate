package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3Connect;
import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectBuilder;
import com.hivemq.client.mqtt.mqtt3.message.connect.connack.Mqtt3ConnAck;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishResult;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3Subscribe;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.Mqtt3SubscribeBuilder;
import com.hivemq.client.mqtt.mqtt3.message.subscribe.suback.Mqtt3SubAck;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3Unsubscribe;
import com.hivemq.client.mqtt.mqtt3.message.unsubscribe.Mqtt3UnsubscribeBuilder;
import com.hivemq.client.rx.FlowableWithSingle;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3RxClient extends Mqtt3Client {
    Single<Mqtt3ConnAck> connect();

    Single<Mqtt3ConnAck> connect(Mqtt3Connect mqtt3Connect);

    Mqtt3ConnectBuilder.Nested<Single<Mqtt3ConnAck>> connectWith();

    Completable disconnect();

    Flowable<Mqtt3PublishResult> publish(Flowable<Mqtt3Publish> flowable);

    Flowable<Mqtt3Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter);

    Flowable<Mqtt3Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z);

    Single<Mqtt3SubAck> subscribe(Mqtt3Subscribe mqtt3Subscribe);

    FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subscribePublishes(Mqtt3Subscribe mqtt3Subscribe);

    FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subscribePublishes(Mqtt3Subscribe mqtt3Subscribe, boolean z);

    Mqtt3SubscribeBuilder.Publishes.Start<FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck>> subscribePublishesWith();

    @Deprecated
    FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck> subscribeStream(Mqtt3Subscribe mqtt3Subscribe);

    @Deprecated
    Mqtt3SubscribeBuilder.Nested.Start<FlowableWithSingle<Mqtt3Publish, Mqtt3SubAck>> subscribeStreamWith();

    Mqtt3SubscribeBuilder.Nested.Start<Single<Mqtt3SubAck>> subscribeWith();

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3Client
    default Mqtt3RxClient toRx() {
        return this;
    }

    Completable unsubscribe(Mqtt3Unsubscribe mqtt3Unsubscribe);

    Mqtt3UnsubscribeBuilder.Nested.Start<Completable> unsubscribeWith();
}
