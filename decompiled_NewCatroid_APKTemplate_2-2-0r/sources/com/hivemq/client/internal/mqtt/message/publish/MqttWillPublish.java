package com.hivemq.client.internal.mqtt.message.publish;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public class MqttWillPublish extends MqttPublish implements Mqtt5WillPublish {
    private final long delayInterval;

    @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublish, com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public MqttWillPublish asWill() {
        return this;
    }

    public MqttWillPublish(MqttTopicImpl mqttTopicImpl, ByteBuffer byteBuffer, MqttQos mqttQos, boolean z, long j, Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator, MqttUtf8StringImpl mqttUtf8StringImpl, MqttTopicImpl mqttTopicImpl2, ByteBuffer byteBuffer2, MqttUserPropertiesImpl mqttUserPropertiesImpl, long j2) {
        super(mqttTopicImpl, byteBuffer, mqttQos, z, j, mqtt5PayloadFormatIndicator, mqttUtf8StringImpl, mqttTopicImpl2, byteBuffer2, mqttUserPropertiesImpl, null);
        this.delayInterval = j2;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish
    public long getDelayInterval() {
        return this.delayInterval;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish
    public MqttPublishBuilder.WillDefault extendAsWill() {
        return new MqttPublishBuilder.WillDefault(this);
    }

    @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublish, com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return super.toAttributeString() + ", delayInterval=" + this.delayInterval;
    }

    @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublish
    public String toString() {
        return "MqttWillPublish{" + toAttributeString() + '}';
    }

    @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublish
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return (obj instanceof MqttWillPublish) && super.equals(obj) && this.delayInterval == ((MqttWillPublish) obj).delayInterval;
    }

    @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublish
    protected boolean canEqual(Object obj) {
        return obj instanceof MqttWillPublish;
    }

    @Override // com.hivemq.client.internal.mqtt.message.publish.MqttPublish
    public int hashCode() {
        return (super.hashCode() * 31) + Long.hashCode(this.delayInterval);
    }
}
