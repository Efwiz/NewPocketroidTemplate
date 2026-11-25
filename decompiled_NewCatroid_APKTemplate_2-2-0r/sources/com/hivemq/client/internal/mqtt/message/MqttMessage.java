package com.hivemq.client.internal.mqtt.message;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttMessage extends Mqtt5Message {

    public interface WithId extends MqttMessage {
        int getPacketIdentifier();
    }

    public interface WithUserProperties extends MqttMessage {
        MqttUserPropertiesImpl getUserProperties();
    }
}
