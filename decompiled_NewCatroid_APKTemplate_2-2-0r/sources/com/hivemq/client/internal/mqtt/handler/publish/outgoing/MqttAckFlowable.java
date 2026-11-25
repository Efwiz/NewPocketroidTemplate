package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import io.reactivex.Flowable;
import io.reactivex.internal.subscriptions.EmptySubscription;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public class MqttAckFlowable extends Flowable<Mqtt5PublishResult> {
    private final MqttClientConfig clientConfig;
    private final Flowable<MqttPublish> publishFlowable;

    public MqttAckFlowable(MqttClientConfig mqttClientConfig, Flowable<MqttPublish> flowable) {
        this.clientConfig = mqttClientConfig;
        this.publishFlowable = flowable;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Mqtt5PublishResult> subscriber) {
        if (this.clientConfig.getState().isConnectedOrReconnect()) {
            MqttOutgoingQosHandler mqttOutgoingQosHandlerOutgoingQosHandler = this.clientConfig.getClientComponent().outgoingQosHandler();
            MqttPublishFlowables publishFlowables = mqttOutgoingQosHandlerOutgoingQosHandler.getPublishFlowables();
            MqttAckFlowableFlow mqttAckFlowableFlow = new MqttAckFlowableFlow(subscriber, this.clientConfig, mqttOutgoingQosHandlerOutgoingQosHandler);
            subscriber.onSubscribe(mqttAckFlowableFlow);
            publishFlowables.add(new MqttPublishFlowableAckLink(this.publishFlowable, mqttAckFlowableFlow));
            return;
        }
        EmptySubscription.error(MqttClientStateExceptions.notConnected(), subscriber);
    }
}
