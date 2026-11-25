package com.hivemq.client.mqtt.mqtt5;

import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5Connect;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5Subscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.Mqtt5SubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5Unsubscribe;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.Mqtt5UnsubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import com.hivemq.client.rx.FlowableWithSingle;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5RxClient extends Mqtt5Client {
    Single<Mqtt5ConnAck> connect();

    Single<Mqtt5ConnAck> connect(Mqtt5Connect mqtt5Connect);

    Mqtt5ConnectBuilder.Nested<Single<Mqtt5ConnAck>> connectWith();

    Completable disconnect();

    Completable disconnect(Mqtt5Disconnect mqtt5Disconnect);

    Mqtt5DisconnectBuilder.Nested<Completable> disconnectWith();

    Flowable<Mqtt5PublishResult> publish(Flowable<Mqtt5Publish> flowable);

    Flowable<Mqtt5Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter);

    Flowable<Mqtt5Publish> publishes(MqttGlobalPublishFilter mqttGlobalPublishFilter, boolean z);

    Completable reauth();

    Single<Mqtt5SubAck> subscribe(Mqtt5Subscribe mqtt5Subscribe);

    FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribePublishes(Mqtt5Subscribe mqtt5Subscribe);

    FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribePublishes(Mqtt5Subscribe mqtt5Subscribe, boolean z);

    Mqtt5SubscribeBuilder.Publishes.Start<FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck>> subscribePublishesWith();

    @Deprecated
    FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> subscribeStream(Mqtt5Subscribe mqtt5Subscribe);

    @Deprecated
    Mqtt5SubscribeBuilder.Nested.Start<FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck>> subscribeStreamWith();

    Mqtt5SubscribeBuilder.Nested.Start<Single<Mqtt5SubAck>> subscribeWith();

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5Client
    default Mqtt5RxClient toRx() {
        return this;
    }

    Single<Mqtt5UnsubAck> unsubscribe(Mqtt5Unsubscribe mqtt5Unsubscribe);

    Mqtt5UnsubscribeBuilder.Nested.Start<Single<Mqtt5UnsubAck>> unsubscribeWith();
}
