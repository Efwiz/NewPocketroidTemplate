package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.ioc.ClientScope;
import io.reactivex.Flowable;
import io.reactivex.internal.util.BackpressureHelper;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
@ClientScope
public class MqttPublishFlowables extends Flowable<Flowable<MqttPublishWithFlow>> implements Subscription {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(MqttPublishFlowables.class);
    private long requested;
    private Subscriber<? super Flowable<MqttPublishWithFlow>> subscriber;

    MqttPublishFlowables() {
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Flowable<MqttPublishWithFlow>> subscriber) {
        this.subscriber = subscriber;
        subscriber.onSubscribe(this);
    }

    public void add(Flowable<MqttPublishWithFlow> flowable) {
        synchronized (this) {
            while (this.requested == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    LOGGER.error("thread interrupted while waiting to publish.", (Throwable) e);
                    return;
                }
            }
            this.subscriber.onNext(flowable);
            this.requested--;
        }
    }

    @Override // org.reactivestreams.Subscription
    public void request(long j) {
        synchronized (this) {
            long jAddCap = BackpressureHelper.addCap(this.requested, j);
            this.requested = jAddCap;
            if (jAddCap == j) {
                notifyAll();
            }
        }
    }

    @Override // org.reactivestreams.Subscription
    public void cancel() {
        this.subscriber = null;
    }
}
