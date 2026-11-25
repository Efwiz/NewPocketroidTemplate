package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.pubrec.MqttPubRec;
import com.hivemq.client.internal.mqtt.message.publish.pubrel.MqttPubRel;
import java.util.function.BooleanSupplier;

/* JADX INFO: loaded from: classes10.dex */
abstract class MqttPubRelWithFlow extends MqttPubOrRelWithFlow {
    private final MqttPubRel pubRel;

    MqttPubRelWithFlow(MqttPubRel mqttPubRel, MqttAckFlow mqttAckFlow) {
        super(mqttAckFlow);
        this.pubRel = mqttPubRel;
    }

    MqttPubRel getPubRel() {
        return this.pubRel;
    }

    static class MqttQos2IntermediateWithFlow extends MqttPubRelWithFlow implements BooleanSupplier {
        private int state;

        MqttQos2IntermediateWithFlow(MqttPubRel mqttPubRel, MqttAckFlow mqttAckFlow) {
            super(mqttPubRel, mqttAckFlow);
        }

        @Override // java.util.function.BooleanSupplier
        public boolean getAsBoolean() {
            int i = this.state + 1;
            this.state = i;
            return i == 2;
        }
    }

    static class MqttQos2CompleteWithFlow extends MqttPubRelWithFlow {
        private final MqttPubRec pubRec;
        private final MqttPublish publish;

        MqttQos2CompleteWithFlow(MqttPublish mqttPublish, MqttPubRec mqttPubRec, MqttPubRel mqttPubRel, MqttAckFlow mqttAckFlow) {
            super(mqttPubRel, mqttAckFlow);
            this.publish = mqttPublish;
            this.pubRec = mqttPubRec;
        }

        MqttPublish getPublish() {
            return this.publish;
        }

        MqttPubRec getPubRec() {
            return this.pubRec;
        }
    }
}
