package com.hivemq.client.mqtt.mqtt3.message.connect;

import com.hivemq.client.internal.mqtt.message.connect.MqttConnectRestrictionsBuilder;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ConnectRestrictions {
    Mqtt3ConnectRestrictionsBuilder extend();

    int getSendMaximum();

    int getSendMaximumPacketSize();

    static Mqtt3ConnectRestrictionsBuilder builder() {
        return new MqttConnectRestrictionsBuilder.Default();
    }
}
