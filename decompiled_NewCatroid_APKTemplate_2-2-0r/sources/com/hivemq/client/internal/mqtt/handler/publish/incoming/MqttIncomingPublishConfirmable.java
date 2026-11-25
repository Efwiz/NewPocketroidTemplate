package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.checkpoint.Confirmable;
import java.util.concurrent.atomic.AtomicBoolean;

/* JADX INFO: loaded from: classes10.dex */
class MqttIncomingPublishConfirmable implements Confirmable, Runnable {
    private final AtomicBoolean confirmed = new AtomicBoolean(false);
    private final MqttIncomingPublishFlow flow;
    private final MqttStatefulPublishWithFlows publishWithFlows;

    MqttIncomingPublishConfirmable(MqttIncomingPublishFlow mqttIncomingPublishFlow, MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        this.flow = mqttIncomingPublishFlow;
        this.publishWithFlows = mqttStatefulPublishWithFlows;
    }

    @Override // com.hivemq.client.internal.checkpoint.Confirmable
    public long getId() {
        return this.publishWithFlows.f186id;
    }

    @Override // com.hivemq.client.internal.checkpoint.Confirmable
    public boolean confirm() {
        if (!this.confirmed.compareAndSet(false, true)) {
            return false;
        }
        this.flow.getEventLoop().execute(this);
        return true;
    }

    @Override // java.lang.Runnable
    public void run() {
        this.publishWithFlows.acknowledge(this.flow);
    }

    static class Qos0 implements Confirmable {
        private final AtomicBoolean confirmed = new AtomicBoolean(false);

        @Override // com.hivemq.client.internal.checkpoint.Confirmable
        public long getId() {
            return 0L;
        }

        Qos0() {
        }

        @Override // com.hivemq.client.internal.checkpoint.Confirmable
        public boolean confirm() {
            return this.confirmed.compareAndSet(false, true);
        }
    }
}
