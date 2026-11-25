package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.MqttProxyConfigBuilderBase;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttProxyConfigBuilderBase<B extends MqttProxyConfigBuilderBase<B>> {
    B address(InetSocketAddress inetSocketAddress);

    B handshakeTimeout(long j, TimeUnit timeUnit);

    B host(String str);

    B host(InetAddress inetAddress);

    B password(String str);

    B port(int i);

    B protocol(MqttProxyProtocol mqttProxyProtocol);

    B username(String str);
}
