package com.hivemq.client.mqtt.mqtt3.message.auth;

import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthViewBuilder;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3SimpleAuth {
    Optional<ByteBuffer> getPassword();

    MqttUtf8String getUsername();

    static Mqtt3SimpleAuthBuilder builder() {
        return new Mqtt3SimpleAuthViewBuilder.Default();
    }
}
