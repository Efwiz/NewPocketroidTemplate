package com.hivemq.client.internal.mqtt.message.connect.connack;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions;

/* JADX INFO: loaded from: classes10.dex */
public class MqttConnAckRestrictions implements Mqtt5ConnAckRestrictions {
    public static final MqttConnAckRestrictions DEFAULT = new MqttConnAckRestrictions(65535, 268435460, 0, DEFAULT_MAXIMUM_QOS, true, true, true, true);
    private final int maximumPacketSize;
    private final MqttQos maximumQos;
    private final int receiveMaximum;
    private final boolean retainAvailable;
    private final boolean sharedSubscriptionAvailable;
    private final boolean subscriptionIdentifiersAvailable;
    private final int topicAliasMaximum;
    private final boolean wildcardSubscriptionAvailable;

    public MqttConnAckRestrictions(int i, int i2, int i3, MqttQos mqttQos, boolean z, boolean z2, boolean z3, boolean z4) {
        this.receiveMaximum = i;
        this.maximumPacketSize = i2;
        this.topicAliasMaximum = i3;
        this.maximumQos = mqttQos;
        this.retainAvailable = z;
        this.wildcardSubscriptionAvailable = z2;
        this.sharedSubscriptionAvailable = z3;
        this.subscriptionIdentifiersAvailable = z4;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public int getReceiveMaximum() {
        return this.receiveMaximum;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public int getMaximumPacketSize() {
        return this.maximumPacketSize;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public int getTopicAliasMaximum() {
        return this.topicAliasMaximum;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public MqttQos getMaximumQos() {
        return this.maximumQos;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public boolean isRetainAvailable() {
        return this.retainAvailable;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public boolean isWildcardSubscriptionAvailable() {
        return this.wildcardSubscriptionAvailable;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public boolean isSharedSubscriptionAvailable() {
        return this.sharedSubscriptionAvailable;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckRestrictions
    public boolean areSubscriptionIdentifiersAvailable() {
        return this.subscriptionIdentifiersAvailable;
    }

    private String toAttributeString() {
        return "receiveMaximum=" + this.receiveMaximum + ", maximumPacketSize=" + this.maximumPacketSize + ", topicAliasMaximum=" + this.topicAliasMaximum + ", maximumQos=" + this.maximumQos + ", retainAvailable=" + this.retainAvailable + ", wildcardSubscriptionAvailable=" + this.wildcardSubscriptionAvailable + ", sharedSubscriptionAvailable=" + this.sharedSubscriptionAvailable + ", subscriptionIdentifiersAvailable=" + this.subscriptionIdentifiersAvailable;
    }

    public String toString() {
        return "MqttConnAckRestrictions{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttConnAckRestrictions)) {
            return false;
        }
        MqttConnAckRestrictions mqttConnAckRestrictions = (MqttConnAckRestrictions) obj;
        return this.receiveMaximum == mqttConnAckRestrictions.receiveMaximum && this.maximumPacketSize == mqttConnAckRestrictions.maximumPacketSize && this.topicAliasMaximum == mqttConnAckRestrictions.topicAliasMaximum && this.maximumQos == mqttConnAckRestrictions.maximumQos && this.retainAvailable == mqttConnAckRestrictions.retainAvailable && this.wildcardSubscriptionAvailable == mqttConnAckRestrictions.wildcardSubscriptionAvailable && this.sharedSubscriptionAvailable == mqttConnAckRestrictions.sharedSubscriptionAvailable && this.subscriptionIdentifiersAvailable == mqttConnAckRestrictions.subscriptionIdentifiersAvailable;
    }

    public int hashCode() {
        return (((((((((((((this.receiveMaximum * 31) + this.maximumPacketSize) * 31) + this.topicAliasMaximum) * 31) + this.maximumQos.hashCode()) * 31) + Boolean.hashCode(this.retainAvailable)) * 31) + Boolean.hashCode(this.wildcardSubscriptionAvailable)) * 31) + Boolean.hashCode(this.sharedSubscriptionAvailable)) * 31) + Boolean.hashCode(this.subscriptionIdentifiersAvailable);
    }
}
