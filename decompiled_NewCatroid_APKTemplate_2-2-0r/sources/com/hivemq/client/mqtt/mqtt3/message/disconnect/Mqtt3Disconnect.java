package com.hivemq.client.mqtt.mqtt3.message.disconnect;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Disconnect extends Mqtt3Message {
    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.DISCONNECT;
    }
}
