package com.hivemq.client.internal.mqtt.message.disconnect.mqtt3;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import com.hivemq.client.mqtt.mqtt3.message.disconnect.Mqtt3Disconnect;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5DisconnectReasonCode;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3DisconnectView implements Mqtt3Disconnect {
    public static final MqttDisconnect DELEGATE = new MqttDisconnect(Mqtt5DisconnectReasonCode.NORMAL_DISCONNECTION, -1, null, null, MqttUserPropertiesImpl.NO_USER_PROPERTIES);
    public static final Mqtt3DisconnectView INSTANCE = new Mqtt3DisconnectView();

    public boolean equals(Object obj) {
        return this == obj;
    }

    private Mqtt3DisconnectView() {
    }

    public String toString() {
        return "MqttDisconnect{}";
    }

    public int hashCode() {
        return Mqtt3MessageType.DISCONNECT.ordinal();
    }
}
