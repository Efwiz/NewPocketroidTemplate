package com.hivemq.client.mqtt;

import com.hivemq.client.internal.mqtt.MqttClientSslConfigImplBuilder;
import java.util.List;
import java.util.Optional;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: classes10.dex */
public interface MqttClientSslConfig {
    public static final long DEFAULT_HANDSHAKE_TIMEOUT_MS = 10000;

    MqttClientSslConfigBuilder extend();

    Optional<List<String>> getCipherSuites();

    long getHandshakeTimeoutMs();

    Optional<HostnameVerifier> getHostnameVerifier();

    Optional<KeyManagerFactory> getKeyManagerFactory();

    Optional<List<String>> getProtocols();

    Optional<TrustManagerFactory> getTrustManagerFactory();

    static MqttClientSslConfigBuilder builder() {
        return new MqttClientSslConfigImplBuilder.Default();
    }
}
