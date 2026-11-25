package com.hivemq.client.mqtt.datatypes;

import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientIdentifier extends MqttUtf8String {
    boolean mustBeAllowedByServer();

    static MqttClientIdentifier of(String str) {
        return MqttClientIdentifierImpl.of(str);
    }
}
