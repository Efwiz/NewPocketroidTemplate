package com.hivemq.client.mqtt.mqtt5.message.disconnect;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectBuilderBase;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5DisconnectBuilderBase<B extends Mqtt5DisconnectBuilderBase<B>> {
    B noSessionExpiry();

    B reasonCode(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode);

    B reasonString(MqttUtf8String mqttUtf8String);

    B reasonString(String str);

    B serverReference(MqttUtf8String mqttUtf8String);

    B serverReference(String str);

    B sessionExpiryInterval(long j);

    Mqtt5UserPropertiesBuilder.Nested<? extends B> userProperties();

    B userProperties(Mqtt5UserProperties mqtt5UserProperties);
}
