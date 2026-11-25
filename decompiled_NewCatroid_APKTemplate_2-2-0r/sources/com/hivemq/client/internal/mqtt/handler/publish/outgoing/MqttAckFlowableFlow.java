package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import androidx.lifecycle.LifecycleKt$$ExternalSyntheticBackportWithForwarding0;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult;
import com.hivemq.client.internal.util.collections.ChunkedArrayQueue;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
class MqttAckFlowableFlow extends MqttAckFlow implements Subscription, Runnable {
    private static final int STATE_BLOCKED = 2;
    private static final int STATE_NEW_REQUESTS = 1;
    private static final int STATE_NO_NEW_REQUESTS = 0;
    private volatile long acknowledged;
    private Throwable error;
    private final AtomicReference<MqttPublishFlowableAckLink.LinkedFlow> linkedFlow;
    private final AtomicLong newRequested;
    private final MqttOutgoingQosHandler outgoingQosHandler;
    private final AtomicLong published;
    private final ChunkedArrayQueue<MqttPublishResult> queue;
    private final AtomicInteger requestState;
    private long requested;
    private final Subscriber<? super MqttPublishResult> subscriber;

    MqttAckFlowableFlow(Subscriber<? super MqttPublishResult> subscriber, MqttClientConfig mqttClientConfig, MqttOutgoingQosHandler mqttOutgoingQosHandler) {
        super(mqttClientConfig);
        this.newRequested = new AtomicLong();
        this.requestState = new AtomicInteger(0);
        this.published = new AtomicLong();
        this.queue = new ChunkedArrayQueue<>(32);
        this.linkedFlow = new AtomicReference<>();
        this.subscriber = subscriber;
        this.outgoingQosHandler = mqttOutgoingQosHandler;
        init();
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlow
    void onNext(MqttPublishResult mqttPublishResult) {
        this.queue.offer(mqttPublishResult);
        run();
    }

    @Override // java.lang.Runnable
    public void run() {
        long jRequested = requested();
        long j = 0;
        long j2 = 0;
        loop0: while (j < jRequested) {
            do {
                MqttPublishResult mqttPublishResultPoll = this.queue.poll();
                if (mqttPublishResultPoll == null) {
                    break loop0;
                }
                this.subscriber.onNext(mqttPublishResultPoll);
                if (mqttPublishResultPoll.acknowledged()) {
                    j2++;
                }
                j++;
            } while (j < jRequested);
            if (isCancelled()) {
                while (true) {
                    MqttPublishResult mqttPublishResultPoll2 = this.queue.poll();
                    if (mqttPublishResultPoll2 == null) {
                        break;
                    } else if (mqttPublishResultPoll2.acknowledged()) {
                        j2++;
                    }
                }
            } else {
                jRequested = addNewRequested();
            }
        }
        long j3 = this.requested;
        if (j3 != Long.MAX_VALUE) {
            this.requested = j3 - j;
        }
        acknowledged(j2);
    }

    @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlow
    void acknowledged(long j) {
        if (j > 0) {
            long j2 = this.acknowledged + j;
            this.acknowledged = j2;
            if (j2 == this.published.get() && setDone()) {
                Throwable th = this.error;
                if (th != null) {
                    this.subscriber.onError(th);
                } else {
                    this.subscriber.onComplete();
                }
            }
            this.outgoingQosHandler.request(j);
        }
    }

    void onComplete(long j) {
        if (this.published.compareAndSet(0L, j) && this.acknowledged == j && setDone()) {
            this.subscriber.onComplete();
        }
    }

    void onError(Throwable th, long j) {
        this.error = th;
        if (!this.published.compareAndSet(0L, j)) {
            RxJavaPlugins.onError(th);
        } else if (this.acknowledged == j && setDone()) {
            this.subscriber.onError(th);
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        if (j <= 0 || isCancelled()) {
            return;
        }
        BackpressureHelper.add(this.newRequested, j);
        if (this.requestState.getAndSet(1) == 2) {
            this.eventLoop.execute(this);
        }
    }

    private long requested() {
        long j = this.requested;
        return j > 0 ? j : addNewRequested();
    }

    private long addNewRequested() {
        while (!this.requestState.compareAndSet(0, 2)) {
            this.requestState.set(0);
            long andSet = this.newRequested.getAndSet(0L);
            if (andSet > 0) {
                long jAddCap = BackpressureHelper.addCap(this.requested, andSet);
                this.requested = jAddCap;
                return jAddCap;
            }
        }
        return 0L;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop
    protected void onCancel() {
        if (this.requestState.get() == 2) {
            this.eventLoop.execute(this);
        }
        cancelLink();
    }

    private void cancelLink() {
        MqttPublishFlowableAckLink.LinkedFlow andSet = this.linkedFlow.getAndSet(MqttPublishFlowableAckLink.LinkedFlow.CANCELLED);
        if (andSet != null) {
            andSet.cancelLink();
        }
    }

    void link(MqttPublishFlowableAckLink.LinkedFlow linkedFlow) {
        if (LifecycleKt$$ExternalSyntheticBackportWithForwarding0.m(this.linkedFlow, null, linkedFlow)) {
            return;
        }
        linkedFlow.cancelLink();
    }
}
