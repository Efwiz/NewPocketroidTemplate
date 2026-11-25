package com.hivemq.client.internal.mqtt.codec.decoder.mqtt5;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttPingRespDecoder;
import com.hivemq.client.mqtt.mqtt5.message.Mqtt5MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt5ClientMessageDecoders extends MqttMessageDecoders {
    @Inject
    Mqtt5ClientMessageDecoders(Mqtt5ConnAckDecoder mqtt5ConnAckDecoder, Mqtt5PublishDecoder mqtt5PublishDecoder, Mqtt5PubAckDecoder mqtt5PubAckDecoder, Mqtt5PubRecDecoder mqtt5PubRecDecoder, Mqtt5PubRelDecoder mqtt5PubRelDecoder, Mqtt5PubCompDecoder mqtt5PubCompDecoder, Mqtt5SubAckDecoder mqtt5SubAckDecoder, Mqtt5UnsubAckDecoder mqtt5UnsubAckDecoder, MqttPingRespDecoder mqttPingRespDecoder, Mqtt5DisconnectDecoder mqtt5DisconnectDecoder, Mqtt5AuthDecoder mqtt5AuthDecoder) {
        this.decoders[Mqtt5MessageType.CONNACK.getCode()] = mqtt5ConnAckDecoder;
        this.decoders[Mqtt5MessageType.PUBLISH.getCode()] = mqtt5PublishDecoder;
        this.decoders[Mqtt5MessageType.PUBACK.getCode()] = mqtt5PubAckDecoder;
        this.decoders[Mqtt5MessageType.PUBREC.getCode()] = mqtt5PubRecDecoder;
        this.decoders[Mqtt5MessageType.PUBREL.getCode()] = mqtt5PubRelDecoder;
        this.decoders[Mqtt5MessageType.PUBCOMP.getCode()] = mqtt5PubCompDecoder;
        this.decoders[Mqtt5MessageType.SUBACK.getCode()] = mqtt5SubAckDecoder;
        this.decoders[Mqtt5MessageType.UNSUBACK.getCode()] = mqtt5UnsubAckDecoder;
        this.decoders[Mqtt5MessageType.PINGRESP.getCode()] = mqttPingRespDecoder;
        this.decoders[Mqtt5MessageType.DISCONNECT.getCode()] = mqtt5DisconnectDecoder;
        this.decoders[Mqtt5MessageType.AUTH.getCode()] = mqtt5AuthDecoder;
    }
}
