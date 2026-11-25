package com.hivemq.client.mqtt.mqtt5.message.ping;

import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PingResp extends Mqtt5Message {
    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.PINGRESP;
    }
}
