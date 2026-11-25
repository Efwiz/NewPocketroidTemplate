package com.hivemq.client.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttSharedTopicFilterImpl;
import com.hivemq.client.internal.mqtt.datatypes.MqttTopicFilterImplBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttSharedTopicFilter extends MqttTopicFilter {
    public static final String SHARE_PREFIX = "$share/";

    MqttSharedTopicFilterBuilder.Complete extendShared();

    String getShareName();

    MqttTopicFilter getTopicFilter();

    static MqttSharedTopicFilter of(String str, String str2) {
        return MqttSharedTopicFilterImpl.of(str, str2);
    }

    static MqttSharedTopicFilterBuilder builder(String str) {
        return new MqttTopicFilterImplBuilder.SharedDefault(str);
    }
}
