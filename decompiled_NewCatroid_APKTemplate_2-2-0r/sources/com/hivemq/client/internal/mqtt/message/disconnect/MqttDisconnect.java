package com.hivemq.client.internal.mqtt.message.disconnect;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

/* JADX INFO: loaded from: classes10.dex */
public class MqttDisconnect extends MqttMessageWithUserProperties.WithReason.WithCode<Mqtt5DisconnectReasonCode> implements Mqtt5Disconnect {
    public static final MqttDisconnect DEFAULT = new MqttDisconnect(DEFAULT_REASON_CODE, -1, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    public static final long SESSION_EXPIRY_INTERVAL_FROM_CONNECT = -1;
    private final MqttUtf8StringImpl serverReference;
    private final long sessionExpiryInterval;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5DisconnectReasonCode getReasonCode() {
        return (Mqtt5DisconnectReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttDisconnect(Mqtt5DisconnectReasonCode mqtt5DisconnectReasonCode, long j, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUtf8StringImpl mqttUtf8StringImpl2, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(mqtt5DisconnectReasonCode, mqttUtf8StringImpl2, mqttUserPropertiesImpl);
        this.sessionExpiryInterval = j;
        this.serverReference = mqttUtf8StringImpl;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect
    public OptionalLong getSessionExpiryInterval() {
        long j = this.sessionExpiryInterval;
        return j == -1 ? OptionalLong.empty() : OptionalLong.of(j);
    }

    public long getRawSessionExpiryInterval() {
        return this.sessionExpiryInterval;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect
    public Optional<MqttUtf8String> getServerReference() {
        return Optional.ofNullable(this.serverReference);
    }

    public MqttUtf8StringImpl getRawServerReference() {
        return this.serverReference;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect
    public MqttDisconnectBuilder.Default extend() {
        return new MqttDisconnectBuilder.Default(this);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "reasonCode=" + getReasonCode() + (this.sessionExpiryInterval == -1 ? "" : ", sessionExpiryInterval=" + this.sessionExpiryInterval) + (this.serverReference != null ? ", serverReference=" + this.serverReference : "") + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttDisconnect{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttDisconnect)) {
            return false;
        }
        MqttDisconnect mqttDisconnect = (MqttDisconnect) obj;
        return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) mqttDisconnect) && this.sessionExpiryInterval == mqttDisconnect.sessionExpiryInterval && Objects.equals(this.serverReference, mqttDisconnect.serverReference);
    }

    public int hashCode() {
        return (((partialHashCode() * 31) + Long.hashCode(this.sessionExpiryInterval)) * 31) + Objects.hashCode(this.serverReference);
    }
}
