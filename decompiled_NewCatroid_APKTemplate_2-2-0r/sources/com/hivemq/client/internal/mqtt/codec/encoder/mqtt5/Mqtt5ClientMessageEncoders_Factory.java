package com.hivemq.client.internal.mqtt.codec.encoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttPingReqEncoder;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5ClientMessageEncoders_Factory implements Factory<Mqtt5ClientMessageEncoders> {
    private final Provider<Mqtt5AuthEncoder> authEncoderProvider;
    private final Provider<Mqtt5ConnectEncoder> connectEncoderProvider;
    private final Provider<Mqtt5DisconnectEncoder> disconnectEncoderProvider;
    private final Provider<MqttPingReqEncoder> pingReqEncoderProvider;
    private final Provider<Mqtt5PubAckEncoder> pubAckEncoderProvider;
    private final Provider<Mqtt5PubCompEncoder> pubCompEncoderProvider;
    private final Provider<Mqtt5PubRecEncoder> pubRecEncoderProvider;
    private final Provider<Mqtt5PubRelEncoder> pubRelEncoderProvider;
    private final Provider<Mqtt5PublishEncoder> publishEncoderProvider;
    private final Provider<Mqtt5SubscribeEncoder> subscribeEncoderProvider;
    private final Provider<Mqtt5UnsubscribeEncoder> unsubscribeEncoderProvider;

    public Mqtt5ClientMessageEncoders_Factory(Provider<Mqtt5ConnectEncoder> provider, Provider<Mqtt5PublishEncoder> provider2, Provider<Mqtt5PubAckEncoder> provider3, Provider<Mqtt5PubRecEncoder> provider4, Provider<Mqtt5PubRelEncoder> provider5, Provider<Mqtt5PubCompEncoder> provider6, Provider<Mqtt5SubscribeEncoder> provider7, Provider<Mqtt5UnsubscribeEncoder> provider8, Provider<MqttPingReqEncoder> provider9, Provider<Mqtt5DisconnectEncoder> provider10, Provider<Mqtt5AuthEncoder> provider11) {
        this.connectEncoderProvider = provider;
        this.publishEncoderProvider = provider2;
        this.pubAckEncoderProvider = provider3;
        this.pubRecEncoderProvider = provider4;
        this.pubRelEncoderProvider = provider5;
        this.pubCompEncoderProvider = provider6;
        this.subscribeEncoderProvider = provider7;
        this.unsubscribeEncoderProvider = provider8;
        this.pingReqEncoderProvider = provider9;
        this.disconnectEncoderProvider = provider10;
        this.authEncoderProvider = provider11;
    }

    @Override // javax.inject.Provider
    public Mqtt5ClientMessageEncoders get() {
        return newInstance(this.connectEncoderProvider.get(), this.publishEncoderProvider.get(), this.pubAckEncoderProvider.get(), this.pubRecEncoderProvider.get(), this.pubRelEncoderProvider.get(), this.pubCompEncoderProvider.get(), this.subscribeEncoderProvider.get(), this.unsubscribeEncoderProvider.get(), this.pingReqEncoderProvider.get(), this.disconnectEncoderProvider.get(), this.authEncoderProvider.get());
    }

    public static Mqtt5ClientMessageEncoders_Factory create(Provider<Mqtt5ConnectEncoder> provider, Provider<Mqtt5PublishEncoder> provider2, Provider<Mqtt5PubAckEncoder> provider3, Provider<Mqtt5PubRecEncoder> provider4, Provider<Mqtt5PubRelEncoder> provider5, Provider<Mqtt5PubCompEncoder> provider6, Provider<Mqtt5SubscribeEncoder> provider7, Provider<Mqtt5UnsubscribeEncoder> provider8, Provider<MqttPingReqEncoder> provider9, Provider<Mqtt5DisconnectEncoder> provider10, Provider<Mqtt5AuthEncoder> provider11) {
        return new Mqtt5ClientMessageEncoders_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static Mqtt5ClientMessageEncoders newInstance(Mqtt5ConnectEncoder mqtt5ConnectEncoder, Mqtt5PublishEncoder mqtt5PublishEncoder, Mqtt5PubAckEncoder mqtt5PubAckEncoder, Mqtt5PubRecEncoder mqtt5PubRecEncoder, Mqtt5PubRelEncoder mqtt5PubRelEncoder, Mqtt5PubCompEncoder mqtt5PubCompEncoder, Mqtt5SubscribeEncoder mqtt5SubscribeEncoder, Mqtt5UnsubscribeEncoder mqtt5UnsubscribeEncoder, MqttPingReqEncoder mqttPingReqEncoder, Mqtt5DisconnectEncoder mqtt5DisconnectEncoder, Mqtt5AuthEncoder mqtt5AuthEncoder) {
        return new Mqtt5ClientMessageEncoders(mqtt5ConnectEncoder, mqtt5PublishEncoder, mqtt5PubAckEncoder, mqtt5PubRecEncoder, mqtt5PubRelEncoder, mqtt5PubCompEncoder, mqtt5SubscribeEncoder, mqtt5UnsubscribeEncoder, mqttPingReqEncoder, mqtt5DisconnectEncoder, mqtt5AuthEncoder);
    }
}
