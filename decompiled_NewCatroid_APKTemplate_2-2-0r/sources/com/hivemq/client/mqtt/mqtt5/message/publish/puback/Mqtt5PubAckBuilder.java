package com.hivemq.client.mqtt.mqtt5.message.publish.puback;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PubAckBuilder {
    Mqtt5PubAckBuilder reasonCode(Mqtt5PubAckReasonCode mqtt5PubAckReasonCode);

    Mqtt5PubAckBuilder reasonString(MqttUtf8String mqttUtf8String);

    Mqtt5PubAckBuilder reasonString(String str);

    Mqtt5UserPropertiesBuilder.Nested<? extends Mqtt5PubAckBuilder> userProperties();

    Mqtt5PubAckBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties);
}
