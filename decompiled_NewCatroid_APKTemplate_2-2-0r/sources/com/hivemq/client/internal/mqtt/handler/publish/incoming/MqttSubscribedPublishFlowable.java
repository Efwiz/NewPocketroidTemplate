package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler;
import com.hivemq.client.internal.mqtt.ioc.ClientComponent;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.rx.FlowableWithSingle;
import com.hivemq.client.rx.reactivestreams.WithSingleSubscriber;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubscribedPublishFlowable extends FlowableWithSingle<Mqtt5Publish, Mqtt5SubAck> {
    private final MqttClientConfig clientConfig;
    private final boolean manualAcknowledgement;
    private final MqttSubscribe subscribe;

    public MqttSubscribedPublishFlowable(MqttSubscribe mqttSubscribe, MqttClientConfig mqttClientConfig, boolean z) {
        this.subscribe = mqttSubscribe;
        this.clientConfig = mqttClientConfig;
        this.manualAcknowledgement = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Mqtt5Publish> subscriber) {
        ClientComponent clientComponent = this.clientConfig.getClientComponent();
        MqttIncomingQosHandler mqttIncomingQosHandlerIncomingQosHandler = clientComponent.incomingQosHandler();
        MqttSubscriptionHandler mqttSubscriptionHandlerSubscriptionHandler = clientComponent.subscriptionHandler();
        MqttSubscribedPublishFlow mqttSubscribedPublishFlow = new MqttSubscribedPublishFlow(subscriber, this.clientConfig, mqttIncomingQosHandlerIncomingQosHandler, this.manualAcknowledgement);
        subscriber.onSubscribe(mqttSubscribedPublishFlow);
        mqttSubscriptionHandlerSubscriptionHandler.subscribe(this.subscribe, mqttSubscribedPublishFlow);
    }

    @Override // com.hivemq.client.rx.FlowableWithSingle
    protected void subscribeBothActual(WithSingleSubscriber<? super Mqtt5Publish, ? super Mqtt5SubAck> withSingleSubscriber) {
        subscribeActual(withSingleSubscriber);
    }
}
