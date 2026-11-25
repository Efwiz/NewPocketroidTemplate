package com.hivemq.client.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicImplBuilder;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttTopic extends MqttUtf8String {
    public static final char TOPIC_LEVEL_SEPARATOR = '/';

    MqttTopicBuilder.Complete extend();

    MqttTopicFilter filter();

    List<String> getLevels();

    static MqttTopic of(String str) {
        return MqttTopicImpl.of(str);
    }

    static MqttTopicBuilder builder() {
        return new MqttTopicImplBuilder.Default();
    }
}
