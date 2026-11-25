package com.hivemq.client.mqtt.mqtt3.message.publish;

import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3Publish extends Mqtt3Message {
    public static final MqttQos DEFAULT_QOS = MqttQos.AT_MOST_ONCE;

    void acknowledge();

    Mqtt3PublishBuilder.Complete extend();

    Optional<ByteBuffer> getPayload();

    byte[] getPayloadAsBytes();

    MqttQos getQos();

    MqttTopic getTopic();

    boolean isRetain();

    static Mqtt3PublishBuilder builder() {
        return new Mqtt3PublishViewBuilder.Default();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.Mqtt3Message
    default Mqtt3MessageType getType() {
        return Mqtt3MessageType.PUBLISH;
    }
}
