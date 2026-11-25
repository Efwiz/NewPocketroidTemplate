package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttPingReqEncoder;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3ClientMessageEncoders_Factory implements Factory<Mqtt3ClientMessageEncoders> {
    private final Provider<Mqtt3ConnectEncoder> connectEncoderProvider;
    private final Provider<Mqtt3DisconnectEncoder> disconnectEncoderProvider;
    private final Provider<MqttPingReqEncoder> pingReqEncoderProvider;
    private final Provider<Mqtt3PubAckEncoder> pubAckEncoderProvider;
    private final Provider<Mqtt3PubCompEncoder> pubCompEncoderProvider;
    private final Provider<Mqtt3PubRecEncoder> pubRecEncoderProvider;
    private final Provider<Mqtt3PubRelEncoder> pubRelEncoderProvider;
    private final Provider<Mqtt3PublishEncoder> publishEncoderProvider;
    private final Provider<Mqtt3SubscribeEncoder> subscribeEncoderProvider;
    private final Provider<Mqtt3UnsubscribeEncoder> unsubscribeEncoderProvider;

    public Mqtt3ClientMessageEncoders_Factory(Provider<Mqtt3ConnectEncoder> provider, Provider<Mqtt3PublishEncoder> provider2, Provider<Mqtt3PubAckEncoder> provider3, Provider<Mqtt3PubRecEncoder> provider4, Provider<Mqtt3PubRelEncoder> provider5, Provider<Mqtt3PubCompEncoder> provider6, Provider<Mqtt3SubscribeEncoder> provider7, Provider<Mqtt3UnsubscribeEncoder> provider8, Provider<MqttPingReqEncoder> provider9, Provider<Mqtt3DisconnectEncoder> provider10) {
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
    }

    @Override // javax.inject.Provider
    public Mqtt3ClientMessageEncoders get() {
        return newInstance(this.connectEncoderProvider.get(), this.publishEncoderProvider.get(), this.pubAckEncoderProvider.get(), this.pubRecEncoderProvider.get(), this.pubRelEncoderProvider.get(), this.pubCompEncoderProvider.get(), this.subscribeEncoderProvider.get(), this.unsubscribeEncoderProvider.get(), this.pingReqEncoderProvider.get(), this.disconnectEncoderProvider.get());
    }

    public static Mqtt3ClientMessageEncoders_Factory create(Provider<Mqtt3ConnectEncoder> provider, Provider<Mqtt3PublishEncoder> provider2, Provider<Mqtt3PubAckEncoder> provider3, Provider<Mqtt3PubRecEncoder> provider4, Provider<Mqtt3PubRelEncoder> provider5, Provider<Mqtt3PubCompEncoder> provider6, Provider<Mqtt3SubscribeEncoder> provider7, Provider<Mqtt3UnsubscribeEncoder> provider8, Provider<MqttPingReqEncoder> provider9, Provider<Mqtt3DisconnectEncoder> provider10) {
        return new Mqtt3ClientMessageEncoders_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10);
    }

    public static Mqtt3ClientMessageEncoders newInstance(Mqtt3ConnectEncoder mqtt3ConnectEncoder, Mqtt3PublishEncoder mqtt3PublishEncoder, Mqtt3PubAckEncoder mqtt3PubAckEncoder, Mqtt3PubRecEncoder mqtt3PubRecEncoder, Mqtt3PubRelEncoder mqtt3PubRelEncoder, Mqtt3PubCompEncoder mqtt3PubCompEncoder, Mqtt3SubscribeEncoder mqtt3SubscribeEncoder, Mqtt3UnsubscribeEncoder mqtt3UnsubscribeEncoder, MqttPingReqEncoder mqttPingReqEncoder, Mqtt3DisconnectEncoder mqtt3DisconnectEncoder) {
        return new Mqtt3ClientMessageEncoders(mqtt3ConnectEncoder, mqtt3PublishEncoder, mqtt3PubAckEncoder, mqtt3PubRecEncoder, mqtt3PubRelEncoder, mqtt3PubCompEncoder, mqtt3SubscribeEncoder, mqtt3UnsubscribeEncoder, mqttPingReqEncoder, mqtt3DisconnectEncoder);
    }
}
