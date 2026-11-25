package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import io.reactivex.Flowable;
import io.reactivex.internal.subscriptions.EmptySubscription;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/* JADX INFO: loaded from: classes10.dex */
public class MqttAckSingleFlowable extends Flowable<Mqtt5PublishResult> {
    private final MqttClientConfig clientConfig;
    private final MqttPublish publish;

    public MqttAckSingleFlowable(MqttClientConfig mqttClientConfig, MqttPublish mqttPublish) {
        this.clientConfig = mqttClientConfig;
        this.publish = mqttPublish;
    }

    @Override // io.reactivex.Flowable
    protected void subscribeActual(Subscriber<? super Mqtt5PublishResult> subscriber) {
        if (this.clientConfig.getState().isConnectedOrReconnect()) {
            MqttOutgoingQosHandler mqttOutgoingQosHandlerOutgoingQosHandler = this.clientConfig.getClientComponent().outgoingQosHandler();
            MqttPublishFlowables publishFlowables = mqttOutgoingQosHandlerOutgoingQosHandler.getPublishFlowables();
            Flow flow = new Flow(subscriber, this.clientConfig, mqttOutgoingQosHandlerOutgoingQosHandler);
            subscriber.onSubscribe(flow);
            publishFlowables.add(Flowable.just(new MqttPublishWithFlow(this.publish, flow)));
            return;
        }
        EmptySubscription.error(MqttClientStateExceptions.notConnected(), subscriber);
    }

    private static class Flow extends MqttAckFlow implements Subscription, Runnable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private static final int STATE_CANCELLED = 3;
        private static final int STATE_NONE = 0;
        private static final int STATE_REQUESTED = 2;
        private static final int STATE_RESULT = 1;
        private final MqttOutgoingQosHandler outgoingQosHandler;
        private MqttPublishResult result;
        private final AtomicInteger state;
        private final Subscriber<? super Mqtt5PublishResult> subscriber;

        Flow(Subscriber<? super Mqtt5PublishResult> subscriber, MqttClientConfig mqttClientConfig, MqttOutgoingQosHandler mqttOutgoingQosHandler) {
            super(mqttClientConfig);
            this.state = new AtomicInteger(0);
            this.subscriber = subscriber;
            this.outgoingQosHandler = mqttOutgoingQosHandler;
            init();
        }

        @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlow
        void onNext(MqttPublishResult mqttPublishResult) {
            int i = this.state.get();
            if (i == 0) {
                if (this.state.compareAndSet(0, 1)) {
                    this.result = mqttPublishResult;
                    return;
                } else {
                    onNext(mqttPublishResult);
                    return;
                }
            }
            if (i == 2) {
                this.subscriber.onNext(mqttPublishResult);
                done(mqttPublishResult);
            } else {
                if (i != 3) {
                    return;
                }
                done(mqttPublishResult);
            }
        }

        private void done(MqttPublishResult mqttPublishResult) {
            if (mqttPublishResult.acknowledged()) {
                acknowledged(1L);
            }
        }

        @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlow
        void acknowledged(long j) {
            if (setDone()) {
                this.subscriber.onComplete();
            }
            this.outgoingQosHandler.request(1L);
        }

        @Override // org.reactivestreams.Subscription
        public void request(long j) {
            if (j <= 0 || this.state.getAndSet(2) != 1) {
                return;
            }
            this.eventLoop.execute(this);
        }

        @Override // com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop
        protected void onCancel() {
            if (this.state.getAndSet(3) == 1) {
                this.eventLoop.execute(this);
            }
        }

        @Override // java.lang.Runnable
        public void run() {
            MqttPublishResult mqttPublishResult = this.result;
            if (mqttPublishResult != null) {
                this.result = null;
                if (!isCancelled()) {
                    this.subscriber.onNext(mqttPublishResult);
                }
                done(mqttPublishResult);
            }
        }
    }
}
