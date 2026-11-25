package com.hivemq.client.mqtt.mqtt5.message.disconnect;

import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnectBuilder;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import java.util.Optional;
import java.util.OptionalLong;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Disconnect extends Mqtt5Message {
    public static final Mqtt5DisconnectReasonCode DEFAULT_REASON_CODE = Mqtt5DisconnectReasonCode.NORMAL_DISCONNECTION;

    Mqtt5DisconnectBuilder extend();

    Mqtt5DisconnectReasonCode getReasonCode();

    Optional<MqttUtf8String> getReasonString();

    Optional<MqttUtf8String> getServerReference();

    OptionalLong getSessionExpiryInterval();

    Mqtt5UserProperties getUserProperties();

    static Mqtt5DisconnectBuilder builder() {
        return new MqttDisconnectBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.DISCONNECT;
    }
}
