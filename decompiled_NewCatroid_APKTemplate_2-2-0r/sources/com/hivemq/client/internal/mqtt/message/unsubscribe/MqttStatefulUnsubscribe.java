package com.hivemq.client.internal.mqtt.message.unsubscribe;

import com.hivemq.client.internal.mqtt.message.MqttStatefulMessage;

/* JADX INFO: loaded from: classes10.dex */
public class MqttStatefulUnsubscribe extends MqttStatefulMessage.WithId<MqttUnsubscribe> {
    MqttStatefulUnsubscribe(MqttUnsubscribe mqttUnsubscribe, int i) {
        super(mqttUnsubscribe, i);
    }

    public String toString() {
        return "MqttStatefulUnsubscribe{" + toAttributeString() + "}";
    }
}
