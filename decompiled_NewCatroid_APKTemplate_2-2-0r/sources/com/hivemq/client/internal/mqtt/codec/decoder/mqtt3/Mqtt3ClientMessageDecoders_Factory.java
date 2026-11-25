package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttPingRespDecoder;
import dagger.internal.Factory;
import javax.inject.Provider;

/* JADX INFO: loaded from: classes10.dex */
public final class Mqtt3ClientMessageDecoders_Factory implements Factory<Mqtt3ClientMessageDecoders> {
    private final Provider<Mqtt3ConnAckDecoder> connAckDecoderProvider;
    private final Provider<MqttPingRespDecoder> pingRespDecoderProvider;
    private final Provider<Mqtt3PubAckDecoder> pubAckDecoderProvider;
    private final Provider<Mqtt3PubCompDecoder> pubCompDecoderProvider;
    private final Provider<Mqtt3PubRecDecoder> pubRecDecoderProvider;
    private final Provider<Mqtt3PubRelDecoder> pubRelDecoderProvider;
    private final Provider<Mqtt3PublishDecoder> publishDecoderProvider;
    private final Provider<Mqtt3SubAckDecoder> subAckDecoderProvider;
    private final Provider<Mqtt3UnsubAckDecoder> unsubAckDecoderProvider;

    public Mqtt3ClientMessageDecoders_Factory(Provider<Mqtt3ConnAckDecoder> provider, Provider<Mqtt3PublishDecoder> provider2, Provider<Mqtt3PubAckDecoder> provider3, Provider<Mqtt3PubRecDecoder> provider4, Provider<Mqtt3PubRelDecoder> provider5, Provider<Mqtt3PubCompDecoder> provider6, Provider<Mqtt3SubAckDecoder> provider7, Provider<Mqtt3UnsubAckDecoder> provider8, Provider<MqttPingRespDecoder> provider9) {
        this.connAckDecoderProvider = provider;
        this.publishDecoderProvider = provider2;
        this.pubAckDecoderProvider = provider3;
        this.pubRecDecoderProvider = provider4;
        this.pubRelDecoderProvider = provider5;
        this.pubCompDecoderProvider = provider6;
        this.subAckDecoderProvider = provider7;
        this.unsubAckDecoderProvider = provider8;
        this.pingRespDecoderProvider = provider9;
    }

    @Override // javax.inject.Provider
    public Mqtt3ClientMessageDecoders get() {
        return newInstance(this.connAckDecoderProvider.get(), this.publishDecoderProvider.get(), this.pubAckDecoderProvider.get(), this.pubRecDecoderProvider.get(), this.pubRelDecoderProvider.get(), this.pubCompDecoderProvider.get(), this.subAckDecoderProvider.get(), this.unsubAckDecoderProvider.get(), this.pingRespDecoderProvider.get());
    }

    public static Mqtt3ClientMessageDecoders_Factory create(Provider<Mqtt3ConnAckDecoder> provider, Provider<Mqtt3PublishDecoder> provider2, Provider<Mqtt3PubAckDecoder> provider3, Provider<Mqtt3PubRecDecoder> provider4, Provider<Mqtt3PubRelDecoder> provider5, Provider<Mqtt3PubCompDecoder> provider6, Provider<Mqtt3SubAckDecoder> provider7, Provider<Mqtt3UnsubAckDecoder> provider8, Provider<MqttPingRespDecoder> provider9) {
        return new Mqtt3ClientMessageDecoders_Factory(provider, provider2, provider3, provider4, provider5, provider6, provider7, provider8, provider9);
    }

    public static Mqtt3ClientMessageDecoders newInstance(Mqtt3ConnAckDecoder mqtt3ConnAckDecoder, Mqtt3PublishDecoder mqtt3PublishDecoder, Mqtt3PubAckDecoder mqtt3PubAckDecoder, Mqtt3PubRecDecoder mqtt3PubRecDecoder, Mqtt3PubRelDecoder mqtt3PubRelDecoder, Mqtt3PubCompDecoder mqtt3PubCompDecoder, Mqtt3SubAckDecoder mqtt3SubAckDecoder, Mqtt3UnsubAckDecoder mqtt3UnsubAckDecoder, MqttPingRespDecoder mqttPingRespDecoder) {
        return new Mqtt3ClientMessageDecoders(mqtt3ConnAckDecoder, mqtt3PublishDecoder, mqtt3PubAckDecoder, mqtt3PubRecDecoder, mqtt3PubRelDecoder, mqtt3PubCompDecoder, mqtt3SubAckDecoder, mqtt3UnsubAckDecoder, mqttPingRespDecoder);
    }
}
