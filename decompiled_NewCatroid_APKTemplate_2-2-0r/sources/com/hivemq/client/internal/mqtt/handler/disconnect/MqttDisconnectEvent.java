package com.hivemq.client.internal.mqtt.handler.disconnect;

import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.rx.CompletableFlow;
import com.hivemq.client.mqtt.lifecycle.MqttDisconnectSource;
import com.hivemq.client.mqtt.mqtt5.exceptions.Mqtt5DisconnectException;
import com.hivemq.client.mqtt.mqtt5.message.disconnect.Mqtt5Disconnect;

/* JADX INFO: loaded from: classes10.dex */
public class MqttDisconnectEvent {
    private final Throwable cause;
    private final MqttDisconnectSource source;

    MqttDisconnectEvent(Throwable th, MqttDisconnectSource mqttDisconnectSource) {
        this.cause = th;
        this.source = mqttDisconnectSource;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public MqttDisconnectSource getSource() {
        return this.source;
    }

    MqttDisconnect getDisconnect() {
        Throwable th = this.cause;
        if (!(th instanceof Mqtt5DisconnectException)) {
            return null;
        }
        Mqtt5Disconnect mqttMessage = ((Mqtt5DisconnectException) th).getMqttMessage();
        if (mqttMessage instanceof MqttDisconnect) {
            return (MqttDisconnect) mqttMessage;
        }
        return null;
    }

    static class ByUser extends MqttDisconnectEvent {
        private final CompletableFlow flow;

        ByUser(MqttDisconnect mqttDisconnect, CompletableFlow completableFlow) {
            super(new Mqtt5DisconnectException(mqttDisconnect, "Client sent DISCONNECT"), MqttDisconnectSource.USER);
            this.flow = completableFlow;
        }

        public CompletableFlow getFlow() {
            return this.flow;
        }
    }
}
