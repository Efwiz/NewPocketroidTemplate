package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;

/* JADX INFO: loaded from: classes10.dex */
class MqttPublishWithFlow extends MqttPubOrRelWithFlow {
    private final MqttPublish publish;

    MqttPublishWithFlow(MqttPublish mqttPublish, MqttAckFlow mqttAckFlow) {
        super(mqttAckFlow);
        this.publish = mqttPublish;
    }

    MqttPublish getPublish() {
        return this.publish;
    }
}
