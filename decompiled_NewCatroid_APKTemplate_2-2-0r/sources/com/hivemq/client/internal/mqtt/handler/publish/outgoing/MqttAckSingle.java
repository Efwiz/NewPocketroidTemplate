package com.hivemq.client.internal.mqtt.handler.publish.outgoing;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishResult;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5PublishResult;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.internal.disposables.EmptyDisposable;

/* JADX INFO: loaded from: classes10.dex */
public class MqttAckSingle extends Single<Mqtt5PublishResult> {
    private final MqttClientConfig clientConfig;
    private final MqttPublish publish;

    public MqttAckSingle(MqttClientConfig mqttClientConfig, MqttPublish mqttPublish) {
        this.clientConfig = mqttClientConfig;
        this.publish = mqttPublish;
    }

    @Override // io.reactivex.Single
    protected void subscribeActual(SingleObserver<? super Mqtt5PublishResult> singleObserver) {
        if (this.clientConfig.getState().isConnectedOrReconnect()) {
            MqttOutgoingQosHandler mqttOutgoingQosHandlerOutgoingQosHandler = this.clientConfig.getClientComponent().outgoingQosHandler();
            MqttPublishFlowables publishFlowables = mqttOutgoingQosHandlerOutgoingQosHandler.getPublishFlowables();
            Flow flow = new Flow(singleObserver, this.clientConfig, mqttOutgoingQosHandlerOutgoingQosHandler);
            singleObserver.onSubscribe(flow);
            publishFlowables.add(Flowable.just(new MqttPublishWithFlow(this.publish, flow)));
            return;
        }
        EmptyDisposable.error(MqttClientStateExceptions.notConnected(), singleObserver);
    }

    private static class Flow extends MqttAckFlow implements Disposable {
        static final /* synthetic */ boolean $assertionsDisabled = false;
        private final SingleObserver<? super Mqtt5PublishResult> observer;
        private final MqttOutgoingQosHandler outgoingQosHandler;
        private MqttPublishResult result;

        Flow(SingleObserver<? super Mqtt5PublishResult> singleObserver, MqttClientConfig mqttClientConfig, MqttOutgoingQosHandler mqttOutgoingQosHandler) {
            super(mqttClientConfig);
            this.observer = singleObserver;
            this.outgoingQosHandler = mqttOutgoingQosHandler;
            init();
        }

        @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlow
        void onNext(MqttPublishResult mqttPublishResult) {
            if (mqttPublishResult.acknowledged()) {
                done(mqttPublishResult);
            } else {
                this.result = mqttPublishResult;
            }
        }

        @Override // com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttAckFlow
        void acknowledged(long j) {
            MqttPublishResult mqttPublishResult = this.result;
            this.result = null;
            done(mqttPublishResult);
        }

        private void done(MqttPublishResult mqttPublishResult) {
            if (setDone()) {
                Throwable rawError = mqttPublishResult.getRawError();
                if (rawError == null) {
                    this.observer.onSuccess(mqttPublishResult);
                } else {
                    this.observer.onError(rawError);
                }
            }
            this.outgoingQosHandler.request(1L);
        }
    }
}
