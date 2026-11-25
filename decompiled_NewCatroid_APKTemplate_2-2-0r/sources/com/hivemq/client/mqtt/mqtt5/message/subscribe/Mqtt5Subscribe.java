package com.hivemq.client.mqtt.mqtt5.message.subscribe;

import com.hivemq.client.internal.mqtt.message.subscribe.MqttSubscribeBuilder;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Subscribe extends Mqtt5Message {
    Mqtt5SubscribeBuilder.Complete extend();

    List<? extends Mqtt5Subscription> getSubscriptions();

    Mqtt5UserProperties getUserProperties();

    static Mqtt5SubscribeBuilder.Start builder() {
        return new MqttSubscribeBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.SUBSCRIBE;
    }
}
