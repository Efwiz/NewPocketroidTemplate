package com.hivemq.client.internal.mqtt.message.unsubscribe.unsuback;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck;
import com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAckReasonCode;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public class MqttUnsubAck extends MqttMessageWithUserProperties.WithReason.WithCodesAndId<Mqtt5UnsubAckReasonCode> implements Mqtt5UnsubAck {
    @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck
    public /* bridge */ /* synthetic */ List getReasonCodes() {
        return super.getReasonCodes();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.unsubscribe.unsuback.Mqtt5UnsubAck
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttUnsubAck(int i, ImmutableList<Mqtt5UnsubAckReasonCode> immutableList, MqttUtf8StringImpl mqttUtf8StringImpl, MqttUserPropertiesImpl mqttUserPropertiesImpl) {
        super(i, immutableList, mqttUtf8StringImpl, mqttUserPropertiesImpl);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason.WithCodesAndId, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties.WithReason, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "reasonCodes=" + getReasonCodes() + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttUnsubAck{" + toAttributeString() + "}";
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof MqttUnsubAck) {
            return partialEquals((MqttMessageWithUserProperties.WithReason.WithCodesAndId) obj);
        }
        return false;
    }

    public int hashCode() {
        return partialHashCode();
    }
}
