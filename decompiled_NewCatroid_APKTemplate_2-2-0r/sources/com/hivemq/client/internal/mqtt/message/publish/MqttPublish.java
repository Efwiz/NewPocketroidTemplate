package com.hivemq.client.internal.mqtt.message.publish;

import com.hivemq.client.internal.checkpoint.Confirmable;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUtf8StringImpl;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttTopicAliasMapping;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.internal.util.ByteBufferUtil;
import com.hivemq.client.internal.util.StringUtil;
import com.hivemq.client.internal.util.collections.ImmutableIntList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PayloadFormatIndicator;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPublish extends MqttMessageWithUserProperties implements Mqtt5Publish {
    public static final long NO_MESSAGE_EXPIRY = Long.MAX_VALUE;
    private final Confirmable confirmable;
    private final MqttUtf8StringImpl contentType;
    private final ByteBuffer correlationData;
    private final long messageExpiryInterval;
    private final ByteBuffer payload;
    private final Mqtt5PayloadFormatIndicator payloadFormatIndicator;
    private final MqttQos qos;
    private final MqttTopicImpl responseTopic;
    private final boolean retain;
    private final MqttTopicImpl topic;

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public /* bridge */ /* synthetic */ Mqtt5UserProperties getUserProperties() {
        return super.getUserProperties();
    }

    public MqttPublish(MqttTopicImpl mqttTopicImpl, ByteBuffer byteBuffer, MqttQos mqttQos, boolean z, long j, Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator, MqttUtf8StringImpl mqttUtf8StringImpl, MqttTopicImpl mqttTopicImpl2, ByteBuffer byteBuffer2, MqttUserPropertiesImpl mqttUserPropertiesImpl, Confirmable confirmable) {
        super(mqttUserPropertiesImpl);
        this.topic = mqttTopicImpl;
        this.payload = byteBuffer;
        this.qos = mqttQos;
        this.retain = z;
        this.messageExpiryInterval = j;
        this.payloadFormatIndicator = mqtt5PayloadFormatIndicator;
        this.contentType = mqttUtf8StringImpl;
        this.responseTopic = mqttTopicImpl2;
        this.correlationData = byteBuffer2;
        this.confirmable = confirmable;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public MqttTopicImpl getTopic() {
        return this.topic;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public Optional<ByteBuffer> getPayload() {
        return ByteBufferUtil.optionalReadOnly(this.payload);
    }

    public ByteBuffer getRawPayload() {
        return this.payload;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public byte[] getPayloadAsBytes() {
        return ByteBufferUtil.copyBytes(this.payload);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public MqttQos getQos() {
        return this.qos;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public boolean isRetain() {
        return this.retain;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public OptionalLong getMessageExpiryInterval() {
        long j = this.messageExpiryInterval;
        return j == Long.MAX_VALUE ? OptionalLong.empty() : OptionalLong.of(j);
    }

    public long getRawMessageExpiryInterval() {
        return this.messageExpiryInterval;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public Optional<Mqtt5PayloadFormatIndicator> getPayloadFormatIndicator() {
        return Optional.ofNullable(this.payloadFormatIndicator);
    }

    public Mqtt5PayloadFormatIndicator getRawPayloadFormatIndicator() {
        return this.payloadFormatIndicator;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public Optional<MqttUtf8String> getContentType() {
        return Optional.ofNullable(this.contentType);
    }

    public MqttUtf8StringImpl getRawContentType() {
        return this.contentType;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public Optional<MqttTopic> getResponseTopic() {
        return Optional.ofNullable(this.responseTopic);
    }

    public MqttTopicImpl getRawResponseTopic() {
        return this.responseTopic;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public Optional<ByteBuffer> getCorrelationData() {
        return ByteBufferUtil.optionalReadOnly(this.correlationData);
    }

    public ByteBuffer getRawCorrelationData() {
        return this.correlationData;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public void acknowledge() {
        Confirmable confirmable = this.confirmable;
        if (confirmable == null) {
            throw new UnsupportedOperationException("A publish must not be acknowledged if manual acknowledgement is not enabled");
        }
        if (!confirmable.confirm()) {
            throw new IllegalStateException("A publish must not be acknowledged more than once");
        }
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public MqttWillPublish asWill() {
        return new MqttPublishBuilder.WillDefault(this).build();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish
    public MqttPublishBuilder.Default extend() {
        return new MqttPublishBuilder.Default(this);
    }

    public MqttStatefulPublish createStateful(int i, boolean z, int i2, ImmutableIntList immutableIntList) {
        return new MqttStatefulPublish(this, i, z, i2, immutableIntList);
    }

    public MqttStatefulPublish createStateful(int i, boolean z, MqttTopicAliasMapping mqttTopicAliasMapping) {
        return createStateful(i, z, mqttTopicAliasMapping == null ? 0 : mqttTopicAliasMapping.onPublish(this.topic), MqttStatefulPublish.DEFAULT_NO_SUBSCRIPTION_IDENTIFIERS);
    }

    public MqttPublish withConfirmable(Confirmable confirmable) {
        return new MqttPublish(this.topic, this.payload, this.qos, this.retain, this.messageExpiryInterval, this.payloadFormatIndicator, this.contentType, this.responseTopic, this.correlationData, getUserProperties(), confirmable);
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties
    protected String toAttributeString() {
        return "topic=" + this.topic + (this.payload == null ? "" : ", payload=" + this.payload.remaining() + "byte") + ", qos=" + this.qos + ", retain=" + this.retain + (this.messageExpiryInterval == Long.MAX_VALUE ? "" : ", messageExpiryInterval=" + this.messageExpiryInterval) + (this.payloadFormatIndicator == null ? "" : ", payloadFormatIndicator=" + this.payloadFormatIndicator) + (this.contentType == null ? "" : ", contentType=" + this.contentType) + (this.responseTopic == null ? "" : ", responseTopic=" + this.responseTopic) + (this.correlationData != null ? ", correlationData=" + this.correlationData.remaining() + "byte" : "") + StringUtil.prepend(", ", super.toAttributeString());
    }

    public String toString() {
        return "MqttPublish{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttPublish)) {
            return false;
        }
        MqttPublish mqttPublish = (MqttPublish) obj;
        return mqttPublish.canEqual(this) && partialEquals(mqttPublish) && this.topic.equals(mqttPublish.topic) && Objects.equals(this.payload, mqttPublish.payload) && this.qos == mqttPublish.qos && this.retain == mqttPublish.retain && this.messageExpiryInterval == mqttPublish.messageExpiryInterval && this.payloadFormatIndicator == mqttPublish.payloadFormatIndicator && Objects.equals(this.contentType, mqttPublish.contentType) && Objects.equals(this.responseTopic, mqttPublish.responseTopic) && Objects.equals(this.correlationData, mqttPublish.correlationData);
    }

    protected boolean canEqual(Object obj) {
        return obj instanceof MqttPublish;
    }

    public int hashCode() {
        return (((((((((((((((((partialHashCode() * 31) + this.topic.hashCode()) * 31) + Objects.hashCode(this.payload)) * 31) + this.qos.hashCode()) * 31) + Boolean.hashCode(this.retain)) * 31) + Long.hashCode(this.messageExpiryInterval)) * 31) + Objects.hashCode(this.payloadFormatIndicator)) * 31) + Objects.hashCode(this.contentType)) * 31) + Objects.hashCode(this.responseTopic)) * 31) + Objects.hashCode(this.correlationData);
    }
}
