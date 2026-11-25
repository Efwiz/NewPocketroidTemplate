package com.hivemq.client.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttUtf8String extends Comparable<MqttUtf8String> {
    boolean containsShouldNotCharacters();

    ByteBuffer toByteBuffer();

    static MqttUtf8String of(String str) {
        return MqttUtf8StringImpl.of(str);
    }
}
