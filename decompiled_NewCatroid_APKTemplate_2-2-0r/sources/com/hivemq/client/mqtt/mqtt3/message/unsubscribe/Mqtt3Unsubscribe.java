package com.hivemq.client.mqtt.mqtt3.message.unsubscribe;

import com.hivemq.client.internal.mqtt.message.unsubscribe.mqtt3.Mqtt3UnsubscribeViewBuilder;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Unsubscribe extends Mqtt3Message {
    Mqtt3UnsubscribeBuilder.Complete extend();

    List<? extends MqttTopicFilter> getTopicFilters();

    static Mqtt3UnsubscribeBuilder.Start builder() {
        return new Mqtt3UnsubscribeViewBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.UNSUBSCRIBE;
    }
}
