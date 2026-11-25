package com.hivemq.client.internal.mqtt.message.auth;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import org.eclipse.jgit.transport.AmazonS3;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSimpleAuth implements Mqtt5SimpleAuth {
    private final ByteBuffer password;
    private final MqttUtf8StringImpl username;

    public MqttSimpleAuth(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuffer byteBuffer) {
        this.username = mqttUtf8StringImpl;
        this.password = byteBuffer;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth
    public Optional<MqttUtf8String> getUsername() {
        return Optional.ofNullable(this.username);
    }

    public MqttUtf8StringImpl getRawUsername() {
        return this.username;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth
    public Optional<ByteBuffer> getPassword() {
        return ByteBufferUtil.optionalReadOnly(this.password);
    }

    public ByteBuffer getRawPassword() {
        return this.password;
    }

    private String toAttributeString() {
        if (this.username == null) {
            return this.password == null ? "" : AmazonS3.Keys.PASSWORD;
        }
        return this.password == null ? "username" : "username and password";
    }

    public String toString() {
        return "MqttSimpleAuth{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttSimpleAuth)) {
            return false;
        }
        MqttSimpleAuth mqttSimpleAuth = (MqttSimpleAuth) obj;
        return Objects.equals(this.username, mqttSimpleAuth.username) && Objects.equals(this.password, mqttSimpleAuth.password);
    }

    public int hashCode() {
        return (Objects.hashCode(this.username) * 31) + Objects.hashCode(this.password);
    }
}
