package com.hivemq.client.internal.mqtt.handler.subscribe;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.handler.util.FlowWithEventLoop;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes10.dex */
class MqttSubOrUnsubAckFlow<T> extends FlowWithEventLoop implements MqttSubscriptionFlow<T>, Disposable {
    private final SingleObserver<? super T> observer;

    MqttSubOrUnsubAckFlow(SingleObserver<? super T> singleObserver, MqttClientConfig mqttClientConfig) {
        super(mqttClientConfig);
        this.observer = singleObserver;
    }

    @Override // com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionFlow
    public void onSuccess(T t) {
        if (setDone()) {
            this.observer.onSuccess(t);
        }
    }

    @Override // com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionFlow
    public void onError(Throwable th) {
        if (setDone()) {
            this.observer.onError(th);
        }
    }
}
