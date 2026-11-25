package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubAckSingle extends Single<Mqtt5SubAck> {
    private final MqttClientConfig clientConfig;
    private final MqttSubscribe subscribe;

    public MqttSubAckSingle(MqttSubscribe mqttSubscribe, MqttClientConfig mqttClientConfig) {
        this.subscribe = mqttSubscribe;
        this.clientConfig = mqttClientConfig;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Mqtt5SubAck> singleObserver) {
        MqttSubscriptionHandler mqttSubscriptionHandlerSubscriptionHandler = this.clientConfig.getClientComponent().subscriptionHandler();
        MqttSubOrUnsubAckFlow mqttSubOrUnsubAckFlow = new MqttSubOrUnsubAckFlow(singleObserver, this.clientConfig);
        singleObserver.onSubscribe(mqttSubOrUnsubAckFlow);
        mqttSubscriptionHandlerSubscriptionHandler.subscribe(this.subscribe, mqttSubOrUnsubAckFlow);
    }
}
