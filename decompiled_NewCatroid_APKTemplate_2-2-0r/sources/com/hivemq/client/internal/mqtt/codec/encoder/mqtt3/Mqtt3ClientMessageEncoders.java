package com.hivemq.client.internal.mqtt.codec.encoder.mqtt3;

import com.hivemq.client.internal.mqtt.codec.encoder.MqttMessageEncoders;
import com.hivemq.client.internal.mqtt.codec.encoder.MqttPingReqEncoder;
import com.hivemq.client.mqtt.mqtt3.message.Mqtt3MessageType;
import javax.inject.Inject;
import javax.inject.Singleton;

/* JADX INFO: loaded from: classes10.dex */
@Singleton
public class Mqtt3ClientMessageEncoders extends MqttMessageEncoders {
    @Inject
    Mqtt3ClientMessageEncoders(Mqtt3ConnectEncoder mqtt3ConnectEncoder, Mqtt3PublishEncoder mqtt3PublishEncoder, Mqtt3PubAckEncoder mqtt3PubAckEncoder, Mqtt3PubRecEncoder mqtt3PubRecEncoder, Mqtt3PubRelEncoder mqtt3PubRelEncoder, Mqtt3PubCompEncoder mqtt3PubCompEncoder, Mqtt3SubscribeEncoder mqtt3SubscribeEncoder, Mqtt3UnsubscribeEncoder mqtt3UnsubscribeEncoder, MqttPingReqEncoder mqttPingReqEncoder, Mqtt3DisconnectEncoder mqtt3DisconnectEncoder) {
        this.encoders[Mqtt3MessageType.CONNECT.getCode()] = mqtt3ConnectEncoder;
        this.encoders[Mqtt3MessageType.PUBLISH.getCode()] = mqtt3PublishEncoder;
        this.encoders[Mqtt3MessageType.PUBACK.getCode()] = mqtt3PubAckEncoder;
        this.encoders[Mqtt3MessageType.PUBREC.getCode()] = mqtt3PubRecEncoder;
        this.encoders[Mqtt3MessageType.PUBREL.getCode()] = mqtt3PubRelEncoder;
        this.encoders[Mqtt3MessageType.PUBCOMP.getCode()] = mqtt3PubCompEncoder;
        this.encoders[Mqtt3MessageType.SUBSCRIBE.getCode()] = mqtt3SubscribeEncoder;
        this.encoders[Mqtt3MessageType.UNSUBSCRIBE.getCode()] = mqtt3UnsubscribeEncoder;
        this.encoders[Mqtt3MessageType.PINGREQ.getCode()] = mqttPingReqEncoder;
        this.encoders[Mqtt3MessageType.DISCONNECT.getCode()] = mqtt3DisconnectEncoder;
    }
}
