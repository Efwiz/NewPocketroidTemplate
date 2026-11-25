package com.hivemq.client.internal.mqtt.message.publish.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import io.reactivex.functions.Function;
import java.nio.ByteBuffer;
import java.util.Optional;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3PublishView implements Mqtt3Publish {
    private final MqttPublish delegate;
    public static final Function<Mqtt5Publish, Mqtt3Publish> MAPPER = new Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView$$ExternalSyntheticLambda0
        @Override // io.reactivex.functions.Function
        public final Object apply(Object obj) {
            return Mqtt3PublishView.of((Mqtt5Publish) obj);
        }
    };
    public static final java.util.function.Function<Mqtt5Publish, Mqtt3Publish> JAVA_MAPPER = new java.util.function.Function() { // from class: com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView$$ExternalSyntheticLambda1
        @Override // java.util.function.Function
        public final Object apply(Object obj) {
            return Mqtt3PublishView.of((Mqtt5Publish) obj);
        }
    };

    public static MqttPublish delegate(MqttTopicImpl mqttTopicImpl, ByteBuffer byteBuffer, MqttQos mqttQos, boolean z) {
        return new MqttPublish(mqttTopicImpl, byteBuffer, mqttQos, z, Long.MAX_VALUE, null, null, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES, null);
    }

    public static MqttStatefulPublish statefulDelegate(MqttPublish mqttPublish, int i, boolean z) {
        return mqttPublish.createStateful(i, z, 0, MqttStatefulPublish.DEFAULT_NO_SUBSCRIPTION_IDENTIFIERS);
    }

    static Mqtt3PublishView of(MqttTopicImpl mqttTopicImpl, ByteBuffer byteBuffer, MqttQos mqttQos, boolean z) {
        return new Mqtt3PublishView(delegate(mqttTopicImpl, byteBuffer, mqttQos, z));
    }

    static Mqtt3PublishView willOf(MqttTopicImpl mqttTopicImpl, ByteBuffer byteBuffer, MqttQos mqttQos, boolean z) {
        return new Mqtt3PublishView(new MqttWillPublish(mqttTopicImpl, byteBuffer, mqttQos, z, Long.MAX_VALUE, null, null, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES, 0L));
    }

    public static Mqtt3PublishView of(Mqtt5Publish mqtt5Publish) {
        return new Mqtt3PublishView((MqttPublish) mqtt5Publish);
    }

    public static Mqtt3PublishView of(MqttPublish mqttPublish) {
        return new Mqtt3PublishView(mqttPublish);
    }

    private Mqtt3PublishView(MqttPublish mqttPublish) {
        this.delegate = mqttPublish;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public MqttTopic getTopic() {
        return this.delegate.getTopic();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public Optional<ByteBuffer> getPayload() {
        return this.delegate.getPayload();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public byte[] getPayloadAsBytes() {
        return this.delegate.getPayloadAsBytes();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public MqttQos getQos() {
        return this.delegate.getQos();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public boolean isRetain() {
        return this.delegate.isRetain();
    }

    public MqttPublish getDelegate() {
        return this.delegate;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public void acknowledge() {
        this.delegate.acknowledge();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish
    public Mqtt3PublishViewBuilder.Default extend() {
        return new Mqtt3PublishViewBuilder.Default(this);
    }

    private String toAttributeString() {
        return "topic=" + getTopic() + (this.delegate.getRawPayload() == null ? "" : ", payload=" + this.delegate.getRawPayload().remaining() + "byte") + ", qos=" + getQos() + ", retain=" + isRetain();
    }

    public String toString() {
        return "MqttPublish{" + toAttributeString() + '}';
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof Mqtt3PublishView) {
            return this.delegate.equals(((Mqtt3PublishView) obj).delegate);
        }
        return false;
    }

    public int hashCode() {
        return this.delegate.hashCode();
    }
}
