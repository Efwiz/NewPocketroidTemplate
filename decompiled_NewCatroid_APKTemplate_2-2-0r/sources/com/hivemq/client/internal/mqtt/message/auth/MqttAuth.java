package com.hivemq.client.internal.mqtt.message.auth;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5AuthReasonCode;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class MqttAuth extends MqttMessageWithUserProperties.WithReason.WithCode<Mqtt5AuthReasonCode> implements Mqtt5Auth {
    private final ByteBuffer data;
    private final MqttUtf8StringImpl method;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5AuthReasonCode getReasonCode() {
        return (Mqtt5AuthReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttAuth(Mqtt5AuthReasonCode mqtt5AuthReasonCode, MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuffer byteBuffer, MqttUtf8StringImpl mqttUtf8StringImpl2, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(mqtt5AuthReasonCode, mqttUtf8StringImpl2, mqttUserPropertiesImpl);
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

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "reasonCode= " + getReasonCode() + ", method=" + this.method + (this.data == null ? "" : ", data=" + this.data.remaining() + "byte") + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttAuth{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttAuth)) {
            return false;
        }
        MqttAuth mqttAuth = (MqttAuth) obj;
        return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) mqttAuth) && this.method.equals(mqttAuth.method) && Objects.equals(this.data, mqttAuth.data);
    }

    public int hashCode() {
        return (((partialHashCode() * 31) + this.method.hashCode()) * 31) + Objects.hashCode(this.data);
    }
}
