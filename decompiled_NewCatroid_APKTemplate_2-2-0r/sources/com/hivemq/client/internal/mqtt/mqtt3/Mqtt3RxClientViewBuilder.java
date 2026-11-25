package com.hivemq.client.internal.mqtt.mqtt3;

import com.hivemq.client.internal.mqtt.MqttAsyncClient;
import com.hivemq.client.internal.mqtt.MqttBlockingClient;
import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.MqttRxClient;
import com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase;
import com.hivemq.client.internal.mqtt.advanced.MqttClientAdvancedConfig;
import com.hivemq.client.internal.mqtt.message.auth.MqttSimpleAuth;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthView;
import com.hivemq.client.internal.mqtt.message.auth.mqtt3.Mqtt3SimpleAuthViewBuilder;
import com.hivemq.client.internal.mqtt.message.publish.MqttWillPublish;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishView;
import com.hivemq.client.internal.mqtt.message.publish.mqtt3.Mqtt3PublishViewBuilder;
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
import com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder;
import com.hivemq.client.mqtt.mqtt3.message.auth.Mqtt3SimpleAuth;
import com.hivemq.client.mqtt.mqtt3.message.publish.Mqtt3Publish;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.function.Function;

/* JADX INFO: loaded from: classes10.dex */
public class Mqtt3RxClientViewBuilder extends MqttRxClientBuilderBase<Mqtt3RxClientViewBuilder> implements Mqtt3ClientBuilder {
    private MqttSimpleAuth simpleAuth;
    private MqttWillPublish willPublish;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.hivemq.client.internal.mqtt.MqttRxClientBuilderBase, com.hivemq.client.internal.mqtt.MqttClientTransportConfigImplBuilder
    public Mqtt3RxClientViewBuilder self() {
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

    public Mqtt3RxClientViewBuilder() {
    }

    public Mqtt3RxClientViewBuilder(MqttRxClientBuilderBase<?> mqttRxClientBuilderBase) {
        super(mqttRxClientBuilderBase);
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3RxClientViewBuilder simpleAuth(Mqtt3SimpleAuth mqtt3SimpleAuth) {
        this.simpleAuth = mqtt3SimpleAuth == null ? null : ((Mqtt3SimpleAuthView) Checks.notImplemented(mqtt3SimpleAuth, Mqtt3SimpleAuthView.class, "Simple auth")).getDelegate();
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3SimpleAuthViewBuilder.Nested<Mqtt3RxClientViewBuilder> simpleAuth() {
        return new Mqtt3SimpleAuthViewBuilder.Nested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientViewBuilder$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.simpleAuth((Mqtt3SimpleAuth) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3RxClientViewBuilder willPublish(Mqtt3Publish mqtt3Publish) {
        this.willPublish = mqtt3Publish == null ? null : ((Mqtt3PublishView) Checks.notImplemented(mqtt3Publish, Mqtt3PublishView.class, "Will publish")).getDelegate().asWill();
        return this;
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3PublishViewBuilder.WillNested<Mqtt3RxClientViewBuilder> willPublish() {
        return new Mqtt3PublishViewBuilder.WillNested<>(new Function() { // from class: com.hivemq.client.internal.mqtt.mqtt3.Mqtt3RxClientViewBuilder$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.willPublish((Mqtt3Publish) obj);
            }
        });
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3RxClientView build() {
        return buildRx();
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3RxClientView buildRx() {
        return new Mqtt3RxClientView(buildRxDelegate());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3AsyncClientView buildAsync() {
        return new Mqtt3AsyncClientView(buildAsyncDelegate());
    }

    @Override // com.hivemq.client.mqtt.mqtt3.Mqtt3ClientBuilder
    public Mqtt3BlockingClientView buildBlocking() {
        return new Mqtt3BlockingClientView(buildBlockingDelegate());
    }

    private MqttRxClient buildRxDelegate() {
        return new MqttRxClient(buildClientConfig());
    }

    private MqttAsyncClient buildAsyncDelegate() {
        return buildRxDelegate().toAsync();
    }

    private MqttBlockingClient buildBlockingDelegate() {
        return buildRxDelegate().toBlocking();
    }

    private MqttClientConfig buildClientConfig() {
        return buildClientConfig(MqttVersion.MQTT_3_1_1, MqttClientAdvancedConfig.DEFAULT, MqttClientConfig.ConnectDefaults.of(this.simpleAuth, null, this.willPublish));
    }
}
