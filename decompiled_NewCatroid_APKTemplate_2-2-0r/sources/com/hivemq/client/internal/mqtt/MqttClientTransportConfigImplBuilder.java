package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.InetSocketAddressUtil;
import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.MqttClientSslConfig;
import com.hivemq.client.mqtt.MqttClientSslConfigBuilder;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase;
import com.hivemq.client.mqtt.MqttProxyConfig;
import com.hivemq.client.mqtt.MqttProxyConfigBuilder;
import com.hivemq.client.mqtt.MqttWebSocketConfig;
import com.hivemq.client.mqtt.MqttWebSocketConfigBuilder;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttClientTransportConfigImplBuilder<B extends MqttClientTransportConfigImplBuilder<B>> {
    private InetSocketAddress localAddress;
    private int mqttConnectTimeoutMs;
    private MqttProxyConfigImpl proxyConfig;
    private InetSocketAddress serverAddress;
    private Object serverHost;
    private int serverPort;
    private int socketConnectTimeoutMs;
    private MqttClientSslConfigImpl sslConfig;
    private MqttWebSocketConfigImpl webSocketConfig;

    abstract B self();

    MqttClientTransportConfigImplBuilder() {
        this.serverHost = "localhost";
        this.serverPort = -1;
        this.socketConnectTimeoutMs = 10000;
        this.mqttConnectTimeoutMs = 60000;
    }

    MqttClientTransportConfigImplBuilder(MqttClientTransportConfigImpl mqttClientTransportConfigImpl) {
        this.serverHost = "localhost";
        this.serverPort = -1;
        this.socketConnectTimeoutMs = 10000;
        this.mqttConnectTimeoutMs = 60000;
        set(mqttClientTransportConfigImpl);
    }

    MqttClientTransportConfigImplBuilder(MqttClientTransportConfigImplBuilder<?> mqttClientTransportConfigImplBuilder) {
        this.serverHost = "localhost";
        this.serverPort = -1;
        this.socketConnectTimeoutMs = 10000;
        this.mqttConnectTimeoutMs = 60000;
        this.serverAddress = mqttClientTransportConfigImplBuilder.serverAddress;
        this.serverHost = mqttClientTransportConfigImplBuilder.serverHost;
        this.serverPort = mqttClientTransportConfigImplBuilder.serverPort;
        this.localAddress = mqttClientTransportConfigImplBuilder.localAddress;
        this.sslConfig = mqttClientTransportConfigImplBuilder.sslConfig;
        this.webSocketConfig = mqttClientTransportConfigImplBuilder.webSocketConfig;
        this.proxyConfig = mqttClientTransportConfigImplBuilder.proxyConfig;
        this.socketConnectTimeoutMs = mqttClientTransportConfigImplBuilder.socketConnectTimeoutMs;
        this.mqttConnectTimeoutMs = mqttClientTransportConfigImplBuilder.mqttConnectTimeoutMs;
    }

    void set(MqttClientTransportConfigImpl mqttClientTransportConfigImpl) {
        this.serverAddress = mqttClientTransportConfigImpl.getServerAddress();
        this.localAddress = mqttClientTransportConfigImpl.getRawLocalAddress();
        this.sslConfig = mqttClientTransportConfigImpl.getRawSslConfig();
        this.webSocketConfig = mqttClientTransportConfigImpl.getRawWebSocketConfig();
        this.proxyConfig = mqttClientTransportConfigImpl.getRawProxyConfig();
        this.socketConnectTimeoutMs = mqttClientTransportConfigImpl.getSocketConnectTimeoutMs();
        this.mqttConnectTimeoutMs = mqttClientTransportConfigImpl.getMqttConnectTimeoutMs();
    }

    public B serverAddress(InetSocketAddress inetSocketAddress) {
        this.serverAddress = (InetSocketAddress) Checks.notNull(inetSocketAddress, "Server address");
        return (B) self();
    }

    public B serverHost(String str) {
        setServerHost(Checks.notEmpty(str, "Server host"));
        return (B) self();
    }

    public B serverHost(InetAddress inetAddress) {
        setServerHost(Checks.notNull(inetAddress, "Server host"));
        return (B) self();
    }

    private void setServerHost(Object obj) {
        this.serverHost = obj;
        InetSocketAddress inetSocketAddress = this.serverAddress;
        if (inetSocketAddress != null) {
            this.serverPort = inetSocketAddress.getPort();
            this.serverAddress = null;
        }
    }

    public B serverPort(int i) {
        this.serverPort = Checks.unsignedShort(i, "Server port");
        InetSocketAddress inetSocketAddress = this.serverAddress;
        if (inetSocketAddress != null) {
            InetAddress address = inetSocketAddress.getAddress();
            if (address != null) {
                this.serverHost = address;
            } else {
                this.serverHost = this.serverAddress.getHostString();
            }
            this.serverAddress = null;
        }
        return (B) self();
    }

    public B localAddress(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress == null) {
            this.localAddress = null;
        } else {
            this.localAddress = checkLocalAddress(inetSocketAddress);
        }
        return (B) self();
    }

    public B localAddress(String str) {
        if (str == null) {
            removeLocalAddress();
        } else {
            this.localAddress = checkLocalAddress(new InetSocketAddress(str, getLocalPort()));
        }
        return (B) self();
    }

    public B localAddress(InetAddress inetAddress) {
        if (inetAddress == null) {
            removeLocalAddress();
        } else {
            this.localAddress = new InetSocketAddress(inetAddress, getLocalPort());
        }
        return (B) self();
    }

    private InetSocketAddress checkLocalAddress(InetSocketAddress inetSocketAddress) {
        if (inetSocketAddress.isUnresolved()) {
            throw new IllegalArgumentException("Local bind address must not be unresolved.");
        }
        return inetSocketAddress;
    }

    private void removeLocalAddress() {
        InetSocketAddress inetSocketAddress = this.localAddress;
        if (inetSocketAddress == null || inetSocketAddress.getAddress() == null) {
            return;
        }
        if (this.localAddress.getPort() == 0) {
            this.localAddress = null;
        } else {
            this.localAddress = new InetSocketAddress(this.localAddress.getPort());
        }
    }

    private int getLocalPort() {
        InetSocketAddress inetSocketAddress = this.localAddress;
        if (inetSocketAddress == null) {
            return 0;
        }
        return inetSocketAddress.getPort();
    }

    public B localPort(int i) {
        if (i == 0) {
            InetSocketAddress inetSocketAddress = this.localAddress;
            if (inetSocketAddress != null && inetSocketAddress.getPort() != 0) {
                if (this.localAddress.getAddress() == null) {
                    this.localAddress = null;
                } else {
                    this.localAddress = new InetSocketAddress(this.localAddress.getAddress(), 0);
                }
            }
        } else {
            InetSocketAddress inetSocketAddress2 = this.localAddress;
            this.localAddress = new InetSocketAddress(inetSocketAddress2 != null ? inetSocketAddress2.getAddress() : null, i);
        }
        return (B) self();
    }

    public B sslWithDefaultConfig() {
        this.sslConfig = MqttClientSslConfigImpl.DEFAULT;
        return (B) self();
    }

    public B sslConfig(MqttClientSslConfig mqttClientSslConfig) {
        this.sslConfig = (MqttClientSslConfigImpl) Checks.notImplementedOrNull(mqttClientSslConfig, MqttClientSslConfigImpl.class, "SSL config");
        return (B) self();
    }

    public MqttClientSslConfigImplBuilder.Nested<B> sslConfig() {
        return new MqttClientSslConfigImplBuilder.Nested<>(this.sslConfig, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.sslConfig((MqttClientSslConfigImpl) obj);
            }
        });
    }

    public B webSocketWithDefaultConfig() {
        this.webSocketConfig = MqttWebSocketConfigImpl.DEFAULT;
        return (B) self();
    }

    public B webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
        this.webSocketConfig = (MqttWebSocketConfigImpl) Checks.notImplementedOrNull(mqttWebSocketConfig, MqttWebSocketConfigImpl.class, "WebSocket config");
        return (B) self();
    }

    public MqttWebSocketConfigImplBuilder.Nested<B> webSocketConfig() {
        return new MqttWebSocketConfigImplBuilder.Nested<>(this.webSocketConfig, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.webSocketConfig((MqttWebSocketConfigImpl) obj);
            }
        });
    }

    public B proxyConfig(MqttProxyConfig mqttProxyConfig) {
        this.proxyConfig = (MqttProxyConfigImpl) Checks.notImplementedOrNull(mqttProxyConfig, MqttProxyConfigImpl.class, "Proxy config");
        return (B) self();
    }

    public MqttProxyConfigImplBuilder.Nested<B> proxyConfig() {
        return new MqttProxyConfigImplBuilder.Nested<>(this.proxyConfig, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.proxyConfig((MqttProxyConfigImpl) obj);
            }
        });
    }

    public B socketConnectTimeout(long j, TimeUnit timeUnit) {
        Checks.notNull(timeUnit, "Time unit");
        this.socketConnectTimeoutMs = (int) Checks.range(timeUnit.toMillis(j), 0L, 2147483647L, "Socket connect timeout in milliseconds");
        return (B) self();
    }

    public B mqttConnectTimeout(long j, TimeUnit timeUnit) {
        Checks.notNull(timeUnit, "Time unit");
        this.mqttConnectTimeoutMs = (int) Checks.range(timeUnit.toMillis(j), 0L, 2147483647L, "MQTT connect timeout in milliseconds");
        return (B) self();
    }

    private InetSocketAddress getServerAddress() {
        InetSocketAddress inetSocketAddress = this.serverAddress;
        if (inetSocketAddress != null) {
            return inetSocketAddress;
        }
        Object obj = this.serverHost;
        if (obj instanceof InetAddress) {
            return new InetSocketAddress((InetAddress) this.serverHost, getServerPort());
        }
        return InetSocketAddressUtil.create((String) obj, getServerPort());
    }

    private int getServerPort() {
        int i = this.serverPort;
        if (i != -1) {
            return i;
        }
        if (this.sslConfig == null) {
            if (this.webSocketConfig == null) {
                return MqttClient.DEFAULT_SERVER_PORT;
            }
            return 80;
        }
        if (this.webSocketConfig == null) {
            return MqttClient.DEFAULT_SERVER_PORT_SSL;
        }
        return 443;
    }

    MqttClientTransportConfigImpl buildTransportConfig() {
        return new MqttClientTransportConfigImpl(getServerAddress(), this.localAddress, this.sslConfig, this.webSocketConfig, this.proxyConfig, this.socketConnectTimeoutMs, this.mqttConnectTimeoutMs);
    }

    public static class Default extends MqttClientTransportConfigImplBuilder<Default> implements MqttClientTransportConfigBuilder {
        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public Default self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localAddress(String str) {
            return (MqttClientTransportConfigBuilderBase) super.localAddress(str);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localAddress(InetAddress inetAddress) {
            return (MqttClientTransportConfigBuilderBase) super.localAddress(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localAddress(InetSocketAddress inetSocketAddress) {
            return (MqttClientTransportConfigBuilderBase) super.localAddress(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localPort(int i) {
            return (MqttClientTransportConfigBuilderBase) super.localPort(i);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase mqttConnectTimeout(long j, TimeUnit timeUnit) {
            return (MqttClientTransportConfigBuilderBase) super.mqttConnectTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase proxyConfig(MqttProxyConfig mqttProxyConfig) {
            return (MqttClientTransportConfigBuilderBase) super.proxyConfig(mqttProxyConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilder.Nested proxyConfig() {
            return super.proxyConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverAddress(InetSocketAddress inetSocketAddress) {
            return (MqttClientTransportConfigBuilderBase) super.serverAddress(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverHost(String str) {
            return (MqttClientTransportConfigBuilderBase) super.serverHost(str);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverHost(InetAddress inetAddress) {
            return (MqttClientTransportConfigBuilderBase) super.serverHost(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverPort(int i) {
            return (MqttClientTransportConfigBuilderBase) super.serverPort(i);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase socketConnectTimeout(long j, TimeUnit timeUnit) {
            return (MqttClientTransportConfigBuilderBase) super.socketConnectTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilder.Nested sslConfig() {
            return super.sslConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase sslConfig(MqttClientSslConfig mqttClientSslConfig) {
            return (MqttClientTransportConfigBuilderBase) super.sslConfig(mqttClientSslConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase sslWithDefaultConfig() {
            return (MqttClientTransportConfigBuilderBase) super.sslWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
            return (MqttClientTransportConfigBuilderBase) super.webSocketConfig(mqttWebSocketConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilder.Nested webSocketConfig() {
            return super.webSocketConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase webSocketWithDefaultConfig() {
            return (MqttClientTransportConfigBuilderBase) super.webSocketWithDefaultConfig();
        }

        public Default() {
        }

        Default(MqttClientTransportConfigImpl mqttClientTransportConfigImpl) {
            super(mqttClientTransportConfigImpl);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilder
        public MqttClientTransportConfigImpl build() {
            return buildTransportConfig();
        }
    }

    public static class Nested<P> extends MqttClientTransportConfigImplBuilder<Nested<P>> implements MqttClientTransportConfigBuilder.Nested<P> {
        private final Function<? super MqttClientTransportConfigImpl, P> parentConsumer;

        /* JADX INFO: Access modifiers changed from: package-private */
        @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public Nested<P> self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localAddress(String str) {
            return (MqttClientTransportConfigBuilderBase) super.localAddress(str);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localAddress(InetAddress inetAddress) {
            return (MqttClientTransportConfigBuilderBase) super.localAddress(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localAddress(InetSocketAddress inetSocketAddress) {
            return (MqttClientTransportConfigBuilderBase) super.localAddress(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase localPort(int i) {
            return (MqttClientTransportConfigBuilderBase) super.localPort(i);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase mqttConnectTimeout(long j, TimeUnit timeUnit) {
            return (MqttClientTransportConfigBuilderBase) super.mqttConnectTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase proxyConfig(MqttProxyConfig mqttProxyConfig) {
            return (MqttClientTransportConfigBuilderBase) super.proxyConfig(mqttProxyConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttProxyConfigBuilder.Nested proxyConfig() {
            return super.proxyConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverAddress(InetSocketAddress inetSocketAddress) {
            return (MqttClientTransportConfigBuilderBase) super.serverAddress(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverHost(String str) {
            return (MqttClientTransportConfigBuilderBase) super.serverHost(str);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverHost(InetAddress inetAddress) {
            return (MqttClientTransportConfigBuilderBase) super.serverHost(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase serverPort(int i) {
            return (MqttClientTransportConfigBuilderBase) super.serverPort(i);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase socketConnectTimeout(long j, TimeUnit timeUnit) {
            return (MqttClientTransportConfigBuilderBase) super.socketConnectTimeout(j, timeUnit);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilder.Nested sslConfig() {
            return super.sslConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase sslConfig(MqttClientSslConfig mqttClientSslConfig) {
            return (MqttClientTransportConfigBuilderBase) super.sslConfig(mqttClientSslConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase sslWithDefaultConfig() {
            return (MqttClientTransportConfigBuilderBase) super.sslWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
            return (MqttClientTransportConfigBuilderBase) super.webSocketConfig(mqttWebSocketConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilder.Nested webSocketConfig() {
            return super.webSocketConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilderBase webSocketWithDefaultConfig() {
            return (MqttClientTransportConfigBuilderBase) super.webSocketWithDefaultConfig();
        }

        public Nested(MqttClientTransportConfigImpl mqttClientTransportConfigImpl, Function<? super MqttClientTransportConfigImpl, P> function) {
            super(mqttClientTransportConfigImpl);
            this.parentConsumer = function;
        }

        Nested(MqttClientTransportConfigImplBuilder<?> mqttClientTransportConfigImplBuilder, Function<? super MqttClientTransportConfigImpl, P> function) {
            super(mqttClientTransportConfigImplBuilder);
            this.parentConsumer = function;
        }

        @Override // com.hivemq.client.mqtt.MqttClientTransportConfigBuilder.Nested
        public P applyTransportConfig() {
            return this.parentConsumer.apply(buildTransportConfig());
        }
    }
}
