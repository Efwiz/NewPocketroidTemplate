package com.hivemq.client.mqtt.mqtt5.message.publish.pubrel;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PubRelBuilder {
    Mqtt5PubRelReasonCode getReasonCode();

    Mqtt5PubRelBuilder reasonString(MqttUtf8String mqttUtf8String);

    Mqtt5PubRelBuilder reasonString(String str);

    Mqtt5UserPropertiesBuilder.Nested<? extends Mqtt5PubRelBuilder> userProperties();

    Mqtt5PubRelBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties);
}
