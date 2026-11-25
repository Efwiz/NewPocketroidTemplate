package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.logging.InternalLogger;
import com.hivemq.client.internal.logging.InternalLoggerFactory;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import io.reactivex.Flowable;
import io.reactivex.FlowableSubscriber;
import io.reactivex.plugins.RxJavaPlugins;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class MqttPublishFlowableAckLink extends Flowable<MqttPublishWithFlow> {
    private final MqttAckFlowableFlow ackFlow;
    private final Flowable<MqttPublish> source;

    interface LinkedFlow {
        public static final LinkedFlow CANCELLED = new LinkedFlow() { // from class: com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttPublishFlowableAckLink$LinkedFlow$$ExternalSyntheticLambda0
            @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttPublishFlowableAckLink.LinkedFlow
            public final void cancelLink() {
                MqttPublishFlowableAckLink.LinkedFlow.lambda$static$0();
            }
        };

        static /* synthetic */ void lambda$static$0() {
        }

        void cancelLink();
    }

    MqttPublishFlowableAckLink(Flowable<MqttPublish> flowable, MqttAckFlowableFlow mqttAckFlowableFlow) {
        this.source = flowable;
        this.ackFlow = mqttAckFlowableFlow;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super MqttPublishWithFlow> subscriber) {
        this.source.subscribe((FlowableSubscriber<? super MqttPublish>) new AckLinkSubscriber(subscriber, this.ackFlow));
    }

    private static class AckLinkSubscriber implements FlowableSubscriber<MqttPublish>, Subscription, LinkedFlow {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final InternalLogger LOGGER = InternalLoggerFactory.getLogger(AckLinkSubscriber.class);
        static final int STATE_CANCELLED = 3;
        static final int STATE_DONE = 2;
        static final int STATE_IN_PROGRESS = 1;
        static final int STATE_NONE = 0;
        private final MqttAckFlowableFlow ackFlow;
        private long published;
        private final Subscriber<? super MqttPublishWithFlow> subscriber;
        private Subscription subscription;
        private final AtomicInteger state = new AtomicInteger(0);
        private final AtomicInteger requestState = new AtomicInteger(0);

        AckLinkSubscriber(Subscriber<? super MqttPublishWithFlow> subscriber, MqttAckFlowableFlow mqttAckFlowableFlow) {
            this.subscriber = subscriber;
            this.ackFlow = mqttAckFlowableFlow;
        }

        @Override // io.reactivex.FlowableSubscriber, org.reactivestreams.Subscriber
        public void onSubscribe(Subscription subscription) {
            this.subscription = subscription;
            this.subscriber.onSubscribe(this);
            this.ackFlow.link(this);
        }

        @Override // org.reactivestreams.Subscriber
        public void onNext(MqttPublish mqttPublish) {
            if (this.state.compareAndSet(0, 1)) {
                this.subscriber.onNext(new MqttPublishWithFlow(mqttPublish, this.ackFlow));
                this.published++;
                if (this.state.compareAndSet(1, 0)) {
                    return;
                }
                cancelActual();
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onComplete() {
            if (this.state.compareAndSet(0, 2)) {
                this.subscriber.onComplete();
                this.ackFlow.onComplete(this.published);
            }
        }

        @Override // org.reactivestreams.Subscriber
        public void onError(Throwable th) {
            if (this.state.compareAndSet(0, 2)) {
                this.subscriber.onComplete();
                this.ackFlow.onError(th, this.published);
            } else {
                RxJavaPlugins.onError(th);
            }
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (this.requestState.compareAndSet(0, 1)) {
                this.subscription.request(j);
                if (this.requestState.compareAndSet(1, 0)) {
                    return;
                }
                this.subscription.cancel();
            }
        }

        @Override // org.reactivestreams.Subscription
        public void cancel() {
            LOGGER.error("MqttPublishFlowables is global and must never cancel. This must not happen and is a bug.");
        }

        @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttPublishFlowableAckLink.LinkedFlow
        public void cancelLink() {
            if (this.state.getAndSet(3) == 0) {
                cancelActual();
            }
        }

        private void cancelActual() {
            if (this.requestState.getAndSet(3) == 0) {
                this.subscription.cancel();
            }
            this.subscriber.onComplete();
        }
    }
}
