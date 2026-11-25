package com.hivemq.client.mqtt.mqtt3.message.publish;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicBuilder;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3PublishBuilderBase.Complete;
import java.nio.ByteBuffer;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3PublishBuilderBase<C extends Complete<C>> {

    public interface Complete<C extends Complete<C>> extends Mqtt3PublishBuilderBase<C> {
        C payload(ByteBuffer byteBuffer);

        C payload(byte[] bArr);

        C qos(MqttQos mqttQos);

        C retain(boolean z);
    }

    MqttTopicBuilder.Nested<? extends C> topic();

    C topic(MqttTopic mqttTopic);

    C topic(String str);
}
