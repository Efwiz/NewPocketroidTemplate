package com.hivemq.client.mqtt.mqtt5.message.publish;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicBuilder;
import com.hivemq.client.mqtt.datatypes.MqttUtf8String;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserProperties;
import com.hivemq.client.mqtt.mqtt5.datatypes.Mqtt5UserPropertiesBuilder;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishBuilderBase.Complete;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5PublishBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt5PublishBuilderBase<C> {
        C contentType(MqttUtf8String mqttUtf8String);

        C contentType(String str);

        C correlationData(ByteBuffer byteBuffer);

        C correlationData(byte[] bArr);

        C messageExpiryInterval(long j);

        C noMessageExpiry();

        C payload(ByteBuffer byteBuffer);

        C payload(byte[] bArr);

        C payloadFormatIndicator(Mqtt5PayloadFormatIndicator mqtt5PayloadFormatIndicator);

        C qos(MqttQos mqttQos);

        MqttTopicBuilder.Nested<? extends C> responseTopic();

        C responseTopic(MqttTopic mqttTopic);

        C responseTopic(String str);

        C retain(boolean z);

        Mqtt5UserPropertiesBuilder.Nested<? extends C> userProperties();

        C userProperties(Mqtt5UserProperties mqtt5UserProperties);
    }

    public interface WillBase<C extends Complete<C>> extends Mqtt5PublishBuilderBase<C> {

        public interface Complete<C extends Complete<C>> extends Complete<C>, WillBase<C> {
            C delayInterval(long j);
        }
    }

    MqttTopicBuilder.Nested<? extends C> topic();

    C topic(MqttTopic mqttTopic);

    C topic(String str);
}
