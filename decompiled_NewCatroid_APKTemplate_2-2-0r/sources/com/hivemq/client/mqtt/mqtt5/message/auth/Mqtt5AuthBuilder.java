package com.hivemq.client.mqtt.mqtt5.message.auth;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5AuthBuilder extends Mqtt5EnhancedAuthBuilder {
    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder
    Mqtt5AuthBuilder data(ByteBuffer byteBuffer);

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder
    Mqtt5AuthBuilder data(byte[] bArr);

    Mqtt5AuthBuilder reasonString(MqttUtf8String mqttUtf8String);

    Mqtt5AuthBuilder reasonString(String str);

    Mqtt5UserPropertiesBuilder.Nested<? extends Mqtt5AuthBuilder> userProperties();

    Mqtt5AuthBuilder userProperties(Mqtt5UserProperties mqtt5UserProperties);
}
