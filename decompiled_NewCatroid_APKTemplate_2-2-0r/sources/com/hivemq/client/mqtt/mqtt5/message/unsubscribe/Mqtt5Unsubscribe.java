package com.hivemq.client.mqtt.mqtt5.message.unsubscribe;

import com.hivemq.client.internal.mqtt.message.unsubscribe.MqttUnsubscribeBuilder;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Unsubscribe extends Mqtt5Message {
    Mqtt5UnsubscribeBuilder.Complete extend();

    List<? extends MqttTopicFilter> getTopicFilters();

    Mqtt5UserProperties getUserProperties();

    static Mqtt5UnsubscribeBuilder.Start builder() {
        return new MqttUnsubscribeBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.UNSUBSCRIBE;
    }
}
