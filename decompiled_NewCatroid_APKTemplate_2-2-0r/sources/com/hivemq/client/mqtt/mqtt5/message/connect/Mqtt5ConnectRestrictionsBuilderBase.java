package com.hivemq.client.mqtt.mqtt5.message.connect;

import com.hivemq.client.mqtt.mqtt5.message.connect.Mqtt5ConnectRestrictionsBuilderBase;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt5ConnectRestrictionsBuilderBase<B extends Mqtt5ConnectRestrictionsBuilderBase<B>> {
    B maximumPacketSize(int i);

    B receiveMaximum(int i);

    B requestProblemInformation(boolean z);

    B requestResponseInformation(boolean z);

    /* JADX INFO: renamed from: sendMaximum */
    B mo2721sendMaximum(int i);

    /* JADX INFO: renamed from: sendMaximumPacketSize */
    B mo2722sendMaximumPacketSize(int i);

    B sendTopicAliasMaximum(int i);

    B topicAliasMaximum(int i);
}
