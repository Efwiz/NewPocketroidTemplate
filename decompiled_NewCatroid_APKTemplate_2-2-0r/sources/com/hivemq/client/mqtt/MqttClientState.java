package com.hivemq.client.mqtt;

import java.util.EnumSet;

/* JADX INFO: loaded from: classes10.dex */
public enum MqttClientState {
    DISCONNECTED,
    CONNECTING,
    CONNECTED,
    DISCONNECTED_RECONNECT,
    CONNECTING_RECONNECT;

    private static final EnumSet<MqttClientState> CONNECTED_OR_RECONNECT = EnumSet.of(CONNECTED, DISCONNECTED_RECONNECT, CONNECTING_RECONNECT);

    public boolean isConnected() {
        return this == CONNECTED;
    }

    public boolean isConnectedOrReconnect() {
        return CONNECTED_OR_RECONNECT.contains(this);
    }
}
