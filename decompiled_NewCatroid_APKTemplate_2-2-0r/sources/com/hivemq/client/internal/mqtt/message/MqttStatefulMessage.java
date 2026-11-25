package com.hivemq.client.internal.mqtt.message;

import com.hivemq.client.internal.mqtt.datatypes.MqttUserPropertiesImpl;
import com.hivemq.client.internal.mqtt.message.MqttMessageWithUserProperties;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttStatefulMessage<M extends MqttMessageWithUserProperties> implements MqttMessage.WithUserProperties {
    private final M statelessMessage;

    protected MqttStatefulMessage(M m) {
        this.statelessMessage = m;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.message.Mqtt5Message
    public Mqtt5MessageType getType() {
        return this.statelessMessage.getType();
    }

    @Override // com.hivemq.client.internal.mqtt.message.MqttMessage.WithUserProperties
    public MqttUserPropertiesImpl getUserProperties() {
        return this.statelessMessage.getUserProperties();
    }

    public M stateless() {
        return this.statelessMessage;
    }

    protected String toAttributeString() {
        return "stateless=" + this.statelessMessage;
    }

    public static abstract class WithId<M extends MqttMessageWithUserProperties> extends MqttStatefulMessage<M> implements MqttMessage.WithId {
        private final int packetIdentifier;

        protected WithId(M m, int i) {
            super(m);
            this.packetIdentifier = i;
        }

        @Override // com.hivemq.client.internal.mqtt.message.MqttMessage.WithId
        public int getPacketIdentifier() {
            return this.packetIdentifier;
        }

        @Override // com.hivemq.client.internal.mqtt.message.MqttStatefulMessage
        protected String toAttributeString() {
            return super.toAttributeString() + ", packetIdentifier=" + this.packetIdentifier;
        }
    }
}
