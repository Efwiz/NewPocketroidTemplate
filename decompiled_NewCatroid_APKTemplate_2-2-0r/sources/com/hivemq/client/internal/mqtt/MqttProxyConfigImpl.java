package com.hivemq.client.internal.mqtt;

import com.hivemq.client.mqtt.MqttProxyConfig;
import com.hivemq.client.mqtt.MqttProxyProtocol;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class MqttProxyConfigImpl implements MqttProxyConfig {
    private final InetSocketAddress address;
    private final int handshakeTimeoutMs;
    private final String password;
    private final MqttProxyProtocol protocol;
    private final String username;

    MqttProxyConfigImpl(MqttProxyProtocol mqttProxyProtocol, InetSocketAddress inetSocketAddress, String str, String str2, int i) {
        this.protocol = mqttProxyProtocol;
        this.address = inetSocketAddress;
        this.username = str;
        this.password = str2;
        this.handshakeTimeoutMs = i;
    }

    @Override // com.hivemq.client.mqtt.MqttProxyConfig
    public MqttProxyProtocol getProtocol() {
        return this.protocol;
    }

    @Override // com.hivemq.client.mqtt.MqttProxyConfig
    public InetSocketAddress getAddress() {
        return this.address;
    }

    @Override // com.hivemq.client.mqtt.MqttProxyConfig
    public Optional<String> getUsername() {
        return Optional.ofNullable(this.username);
    }

    public String getRawUsername() {
        return this.username;
    }

    @Override // com.hivemq.client.mqtt.MqttProxyConfig
    public Optional<String> getPassword() {
        return Optional.ofNullable(this.password);
    }

    public String getRawPassword() {
        return this.password;
    }

    @Override // com.hivemq.client.mqtt.MqttProxyConfig
    public int getHandshakeTimeoutMs() {
        return this.handshakeTimeoutMs;
    }

    @Override // com.hivemq.client.mqtt.MqttProxyConfig
    public MqttProxyConfigImplBuilder.Default extend() {
        return new MqttProxyConfigImplBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttProxyConfigImpl)) {
            return false;
        }
        MqttProxyConfigImpl mqttProxyConfigImpl = (MqttProxyConfigImpl) obj;
        return this.protocol == mqttProxyConfigImpl.protocol && this.address.equals(mqttProxyConfigImpl.address) && Objects.equals(this.username, mqttProxyConfigImpl.username) && Objects.equals(this.password, mqttProxyConfigImpl.password) && this.handshakeTimeoutMs == mqttProxyConfigImpl.handshakeTimeoutMs;
    }

    public int hashCode() {
        return (((((((this.protocol.hashCode() * 31) + this.address.hashCode()) * 31) + Objects.hashCode(this.username)) * 31) + Objects.hashCode(this.password)) * 31) + Integer.hashCode(this.handshakeTimeoutMs);
    }
}
