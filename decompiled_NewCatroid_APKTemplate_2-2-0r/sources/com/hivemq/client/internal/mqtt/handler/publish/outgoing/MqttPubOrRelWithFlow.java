package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.util.collections.NodeList;

/* JADX INFO: loaded from: classes10.dex */
abstract class MqttPubOrRelWithFlow extends NodeList.Node<MqttPubOrRelWithFlow> {
    private final MqttAckFlow ackFlow;
    int packetIdentifier;

    MqttPubOrRelWithFlow(MqttAckFlow mqttAckFlow) {
        this.ackFlow = mqttAckFlow;
    }

    MqttAckFlow getAckFlow() {
        return this.ackFlow;
    }
}
