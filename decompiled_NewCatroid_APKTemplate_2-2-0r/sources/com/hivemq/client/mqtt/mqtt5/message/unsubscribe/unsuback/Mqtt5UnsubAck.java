package com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import java.util.List;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UnsubAck extends Mqtt5Message {
    List<Mqtt5UnsubAckReasonCode> getReasonCodes();

    Optional<MqttUtf8String> getReasonString();

    Mqtt5UserProperties getUserProperties();

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.UNSUBACK;
    }
}
