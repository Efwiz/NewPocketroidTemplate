package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttTopicAliasMapping {
    int getTopicAliasMaximum();

    int onPublish(MqttTopicImpl mqttTopicImpl);
}
