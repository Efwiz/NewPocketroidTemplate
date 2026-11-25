package com.hivemq.client.internal.mqtt.message.publish.pubrec;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRec;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRecReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubRec extends MqttMessageWithUserProperties.WithReason.WithCode.WithId<Mqtt5PubRecReasonCode> implements Mqtt5PubRec {
    public static final Mqtt5PubRecReasonCode DEFAULT_REASON_CODE = Mqtt5PubRecReasonCode.SUCCESS;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5PubRecReasonCode getReasonCode() {
        return (Mqtt5PubRecReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrec.Mqtt5PubRec
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttPubRec(int i, Mqtt5PubRecReasonCode mqtt5PubRecReasonCode, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(i, mqtt5PubRecReasonCode, mqttUtf8StringImpl, mqttUserPropertiesImpl);
    }

    public String toString() {
        return "MqttPubRec{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttPubRec) {
            return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) obj);
        }
        return false;
    }

    public int hashCode() {
        return partialHashCode();
    }
}
