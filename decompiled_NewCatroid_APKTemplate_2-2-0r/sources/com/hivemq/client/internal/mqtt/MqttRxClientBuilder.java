package com.hivemq.client.internal.mqtt;

import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfig;
import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfigBuilder;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuthBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublish;
import com.hivemq.client.internal.mqtt.message.publish.MqttPublishBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.util.Checks;
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
import com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder;
import com.hivemq.client.mqtt.mqtt5.advanced.Mqtt5ClientAdvancedConfig;
import com.hivemq.client.mqtt.mqtt5.auth.Mqtt5EnhancedAuthMechanism;
import com.hivemq.client.mqtt.mqtt5.message.auth.Mqtt5SimpleAuth;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class MqttRxClientBuilder extends MqttRxClientBuilderBase<MqttRxClientBuilder> implements Mqtt5ClientBuilder {
    private MqttClientAdvancedConfig advancedConfig;
    private Mqtt5EnhancedAuthMechanism enhancedAuthMechanism;
    private MqttSimpleAuth simpleAuth;
    private MqttWillPublish willPublish;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public MqttRxClientBuilder self() {
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

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: serverAddress */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2673serverAddress(InetSocketAddress inetSocketAddress) {
        return (MqttClientBuilderBase) super.serverAddress(inetSocketAddress);
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: serverHost */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2674serverHost(String str) {
        return (MqttClientBuilderBase) super.serverHost(str);
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: serverHost */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2675serverHost(InetAddress inetAddress) {
        return (MqttClientBuilderBase) super.serverHost(inetAddress);
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: serverPort */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2676serverPort(int i) {
        return (MqttClientBuilderBase) super.serverPort(i);
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: sslConfig */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2677sslConfig(MqttClientSslConfig mqttClientSslConfig) {
        return (MqttClientBuilderBase) super.sslConfig(mqttClientSslConfig);
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    public /* bridge */ /* synthetic */ MqttClientSslConfigBuilder.Nested sslConfig() {
        return super.sslConfig();
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: sslWithDefaultConfig */
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

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: webSocketConfig */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2679webSocketConfig(MqttWebSocketConfig mqttWebSocketConfig) {
        return (MqttClientBuilderBase) super.webSocketConfig(mqttWebSocketConfig);
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    public /* bridge */ /* synthetic */ MqttWebSocketConfigBuilder.Nested webSocketConfig() {
        return super.webSocketConfig();
    }

    @Override // com.hivemq.client.mqtt.MqttClientBuilderBase
    /* JADX INFO: renamed from: webSocketWithDefaultConfig */
    public /* bridge */ /* synthetic */ MqttClientBuilderBase mo2680webSocketWithDefaultConfig() {
        return (MqttClientBuilderBase) super.webSocketWithDefaultConfig();
    }

    public MqttRxClientBuilder() {
        this.advancedConfig = MqttClientAdvancedConfig.DEFAULT;
    }

    MqttRxClientBuilder(MqttRxClientBuilderBase<?> mqttRxClientBuilderBase) {
        super(mqttRxClientBuilderBase);
        this.advancedConfig = MqttClientAdvancedConfig.DEFAULT;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttRxClientBuilder advancedConfig(Mqtt5ClientAdvancedConfig mqtt5ClientAdvancedConfig) {
        this.advancedConfig = (MqttClientAdvancedConfig) Checks.notImplemented(mqtt5ClientAdvancedConfig, MqttClientAdvancedConfig.class, "Advanced config");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttClientAdvancedConfigBuilder.Nested<MqttRxClientBuilder> advancedConfig() {
        return new MqttClientAdvancedConfigBuilder.Nested<>(this.advancedConfig, new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClientBuilder$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.advancedConfig((Mqtt5ClientAdvancedConfig) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttRxClientBuilder simpleAuth(Mqtt5SimpleAuth mqtt5SimpleAuth) {
        this.simpleAuth = (MqttSimpleAuth) Checks.notImplementedOrNull(mqtt5SimpleAuth, MqttSimpleAuth.class, "Simple auth");
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttSimpleAuthBuilder.Nested<MqttRxClientBuilder> simpleAuth() {
        return new MqttSimpleAuthBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClientBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.simpleAuth((Mqtt5SimpleAuth) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttRxClientBuilder enhancedAuth(Mqtt5EnhancedAuthMechanism mqtt5EnhancedAuthMechanism) {
        this.enhancedAuthMechanism = mqtt5EnhancedAuthMechanism;
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttRxClientBuilder willPublish(Mqtt5Publish mqtt5Publish) {
        this.willPublish = mqtt5Publish == null ? null : ((MqttPublish) Checks.notImplemented(mqtt5Publish, MqttPublish.class, "Will publish")).asWill();
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttPublishBuilder.WillNested<MqttRxClientBuilder> willPublish() {
        return new MqttPublishBuilder.WillNested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.MqttRxClientBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.willPublish((Mqtt5Publish) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttRxClient build() {
        return buildRx();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttRxClient buildRx() {
        return new MqttRxClient(buildClientConfig());
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttAsyncClient buildAsync() {
        return buildRx().toAsync();
    }

    @Override // com.hivemq.client.mqtt.mqtt5.Mqtt5ClientBuilder
    public MqttBlockingClient buildBlocking() {
        return buildRx().toBlocking();
    }

    private MqttClientConfig buildClientConfig() {
        return buildClientConfig(MqttVersion.MQTT_5_0, this.advancedConfig, MqttClientConfig.ConnectDefaults.of(this.simpleAuth, this.enhancedAuthMechanism, this.willPublish));
    }
}
