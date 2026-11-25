package com.hivemq.client.mqtt.mqtt3.message.auth;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuthBuilderBase.Complete;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SimpleAuthBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt3SimpleAuthBuilderBase<C> {
        C password(ByteBuffer byteBuffer);

        C password(byte[] bArr);
    }

    C username(MqttUtf8String mqttUtf8String);

    C username(String str);
}
