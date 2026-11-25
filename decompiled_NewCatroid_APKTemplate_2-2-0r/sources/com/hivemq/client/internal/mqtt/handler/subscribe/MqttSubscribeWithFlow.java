package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribe;
import com.hivemq.client.internal.mqtt.message.subscribe.suback.MqttSubAck;

/* JADX INFO: loaded from: classes10.dex */
class MqttSubscribeWithFlow extends MqttSubOrUnsubWithFlow {
    private final MqttSubscriptionFlow<MqttSubAck> flow;
    final MqttSubscribe subscribe;
    final int subscriptionIdentifier;

    MqttSubscribeWithFlow(MqttSubscribe mqttSubscribe, int i, MqttSubscriptionFlow<MqttSubAck> mqttSubscriptionFlow) {
        this.subscribe = mqttSubscribe;
        this.subscriptionIdentifier = i;
        this.flow = mqttSubscriptionFlow;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubOrUnsubWithFlow
    MqttSubscriptionFlow<MqttSubAck> getFlow() {
        return this.flow;
    }
}
