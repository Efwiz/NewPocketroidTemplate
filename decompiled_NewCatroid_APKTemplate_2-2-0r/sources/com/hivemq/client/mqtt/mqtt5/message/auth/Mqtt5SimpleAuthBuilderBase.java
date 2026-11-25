package com.hivemq.client.mqtt.mqtt5.message.auth;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuthBuilderBase;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SimpleAuthBuilderBase<C extends Mqtt5SimpleAuthBuilderBase<C>> {
    C password(ByteBuffer byteBuffer);

    C password(byte[] bArr);

    C username(MqttUtf8String mqttUtf8String);

    C username(String str);
}
