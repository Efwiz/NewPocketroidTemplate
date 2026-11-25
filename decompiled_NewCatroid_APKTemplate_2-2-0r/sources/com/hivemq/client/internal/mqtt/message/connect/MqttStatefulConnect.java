package com.hivemq.client.internal.mqtt.message.connect;

import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.message.MqttStatefulMessage;
import com.hivemq.client.internal.mqtt.message.auth.MqttEnhancedAuth;

/* JADX INFO: loaded from: classes10.dex */
public class MqttStatefulConnect extends MqttStatefulMessage<MqttConnect> {
    private final MqttClientIdentifierImpl clientIdentifier;
    private final MqttEnhancedAuth enhancedAuth;

    MqttStatefulConnect(MqttConnect mqttConnect, MqttClientIdentifierImpl mqttClientIdentifierImpl, MqttEnhancedAuth mqttEnhancedAuth) {
        super(mqttConnect);
        this.clientIdentifier = mqttClientIdentifierImpl;
        this.enhancedAuth = mqttEnhancedAuth;
    }

    public MqttClientIdentifierImpl getClientIdentifier() {
        return this.clientIdentifier;
    }

    public MqttEnhancedAuth getEnhancedAuth() {
        return this.enhancedAuth;
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttStatefulMessage
    protected String toAttributeString() {
        return super.toAttributeString() + ", clientIdentifier=" + this.clientIdentifier + (this.enhancedAuth == null ? "" : ", enhancedAuth=" + this.enhancedAuth);
    }

    public String toString() {
        return "MqttStatefulConnect{" + toAttributeString() + '}';
    }
}
