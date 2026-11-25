package com.hivemq.client.mqtt.mqtt5.message.publish.pubrec;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PubRecBuilder {
    Mqtt5PubRecBuilder reasonCode(Mqtt5PubRecReasonCode mqtt5PubRecReasonCode);

    Mqtt5PubRecBuilder reasonString(MqttUtf8String mqttUtf8String);

    Mqtt5PubRecBuilder reasonString(String str);

    Mqtt5UserPropertiesBuilder.Nested<? extends Mqtt5PubRecBuilder> userProperties();

    Mqtt5PubRecBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties);
}
