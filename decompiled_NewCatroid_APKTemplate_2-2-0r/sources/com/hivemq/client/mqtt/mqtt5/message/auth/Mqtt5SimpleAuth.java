package com.hivemq.client.mqtt.mqtt5.message.auth;

import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuthBuilder;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5SimpleAuth {
    Optional<ByteBuffer> getPassword();

    Optional<MqttUtf8String> getUsername();

    static Mqtt5SimpleAuthBuilder builder() {
        return new MqttSimpleAuthBuilder.Default();
    }
}
