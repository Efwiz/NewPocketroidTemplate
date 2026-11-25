package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribe;
import com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback.MqttUnsubAck;

/* JADX INFO: loaded from: classes10.dex */
class MqttUnsubscribeWithFlow extends MqttSubOrUnsubWithFlow {
    private final MqttSubOrUnsubAckFlow<MqttUnsubAck> unsubAckFlow;
    final MqttUnsubscribe unsubscribe;

    MqttUnsubscribeWithFlow(MqttUnsubscribe mqttUnsubscribe, MqttSubOrUnsubAckFlow<MqttUnsubAck> mqttSubOrUnsubAckFlow) {
        this.unsubscribe = mqttUnsubscribe;
        this.unsubAckFlow = mqttSubOrUnsubAckFlow;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Override // com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubOrUnsubWithFlow
    public MqttSubOrUnsubAckFlow<MqttUnsubAck> getFlow() {
        return this.unsubAckFlow;
    }
}
