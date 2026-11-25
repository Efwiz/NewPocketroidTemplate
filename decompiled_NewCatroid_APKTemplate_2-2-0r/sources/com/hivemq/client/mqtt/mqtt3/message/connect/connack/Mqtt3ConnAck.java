package com.hivemq.client.mqtt.mqtt3.message.connect.connack;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ConnAck extends Mqtt3Message {
    Mqtt3ConnAckReturnCode getReturnCode();

    boolean isSessionPresent();

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.CONNACK;
    }
}
