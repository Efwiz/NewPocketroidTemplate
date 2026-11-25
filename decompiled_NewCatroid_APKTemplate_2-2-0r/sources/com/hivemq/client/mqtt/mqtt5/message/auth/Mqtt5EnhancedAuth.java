package com.hivemq.client.mqtt.mqtt5.message.auth;

import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5EnhancedAuth {
    Optional<ByteBuffer> getData();

    MqttUtf8String getMethod();
}
