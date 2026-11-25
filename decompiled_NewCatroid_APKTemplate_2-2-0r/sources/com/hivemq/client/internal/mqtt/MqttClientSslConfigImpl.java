package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClientSslConfig;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientSslConfigImpl implements MqttClientSslConfig {
    static final MqttClientSslConfigImpl DEFAULT;
    static final HostnameVerifier DEFAULT_HOSTNAME_VERIFIER;
    private final ImmutableList<String> cipherSuites;
    private final int handshakeTimeoutMs;
    private final HostnameVerifier hostnameVerifier;
    private final KeyManagerFactory keyManagerFactory;
    private final ImmutableList<String> protocols;
    private final TrustManagerFactory trustManagerFactory;

    static {
        HostnameVerifier defaultHostnameVerifier;
        try {
            new SSLParameters().setEndpointIdentificationAlgorithm("HTTPS");
            defaultHostnameVerifier = null;
        } catch (NoSuchMethodError unused) {
            defaultHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();
        }
        HostnameVerifier hostnameVerifier = defaultHostnameVerifier;
        DEFAULT_HOSTNAME_VERIFIER = hostnameVerifier;
        DEFAULT = new MqttClientSslConfigImpl(null, null, null, null, 10000, hostnameVerifier);
    }

    MqttClientSslConfigImpl(KeyManagerFactory keyManagerFactory, TrustManagerFactory trustManagerFactory, ImmutableList<String> immutableList, ImmutableList<String> immutableList2, int i, HostnameVerifier hostnameVerifier) {
        this.keyManagerFactory = keyManagerFactory;
        this.trustManagerFactory = trustManagerFactory;
        this.cipherSuites = immutableList;
        this.protocols = immutableList2;
        this.handshakeTimeoutMs = i;
        this.hostnameVerifier = hostnameVerifier;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public Optional<KeyManagerFactory> getKeyManagerFactory() {
        return Optional.ofNullable(this.keyManagerFactory);
    }

    public KeyManagerFactory getRawKeyManagerFactory() {
        return this.keyManagerFactory;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public Optional<TrustManagerFactory> getTrustManagerFactory() {
        return Optional.ofNullable(this.trustManagerFactory);
    }

    public TrustManagerFactory getRawTrustManagerFactory() {
        return this.trustManagerFactory;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public Optional<List<String>> getCipherSuites() {
        return Optional.ofNullable(this.cipherSuites);
    }

    public ImmutableList<String> getRawCipherSuites() {
        return this.cipherSuites;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public Optional<List<String>> getProtocols() {
        return Optional.ofNullable(this.protocols);
    }

    public ImmutableList<String> getRawProtocols() {
        return this.protocols;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public long getHandshakeTimeoutMs() {
        return this.handshakeTimeoutMs;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public Optional<HostnameVerifier> getHostnameVerifier() {
        return Optional.ofNullable(this.hostnameVerifier);
    }

    public HostnameVerifier getRawHostnameVerifier() {
        return this.hostnameVerifier;
    }

    @Override // com.hivemq.client.mqtt.MqttClientSslConfig
    public MqttClientSslConfigImplBuilder.Default extend() {
        return new MqttClientSslConfigImplBuilder.Default(this);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MqttClientSslConfigImpl)) {
            return false;
        }
        MqttClientSslConfigImpl mqttClientSslConfigImpl = (MqttClientSslConfigImpl) obj;
        return Objects.equals(this.keyManagerFactory, mqttClientSslConfigImpl.keyManagerFactory) && Objects.equals(this.trustManagerFactory, mqttClientSslConfigImpl.trustManagerFactory) && Objects.equals(this.cipherSuites, mqttClientSslConfigImpl.cipherSuites) && Objects.equals(this.protocols, mqttClientSslConfigImpl.protocols) && this.handshakeTimeoutMs == mqttClientSslConfigImpl.handshakeTimeoutMs && Objects.equals(this.hostnameVerifier, mqttClientSslConfigImpl.hostnameVerifier);
    }

    public int hashCode() {
        return (((((((((Objects.hashCode(this.keyManagerFactory) * 31) + Objects.hashCode(this.trustManagerFactory)) * 31) + Objects.hashCode(this.cipherSuites)) * 31) + Objects.hashCode(this.protocols)) * 31) + Integer.hashCode(this.handshakeTimeoutMs)) * 31) + Objects.hashCode(this.hostnameVerifier);
    }
}
