package com.hivemq.client.mqtt.mqtt5.message.auth;

import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5EnhancedAuthBuilder {
    Mqtt5EnhancedAuthBuilder data(ByteBuffer byteBuffer);

    Mqtt5EnhancedAuthBuilder data(byte[] bArr);
}
