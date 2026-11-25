package com.hivemq.client.mqtt.mqtt5.message.connect.connack;

import com.hivemq.client.mqtt.datatypes.MqttQos;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnAckRestrictions {
    public static final int DEFAULT_MAXIMUM_PACKET_SIZE_NO_LIMIT = 268435460;
    public static final MqttQos DEFAULT_MAXIMUM_QOS = MqttQos.EXACTLY_ONCE;
    public static final int DEFAULT_RECEIVE_MAXIMUM = 65535;
    public static final boolean DEFAULT_RETAIN_AVAILABLE = true;
    public static final boolean DEFAULT_SHARED_SUBSCRIPTION_AVAILABLE = true;
    public static final boolean DEFAULT_SUBSCRIPTION_IDENTIFIERS_AVAILABLE = true;
    public static final int DEFAULT_TOPIC_ALIAS_MAXIMUM = 0;
    public static final boolean DEFAULT_WILDCARD_SUBSCRIPTION_AVAILABLE = true;

    boolean areSubscriptionIdentifiersAvailable();

    int getMaximumPacketSize();

    MqttQos getMaximumQos();

    int getReceiveMaximum();

    int getTopicAliasMaximum();

    boolean isRetainAvailable();

    boolean isSharedSubscriptionAvailable();

    boolean isWildcardSubscriptionAvailable();
}
