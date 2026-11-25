package com.hivemq.client.internal.mqtt.message.connect;

import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions;
import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions;

/* JADX INFO: loaded from: classes10.dex */
public class MqttConnectRestrictions implements Mqtt5ConnectRestrictions, Mqtt3ConnectRestrictions {
    public static final MqttConnectRestrictions DEFAULT = new MqttConnectRestrictions(65535, 65535, 268435460, 268435460, 0, 16, true, false);
    private final int maximumPacketSize;
    private final int receiveMaximum;
    private final boolean requestProblemInformation;
    private final boolean requestResponseInformation;
    private final int sendMaximum;
    private final int sendMaximumPacketSize;
    private final int sendTopicAliasMaximum;
    private final int topicAliasMaximum;

    public MqttConnectRestrictions(int i, int i2, int i3, int i4, int i5, int i6, boolean z, boolean z2) {
        this.receiveMaximum = i;
        this.sendMaximum = i2;
        this.maximumPacketSize = i3;
        this.sendMaximumPacketSize = i4;
        this.topicAliasMaximum = i5;
        this.sendTopicAliasMaximum = i6;
        this.requestProblemInformation = z;
        this.requestResponseInformation = z2;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions
    public int getReceiveMaximum() {
        return this.receiveMaximum;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions, com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions
    public int getSendMaximum() {
        return this.sendMaximum;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions
    public int getMaximumPacketSize() {
        return this.maximumPacketSize;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions, com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions
    public int getSendMaximumPacketSize() {
        return this.sendMaximumPacketSize;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions
    public int getTopicAliasMaximum() {
        return this.topicAliasMaximum;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions
    public int getSendTopicAliasMaximum() {
        return this.sendTopicAliasMaximum;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions
    public boolean isRequestProblemInformation() {
        return this.requestProblemInformation;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions
    public boolean isRequestResponseInformation() {
        return this.requestResponseInformation;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictions, com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictions
    public MqttConnectRestrictionsBuilder.Default extend() {
        return new MqttConnectRestrictionsBuilder.Default(this);
    }

    private String toAttributeString() {
        return "receiveMaximum=" + this.receiveMaximum + ", sendMaximum=" + this.sendMaximum + ", maximumPacketSize=" + this.maximumPacketSize + ", sendMaximumPacketSize=" + this.sendMaximumPacketSize + ", topicAliasMaximum=" + this.topicAliasMaximum + ", sendTopicAliasMaximum=" + this.sendTopicAliasMaximum + ", requestProblemInformation=" + this.requestProblemInformation + ", requestResponseInformation=" + this.requestResponseInformation;
    }

    public String toString() {
        return "MqttConnectRestrictions{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttConnectRestrictions)) {
            return false;
        }
        MqttConnectRestrictions mqttConnectRestrictions = (MqttConnectRestrictions) obj;
        return this.receiveMaximum == mqttConnectRestrictions.receiveMaximum && this.sendMaximum == mqttConnectRestrictions.sendMaximum && this.maximumPacketSize == mqttConnectRestrictions.maximumPacketSize && this.sendMaximumPacketSize == mqttConnectRestrictions.sendMaximumPacketSize && this.topicAliasMaximum == mqttConnectRestrictions.topicAliasMaximum && this.sendTopicAliasMaximum == mqttConnectRestrictions.sendTopicAliasMaximum && this.requestProblemInformation == mqttConnectRestrictions.requestProblemInformation && this.requestResponseInformation == mqttConnectRestrictions.requestResponseInformation;
    }

    public int hashCode() {
        return (((((((((((((this.receiveMaximum * 31) + this.sendMaximum) * 31) + this.maximumPacketSize) * 31) + this.sendMaximumPacketSize) * 31) + this.topicAliasMaximum) * 31) + this.sendTopicAliasMaximum) * 31) + Boolean.hashCode(this.requestProblemInformation)) * 31) + Boolean.hashCode(this.requestResponseInformation);
    }
}
