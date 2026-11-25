package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase;
import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImpl;
import com.hivemq.client.internal.mqtt.lifecycle.MqttClientAutoReconnectImplBuilder;
import com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientViewBuilder;
import com.hivemq.client.internal.mqtt.util.MqttChecks;
import com.hivemq.client.internal.util.Checks;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClientBuilder;
import com.hivemq.client.mqtt.MqttClientBuilderBase;
import com.hivemq.client.mqtt.MqttClientExecutorConfig;
import com.hivemq.client.mqtt.MqttClientExecutorConfigBuilder;
import com.hivemq.client.mqtt.MqttClientSslConfig;
import com.hivemq.client.mqtt.MqttClientSslConfigBuilder;
import com.hivemq.client.mqtt.MqttClientTransportConfig;
import com.hivemq.client.mqtt.MqttClientTransportConfigBuilder;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.MqttWebSocketConfig;
import com.hivemq.client.mqtt.MqttWebSocketConfigBuilder;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnectBuilder;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public abstract class MqttRxClientBuilderBase<B extends MqttRxClientBuilderBase<B>> extends MqttClientTransportConfigImplBuilder<B> {
    private MqttClientAutoReconnectImpl autoReconnect;
    private ImmutableList.Builder<MqttClientConnectedListener> connectedListenersBuilder;
    private ImmutableList.Builder<MqttClientDisconnectedListener> disconnectedListenersBuilder;
    private MqttClientExecutorConfigImpl executorConfig;
    private MqttClientIdentifierImpl identifier;
    private MqttClientTransportConfigImpl transportConfig;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public abstract B self();

    protected MqttRxClientBuilderBase() {
        this.identifier = MqttClientIdentifierImpl.REQUEST_CLIENT_IDENTIFIER_FROM_SERVER;
        this.transportConfig = MqttClientTransportConfigImpl.DEFAULT;
        this.executorConfig = MqttClientExecutorConfigImpl.DEFAULT;
    }

    protected MqttRxClientBuilderBase(MqttRxClientBuilderBase<?> mqttRxClientBuilderBase) {
        super(mqttRxClientBuilderBase);
        this.identifier = MqttClientIdentifierImpl.REQUEST_CLIENT_IDENTIFIER_FROM_SERVER;
        this.transportConfig = MqttClientTransportConfigImpl.DEFAULT;
        this.executorConfig = MqttClientExecutorConfigImpl.DEFAULT;
        this.identifier = mqttRxClientBuilderBase.identifier;
        this.transportConfig = mqttRxClientBuilderBase.transportConfig;
        this.executorConfig = mqttRxClientBuilderBase.executorConfig;
        this.autoReconnect = mqttRxClientBuilderBase.autoReconnect;
        this.connectedListenersBuilder = mqttRxClientBuilderBase.connectedListenersBuilder;
        this.disconnectedListenersBuilder = mqttRxClientBuilderBase.disconnectedListenersBuilder;
    }

    public B identifier(String str) {
        this.identifier = MqttClientIdentifierImpl.of(str);
        return (B) self();
    }

    public B identifier(MqttClientIdentifier mqttClientIdentifier) {
        this.identifier = MqttChecks.clientIdentifier(mqttClientIdentifier);
        return (B) self();
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B serverAddress(InetSocketAddress inetSocketAddress) {
        this.transportConfig = null;
        return (B) super.serverAddress(inetSocketAddress);
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B serverHost(String str) {
        this.transportConfig = null;
        return (B) super.serverHost(str);
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B serverHost(InetAddress inetAddress) {
        this.transportConfig = null;
        return (B) super.serverHost(inetAddress);
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B serverPort(int i) {
        this.transportConfig = null;
        return (B) super.serverPort(i);
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B sslWithDefaultConfig() {
        this.transportConfig = null;
        return (B) super.sslWithDefaultConfig();
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B sslConfig(MqttClientSslConfig mqttClientSslConfig) {
        return (B) super.sslConfig(mqttClientSslConfig);
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B webSocketWithDefaultConfig() {
        this.transportConfig = null;
        return (B) super.webSocketWithDefaultConfig();
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public B webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
        this.transportConfig = null;
        return (B) super.webSocketConfig(mqttWebSocketConfig);
    }

    public B transportConfig(MqttClientTransportConfig mqttClientTransportConfig) {
        MqttClientTransportConfigImpl mqttClientTransportConfigImpl = (MqttClientTransportConfigImpl) Checks.notImplemented(mqttClientTransportConfig, MqttClientTransportConfigImpl.class, "Transport config");
        this.transportConfig = mqttClientTransportConfigImpl;
        set(mqttClientTransportConfigImpl);
        return (B) self();
    }

    public MqttClientTransportConfigImplBuilder.Nested<B> transportConfig() {
        return new MqttClientTransportConfigImplBuilder.Nested<>(this, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.transportConfig((MqttClientTransportConfigImpl) obj);
            }
        });
    }

    public B executorConfig(MqttClientExecutorConfig mqttClientExecutorConfig) {
        this.executorConfig = (MqttClientExecutorConfigImpl) Checks.notImplemented(mqttClientExecutorConfig, MqttClientExecutorConfigImpl.class, "Executor config");
        return (B) self();
    }

    public MqttClientExecutorConfigImplBuilder.Nested<B> executorConfig() {
        return new MqttClientExecutorConfigImplBuilder.Nested<>(this.executorConfig, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.executorConfig((MqttClientExecutorConfigImpl) obj);
            }
        });
    }

    public B automaticReconnectWithDefaultConfig() {
        this.autoReconnect = MqttClientAutoReconnectImpl.DEFAULT;
        return (B) self();
    }

    public B automaticReconnect(MqttClientAutoReconnect mqttClientAutoReconnect) {
        this.autoReconnect = (MqttClientAutoReconnectImpl) Checks.notImplementedOrNull(mqttClientAutoReconnect, MqttClientAutoReconnectImpl.class, "Automatic reconnect");
        return (B) self();
    }

    public MqttClientAutoReconnectImplBuilder.Nested<B> automaticReconnect() {
        return new MqttClientAutoReconnectImplBuilder.Nested<>(this.autoReconnect, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.automaticReconnect((MqttClientAutoReconnectImpl) obj);
            }
        });
    }

    public B addConnectedListener(MqttClientConnectedListener mqttClientConnectedListener) {
        Checks.notNull(mqttClientConnectedListener, "Connected listener");
        if (this.connectedListenersBuilder == null) {
            this.connectedListenersBuilder = ImmutableList.builder();
        }
        this.connectedListenersBuilder.add(mqttClientConnectedListener);
        return (B) self();
    }

    public B addDisconnectedListener(MqttClientDisconnectedListener mqttClientDisconnectedListener) {
        Checks.notNull(mqttClientDisconnectedListener, "Disconnected listener");
        if (this.disconnectedListenersBuilder == null) {
            this.disconnectedListenersBuilder = ImmutableList.builder();
        }
        this.disconnectedListenersBuilder.add(mqttClientDisconnectedListener);
        return (B) self();
    }

    @Override // com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    MqttClientTransportConfigImpl buildTransportConfig() {
        MqttClientTransportConfigImpl mqttClientTransportConfigImpl = this.transportConfig;
        return mqttClientTransportConfigImpl == null ? super.buildTransportConfig() : mqttClientTransportConfigImpl;
    }

    private ImmutableList<MqttClientConnectedListener> buildConnectedListeners() {
        ImmutableList.Builder<MqttClientConnectedListener> builder = this.connectedListenersBuilder;
        if (builder == null) {
            return ImmutableList.of();
        }
        return builder.build();
    }

    private ImmutableList<MqttClientDisconnectedListener> buildDisconnectedListeners() {
        ImmutableList.Builder<MqttClientDisconnectedListener> builder = this.disconnectedListenersBuilder;
        if (builder == null) {
            MqttClientAutoReconnectImpl mqttClientAutoReconnectImpl = this.autoReconnect;
            if (mqttClientAutoReconnectImpl == null) {
                return ImmutableList.of();
            }
            return ImmutableList.of(mqttClientAutoReconnectImpl);
        }
        if (this.autoReconnect == null) {
            return builder.build();
        }
        return ImmutableList.builder().add(this.autoReconnect).addAll(this.disconnectedListenersBuilder.build()).build();
    }

    protected MqttClientConfig buildClientConfig(MqttVersion mqttVersion, MqttClientAdvancedConfig mqttClientAdvancedConfig, MqttClientConfig.ConnectDefaults connectDefaults) {
        return new MqttClientConfig(mqttVersion, this.identifier, buildTransportConfig(), this.executorConfig, mqttClientAdvancedConfig, connectDefaults, buildConnectedListeners(), buildDisconnectedListeners());
    }

    public static class Choose extends MqttRxClientBuilderBase<Choose> implements MqttClientBuilder {
        /* JADX INFO: Access modifiers changed from: protected */
        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public Choose self() {
            return this;
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase addConnectedListener(MqttClientConnectedListener mqttClientConnectedListener) {
            return (MqttClientBuilderBase) super.addConnectedListener(mqttClientConnectedListener);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase addDisconnectedListener(MqttClientDisconnectedListener mqttClientDisconnectedListener) {
            return (MqttClientBuilderBase) super.addDisconnectedListener(mqttClientDisconnectedListener);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase automaticReconnect(MqttClientAutoReconnect mqttClientAutoReconnect) {
            return (MqttClientBuilderBase) super.automaticReconnect(mqttClientAutoReconnect);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientAutoReconnectBuilder.Nested automaticReconnect() {
            return super.automaticReconnect();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase automaticReconnectWithDefaultConfig() {
            return (MqttClientBuilderBase) super.automaticReconnectWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase executorConfig(MqttClientExecutorConfig mqttClientExecutorConfig) {
            return (MqttClientBuilderBase) super.executorConfig(mqttClientExecutorConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientExecutorConfigBuilder.Nested executorConfig() {
            return super.executorConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase identifier(MqttClientIdentifier mqttClientIdentifier) {
            return (MqttClientBuilderBase) super.identifier(mqttClientIdentifier);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase identifier(String str) {
            return (MqttClientBuilderBase) super.identifier(str);
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder serverAddress(InetSocketAddress inetSocketAddress) {
            return super.serverAddress(inetSocketAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: serverAddress, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2673serverAddress(InetSocketAddress inetSocketAddress) {
            return (MqttClientBuilderBase) super.serverAddress(inetSocketAddress);
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder serverHost(String str) {
            return super.serverHost(str);
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder serverHost(InetAddress inetAddress) {
            return super.serverHost(inetAddress);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: serverHost, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2674serverHost(String str) {
            return (MqttClientBuilderBase) super.serverHost(str);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: serverHost, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2675serverHost(InetAddress inetAddress) {
            return (MqttClientBuilderBase) super.serverHost(inetAddress);
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder serverPort(int i) {
            return super.serverPort(i);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: serverPort, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2676serverPort(int i) {
            return (MqttClientBuilderBase) super.serverPort(i);
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder sslConfig(MqttClientSslConfig mqttClientSslConfig) {
            return super.sslConfig(mqttClientSslConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: sslConfig, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2677sslConfig(MqttClientSslConfig mqttClientSslConfig) {
            return (MqttClientBuilderBase) super.sslConfig(mqttClientSslConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientSslConfigBuilder.Nested sslConfig() {
            return super.sslConfig();
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder sslWithDefaultConfig() {
            return super.sslWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: sslWithDefaultConfig, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2678sslWithDefaultConfig() {
            return (MqttClientBuilderBase) super.sslWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientBuilderBase transportConfig(MqttClientTransportConfig mqttClientTransportConfig) {
            return (MqttClientBuilderBase) super.transportConfig(mqttClientTransportConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttClientTransportConfigBuilder.Nested transportConfig() {
            return super.transportConfig();
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
            return super.webSocketConfig(mqttWebSocketConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: webSocketConfig, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2679webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
            return (MqttClientBuilderBase) super.webSocketConfig(mqttWebSocketConfig);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilder.Nested webSocketConfig() {
            return super.webSocketConfig();
        }

        @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
        public /* bridge */ /* synthetic */ MqttClientTransportConfigImplBuilder webSocketWithDefaultConfig() {
            return super.webSocketWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
        /* JADX INFO: renamed from: webSocketWithDefaultConfig, reason: collision with other method in class */
        public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2680webSocketWithDefaultConfig() {
            return (MqttClientBuilderBase) super.webSocketWithDefaultConfig();
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilder
        public Mqtt3RxClientViewBuilder useMqttVersion3() {
            return new Mqtt3RxClientViewBuilder(this);
        }

        @Override // com.hivemq.client.mqtt.MqttClientBuilder
        public MqttRxClientBuilder useMqttVersion5() {
            return new MqttRxClientBuilder(this);
        }
    }
}
