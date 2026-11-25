package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.MqttWebSocketConfigBuilderBase;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttWebSocketConfigBuilderBase<B extends MqttWebSocketConfigBuilderBase<B>> {
    B handshakeTimeout(long j, TimeUnit timeUnit);

    B queryString(String str);

    B serverPath(String str);

    B subprotocol(String str);
}
