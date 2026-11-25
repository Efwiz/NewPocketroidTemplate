package com.hivemq.client.mqtt.mqtt5.message.connect.connack;

import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5EnhancedAuth;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.OptionalLong;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnAck extends Mqtt5Message {
    Optional<MqttClientIdentifier> getAssignedClientIdentifier();

    Optional<Mqtt5EnhancedAuth> getEnhancedAuth();

    Mqtt5ConnAckReasonCode getReasonCode();

    Optional<MqttUtf8String> getReasonString();

    Optional<MqttUtf8String> getResponseInformation();

    Mqtt5ConnAckRestrictions getRestrictions();

    OptionalInt getServerKeepAlive();

    Optional<MqttUtf8String> getServerReference();

    OptionalLong getSessionExpiryInterval();

    Mqtt5UserProperties getUserProperties();

    boolean isSessionPresent();

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.CONNACK;
    }
}
