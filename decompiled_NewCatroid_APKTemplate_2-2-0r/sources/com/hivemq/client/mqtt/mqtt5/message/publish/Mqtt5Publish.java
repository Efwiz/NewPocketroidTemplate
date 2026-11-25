package com.hivemq.client.mqtt.mqtt5.message.publish;

import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import java.nio.ByteBuffer;
import java.util.Optional;
import java.util.OptionalLong;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5Publish extends Mqtt5Message {
    public static final MqttQos DEFAULT_QOS = MqttQos.AT_MOST_ONCE;

    void acknowledge();

    Mqtt5WillPublish asWill();

    Mqtt5PublishBuilder.Complete extend();

    Optional<MqttUtf8String> getContentType();

    Optional<ByteBuffer> getCorrelationData();

    OptionalLong getMessageExpiryInterval();

    Optional<ByteBuffer> getPayload();

    byte[] getPayloadAsBytes();

    Optional<Mqtt5PayloadFormatIndicator> getPayloadFormatIndicator();

    MqttQos getQos();

    Optional<MqttTopic> getResponseTopic();

    MqttTopic getTopic();

    Mqtt5UserProperties getUserProperties();

    boolean isRetain();

    static Mqtt5PublishBuilder builder() {
        return new MqttPublishBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    default Mqtt5MessageType getType() {
        return Mqtt5MessageType.PUBLISH;
    }
}
