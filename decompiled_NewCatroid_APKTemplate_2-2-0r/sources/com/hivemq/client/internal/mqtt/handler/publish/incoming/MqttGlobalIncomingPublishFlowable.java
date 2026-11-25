package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler;
import com.hivemq.client.internal.mqtt.ioc.ClientComponent;
import com.hivemq.client.mqtt.MqttGlobalPublishFilter;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import io.reactivex.Flowable;
import org.reactivestreams.Subscriber;

/* JADX INFO: loaded from: classes10.dex */
public class MqttGlobalIncomingPublishFlowable extends Flowable<Mqtt5Publish> {
    private final MqttClientConfig clientConfig;
    private final MqttGlobalPublishFilter filter;
    private final boolean manualAcknowledgement;

    public MqttGlobalIncomingPublishFlowable(MqttGlobalPublishFilter mqttGlobalPublishFilter, MqttClientConfig mqttClientConfig, boolean z) {
        this.filter = mqttGlobalPublishFilter;
        this.clientConfig = mqttClientConfig;
        this.manualAcknowledgement = z;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Mqtt5Publish> subscriber) {
        ClientComponent clientComponent = this.clientConfig.getClientComponent();
        MqttIncomingQosHandler mqttIncomingQosHandlerIncomingQosHandler = clientComponent.incomingQosHandler();
        MqttSubscriptionHandler mqttSubscriptionHandlerSubscriptionHandler = clientComponent.subscriptionHandler();
        MqttGlobalIncomingPublishFlow mqttGlobalIncomingPublishFlow = new MqttGlobalIncomingPublishFlow(subscriber, this.clientConfig, mqttIncomingQosHandlerIncomingQosHandler, this.filter, this.manualAcknowledgement);
        subscriber.onSubscribe(mqttGlobalIncomingPublishFlow);
        mqttSubscriptionHandlerSubscriptionHandler.subscribeGlobal(mqttGlobalIncomingPublishFlow);
    }
}
