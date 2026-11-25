package com.hivemq.client.internal.mqtt.message.publish.pubcomp;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubComp;
import com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubCompReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPubComp extends MqttMessageWithUserProperties.WithReason.WithCode.WithId<Mqtt5PubCompReasonCode> implements Mqtt5PubComp {
    public static final Mqtt5PubCompReasonCode DEFAULT_REASON_CODE = Mqtt5PubCompReasonCode.SUCCESS;

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCode, com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5Auth
    public /* bridge */ /* synthetic */ Mqtt5PubCompReasonCode getReasonCode() {
        return (Mqtt5PubCompReasonCode) super.getReasonCode();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.pubcomp.Mqtt5PubComp
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttPubComp(int i, Mqtt5PubCompReasonCode mqtt5PubCompReasonCode, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(i, mqtt5PubCompReasonCode, mqttUtf8StringImpl, mqttUserPropertiesImpl);
    }

    public String toString() {
        return "MqttPubComp{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttPubComp) {
            return partialEquals((MqttMessageWithUserProperties.WithReason.WithCode) obj);
        }
        return false;
    }

    public int hashCode() {
        return partialHashCode();
    }
}
