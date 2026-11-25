package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.util.collections.NodeList;

/* JADX INFO: loaded from: classes10.dex */
abstract class MqttSubOrUnsubWithFlow extends NodeList.Node<MqttSubOrUnsubWithFlow> {
    int packetIdentifier;

    abstract MqttSubscriptionFlow<?> getFlow();

    MqttSubOrUnsubWithFlow() {
    }
}
