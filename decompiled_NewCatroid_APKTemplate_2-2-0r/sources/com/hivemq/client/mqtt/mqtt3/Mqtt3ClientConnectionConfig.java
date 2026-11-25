package com.hivemq.client.mqtt.mqtt3;

import com.hivemq.client.mqtt.MqttClientConnectionConfig;

/* JADX INFO: loaded from: classes10.dex */
public interface Mqtt3ClientConnectionConfig extends MqttClientConnectionConfig {

    public interface RestrictionsForClient {
        int getSendMaximum();
    }

    RestrictionsForClient getRestrictionsForClient();
}
