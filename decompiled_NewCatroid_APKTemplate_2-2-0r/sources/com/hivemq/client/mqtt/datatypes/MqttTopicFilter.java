package com.hivemq.client.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;
import java.util.List;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttTopicFilter extends MqttUtf8String {
    public static final char MULTI_LEVEL_WILDCARD = '#';
    public static final char SINGLE_LEVEL_WILDCARD = '+';

    boolean containsMultiLevelWildcard();

    boolean containsSingleLevelWildcard();

    boolean containsWildcards();

    MqttTopicFilterBuilder.Complete extend();

    List<String> getLevels();

    boolean isShared();

    boolean matches(MqttTopic mqttTopic);

    boolean matches(MqttTopicFilter mqttTopicFilter);

    MqttSharedTopicFilter share(String str);

    static MqttTopicFilter of(String str) {
        return MqttTopicFilterImpl.of(str);
    }

    static MqttTopicFilterBuilder builder() {
        return new MqttTopicFilterImplBuilder.Default();
    }
}
