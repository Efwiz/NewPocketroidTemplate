package com.hivemq.client.internal.mqtt.handler.subscribe;

import io.netty.channel.EventLoop;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttSubscriptionFlow<M> {
    EventLoop getEventLoop();

    boolean init();

    boolean isCancelled();

    void onError(Throwable th);

    void onSuccess(M m);
}
