package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult;

/* JADX INFO: loaded from: classes10.dex */
abstract class MqttAckFlow extends FlowWithEventLoop {
    abstract void acknowledged(long j);

    abstract void onNext(MqttPublishResult mqttPublishResult);

    MqttAckFlow(MqttClientConfig mqttClientConfig) {
        super(mqttClientConfig);
    }
}
