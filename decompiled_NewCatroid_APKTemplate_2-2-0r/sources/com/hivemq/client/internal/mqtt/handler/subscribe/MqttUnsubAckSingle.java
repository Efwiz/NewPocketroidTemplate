package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.MqttUnsubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import io.reactivex.Single;
import io.reactivex.SingleObserver;

/* JADX INFO: loaded from: classes10.dex */
public class MqttUnsubAckSingle extends Single<Mqtt5UnsubAck> {
    private final MqttClientConfig clientConfig;
    private final MqttUnsubscribe unsubscribe;

    public MqttUnsubAckSingle(MqttUnsubscribe mqttUnsubscribe, MqttClientConfig mqttClientConfig) {
        this.unsubscribe = mqttUnsubscribe;
        this.clientConfig = mqttClientConfig;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Mqtt5UnsubAck> singleObserver) {
        MqttSubscriptionHandler mqttSubscriptionHandlerSubscriptionHandler = this.clientConfig.getClientComponent().subscriptionHandler();
        MqttSubOrUnsubAckFlow<MqttUnsubAck> mqttSubOrUnsubAckFlow = new MqttSubOrUnsubAckFlow<>(singleObserver, this.clientConfig);
        singleObserver.onSubscribe(mqttSubOrUnsubAckFlow);
        mqttSubscriptionHandlerSubscriptionHandler.unsubscribe(this.unsubscribe, mqttSubOrUnsubAckFlow);
    }
}
