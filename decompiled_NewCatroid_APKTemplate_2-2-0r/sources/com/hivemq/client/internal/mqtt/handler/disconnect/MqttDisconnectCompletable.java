package com.hivemq.client.internal.mqtt.handler.disconnect;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.mqtt.message.disconnect.MqttDisconnect;
import com.hivemq.client.internal.rx.CompletableFlow;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

/* JADX INFO: loaded from: classes10.dex */
public class MqttDisconnectCompletable extends Completable {
    private final MqttClientConfig clientConfig;
    private final MqttDisconnect disconnect;

    public MqttDisconnectCompletable(MqttClientConfig mqttClientConfig, MqttDisconnect mqttDisconnect) {
        this.clientConfig = mqttClientConfig;
        this.disconnect = mqttDisconnect;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        MqttClientConnectionConfig rawConnectionConfig = this.clientConfig.getRawConnectionConfig();
        if (rawConnectionConfig == null) {
            EmptyDisposable.error(MqttClientStateExceptions.notConnected(), completableObserver);
            return;
        }
        MqttDisconnectHandler mqttDisconnectHandler = (MqttDisconnectHandler) rawConnectionConfig.getChannel().pipeline().get(MqttDisconnectHandler.NAME);
        if (mqttDisconnectHandler == null) {
            EmptyDisposable.error(MqttClientStateExceptions.notConnected(), completableObserver);
            return;
        }
        CompletableFlow completableFlow = new CompletableFlow(completableObserver);
        completableObserver.onSubscribe(completableFlow);
        mqttDisconnectHandler.disconnect(this.disconnect, completableFlow);
    }
}
