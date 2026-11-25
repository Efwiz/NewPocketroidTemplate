package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttStatefulPublish;
import com.hivemq.client.internal.util.collections.HandleList;
import com.hivemq.client.mqtt.datatypes.MqttQos;

/* JADX INFO: loaded from: classes10.dex */
class MqttStatefulPublishWithFlows extends HandleList<MqttIncomingPublishFlow> {
    long connectionIndex;

    /* JADX INFO: renamed from: id, reason: collision with root package name */
    long f186id;
    private int missingAcknowledgements;
    final MqttStatefulPublish publish;
    boolean subscriptionFound;

    MqttStatefulPublishWithFlows(MqttStatefulPublish mqttStatefulPublish) {
        this.publish = mqttStatefulPublish;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // com.hivemq.client.internal.util.collections.HandleList
    public HandleList.Handle<MqttIncomingPublishFlow> add(MqttIncomingPublishFlow mqttIncomingPublishFlow) {
        if (((MqttPublish) this.publish.stateless()).getQos() != MqttQos.AT_MOST_ONCE && mqttIncomingPublishFlow.manualAcknowledgement) {
            this.missingAcknowledgements++;
            mqttIncomingPublishFlow.increaseMissingAcknowledgements();
        }
        return super.add(mqttIncomingPublishFlow);
    }

    boolean areAcknowledged() {
        return this.missingAcknowledgements == 0;
    }

    void acknowledge(MqttIncomingPublishFlow mqttIncomingPublishFlow) {
        int i = this.missingAcknowledgements - 1;
        this.missingAcknowledgements = i;
        mqttIncomingPublishFlow.acknowledge(i == 0);
    }
}
