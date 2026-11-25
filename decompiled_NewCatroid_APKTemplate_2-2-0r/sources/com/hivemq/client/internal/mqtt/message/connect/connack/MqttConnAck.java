package com.hivemq.client.internal.mqtt.message.connect.connack;

import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuth;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

/* JADX INFO: loaded from: classes10.dex */
public class MqttConnAck extends MqttMessageWithUserProperties.WithReason.WithCode<Mqtt5ConnAckReasonCode> implements Mqtt5ConnAck {
    public static final int KEEP_ALIVE_FROM_CONNECT = -1;
    public static final long SESSION_EXPIRY_INTERVAL_FROM_CONNECT = -1;
    private final MqttClientIdentifierImpl assignedClientIdentifier;
    private final Mqtt5EnhancedAuth enhancedAuth;
    private final MqttUtf8StringImpl responseInformation;
    private final MqttConnAckRestrictions restrictions;
    private final int serverKeepAlive;
    private final MqttUtf8StringImpl serverReference;
    private final long sessionExpiryInterval;
    private final boolean sessionPresent;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5ConnAckReasonCode getReasonCode() {
        return (Mqtt5ConnAckReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttConnAck(Mqtt5ConnAckReasonCode mqtt5ConnAckReasonCode, boolean z, long j, int i, MqttClientIdentifierImpl mqttClientIdentifierImpl, Mqtt5EnhancedAuth mqtt5EnhancedAuth, MqttConnAckRestrictions mqttConnAckRestrictions, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUtf8StringImpl mqttUtf8StringImpl2, MqttUtf8StringImpl mqttUtf8StringImpl3, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(mqtt5ConnAckReasonCode, mqttUtf8StringImpl3, mqttUserPropertiesImpl);
        this.sessionPresent = z;
        this.sessionExpiryInterval = j;
        this.serverKeepAlive = i;
        this.assignedClientIdentifier = mqttClientIdentifierImpl;
        this.enhancedAuth = mqtt5EnhancedAuth;
        this.restrictions = mqttConnAckRestrictions;
        this.responseInformation = mqttUtf8StringImpl;
        this.serverReference = mqttUtf8StringImpl2;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public boolean isSessionPresent() {
        return this.sessionPresent;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public OptionalLong getSessionExpiryInterval() {
        long j = this.sessionExpiryInterval;
        return j == -1 ? OptionalLong.empty() : OptionalLong.of(j);
    }

    public long getRawSessionExpiryInterval() {
        return this.sessionExpiryInterval;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public OptionalInt getServerKeepAlive() {
        int i = this.serverKeepAlive;
        return i == -1 ? OptionalInt.empty() : OptionalInt.of(i);
    }

    public int getRawServerKeepAlive() {
        return this.serverKeepAlive;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public Optional<MqttClientIdentifier> getAssignedClientIdentifier() {
        return Optional.ofNullable(this.assignedClientIdentifier);
    }

    public MqttClientIdentifierImpl getRawAssignedClientIdentifier() {
        return this.assignedClientIdentifier;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public Optional<Mqtt5EnhancedAuth> getEnhancedAuth() {
        return Optional.ofNullable(this.enhancedAuth);
    }

    public Mqtt5EnhancedAuth getRawEnhancedAuth() {
        return this.enhancedAuth;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public MqttConnAckRestrictions getRestrictions() {
        return this.restrictions;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public Optional<MqttUtf8String> getResponseInformation() {
        return Optional.ofNullable(this.responseInformation);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck
    public Optional<MqttUtf8String> getServerReference() {
        return Optional.ofNullable(this.serverReference);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "reasonCode=" + getReasonCode() + ", sessionPresent=" + this.sessionPresent + (this.sessionExpiryInterval == -1 ? "" : ", sessionExpiryInterval=" + this.sessionExpiryInterval) + (((long) this.serverKeepAlive) == -1 ? "" : ", serverKeepAlive=" + this.serverKeepAlive) + (this.assignedClientIdentifier == null ? "" : ", assignedClientIdentifier=" + this.assignedClientIdentifier) + (this.enhancedAuth == null ? "" : ", enhancedAuth=" + this.enhancedAuth) + (this.restrictions == MqttConnAckRestrictions.DEFAULT ? "" : ", restrictions=" + this.restrictions) + (this.responseInformation == null ? "" : ", responseInformation=" + this.responseInformation) + (this.serverReference != null ? ", serverReference=" + this.serverReference : "") + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttConnAck{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttConnAck)) {
            return false;
        }
        MqttConnAck mqttConnAck = (MqttConnAck) obj;
        return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) mqttConnAck) && this.sessionPresent == mqttConnAck.sessionPresent && this.sessionExpiryInterval == mqttConnAck.sessionExpiryInterval && this.serverKeepAlive == mqttConnAck.serverKeepAlive && Objects.equals(this.assignedClientIdentifier, mqttConnAck.assignedClientIdentifier) && Objects.equals(this.enhancedAuth, mqttConnAck.enhancedAuth) && this.restrictions.equals(mqttConnAck.restrictions) && Objects.equals(this.responseInformation, mqttConnAck.responseInformation) && Objects.equals(this.serverReference, mqttConnAck.serverReference);
    }

    public int hashCode() {
        return (((((((((((((((partialHashCode() * 31) + Boolean.hashCode(this.sessionPresent)) * 31) + Long.hashCode(this.sessionExpiryInterval)) * 31) + this.serverKeepAlive) * 31) + Objects.hashCode(this.assignedClientIdentifier)) * 31) + Objects.hashCode(this.enhancedAuth)) * 31) + this.restrictions.hashCode()) * 31) + Objects.hashCode(this.responseInformation)) * 31) + Objects.hashCode(this.serverReference);
    }
}
