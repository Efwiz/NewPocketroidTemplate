package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfig;
import com.hivemq.client.internal.mqtt.datatypes.MqttClientIdentifierImpl;
import com.hivemq.client.internal.mqtt.ioc.ClientComponent;
import com.hivemq.client.internal.mqtt.ioc.SingletonComponent;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.netty.NettyEventLoopProvider;
import com.hivemq.client.internal.util.ExecutorUtil;
import com.hivemq.client.internal.util.collections.ImmutableList;
import com.hivemq.client.mqtt.MqttClientState;
import com.hivemq.client.mqtt.MqttVersion;
import com.hivemq.client.mqtt.datatypes.MqttClientIdentifier;
import com.hivemq.client.mqtt.lifecycle.MqttClientAutoReconnect;
import com.hivemq.client.mqtt.lifecycle.MqttClientConnectedListener;
import com.hivemq.client.mqtt.lifecycle.MqttClientDisconnectedListener;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig;
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConnectionConfig;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5WillPublish;
import io.netty.channel.EventLoop;
import io.netty.handler.ssl.SslContext;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/* JADX INFO: loaded from: classes10.dex */
public class MqttClientConfig implements Mqtt5ClientConfig {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private final MqttClientAdvancedConfig advancedConfig;
    private volatile MqttClientIdentifierImpl clientIdentifier;
    private final ConnectDefaults connectDefaults;
    private final ImmutableList<MqttClientConnectedListener> connectedListeners;
    private volatile MqttClientConnectionConfig connectionConfig;
    private SslContext currentSslContext;
    private MqttClientTransportConfigImpl currentTransportConfig;
    private final ImmutableList<MqttClientDisconnectedListener> disconnectedListeners;
    private volatile EventLoop eventLoop;
    private long eventLoopAcquireCount;
    private int eventLoopAcquires;
    private final MqttClientExecutorConfigImpl executorConfig;
    private final MqttVersion mqttVersion;
    private boolean republishIfSessionExpired;
    private boolean resubscribeIfSessionExpired;
    private final MqttClientTransportConfigImpl transportConfig;
    private final ClientComponent clientComponent = SingletonComponent.INSTANCE.clientComponentBuilder().clientConfig(this).build();
    private final AtomicReference<MqttClientState> state = new AtomicReference<>(MqttClientState.DISCONNECTED);

