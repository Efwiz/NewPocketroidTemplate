package com.hivemq.client.internal.mqtt.message.publish.puback;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAck;
import com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAckReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubAck extends MqttMessageWithUserProperties.WithReason.WithCode.WithId<Mqtt5PubAckReasonCode> implements Mqtt5PubAck {
    public static final Mqtt5PubAckReasonCode DEFAULT_REASON_CODE = Mqtt5PubAckReasonCode.SUCCESS;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5PubAckReasonCode getReasonCode() {
        return (Mqtt5PubAckReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.puback.Mqtt5PubAck
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttPubAck(int i, Mqtt5PubAckReasonCode mqtt5PubAckReasonCode, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(i, mqtt5PubAckReasonCode, mqttUtf8StringImpl, mqttUserPropertiesImpl);
    }

    public String toString() {
        return "MqttPubAck{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttPubAck) {
            return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) obj);
        }
        return false;
    }

    public int hashCode() {
        return partialHashCode();
    }
}
