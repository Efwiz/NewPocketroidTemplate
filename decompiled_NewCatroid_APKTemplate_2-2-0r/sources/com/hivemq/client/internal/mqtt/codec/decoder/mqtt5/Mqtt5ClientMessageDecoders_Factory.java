package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttPingRespDecoder;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt5ClientMessageDecoders_Factory implements Factory<Mqtt5ClientMessageDecoders> {
    private final Provider<Mqtt5AuthDecoder> authDecoderProvider;
    private final Provider<Mqtt5ConnAckDecoder> connAckDecoderProvider;
    private final Provider<Mqtt5DisconnectDecoder> disconnectDecoderProvider;
    private final Provider<MqttPingRespDecoder> pingRespDecoderProvider;
    private final Provider<Mqtt5PubAckDecoder> pubAckDecoderProvider;
    private final Provider<Mqtt5PubCompDecoder> pubCompDecoderProvider;
    private final Provider<Mqtt5PubRecDecoder> pubRecDecoderProvider;
    private final Provider<Mqtt5PubRelDecoder> pubRelDecoderProvider;
    private final Provider<Mqtt5PublishDecoder> publishDecoderProvider;
    private final Provider<Mqtt5SubAckDecoder> subAckDecoderProvider;
    private final Provider<Mqtt5UnsubAckDecoder> unsubAckDecoderProvider;

    public Mqtt5ClientMessageDecoders_Factory(Provider<Mqtt5ConnAckDecoder> provider, Provider<Mqtt5PublishDecoder> provider2, Provider<Mqtt5PubAckDecoder> provider3, Provider<Mqtt5PubRecDecoder> provider4, Provider<Mqtt5PubRelDecoder> provider5, Provider<Mqtt5PubCompDecoder> provider6, Provider<Mqtt5SubAckDecoder> provider7, Provider<Mqtt5UnsubAckDecoder> provider8, Provider<MqttPingRespDecoder> provider9, Provider<Mqtt5DisconnectDecoder> provider10, Provider<Mqtt5AuthDecoder> provider11) {
        this.connAckDecoderProvider = provider;
        this.publishDecoderProvider = provider2;
        this.pubAckDecoderProvider = provider3;
        this.pubRecDecoderProvider = provider4;
        this.pubRelDecoderProvider = provider5;
        this.pubCompDecoderProvider = provider6;
        this.subAckDecoderProvider = provider7;
        this.unsubAckDecoderProvider = provider8;
        this.pingRespDecoderProvider = provider9;
        this.disconnectDecoderProvider = provider10;
        this.authDecoderProvider = provider11;
    }

    @Override // javax.inject.Provider
    public Mqtt5ClientMessageDecoders get() {
        return newInstance(this.connAckDecoderProvider.get(), this.publishDecoderProvider.get(), this.pubAckDecoderProvider.get(), this.pubRecDecoderProvider.get(), this.pubRelDecoderProvider.get(), this.pubCompDecoderProvider.get(), this.subAckDecoderProvider.get(), this.unsubAckDecoderProvider.get(), this.pingRespDecoderProvider.get(), this.disconnectDecoderProvider.get(), this.authDecoderProvider.get());
    }

    public static Mqtt5ClientMessageDecoders_Factory create(Provider<Mqtt5ConnAckDecoder> provider, Provider<Mqtt5PublishDecoder> provider2, Provider<Mqtt5PubAckDecoder> provider3, Provider<Mqtt5PubRecDecoder> provider4, Provider<Mqtt5PubRelDecoder> provider5, Provider<Mqtt5PubCompDecoder> provider6, Provider<Mqtt5SubAckDecoder> provider7, Provider<Mqtt5UnsubAckDecoder> provider8, Provider<MqttPingRespDecoder> provider9, Provider<Mqtt5DisconnectDecoder> provider10, Provider<Mqtt5AuthDecoder> provider11) {
        return new Mqtt5ClientMessageDecoders_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9, provider10, provider11);
    }

    public static Mqtt5ClientMessageDecoders newInstance(Mqtt5ConnAckDecoder mqtt5ConnAckDecoder, Mqtt5PublishDecoder mqtt5PublishDecoder, Mqtt5PubAckDecoder mqtt5PubAckDecoder, Mqtt5PubRecDecoder mqtt5PubRecDecoder, Mqtt5PubRelDecoder mqtt5PubRelDecoder, Mqtt5PubCompDecoder mqtt5PubCompDecoder, Mqtt5SubAckDecoder mqtt5SubAckDecoder, Mqtt5UnsubAckDecoder mqtt5UnsubAckDecoder, MqttPingRespDecoder mqttPingRespDecoder, Mqtt5DisconnectDecoder mqtt5DisconnectDecoder, Mqtt5AuthDecoder mqtt5AuthDecoder) {
        return new Mqtt5ClientMessageDecoders(mqtt5ConnAckDecoder, mqtt5PublishDecoder, mqtt5PubAckDecoder, mqtt5PubRecDecoder, mqtt5PubRelDecoder, mqtt5PubCompDecoder, mqtt5SubAckDecoder, mqtt5UnsubAckDecoder, mqttPingRespDecoder, mqtt5DisconnectDecoder, mqtt5AuthDecoder);
    }
}
