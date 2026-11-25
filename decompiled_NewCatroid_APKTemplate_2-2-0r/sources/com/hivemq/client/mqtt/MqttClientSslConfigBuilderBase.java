package com.hivemq.client.mqtt;

import com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientSslConfigBuilderBase<B extends MqttClientSslConfigBuilderBase<B>> {
    B cipherSuites(Collection<String> collection);

    B handshakeTimeout(long j, TimeUnit timeUnit);

    B hostnameVerifier(HostnameVerifier hostnameVerifier);

    B keyManagerFactory(KeyManagerFactory keyManagerFactory);

    B protocols(Collection<String> collection);

    B trustManagerFactory(TrustManagerFactory trustManagerFactory);
}
