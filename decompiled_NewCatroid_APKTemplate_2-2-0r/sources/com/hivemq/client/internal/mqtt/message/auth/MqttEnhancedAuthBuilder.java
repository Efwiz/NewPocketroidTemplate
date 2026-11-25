package com.hivemq.client.internal.mqtt.message.auth;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class MqttEnhancedAuthBuilder implements Mqtt5EnhancedAuthBuilder {
    private ByteBuffer data;
    private final MqttUtf8StringImpl method;

    public MqttEnhancedAuthBuilder(MqttUtf8StringImpl mqttUtf8StringImpl) {
        Checks.notNull(mqttUtf8StringImpl, "Method");
        this.method = mqttUtf8StringImpl;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder
    public MqttEnhancedAuthBuilder data(byte[] bArr) {
        this.data = MqttChecks.binaryDataOrNull(bArr, "Auth data");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuthBuilder
    public MqttEnhancedAuthBuilder data(ByteBuffer byteBuffer) {
        this.data = MqttChecks.binaryDataOrNull(byteBuffer, "Auth data");
        return this;
    }

    public MqttEnhancedAuth build() {
        return new MqttEnhancedAuth(this.method, this.data);
    }
}
