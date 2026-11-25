package com.hivemq.client.mqtt.mqtt5.message.connect;

import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictionsBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnectRestrictions {
    public static final int DEFAULT_MAXIMUM_PACKET_SIZE = 268435460;
    public static final int DEFAULT_RECEIVE_MAXIMUM = 65535;
    public static final boolean DEFAULT_REQUEST_PROBLEM_INFORMATION = true;
    public static final boolean DEFAULT_REQUEST_RESPONSE_INFORMATION = false;
    public static final int DEFAULT_SEND_MAXIMUM = 65535;
    public static final int DEFAULT_SEND_MAXIMUM_PACKET_SIZE = 268435460;
    public static final int DEFAULT_SEND_TOPIC_ALIAS_MAXIMUM = 16;
    public static final int DEFAULT_TOPIC_ALIAS_MAXIMUM = 0;

    Mqtt5ConnectRestrictionsBuilder extend();

    int getMaximumPacketSize();

    int getReceiveMaximum();

    int getSendMaximum();

    int getSendMaximumPacketSize();

    int getSendTopicAliasMaximum();

    int getTopicAliasMaximum();

    boolean isRequestProblemInformation();

    boolean isRequestResponseInformation();

    static Mqtt5ConnectRestrictionsBuilder builder() {
        return new MqttConnectRestrictionsBuilder.Default();
    }
}
