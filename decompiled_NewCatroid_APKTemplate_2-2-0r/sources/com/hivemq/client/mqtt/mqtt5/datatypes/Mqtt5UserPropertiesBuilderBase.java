package com.hivemq.client.mqtt.mqtt5.datatypes;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilderBase;
import java.util.Collection;
import java.util.stream.Stream;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5UserPropertiesBuilderBase<B extends Mqtt5UserPropertiesBuilderBase<B>> {
    B add(MqttUtf8String mqttUtf8String, MqttUtf8String mqttUtf8String2);

    B add(Mqtt5UserProperty mqtt5UserProperty);

    B add(String str, String str2);

    B addAll(Collection<? extends Mqtt5UserProperty> collection);

    B addAll(Stream<? extends Mqtt5UserProperty> stream);

    B addAll(Mqtt5UserProperty... mqtt5UserPropertyArr);
}
