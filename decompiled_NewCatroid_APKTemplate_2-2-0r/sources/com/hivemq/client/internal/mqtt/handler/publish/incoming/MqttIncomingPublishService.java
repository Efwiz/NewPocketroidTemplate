package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.checkpoint.Confirmable;
import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.util.collections.ChunkedArrayQueue;
import com.hivemq.client.internal.util.collections.HandleList;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
class MqttIncomingPublishService {
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttIncomingPublishService.class);
    private static final boolean QOS_0_DROP_OLDEST = true;
    private int blockingFlowCount;
    final MqttIncomingPublishFlows incomingPublishFlows;
    private final MqttIncomingQosHandler incomingQosHandler;
    private long nextQoS1Or2PublishId;
    private final ChunkedArrayQueue<MqttStatefulPublishWithFlows>.Iterator qos0It;
    private final ChunkedArrayQueue<MqttStatefulPublishWithFlows> qos0Queue;
    private final ChunkedArrayQueue<MqttStatefulPublishWithFlows>.Iterator qos1Or2It;
    private final ChunkedArrayQueue<MqttStatefulPublishWithFlows> qos1Or2Queue;
    private int referencedFlowCount;
    private int runIndex;

    MqttIncomingPublishService(MqttIncomingQosHandler mqttIncomingQosHandler, MqttIncomingPublishFlows mqttIncomingPublishFlows) {
        ChunkedArrayQueue<MqttStatefulPublishWithFlows> chunkedArrayQueue = new ChunkedArrayQueue<>(32);
        this.qos0Queue = chunkedArrayQueue;
        this.qos0It = chunkedArrayQueue.iterator();
        ChunkedArrayQueue<MqttStatefulPublishWithFlows> chunkedArrayQueue2 = new ChunkedArrayQueue<>(32);
        this.qos1Or2Queue = chunkedArrayQueue2;
        this.qos1Or2It = chunkedArrayQueue2.iterator();
        this.nextQoS1Or2PublishId = 1L;
        this.incomingQosHandler = mqttIncomingQosHandler;
        this.incomingPublishFlows = mqttIncomingPublishFlows;
    }

    void onPublishQos0(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows, int i) {
        if (this.qos0Queue.size() >= i) {
            LOGGER.warn("QoS 0 publish message dropped.");
            this.qos0It.reset();
            MqttStatefulPublishWithFlows next = this.qos0It.next();
            this.qos0It.remove();
            Object first = next.getFirst();
            while (true) {
                HandleList.Handle handle = (HandleList.Handle) first;
                if (handle == null) {
                    break;
                }
                if (((MqttIncomingPublishFlow) handle.getElement()).dereference() == 0) {
                    this.referencedFlowCount--;
                }
                first = handle.getNext();
            }
        }
        onPublish(mqttStatefulPublishWithFlows);
        if (mqttStatefulPublishWithFlows.isEmpty()) {
            return;
        }
        this.qos0Queue.offer(mqttStatefulPublishWithFlows);
    }

    boolean onPublishQos1Or2(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows, int i) {
        if (this.qos1Or2Queue.size() >= i) {
            return false;
        }
        long j = this.nextQoS1Or2PublishId;
        this.nextQoS1Or2PublishId = 1 + j;
        mqttStatefulPublishWithFlows.f186id = j;
        onPublish(mqttStatefulPublishWithFlows);
        if (this.qos1Or2Queue.isEmpty() && mqttStatefulPublishWithFlows.isEmpty() && mqttStatefulPublishWithFlows.areAcknowledged()) {
            this.incomingQosHandler.ack(mqttStatefulPublishWithFlows);
            return true;
        }
        this.qos1Or2Queue.offer(mqttStatefulPublishWithFlows);
        return true;
    }

    private void onPublish(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        this.incomingPublishFlows.findMatching(mqttStatefulPublishWithFlows);
        if (mqttStatefulPublishWithFlows.isEmpty()) {
            LOGGER.warn("No publish flow registered for {}.", mqttStatefulPublishWithFlows.publish);
        }
        drain();
        Object first = mqttStatefulPublishWithFlows.getFirst();
        while (true) {
            HandleList.Handle handle = (HandleList.Handle) first;
            if (handle != null) {
                if (((MqttIncomingPublishFlow) handle.getElement()).reference() == 1) {
                    this.referencedFlowCount++;
                }
                first = handle.getNext();
            } else {
                emit(mqttStatefulPublishWithFlows);
                return;
            }
        }
    }

    void drain() {
        this.runIndex++;
        this.blockingFlowCount = 0;
        this.qos1Or2It.reset();
        while (this.qos1Or2It.hasNext()) {
            MqttStatefulPublishWithFlows next = this.qos1Or2It.next();
            emit(next);
            if (this.qos1Or2It.getIterated() == 1 && next.isEmpty() && next.areAcknowledged()) {
                this.qos1Or2It.remove();
                this.incomingQosHandler.ack(next);
            } else if (this.blockingFlowCount == this.referencedFlowCount) {
                return;
            }
        }
        this.qos0It.reset();
        while (this.qos0It.hasNext()) {
            MqttStatefulPublishWithFlows next2 = this.qos0It.next();
            emit(next2);
            if (this.qos0It.getIterated() == 1 && next2.isEmpty()) {
                this.qos0It.remove();
            } else if (this.blockingFlowCount == this.referencedFlowCount) {
                return;
            }
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void emit(MqttStatefulPublishWithFlows mqttStatefulPublishWithFlows) {
        Confirmable mqttIncomingPublishConfirmable;
        Object first = mqttStatefulPublishWithFlows.getFirst();
        while (true) {
            HandleList.Handle handle = (HandleList.Handle) first;
            if (handle == null) {
                return;
            }
            MqttIncomingPublishFlow mqttIncomingPublishFlow = (MqttIncomingPublishFlow) handle.getElement();
            if (mqttIncomingPublishFlow.isCancelled()) {
                mqttStatefulPublishWithFlows.remove(handle);
                if (mqttIncomingPublishFlow.dereference() == 0) {
                    this.referencedFlowCount--;
                }
            } else {
                long jRequested = mqttIncomingPublishFlow.requested(this.runIndex);
                if (jRequested > 0) {
                    MqttPublish mqttPublishWithConfirmable = (MqttPublish) mqttStatefulPublishWithFlows.publish.stateless();
                    if (mqttIncomingPublishFlow.manualAcknowledgement) {
                        if (mqttPublishWithConfirmable.getQos() == MqttQos.AT_MOST_ONCE) {
                            mqttIncomingPublishConfirmable = new MqttIncomingPublishConfirmable.Qos0();
                        } else {
                            mqttIncomingPublishConfirmable = new MqttIncomingPublishConfirmable(mqttIncomingPublishFlow, mqttStatefulPublishWithFlows);
                        }
                        mqttPublishWithConfirmable = mqttPublishWithConfirmable.withConfirmable(mqttIncomingPublishConfirmable);
                    }
                    mqttIncomingPublishFlow.onNext((Mqtt5Publish) mqttPublishWithConfirmable);
                    mqttStatefulPublishWithFlows.remove(handle);
                    if (mqttIncomingPublishFlow.dereference() == 0) {
                        this.referencedFlowCount--;
                        mqttIncomingPublishFlow.checkDone();
                    }
                } else if (jRequested == 0) {
                    int i = this.blockingFlowCount + 1;
                    this.blockingFlowCount = i;
                    if (i == this.referencedFlowCount) {
                        return;
                    }
                } else {
                    continue;
                }
            }
            first = handle.getNext();
        }
    }
}
