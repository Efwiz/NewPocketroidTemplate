package com.hivemq.client.internal.mqtt.message.auth;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuth;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class MqttEnhancedAuth implements Mqtt5EnhancedAuth {
    private final ByteBuffer data;
    private final MqttUtf8StringImpl method;

    public MqttEnhancedAuth(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuffer byteBuffer) {
        this.method = mqttUtf8StringImpl;
        this.data = byteBuffer;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuth
    public MqttUtf8StringImpl getMethod() {
        return this.method;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuth
    public Optional<ByteBuffer> getData() {
        return ByteBufferUtil.optionalReadOnly(this.data);
    }

    public ByteBuffer getRawData() {
        return this.data;
    }

    private String toAttributeString() {
        return "method=" + this.method + (this.data == null ? "" : ", data=" + this.data.remaining() + "byte");
    }

    public String toString() {
        return "MqttEnhancedAuth{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttEnhancedAuth)) {
            return false;
        }
        MqttEnhancedAuth mqttEnhancedAuth = (MqttEnhancedAuth) obj;
        return this.method.equals(mqttEnhancedAuth.method) && Objects.equals(this.data, mqttEnhancedAuth.data);
    }

    public int hashCode() {
        return (this.method.hashCode() * 31) + Objects.hashCode(this.data);
    }
}
