package com.hivemq.client.internal.mqtt.handler.connect;

import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;

/* JADX INFO: loaded from: classes10.dex */
public class MqttConnAckFlow {
    private final int attempts;
    private final Disposable disposable;
    private boolean done;
    private final SingleObserver<? super Mqtt5ConnAck> observer;

    MqttConnAckFlow(SingleObserver<? super Mqtt5ConnAck> singleObserver) {
        this.observer = singleObserver;
        this.disposable = new MqttConnAckDisposable();
        this.attempts = 0;
    }

    MqttConnAckFlow(MqttConnAckFlow mqttConnAckFlow) {
        if (mqttConnAckFlow == null) {
            this.observer = null;
            this.disposable = new MqttConnAckDisposable();
            this.attempts = 0;
        } else {
            this.observer = mqttConnAckFlow.observer;
            this.disposable = mqttConnAckFlow.disposable;
            this.attempts = mqttConnAckFlow.attempts + 1;
        }
    }

    boolean setDone() {
        if (this.done) {
            return false;
        }
        this.done = true;
        return true;
    }

    void onSuccess(Mqtt5ConnAck mqtt5ConnAck) {
        SingleObserver<? super Mqtt5ConnAck> singleObserver = this.observer;
        if (singleObserver != null) {
            singleObserver.onSuccess(mqtt5ConnAck);
        }
    }

    void onError(Throwable th) {
        SingleObserver<? super Mqtt5ConnAck> singleObserver = this.observer;
        if (singleObserver != null) {
            singleObserver.onError(th);
        }
    }

    Disposable getDisposable() {
        return this.disposable;
    }

    int getAttempts() {
        return this.attempts;
    }

    private static class MqttConnAckDisposable implements Disposable {
        private volatile boolean disposed;

        private MqttConnAckDisposable() {
        }

        @Override // io.reactivex.disposables.Disposable
        public void dispose() {
            this.disposed = true;
        }

        @Override // io.reactivex.disposables.Disposable
        public boolean isDisposed() {
            return this.disposed;
        }
    }
}
