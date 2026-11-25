package com.hivemq.client.mqtt.mqtt3.message.subscribe;

import com.hivemq.client.internal.mqtt.message.subscribe.mqtt3.Mqtt3SubscribeViewBuilder;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Subscribe extends Mqtt3Message {
    Mqtt3SubscribeBuilder.Complete extend();

    List<? extends Mqtt3Subscription> getSubscriptions();

    static Mqtt3SubscribeBuilder.Start builder() {
        return new Mqtt3SubscribeViewBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.SUBSCRIBE;
    }
}
