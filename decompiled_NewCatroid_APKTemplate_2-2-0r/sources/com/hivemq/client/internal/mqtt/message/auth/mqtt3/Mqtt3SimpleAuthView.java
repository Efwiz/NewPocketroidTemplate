package com.hivemq.client.internal.mqtt.message.auth.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3SimpleAuthView implements Mqtt3SimpleAuth {
    private final MqttSimpleAuth delegate;

    private static MqttSimpleAuth delegate(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuffer byteBuffer) {
        return new MqttSimpleAuth(mqttUtf8StringImpl, byteBuffer);
    }

    static Mqtt3SimpleAuthView of(MqttUtf8StringImpl mqttUtf8StringImpl, ByteBuffer byteBuffer) {
        return new Mqtt3SimpleAuthView(delegate(mqttUtf8StringImpl, byteBuffer));
    }

    public static Mqtt3SimpleAuthView of(MqttSimpleAuth mqttSimpleAuth) {
        return new Mqtt3SimpleAuthView(mqttSimpleAuth);
    }

    private Mqtt3SimpleAuthView(MqttSimpleAuth mqttSimpleAuth) {
        this.delegate = mqttSimpleAuth;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth
    public MqttUtf8String getUsername() {
        return (MqttUtf8String) Checks.stateNotNull(this.delegate.getRawUsername(), "Username");
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth
    public Optional<ByteBuffer> getPassword() {
        return this.delegate.getPassword();
    }

    public MqttSimpleAuth getDelegate() {
        return this.delegate;
    }

    public String toString() {
        return this.delegate.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3SimpleAuthView) {
            return this.delegate.equals(((Mqtt3SimpleAuthView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