    public MqttClientConfig(MqttVersion mqttVersion, MqttClientIdentifierImpl mqttClientIdentifierImpl, MqttClientTransportConfigImpl mqttClientTransportConfigImpl, MqttClientExecutorConfigImpl mqttClientExecutorConfigImpl, MqttClientAdvancedConfig mqttClientAdvancedConfig, ConnectDefaults connectDefaults, ImmutableList<MqttClientConnectedListener> immutableList, ImmutableList<MqttClientDisconnectedListener> immutableList2) {
        this.mqttVersion = mqttVersion;
        this.clientIdentifier = mqttClientIdentifierImpl;
        this.transportConfig = mqttClientTransportConfigImpl;
        this.executorConfig = mqttClientExecutorConfigImpl;
        this.advancedConfig = mqttClientAdvancedConfig;
        this.connectDefaults = connectDefaults;
        this.connectedListeners = immutableList;
        this.disconnectedListeners = immutableList2;
        this.currentTransportConfig = mqttClientTransportConfigImpl;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttVersion getMqttVersion() {
        return this.mqttVersion;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public Optional<MqttClientIdentifier> getClientIdentifier() {
        return this.clientIdentifier == MqttClientIdentifierImpl.REQUEST_CLIENT_IDENTIFIER_FROM_SERVER ? Optional.empty() : Optional.of(this.clientIdentifier);
    }

    public MqttClientIdentifierImpl getRawClientIdentifier() {
        return this.clientIdentifier;
    }

    public void setClientIdentifier(MqttClientIdentifierImpl mqttClientIdentifierImpl) {
        this.clientIdentifier = mqttClientIdentifierImpl;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttClientTransportConfigImpl getTransportConfig() {
        return this.transportConfig;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttClientExecutorConfigImpl getExecutorConfig() {
        return this.executorConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig
    public MqttClientAdvancedConfig getAdvancedConfig() {
        return this.advancedConfig;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig
    public Optional<Mqtt5SimpleAuth> getSimpleAuth() {
        return Optional.ofNullable(this.connectDefaults.simpleAuth);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig
    public Optional<Mqtt5EnhancedAuthMechanism> getEnhancedAuthMechanism() {
        return Optional.ofNullable(this.connectDefaults.enhancedAuthMechanism);
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig
    public Optional<Mqtt5WillPublish> getWillPublish() {
        return Optional.ofNullable(this.connectDefaults.willPublish);
    }

    public ConnectDefaults getConnectDefaults() {
        return this.connectDefaults;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public Optional<MqttClientAutoReconnect> getAutomaticReconnect() {
        ImmutableList.ImmutableListIterator<MqttClientDisconnectedListener> it = this.disconnectedListeners.iterator();
        while (it.hasNext()) {
            MqttClientDisconnectedListener next = it.next();
            if (next instanceof MqttClientAutoReconnect) {
                return Optional.of((MqttClientAutoReconnect) next);
            }
        }
        return Optional.empty();
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public ImmutableList<MqttClientConnectedListener> getConnectedListeners() {
        return this.connectedListeners;
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public ImmutableList<MqttClientDisconnectedListener> getDisconnectedListeners() {
        return this.disconnectedListeners;
    }

    public ClientComponent getClientComponent() {
        return this.clientComponent;
    }

    public EventLoop acquireEventLoop() {
        EventLoop eventLoopAcquireEventLoop;
        synchronized (this.state) {
            this.eventLoopAcquires++;
            this.eventLoopAcquireCount++;
            eventLoopAcquireEventLoop = this.eventLoop;
            if (eventLoopAcquireEventLoop == null) {
                eventLoopAcquireEventLoop = NettyEventLoopProvider.INSTANCE.acquireEventLoop(this.executorConfig.getRawNettyExecutor(), this.executorConfig.getRawNettyThreads());
                this.eventLoop = eventLoopAcquireEventLoop;
            }
        }
        return eventLoopAcquireEventLoop;
    }

    public void releaseEventLoop() {
        synchronized (this.state) {
            int i = this.eventLoopAcquires - 1;
            this.eventLoopAcquires = i;
            if (i == 0) {
                EventLoop eventLoop = this.eventLoop;
                final long j = this.eventLoopAcquireCount;
                eventLoop.execute(new Runnable() { // from class: com.hivemq.client.internal.mqtt.MqttClientConfig$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        this.f$0.m2672x9f08323(j);
                    }
                });
            }
        }
    }

    /* JADX INFO: renamed from: lambda$releaseEventLoop$0$com-hivemq-client-internal-mqtt-MqttClientConfig, reason: not valid java name */
    /* synthetic */ void m2672x9f08323(long j) {
        synchronized (this.state) {
            if (j == this.eventLoopAcquireCount) {
                this.eventLoop = null;
                NettyEventLoopProvider.INSTANCE.releaseEventLoop(this.executorConfig.getRawNettyExecutor());
            }
        }
    }

    public boolean executeInEventLoop(Runnable runnable) {
        EventLoop eventLoop = this.eventLoop;
        if (eventLoop == null) {
            return false;
        }
        return ExecutorUtil.execute(eventLoop, runnable);
    }

    @Override // com.hivemq.client.mqtt.MqttClientConfig
    public MqttClientState getState() {
        return this.state.get();
    }

    public AtomicReference<MqttClientState> getRawState() {
        return this.state;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientConfig, com.hivemq.client.mqtt.MqttClientConfig
    public Optional<Mqtt5ClientConnectionConfig> getConnectionConfig() {
        return Optional.ofNullable(this.connectionConfig);
    }

    public MqttClientConnectionConfig getRawConnectionConfig() {
        return this.connectionConfig;
    }

    public void setConnectionConfig(MqttClientConnectionConfig mqttClientConnectionConfig) {
        this.connectionConfig = mqttClientConnectionConfig;
    }

    public MqttClientTransportConfigImpl getCurrentTransportConfig() {
        return this.currentTransportConfig;
    }

    public void setCurrentTransportConfig(MqttClientTransportConfigImpl mqttClientTransportConfigImpl) {
        if (this.currentTransportConfig.equals(mqttClientTransportConfigImpl)) {
            return;
        }
        this.currentTransportConfig = mqttClientTransportConfigImpl;
        this.currentSslContext = null;
    }

    public SslContext getCurrentSslContext() {
        return this.currentSslContext;
    }

    public void setCurrentSslContext(SslContext sslContext) {
        this.currentSslContext = sslContext;
    }

    public boolean isResubscribeIfSessionExpired() {
        return this.resubscribeIfSessionExpired;
    }

    public void setResubscribeIfSessionExpired(boolean z) {
        this.resubscribeIfSessionExpired = z;
    }

    public boolean isRepublishIfSessionExpired() {
        return this.republishIfSessionExpired;
    }

    public void setRepublishIfSessionExpired(boolean z) {
        this.republishIfSessionExpired = z;
    }

    public static class ConnectDefaults {
        private static final ConnectDefaults EMPTY = new ConnectDefaults(null, null, null);
        final Mqtt5EnhancedAuthMechanism enhancedAuthMechanism;
        final MqttSimpleAuth simpleAuth;
        final MqttWillPublish willPublish;

        public static ConnectDefaults of(MqttSimpleAuth mqttSimpleAuth, Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism, MqttWillPublish mqttWillPublish) {
            if (mqttSimpleAuth == null && mqtt5EnhancedAuthMechanism == null && mqttWillPublish == null) {
                return EMPTY;
            }
            return new ConnectDefaults(mqttSimpleAuth, mqtt5EnhancedAuthMechanism, mqttWillPublish);
        }

        private ConnectDefaults(MqttSimpleAuth mqttSimpleAuth, Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism, MqttWillPublish mqttWillPublish) {
            this.simpleAuth = mqttSimpleAuth;
            this.enhancedAuthMechanism = mqtt5EnhancedAuthMechanism;
            this.willPublish = mqttWillPublish;
        }

        public MqttSimpleAuth getSimpleAuth() {
            return this.simpleAuth;
        }

        public Mqtt5EnhancedAuthMechanism getEnhancedAuthMechanism() {
            return this.enhancedAuthMechanism;
        }

        public MqttWillPublish getWillPublish() {
            return this.willPublish;
        }
    }
}
