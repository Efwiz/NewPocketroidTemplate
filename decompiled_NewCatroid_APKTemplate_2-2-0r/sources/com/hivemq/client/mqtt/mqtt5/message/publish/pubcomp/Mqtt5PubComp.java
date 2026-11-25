package com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PubComp extends Mqtt5Message {
    Mqtt5PubCompReasonCode getReasonCode();

    Optional<MqttUtf8String> getReasonString();

    Mqtt5UserProperties getUserProperties();

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.PUBCOMP;
    }
}
