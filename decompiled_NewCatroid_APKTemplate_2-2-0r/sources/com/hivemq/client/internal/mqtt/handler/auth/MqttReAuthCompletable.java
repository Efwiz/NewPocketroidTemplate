package com.hivemq.client.internal.mqtt.handler.auth;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttClientConnectionConfig;
import com.hivemq.client.internal.mqtt.exceptions.MqttClientStateExceptions;
import com.hivemq.client.internal.rx.CompletableFlow;
import io.netty.channel.ChannelHandler;
import io.reactivex.Completable;
import io.reactivex.CompletableObserver;
import io.reactivex.internal.disposables.EmptyDisposable;

/* JADX INFO: loaded from: classes10.dex */
public class MqttReAuthCompletable extends Completable {
    private final MqttClientConfig clientConfig;

    public MqttReAuthCompletable(MqttClientConfig mqttClientConfig) {
        this.clientConfig = mqttClientConfig;
    }

    @Override // io.reactivex.Completable
    protected void subscribeActual(CompletableObserver completableObserver) {
        MqttClientConnectionConfig rawConnectionConfig = this.clientConfig.getRawConnectionConfig();
        if (rawConnectionConfig == null) {
            EmptyDisposable.error(MqttClientStateExceptions.notConnected(), completableObserver);
            return;
        }
        if (rawConnectionConfig.getRawEnhancedAuthMechanism() == null) {
            EmptyDisposable.error(new UnsupportedOperationException("Reauth is not available if enhanced auth was not used during connect"), completableObserver);
            return;
        }
        ChannelHandler channelHandler = rawConnectionConfig.getChannel().pipeline().get(MqttAuthHandler.NAME);
        if (channelHandler == null) {
            EmptyDisposable.error(MqttClientStateExceptions.notConnected(), completableObserver);
        } else {
            if (!(channelHandler instanceof MqttReAuthHandler)) {
                EmptyDisposable.error(new UnsupportedOperationException("Auth is still pending"), completableObserver);
                return;
            }
            CompletableFlow completableFlow = new CompletableFlow(completableObserver);
            completableObserver.onSubscribe(completableFlow);
            ((MqttReAuthHandler) channelHandler).reauth(completableFlow);
        }
    }
}
