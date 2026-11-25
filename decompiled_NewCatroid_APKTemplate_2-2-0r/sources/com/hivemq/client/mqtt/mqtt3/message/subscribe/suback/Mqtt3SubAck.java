package com.hivemq.client.mqtt.mqtt3.message.subscribe.suback;

import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SubAck extends Mqtt3Message {
    List<Mqtt3SubAckReturnCode> getReturnCodes();

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.SUBACK;
    }
}
