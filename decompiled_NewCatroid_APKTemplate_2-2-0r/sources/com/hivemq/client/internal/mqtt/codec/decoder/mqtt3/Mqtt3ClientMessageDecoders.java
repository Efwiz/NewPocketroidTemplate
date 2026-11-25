package com.hivemq.client.internal.mqtt.codec.decoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.decoder.MqttMessageDecoders;
import com.hivemq.client.internal.mqtt.codec.decoder.MqttPingRespDecoder;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3ClientMessageDecoders extends MqttMessageDecoders {
    @Inject
    Mqtt3ClientMessageDecoders(Mqtt3ConnAckDecoder mqtt3ConnAckDecoder, Mqtt3PublishDecoder mqtt3PublishDecoder, Mqtt3PubAckDecoder mqtt3PubAckDecoder, Mqtt3PubRecDecoder mqtt3PubRecDecoder, Mqtt3PubRelDecoder mqtt3PubRelDecoder, Mqtt3PubCompDecoder mqtt3PubCompDecoder, Mqtt3SubAckDecoder mqtt3SubAckDecoder, Mqtt3UnsubAckDecoder mqtt3UnsubAckDecoder, MqttPingRespDecoder mqttPingRespDecoder) {
        this.decoders[Mqtt3MessageType.CONNACK.getCode()] = mqtt3ConnAckDecoder;
        this.decoders[Mqtt3MessageType.PUBLISH.getCode()] = mqtt3PublishDecoder;
        this.decoders[Mqtt3MessageType.PUBACK.getCode()] = mqtt3PubAckDecoder;
        this.decoders[Mqtt3MessageType.PUBREC.getCode()] = mqtt3PubRecDecoder;
        this.decoders[Mqtt3MessageType.PUBREL.getCode()] = mqtt3PubRelDecoder;
        this.decoders[Mqtt3MessageType.PUBCOMP.getCode()] = mqtt3PubCompDecoder;
        this.decoders[Mqtt3MessageType.SUBACK.getCode()] = mqtt3SubAckDecoder;
        this.decoders[Mqtt3MessageType.UNSUBACK.getCode()] = mqtt3UnsubAckDecoder;
        this.decoders[Mqtt3MessageType.PINGRESP.getCode()] = mqttPingRespDecoder;
    }
}
