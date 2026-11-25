package com.hivemq.client.internal.mqtt.message.subscribe.suback;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck;
import com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAckReasonCode;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MqttSubAck extends MqttMessageWithUserProperties.WithReason.WithCodesAndId<Mqtt5SubAckReasonCode> implements Mqtt5SubAck {
    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck
    public /* bridge */ /* synthetic */ List getReasonCodes() {
        return super.getReasonCodes();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.subscribe.suback.Mqtt5SubAck
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttSubAck(int i, ImmutableList<Mqtt5SubAckReasonCode> immutableList, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(i, immutableList, mqttUtf8StringImpl, mqttUserPropertiesImpl);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCodesAndId, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "reasonCodes=" + getReasonCodes() + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttSubAck{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttSubAck) {
            return partialEquals((MqttMessageWithUserProperties.WithReason.WithCodesAndId) obj);
        }
        return false;
    }

    public int hashCode() {
        return partialHashCode();
    }
}
