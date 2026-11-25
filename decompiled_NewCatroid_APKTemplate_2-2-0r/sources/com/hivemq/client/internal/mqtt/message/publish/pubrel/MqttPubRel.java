package com.hivemq.client.internal.mqtt.message.publish.pubrel;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRel;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRelReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubRel extends MqttMessageWithUserProperties.WithReason.WithCode.WithId<Mqtt5PubRelReasonCode> implements Mqtt5PubRel {
    public static final Mqtt5PubRelReasonCode DEFAULT_REASON_CODE = Mqtt5PubRelReasonCode.SUCCESS;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5PubRelReasonCode getReasonCode() {
        return (Mqtt5PubRelReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubrel.Mqtt5PubRel
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttPubRel(int i, Mqtt5PubRelReasonCode mqtt5PubRelReasonCode, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(i, mqtt5PubRelReasonCode, mqttUtf8StringImpl, mqttUserPropertiesImpl);
    }

    public String toString() {
        return "MqttPubRel{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttPubRel) {
            return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) obj);
        }
        return false;
    }

    public int hashCode() {
        return partialHashCode();
    }
}
