package com.hivemq.client.mqtt.mqtt3.message.connect;

import com.hivemq.client.mqtt.mqtt3.message.connect.Mqtt3ConnectRestrictionsBuilderBase;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ConnectRestrictionsBuilderBase<B extends Mqtt3ConnectRestrictionsBuilderBase<B>> {
    B sendMaximum(int i);

    B sendMaximumPacketSize(int i);
}
