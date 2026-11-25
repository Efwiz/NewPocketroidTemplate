package com.hivemq.client.internal.mqtt.ioc;

import com.hivemq.client.internal.mqtt.MqttClientConfig;
import com.hivemq.client.internal.mqtt.codec.MqttCodecModule_ProvideMessageDecodersFactory;
import com.hivemq.client.internal.mqtt.codec.MqttCodecModule_ProvideMessageEncodersFactory;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttPingRespDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttPingRespDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3ClientMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3ClientMessageDecoders_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3ConnAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3ConnAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubCompDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubCompDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubRecDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubRecDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubRelDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PubRelDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PublishDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3PublishDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3SubAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3SubAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3UnsubAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt3.Mqtt3UnsubAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5AuthDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5AuthDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5ClientMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5ClientMessageDecoders_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5ConnAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5ConnAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5DisconnectDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5DisconnectDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubCompDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubCompDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubRecDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubRecDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubRelDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PubRelDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PublishDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5PublishDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5SubAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5SubAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5UnsubAckDecoder;
import com.hivemq.client.internal.mqtt.codec.decoder.mqtt5.Mqtt5UnsubAckDecoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttPingReqEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttPingReqEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3ClientMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3ClientMessageEncoders_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3ConnectEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3ConnectEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3DisconnectEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3DisconnectEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubAckEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubAckEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubCompEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubCompEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubRecEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubRecEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubRelEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PubRelEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PublishEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3PublishEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3SubscribeEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3SubscribeEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3UnsubscribeEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt3.Mqtt3UnsubscribeEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5AuthEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5AuthEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5ClientMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5ClientMessageEncoders_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5ConnectEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5ConnectEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5DisconnectEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5DisconnectEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubAckEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubAckEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubCompEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubCompEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubRecEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubRecEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubRelEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PubRelEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PublishEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5PublishEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5SubscribeEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5SubscribeEncoder_Factory;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5UnsubscribeEncoder;
import com.hivemq.client.internal.mqtt.codec.encoder.mqtt5.Mqtt5UnsubscribeEncoder_Factory;
import com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer;
import com.hivemq.client.internal.mqtt.handler.MqttChannelInitializer_Factory;
import com.hivemq.client.internal.mqtt.handler.MqttSession;
import com.hivemq.client.internal.mqtt.handler.MqttSession_Factory;
import com.hivemq.client.internal.mqtt.handler.auth.MqttAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttConnectAuthHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.auth.MqttDisconnectOnAuthHandler;
import com.hivemq.client.internal.mqtt.handler.auth.MqttDisconnectOnAuthHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnAckFlow;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler;
import com.hivemq.client.internal.mqtt.handler.connect.MqttConnectHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler;
import com.hivemq.client.internal.mqtt.handler.disconnect.MqttDisconnectHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlows;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingPublishFlows_Factory;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler;
import com.hivemq.client.internal.mqtt.handler.publish.incoming.MqttIncomingQosHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler;
import com.hivemq.client.internal.mqtt.handler.publish.outgoing.MqttOutgoingQosHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler;
import com.hivemq.client.internal.mqtt.handler.subscribe.MqttSubscriptionHandler_Factory;
import com.hivemq.client.internal.mqtt.handler.websocket.MqttWebSocketCodec;
import com.hivemq.client.internal.mqtt.handler.websocket.MqttWebSocketCodec_Factory;
import com.hivemq.client.internal.mqtt.handler.websocket.MqttWebSocketInitializer;
import com.hivemq.client.internal.mqtt.handler.websocket.MqttWebSocketInitializer_Factory;
import com.hivemq.client.internal.mqtt.message.connect.MqttConnect;
import dagger.internal.DoubleCheck;
import dagger.internal.InstanceFactory;
import dagger.internal.Preconditions;
import io.netty.bootstrap.Bootstrap;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class DaggerSingletonComponent implements SingletonComponent {
    private Provider<Mqtt3ClientMessageDecoders> mqtt3ClientMessageDecodersProvider;
    private Provider<Mqtt3ClientMessageEncoders> mqtt3ClientMessageEncodersProvider;
    private Provider<Mqtt3ConnAckDecoder> mqtt3ConnAckDecoderProvider;
    private Provider<Mqtt3ConnectEncoder> mqtt3ConnectEncoderProvider;
    private Provider<Mqtt3DisconnectEncoder> mqtt3DisconnectEncoderProvider;
    private Provider<Mqtt3PubAckDecoder> mqtt3PubAckDecoderProvider;
    private Provider<Mqtt3PubAckEncoder> mqtt3PubAckEncoderProvider;
    private Provider<Mqtt3PubCompDecoder> mqtt3PubCompDecoderProvider;
    private Provider<Mqtt3PubCompEncoder> mqtt3PubCompEncoderProvider;
    private Provider<Mqtt3PubRecDecoder> mqtt3PubRecDecoderProvider;
    private Provider<Mqtt3PubRecEncoder> mqtt3PubRecEncoderProvider;
    private Provider<Mqtt3PubRelDecoder> mqtt3PubRelDecoderProvider;
    private Provider<Mqtt3PubRelEncoder> mqtt3PubRelEncoderProvider;
    private Provider<Mqtt3PublishDecoder> mqtt3PublishDecoderProvider;
    private Provider<Mqtt3PublishEncoder> mqtt3PublishEncoderProvider;
    private Provider<Mqtt3SubAckDecoder> mqtt3SubAckDecoderProvider;
    private Provider<Mqtt3SubscribeEncoder> mqtt3SubscribeEncoderProvider;
    private Provider<Mqtt3UnsubAckDecoder> mqtt3UnsubAckDecoderProvider;
    private Provider<Mqtt3UnsubscribeEncoder> mqtt3UnsubscribeEncoderProvider;
    private Provider<Mqtt5AuthDecoder> mqtt5AuthDecoderProvider;
    private Provider<Mqtt5AuthEncoder> mqtt5AuthEncoderProvider;
    private Provider<Mqtt5ClientMessageDecoders> mqtt5ClientMessageDecodersProvider;
    private Provider<Mqtt5ClientMessageEncoders> mqtt5ClientMessageEncodersProvider;
    private Provider<Mqtt5ConnAckDecoder> mqtt5ConnAckDecoderProvider;
    private Provider<Mqtt5ConnectEncoder> mqtt5ConnectEncoderProvider;
    private Provider<Mqtt5DisconnectDecoder> mqtt5DisconnectDecoderProvider;
    private Provider<Mqtt5DisconnectEncoder> mqtt5DisconnectEncoderProvider;
    private Provider<Mqtt5PubAckDecoder> mqtt5PubAckDecoderProvider;
    private Provider<Mqtt5PubAckEncoder> mqtt5PubAckEncoderProvider;
    private Provider<Mqtt5PubCompDecoder> mqtt5PubCompDecoderProvider;
    private Provider<Mqtt5PubCompEncoder> mqtt5PubCompEncoderProvider;
    private Provider<Mqtt5PubRecDecoder> mqtt5PubRecDecoderProvider;
    private Provider<Mqtt5PubRecEncoder> mqtt5PubRecEncoderProvider;
    private Provider<Mqtt5PubRelDecoder> mqtt5PubRelDecoderProvider;
    private Provider<Mqtt5PubRelEncoder> mqtt5PubRelEncoderProvider;
    private Provider<Mqtt5PublishDecoder> mqtt5PublishDecoderProvider;
    private Provider<Mqtt5PublishEncoder> mqtt5PublishEncoderProvider;
    private Provider<Mqtt5SubAckDecoder> mqtt5SubAckDecoderProvider;
    private Provider<Mqtt5SubscribeEncoder> mqtt5SubscribeEncoderProvider;
    private Provider<Mqtt5UnsubAckDecoder> mqtt5UnsubAckDecoderProvider;
    private Provider<Mqtt5UnsubscribeEncoder> mqtt5UnsubscribeEncoderProvider;
    private Provider<MqttDisconnectOnAuthHandler> mqttDisconnectOnAuthHandlerProvider;
    private Provider<MqttPingReqEncoder> mqttPingReqEncoderProvider;
    private Provider<MqttPingRespDecoder> mqttPingRespDecoderProvider;
    private Provider<MqttWebSocketCodec> mqttWebSocketCodecProvider;

    private DaggerSingletonComponent() {
        initialize();
    }

    public static Builder builder() {
        return new Builder();
    }

    public static SingletonComponent create() {
        return new Builder().build();
    }

    private void initialize() {
        Provider<Mqtt5PublishEncoder> provider = DoubleCheck.provider(Mqtt5PublishEncoder_Factory.create());
        this.mqtt5PublishEncoderProvider = provider;
        this.mqtt5ConnectEncoderProvider = DoubleCheck.provider(Mqtt5ConnectEncoder_Factory.create(provider));
        this.mqtt5PubAckEncoderProvider = DoubleCheck.provider(Mqtt5PubAckEncoder_Factory.create());
        this.mqtt5PubRecEncoderProvider = DoubleCheck.provider(Mqtt5PubRecEncoder_Factory.create());
        this.mqtt5PubRelEncoderProvider = DoubleCheck.provider(Mqtt5PubRelEncoder_Factory.create());
        this.mqtt5PubCompEncoderProvider = DoubleCheck.provider(Mqtt5PubCompEncoder_Factory.create());
        this.mqtt5SubscribeEncoderProvider = DoubleCheck.provider(Mqtt5SubscribeEncoder_Factory.create());
        this.mqtt5UnsubscribeEncoderProvider = DoubleCheck.provider(Mqtt5UnsubscribeEncoder_Factory.create());
        this.mqttPingReqEncoderProvider = DoubleCheck.provider(MqttPingReqEncoder_Factory.create());
        this.mqtt5DisconnectEncoderProvider = DoubleCheck.provider(Mqtt5DisconnectEncoder_Factory.create());
        Provider<Mqtt5AuthEncoder> provider2 = DoubleCheck.provider(Mqtt5AuthEncoder_Factory.create());
        this.mqtt5AuthEncoderProvider = provider2;
        this.mqtt5ClientMessageEncodersProvider = DoubleCheck.provider(Mqtt5ClientMessageEncoders_Factory.create(this.mqtt5ConnectEncoderProvider, this.mqtt5PublishEncoderProvider, this.mqtt5PubAckEncoderProvider, this.mqtt5PubRecEncoderProvider, this.mqtt5PubRelEncoderProvider, this.mqtt5PubCompEncoderProvider, this.mqtt5SubscribeEncoderProvider, this.mqtt5UnsubscribeEncoderProvider, this.mqttPingReqEncoderProvider, this.mqtt5DisconnectEncoderProvider, provider2));
        this.mqtt3ConnectEncoderProvider = DoubleCheck.provider(Mqtt3ConnectEncoder_Factory.create());
        this.mqtt3PublishEncoderProvider = DoubleCheck.provider(Mqtt3PublishEncoder_Factory.create());
        this.mqtt3PubAckEncoderProvider = DoubleCheck.provider(Mqtt3PubAckEncoder_Factory.create());
        this.mqtt3PubRecEncoderProvider = DoubleCheck.provider(Mqtt3PubRecEncoder_Factory.create());
        this.mqtt3PubRelEncoderProvider = DoubleCheck.provider(Mqtt3PubRelEncoder_Factory.create());
        this.mqtt3PubCompEncoderProvider = DoubleCheck.provider(Mqtt3PubCompEncoder_Factory.create());
        this.mqtt3SubscribeEncoderProvider = DoubleCheck.provider(Mqtt3SubscribeEncoder_Factory.create());
        this.mqtt3UnsubscribeEncoderProvider = DoubleCheck.provider(Mqtt3UnsubscribeEncoder_Factory.create());
        Provider<Mqtt3DisconnectEncoder> provider3 = DoubleCheck.provider(Mqtt3DisconnectEncoder_Factory.create());
        this.mqtt3DisconnectEncoderProvider = provider3;
        this.mqtt3ClientMessageEncodersProvider = DoubleCheck.provider(Mqtt3ClientMessageEncoders_Factory.create(this.mqtt3ConnectEncoderProvider, this.mqtt3PublishEncoderProvider, this.mqtt3PubAckEncoderProvider, this.mqtt3PubRecEncoderProvider, this.mqtt3PubRelEncoderProvider, this.mqtt3PubCompEncoderProvider, this.mqtt3SubscribeEncoderProvider, this.mqtt3UnsubscribeEncoderProvider, this.mqttPingReqEncoderProvider, provider3));
        this.mqtt5ConnAckDecoderProvider = DoubleCheck.provider(Mqtt5ConnAckDecoder_Factory.create());
        this.mqtt5PublishDecoderProvider = DoubleCheck.provider(Mqtt5PublishDecoder_Factory.create());
        this.mqtt5PubAckDecoderProvider = DoubleCheck.provider(Mqtt5PubAckDecoder_Factory.create());
        this.mqtt5PubRecDecoderProvider = DoubleCheck.provider(Mqtt5PubRecDecoder_Factory.create());
        this.mqtt5PubRelDecoderProvider = DoubleCheck.provider(Mqtt5PubRelDecoder_Factory.create());
        this.mqtt5PubCompDecoderProvider = DoubleCheck.provider(Mqtt5PubCompDecoder_Factory.create());
        this.mqtt5SubAckDecoderProvider = DoubleCheck.provider(Mqtt5SubAckDecoder_Factory.create());
        this.mqtt5UnsubAckDecoderProvider = DoubleCheck.provider(Mqtt5UnsubAckDecoder_Factory.create());
        this.mqttPingRespDecoderProvider = DoubleCheck.provider(MqttPingRespDecoder_Factory.create());
        this.mqtt5DisconnectDecoderProvider = DoubleCheck.provider(Mqtt5DisconnectDecoder_Factory.create());
        Provider<Mqtt5AuthDecoder> provider4 = DoubleCheck.provider(Mqtt5AuthDecoder_Factory.create());
        this.mqtt5AuthDecoderProvider = provider4;
        this.mqtt5ClientMessageDecodersProvider = DoubleCheck.provider(Mqtt5ClientMessageDecoders_Factory.create(this.mqtt5ConnAckDecoderProvider, this.mqtt5PublishDecoderProvider, this.mqtt5PubAckDecoderProvider, this.mqtt5PubRecDecoderProvider, this.mqtt5PubRelDecoderProvider, this.mqtt5PubCompDecoderProvider, this.mqtt5SubAckDecoderProvider, this.mqtt5UnsubAckDecoderProvider, this.mqttPingRespDecoderProvider, this.mqtt5DisconnectDecoderProvider, provider4));
        this.mqtt3ConnAckDecoderProvider = DoubleCheck.provider(Mqtt3ConnAckDecoder_Factory.create());
        this.mqtt3PublishDecoderProvider = DoubleCheck.provider(Mqtt3PublishDecoder_Factory.create());
        this.mqtt3PubAckDecoderProvider = DoubleCheck.provider(Mqtt3PubAckDecoder_Factory.create());
        this.mqtt3PubRecDecoderProvider = DoubleCheck.provider(Mqtt3PubRecDecoder_Factory.create());
        this.mqtt3PubRelDecoderProvider = DoubleCheck.provider(Mqtt3PubRelDecoder_Factory.create());
        this.mqtt3PubCompDecoderProvider = DoubleCheck.provider(Mqtt3PubCompDecoder_Factory.create());
        this.mqtt3SubAckDecoderProvider = DoubleCheck.provider(Mqtt3SubAckDecoder_Factory.create());
        Provider<Mqtt3UnsubAckDecoder> provider5 = DoubleCheck.provider(Mqtt3UnsubAckDecoder_Factory.create());
        this.mqtt3UnsubAckDecoderProvider = provider5;
        this.mqtt3ClientMessageDecodersProvider = DoubleCheck.provider(Mqtt3ClientMessageDecoders_Factory.create(this.mqtt3ConnAckDecoderProvider, this.mqtt3PublishDecoderProvider, this.mqtt3PubAckDecoderProvider, this.mqtt3PubRecDecoderProvider, this.mqtt3PubRelDecoderProvider, this.mqtt3PubCompDecoderProvider, this.mqtt3SubAckDecoderProvider, provider5, this.mqttPingRespDecoderProvider));
        this.mqttDisconnectOnAuthHandlerProvider = DoubleCheck.provider(MqttDisconnectOnAuthHandler_Factory.create());
        this.mqttWebSocketCodecProvider = DoubleCheck.provider(MqttWebSocketCodec_Factory.create());
    }

    @Override // com.hivemq.client.internal.mqtt.ioc.SingletonComponent
    public ClientComponent.Builder clientComponentBuilder() {
        return new ClientComponentBuilder();
    }

    public static final class Builder {
        private Builder() {
        }

        public SingletonComponent build() {
            return new DaggerSingletonComponent();
        }
    }

    private final class ClientComponentBuilder implements ClientComponent.Builder {
        private MqttClientConfig clientConfig;

        private ClientComponentBuilder() {
        }

        @Override // com.hivemq.client.internal.mqtt.ioc.ClientComponent.Builder
        public ClientComponentBuilder clientConfig(MqttClientConfig mqttClientConfig) {
            this.clientConfig = (MqttClientConfig) Preconditions.checkNotNull(mqttClientConfig);
            return this;
        }

        @Override // com.hivemq.client.internal.mqtt.ioc.ClientComponent.Builder
        public ClientComponent build() {
            Preconditions.checkBuilderRequirement(this.clientConfig, MqttClientConfig.class);
            return new ClientComponentImpl(this.clientConfig);
        }
    }

    private final class ClientComponentImpl implements ClientComponent {
        private Provider<MqttClientConfig> clientConfigProvider;
        private Provider<MqttIncomingPublishFlows> mqttIncomingPublishFlowsProvider;
        private Provider<MqttIncomingQosHandler> mqttIncomingQosHandlerProvider;
        private Provider<MqttOutgoingQosHandler> mqttOutgoingQosHandlerProvider;
        private Provider<MqttSession> mqttSessionProvider;
        private Provider<MqttSubscriptionHandler> mqttSubscriptionHandlerProvider;

        private ClientComponentImpl(MqttClientConfig mqttClientConfig) {
            initialize(mqttClientConfig);
        }

        private void initialize(MqttClientConfig mqttClientConfig) {
            this.clientConfigProvider = InstanceFactory.create(mqttClientConfig);
            Provider<MqttIncomingPublishFlows> provider = DoubleCheck.provider(MqttIncomingPublishFlows_Factory.create());
            this.mqttIncomingPublishFlowsProvider = provider;
            this.mqttSubscriptionHandlerProvider = DoubleCheck.provider(MqttSubscriptionHandler_Factory.create(this.clientConfigProvider, provider));
            this.mqttIncomingQosHandlerProvider = DoubleCheck.provider(MqttIncomingQosHandler_Factory.create(this.clientConfigProvider, this.mqttIncomingPublishFlowsProvider));
            Provider<MqttOutgoingQosHandler> provider2 = DoubleCheck.provider(MqttOutgoingQosHandler_Factory.create(this.clientConfigProvider));
            this.mqttOutgoingQosHandlerProvider = provider2;
            this.mqttSessionProvider = DoubleCheck.provider(MqttSession_Factory.create(this.mqttSubscriptionHandlerProvider, this.mqttIncomingQosHandlerProvider, provider2));
        }

        @Override // com.hivemq.client.internal.mqtt.ioc.ClientComponent
        public ConnectionComponent.Builder connectionComponentBuilder() {
            return new ConnectionComponentBuilder();
        }

        @Override // com.hivemq.client.internal.mqtt.ioc.ClientComponent
        public MqttSubscriptionHandler subscriptionHandler() {
            return this.mqttSubscriptionHandlerProvider.get();
        }

        @Override // com.hivemq.client.internal.mqtt.ioc.ClientComponent
        public MqttIncomingQosHandler incomingQosHandler() {
            return this.mqttIncomingQosHandlerProvider.get();
        }

        @Override // com.hivemq.client.internal.mqtt.ioc.ClientComponent
        public MqttOutgoingQosHandler outgoingQosHandler() {
            return this.mqttOutgoingQosHandlerProvider.get();
        }

        private final class ConnectionComponentBuilder implements ConnectionComponent.Builder {
            private MqttConnAckFlow connAckFlow;
            private MqttConnect connect;

            private ConnectionComponentBuilder() {
            }

            @Override // com.hivemq.client.internal.mqtt.ioc.ConnectionComponent.Builder
            public ConnectionComponentBuilder connect(MqttConnect mqttConnect) {
                this.connect = (MqttConnect) Preconditions.checkNotNull(mqttConnect);
                return this;
            }

            @Override // com.hivemq.client.internal.mqtt.ioc.ConnectionComponent.Builder
            public ConnectionComponentBuilder connAckFlow(MqttConnAckFlow mqttConnAckFlow) {
                this.connAckFlow = (MqttConnAckFlow) Preconditions.checkNotNull(mqttConnAckFlow);
                return this;
            }

            @Override // com.hivemq.client.internal.mqtt.ioc.ConnectionComponent.Builder
            public ConnectionComponent build() {
                Preconditions.checkBuilderRequirement(this.connect, MqttConnect.class);
                Preconditions.checkBuilderRequirement(this.connAckFlow, MqttConnAckFlow.class);
                return new ConnectionComponentImpl(this.connect, this.connAckFlow);
            }
        }

        private final class ConnectionComponentImpl implements ConnectionComponent {
            private Provider<MqttConnAckFlow> connAckFlowProvider;
            private Provider<MqttConnect> connectProvider;
            private Provider<MqttChannelInitializer> mqttChannelInitializerProvider;
            private Provider<MqttConnectAuthHandler> mqttConnectAuthHandlerProvider;
            private Provider<MqttConnectHandler> mqttConnectHandlerProvider;
            private Provider<MqttDecoder> mqttDecoderProvider;
            private Provider<MqttDisconnectHandler> mqttDisconnectHandlerProvider;
            private Provider<MqttEncoder> mqttEncoderProvider;
            private Provider<MqttWebSocketInitializer> mqttWebSocketInitializerProvider;
            private Provider<MqttAuthHandler> provideAuthHandlerProvider;
            private Provider<MqttMessageDecoders> provideMessageDecodersProvider;
            private Provider<MqttMessageEncoders> provideMessageEncodersProvider;

            private ConnectionComponentImpl(MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow) {
                initialize(mqttConnect, mqttConnAckFlow);
            }

            private void initialize(MqttConnect mqttConnect, MqttConnAckFlow mqttConnAckFlow) {
                this.connectProvider = InstanceFactory.create(mqttConnect);
                this.connAckFlowProvider = InstanceFactory.create(mqttConnAckFlow);
                Provider<MqttMessageEncoders> provider = DoubleCheck.provider(MqttCodecModule_ProvideMessageEncodersFactory.create(ClientComponentImpl.this.clientConfigProvider, DaggerSingletonComponent.this.mqtt5ClientMessageEncodersProvider, DaggerSingletonComponent.this.mqtt3ClientMessageEncodersProvider));
                this.provideMessageEncodersProvider = provider;
                this.mqttEncoderProvider = DoubleCheck.provider(MqttEncoder_Factory.create(provider));
                Provider<MqttMessageDecoders> provider2 = DoubleCheck.provider(MqttCodecModule_ProvideMessageDecodersFactory.create(ClientComponentImpl.this.clientConfigProvider, DaggerSingletonComponent.this.mqtt5ClientMessageDecodersProvider, DaggerSingletonComponent.this.mqtt3ClientMessageDecodersProvider));
                this.provideMessageDecodersProvider = provider2;
                this.mqttDecoderProvider = DoubleCheck.provider(MqttDecoder_Factory.create(provider2, ClientComponentImpl.this.clientConfigProvider, this.connectProvider));
                this.mqttConnectHandlerProvider = DoubleCheck.provider(MqttConnectHandler_Factory.create(this.connectProvider, this.connAckFlowProvider, ClientComponentImpl.this.clientConfigProvider, ClientComponentImpl.this.mqttSessionProvider, this.mqttDecoderProvider));
                this.mqttDisconnectHandlerProvider = DoubleCheck.provider(MqttDisconnectHandler_Factory.create(ClientComponentImpl.this.clientConfigProvider, ClientComponentImpl.this.mqttSessionProvider));
                Provider<MqttConnectAuthHandler> provider3 = DoubleCheck.provider(MqttConnectAuthHandler_Factory.create(ClientComponentImpl.this.clientConfigProvider, this.connectProvider));
                this.mqttConnectAuthHandlerProvider = provider3;
                this.provideAuthHandlerProvider = DoubleCheck.provider(ConnectionModule_ProvideAuthHandlerFactory.create(this.connectProvider, provider3, DaggerSingletonComponent.this.mqttDisconnectOnAuthHandlerProvider));
                this.mqttWebSocketInitializerProvider = DoubleCheck.provider(MqttWebSocketInitializer_Factory.create(DaggerSingletonComponent.this.mqttWebSocketCodecProvider));
                this.mqttChannelInitializerProvider = DoubleCheck.provider(MqttChannelInitializer_Factory.create(ClientComponentImpl.this.clientConfigProvider, this.connectProvider, this.connAckFlowProvider, this.mqttEncoderProvider, this.mqttConnectHandlerProvider, this.mqttDisconnectHandlerProvider, this.provideAuthHandlerProvider, this.mqttWebSocketInitializerProvider));
            }

            @Override // com.hivemq.client.internal.mqtt.ioc.ConnectionComponent
            public Bootstrap bootstrap() {
                return ConnectionModule_ProvideBootstrapFactory.provideBootstrap(this.mqttChannelInitializerProvider.get());
            }
        }
    }
}
