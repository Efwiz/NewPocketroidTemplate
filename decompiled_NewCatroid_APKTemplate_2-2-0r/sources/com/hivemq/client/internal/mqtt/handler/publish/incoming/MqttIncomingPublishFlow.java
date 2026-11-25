package com.hivemq.client.internal.mqtt.handler.publish.incoming;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import io.reactivex.Emitter;
import io.reactivex.internal.util.BackpressureHelper;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
abstract class MqttIncomingPublishFlow extends FlowWithEventLoop implements Emitter<Mqtt5Publish>, Subscription, Runnable {
    private static final int STATE_BLOCKED = 2;
    private static final int STATE_NEW_REQUESTS = 1;
    private static final int STATE_NO_NEW_REQUESTS = 0;
    private long blockedIndex;
    private boolean blocking;
    private boolean done;
    private Throwable error;
    final MqttIncomingPublishService incomingPublishService;
    final boolean manualAcknowledgement;
    private int missingAcknowledgements;
    private final AtomicLong newRequested;
    private int referenced;
    private final AtomicInteger requestState;
    private long requested;
    final Subscriber<? super Mqtt5Publish> subscriber;

    MqttIncomingPublishFlow(Subscriber<? super Mqtt5Publish> subscriber, MqttClientConfig mqttClientConfig, MqttIncomingQosHandler mqttIncomingQosHandler, boolean z) {
        super(mqttClientConfig);
        this.newRequested = new AtomicLong();
        this.requestState = new AtomicInteger(0);
        this.subscriber = subscriber;
        this.incomingPublishService = mqttIncomingQosHandler.incomingPublishService;
        this.manualAcknowledgement = z;
    }

    /* JADX WARN: Can't rename method to resolve collision */
    @Override // io.reactivex.Emitter
    public void onNext(Mqtt5Publish mqtt5Publish) {
        this.subscriber.onNext(mqtt5Publish);
        long j = this.requested;
        if (j != Long.MAX_VALUE) {
            this.requested = j - 1;
        }
    }

    public void onComplete() {
        if (this.done) {
            return;
        }
        this.done = true;
        if (setDone()) {
            this.subscriber.onComplete();
        } else {
            this.incomingPublishService.drain();
        }
    }

    public void onError(Throwable th) {
        if (this.done) {
            if (th != this.error) {
                RxJavaPlugins.onError(th);
            }
        } else {
            this.error = th;
            this.done = true;
            if (setDone()) {
                this.subscriber.onError(th);
            } else {
                this.incomingPublishService.drain();
            }
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop
    protected boolean setDone() {
        return this.referenced == 0 && this.missingAcknowledgements == 0 && super.setDone();
    }

    void checkDone() {
        if (this.done && setDone()) {
            Throwable th = this.error;
            if (th != null) {
                this.subscriber.onError(th);
            } else {
                this.subscriber.onComplete();
            }
        }
    }

    public void request(long j) {
        if (j <= 0 || isCancelled()) {
            return;
        }
        BackpressureHelper.add(this.newRequested, j);
        if (this.requestState.getAndSet(1) == 2) {
            this.eventLoop.execute(this);
        }
    }

    public void run() {
        if (this.referenced > 0) {
            this.incomingPublishService.drain();
        }
    }

    long requested(long j) {
        long j2 = this.requested;
        if (j2 > 0) {
            return j2;
        }
        if (this.blocking && this.blockedIndex != j) {
            this.blocking = false;
        }
        if (this.blocking) {
            return -1L;
        }
        while (!this.requestState.compareAndSet(0, 2)) {
            this.requestState.set(0);
            long andSet = this.newRequested.getAndSet(0L);
            if (andSet > 0) {
                long jAddCap = BackpressureHelper.addCap(this.requested, andSet);
                this.requested = jAddCap;
                return jAddCap;
            }
        }
        this.blockedIndex = j;
        this.blocking = true;
        return 0L;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop
    protected void onCancel() {
        this.eventLoop.execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlow$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.runCancel();
            }
        });
    }

    void runCancel() {
        if (this.referenced > 0) {
            this.incomingPublishService.drain();
        }
    }

    void increaseMissingAcknowledgements() {
        this.missingAcknowledgements++;
    }

    void acknowledge(boolean z) {
        if (z) {
            this.incomingPublishService.drain();
        }
        int i = this.missingAcknowledgements - 1;
        this.missingAcknowledgements = i;
        if (i == 0) {
            checkDone();
        }
    }

    int reference() {
        int i = this.referenced + 1;
        this.referenced = i;
        return i;
    }

    int dereference() {
        int i = this.referenced - 1;
        this.referenced = i;
        return i;
    }
}
