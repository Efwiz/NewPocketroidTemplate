package com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PubCompBuilder {
    Mqtt5PubCompReasonCode getReasonCode();

    Mqtt5PubCompBuilder reasonString(MqttUtf8String mqttUtf8String);

    Mqtt5PubCompBuilder reasonString(String str);

    Mqtt5UserPropertiesBuilder.Nested<? extends Mqtt5PubCompBuilder> userProperties();

    Mqtt5PubCompBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties);
}
