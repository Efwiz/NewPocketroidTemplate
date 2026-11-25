package com.hivemq.client.internal.mqtt;

import com.hivemq.client.mqtt.MqttWebSocketConfig;

/* JADX INFO: loaded from: classes10.dex */
public class MqttWebSocketConfigImpl implements MqttWebSocketConfig {
    static final MqttWebSocketConfigImpl DEFAULT = new MqttWebSocketConfigImpl("", "", MqttWebSocketConfig.DEFAULT_MQTT_SUBPROTOCOL, 10000);
    private final int handshakeTimeoutMs;
    private final String queryString;
    private final String serverPath;
    private final String subprotocol;

    MqttWebSocketConfigImpl(String str, String str2, String str3, int i) {
        this.serverPath = str;
        this.queryString = str2;
        this.subprotocol = str3;
        this.handshakeTimeoutMs = i;
    }

    @Override // com.hivemq.client.mqtt.MqttWebSocketConfig
    public String getServerPath() {
        return this.serverPath;
    }

    @Override // com.hivemq.client.mqtt.MqttWebSocketConfig
    public String getQueryString() {
        return this.queryString;
    }

    @Override // com.hivemq.client.mqtt.MqttWebSocketConfig
    public String getSubprotocol() {
        return this.subprotocol;
    }

    @Override // com.hivemq.client.mqtt.MqttWebSocketConfig
    public int getHandshakeTimeoutMs() {
        return this.handshakeTimeoutMs;
    }

    @Override // com.hivemq.client.mqtt.MqttWebSocketConfig
    public MqttWebSocketConfigImplBuilder.Default extend() {
        return new MqttWebSocketConfigImplBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttWebSocketConfigImpl)) {
            return false;
        }
        MqttWebSocketConfigImpl mqttWebSocketConfigImpl = (MqttWebSocketConfigImpl) obj;
        return this.serverPath.equals(mqttWebSocketConfigImpl.serverPath) && this.queryString.equals(mqttWebSocketConfigImpl.queryString) && this.subprotocol.equals(mqttWebSocketConfigImpl.subprotocol) && this.handshakeTimeoutMs == mqttWebSocketConfigImpl.handshakeTimeoutMs;
    }

    public int hashCode() {
        return (((((this.serverPath.hashCode() * 31) + this.queryString.hashCode()) * 31) + this.subprotocol.hashCode()) * 31) + Integer.hashCode(this.handshakeTimeoutMs);
    }
}
