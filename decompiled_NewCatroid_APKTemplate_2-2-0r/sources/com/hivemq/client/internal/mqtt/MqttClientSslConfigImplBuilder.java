package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.MqttClientSslConfigImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClientSslConfig;
import com.hivemq.client.mqtt.MqttClientSslConfigBuilder;
import com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManagerFactory;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttClientSslConfigImplBuilder<B extends MqttClientSslConfigImplBuilder<B>> {
    private ImmutableList<String> cipherSuites;
    private int handshakeTimeoutMs;
    private HostnameVerifier hostnameVerifier;
    private KeyManagerFactory keyManagerFactory;
    private ImmutableList<String> protocols;
    private TrustManagerFactory trustManagerFactory;

    abstract B self();

    MqttClientSslConfigImplBuilder() {
        this.handshakeTimeoutMs = 10000;
        this.hostnameVerifier = MqttClientSslConfigImpl.DEFAULT_HOSTNAME_VERIFIER;
    }

    MqttClientSslConfigImplBuilder(MqttClientSslConfigImpl mqttClientSslConfigImpl) {
        this.handshakeTimeoutMs = 10000;
        this.hostnameVerifier = MqttClientSslConfigImpl.DEFAULT_HOSTNAME_VERIFIER;
        if (mqttClientSslConfigImpl != null) {
            this.keyManagerFactory = mqttClientSslConfigImpl.getRawKeyManagerFactory();
            this.trustManagerFactory = mqttClientSslConfigImpl.getRawTrustManagerFactory();
            this.cipherSuites = mqttClientSslConfigImpl.getRawCipherSuites();
            this.protocols = mqttClientSslConfigImpl.getRawProtocols();
            this.handshakeTimeoutMs = (int) mqttClientSslConfigImpl.getHandshakeTimeoutMs();
            this.hostnameVerifier = mqttClientSslConfigImpl.getRawHostnameVerifier();
        }
    }

    public B keyManagerFactory(KeyManagerFactory keyManagerFactory) {
        this.keyManagerFactory = keyManagerFactory;
        return (B) self();
    }

    public B trustManagerFactory(TrustManagerFactory trustManagerFactory) {
        this.trustManagerFactory = trustManagerFactory;
        return (B) self();
    }

    public B cipherSuites(Collection<String> collection) {
        this.cipherSuites = collection == null ? null : ImmutableList.copyOf(collection, "Cipher suites");
        return (B) self();
    }

    public B protocols(Collection<String> collection) {
        this.protocols = collection == null ? null : ImmutableList.copyOf(collection, "Protocols");
        return (B) self();
    }

    public B handshakeTimeout(long j, TimeUnit timeUnit) {
        Checks.notNull(timeUnit, "Time unit");
        this.handshakeTimeoutMs = (int) Checks.range(timeUnit.toMillis(j), 0L, 2147483647L, "Handshake timeout in milliseconds");
        return (B) self();
    }

    public B hostnameVerifier(HostnameVerifier hostnameVerifier) {
        if (hostnameVerifier == null) {
            hostnameVerifier = MqttClientSslConfigImpl.DEFAULT_HOSTNAME_VERIFIER;
        }
        this.hostnameVerifier = hostnameVerifier;
        return (B) self();
    }

    public MqttClientSslConfigImpl build() {
        return new MqttClientSslConfigImpl(this.keyManagerFactory, this.trustManagerFactory, this.cipherSuites, this.protocols, this.handshakeTimeoutMs, this.hostnameVerifier);
    }

    public static class Default extends MqttClientSslConfigImplBuilder<Default> implements MqttClientSslConfigBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttClientSslConfigImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilder
        public /* bridge */ /* synthetic */ MqttClientSslConfig build() {
            return super.build();
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase cipherSuites(Collection collection) {
            return (MqttClientSslConfigBuilderBase) super.cipherSuites((Collection<String>) collection);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase handshakeTimeout(long j, TimeUnit timeUnit) {
            return (MqttClientSslConfigBuilderBase) super.handshakeTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase hostnameVerifier(HostnameVerifier hostnameVerifier) {
            return (MqttClientSslConfigBuilderBase) super.hostnameVerifier(hostnameVerifier);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase keyManagerFactory(KeyManagerFactory keyManagerFactory) {
            return (MqttClientSslConfigBuilderBase) super.keyManagerFactory(keyManagerFactory);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase protocols(Collection collection) {
            return (MqttClientSslConfigBuilderBase) super.protocols((Collection<String>) collection);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase trustManagerFactory(TrustManagerFactory trustManagerFactory) {
            return (MqttClientSslConfigBuilderBase) super.trustManagerFactory(trustManagerFactory);
        }

        public Default() {
        }

        Default(MqttClientSslConfigImpl mqttClientSslConfigImpl) {
            super(mqttClientSslConfigImpl);
        }
    }

    public static class Nested<P> extends MqttClientSslConfigImplBuilder<Nested<P>> implements MqttClientSslConfigBuilder.Nested<P> {
        private final Function<? super MqttClientSslConfigImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttClientSslConfigImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase cipherSuites(Collection collection) {
            return (MqttClientSslConfigBuilderBase) super.cipherSuites((Collection<String>) collection);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase handshakeTimeout(long j, TimeUnit timeUnit) {
            return (MqttClientSslConfigBuilderBase) super.handshakeTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase hostnameVerifier(HostnameVerifier hostnameVerifier) {
            return (MqttClientSslConfigBuilderBase) super.hostnameVerifier(hostnameVerifier);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase keyManagerFactory(KeyManagerFactory keyManagerFactory) {
            return (MqttClientSslConfigBuilderBase) super.keyManagerFactory(keyManagerFactory);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase protocols(Collection collection) {
            return (MqttClientSslConfigBuilderBase) super.protocols((Collection<String>) collection);
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilderBase trustManagerFactory(TrustManagerFactory trustManagerFactory) {
            return (MqttClientSslConfigBuilderBase) super.trustManagerFactory(trustManagerFactory);
        }

        Nested(MqttClientSslConfigImpl mqttClientSslConfigImpl, Function<? super MqttClientSslConfigImpl, P> function) {
            super(mqttClientSslConfigImpl);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.MqttClientSslConfigBuilder.Nested
        public P applySslConfig() {
            return this.parentConsumer.apply(build());
        }
    }
}
